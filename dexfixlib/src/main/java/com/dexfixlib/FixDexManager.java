package com.dexfixlib;

import android.content.Context;
import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import dalvik.system.BaseDexClassLoader;

public class FixDexManager {

    private static final String TAG = "FixDexManager";
    private Context mContext;
    private File mDexDir;

    public FixDexManager(Context context) {
        this.mContext = context;
        //获取应用可以访问的dex目录
        this.mDexDir = context.getDir("odex" , Context.MODE_PRIVATE);
    }

    /**
     * 修复dex包
     * @param fixDexPath
     */
    public void fixDex(String fixDexPath) throws Exception{

        //2.获取下载好的补丁的dexElement
        //2.1 移动到系统能够访问的dex目录下
        File srcFile = new File(fixDexPath);
        if(!srcFile.exists()){
            throw new FileNotFoundException(fixDexPath);
        }
        File destFile = new File(mDexDir, srcFile.getName());
        if(destFile.exists()){
            Log.d(TAG, "paht has be loaded");
            return;
        }
        copyFile(srcFile, destFile);
        //2.2classLoader读取fixDex路径
        List<File> fixDexFiles = new ArrayList<>();
        fixDexFiles.add(destFile);
        fixDexFiles(fixDexFiles);
    }

    private void injectDexElement(ClassLoader classLoader, Object dexelement) throws Exception{
        Field pathListField = BaseDexClassLoader.class.getDeclaredField("pathList");
        pathListField.setAccessible(true);
        Object pathList = pathListField.get(classLoader);

        Field dexElementsField = pathList.getClass().getDeclaredField("dexElements");
        dexElementsField.setAccessible(true);

        dexElementsField.set(pathList, dexelement);
    }

    /**
     * 合并数组
     */
    private static Object combineArray(Object arrayLhs, Object arrayRhs){
        Class<?> localClass = arrayLhs.getClass().getComponentType();
        int i = Array.getLength(arrayLhs);
        int j = i + Array.getLength(arrayRhs);
        Object result = Array.newInstance(localClass, j);
        for(int k = 0; k < j; k++){
            if(k < i){
                Array.set(result, k, Array.get(arrayLhs, k));
            }else {
                Array.set(result, k, Array.get(arrayRhs, k-i));
            }
        }
        return result;
    }

    /**
     * 获取dexElement
     * @param classLoader
     * @return
     */
    private Object getDexElementByClassLoader(ClassLoader classLoader) throws Exception{
        //获取pathlist
        Field pathListField = BaseDexClassLoader.class.getDeclaredField("pathList");
        pathListField.setAccessible(true);
        Object pathList = pathListField.get(classLoader);
        //获取dexElements
        Field dexElmentField = pathList.getClass().getDeclaredField("dexElements");
        dexElmentField.setAccessible(true);
        return dexElmentField.get(pathList);
    }

    public static void copyFile(File src, File dest) throws IOException{
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            if(!dest.exists()){
                dest.createNewFile();
            }
            inChannel = new FileInputStream(src).getChannel();
            outChannel = new FileOutputStream(dest).getChannel();
            inChannel.transferTo(0, inChannel.size(), outChannel);
        }finally {
            if(inChannel != null){
                inChannel.close();
            }
            if(outChannel != null){
                outChannel.close();
            }
        }
    }

    public void loadFixDex() throws Exception{
        File[] dexFiles = mDexDir.listFiles();
        ArrayList<File> fixDexFiles = new ArrayList<>();
        for (File dexFile : dexFiles) {
            if(dexFile.getName().endsWith(".dex")){
                fixDexFiles.add(dexFile);
            }
        }
        fixDexFiles(fixDexFiles);
    }

    private void fixDexFiles(List<File> fixDexFiles) throws Exception{
        //1.先获取已经运行的dexElement
        ClassLoader applicantClassLoader = mContext.getClassLoader();
        Object dexelement = getDexElementByClassLoader(applicantClassLoader);
        //3.把补丁的dexElement插到已经运行的dexElement的最前面
        File optimizeDirectory = new File(mDexDir, "odex");
        if(!optimizeDirectory.exists()){
            optimizeDirectory.mkdirs();
        }
        for (File fixDexFile : fixDexFiles) {
            ClassLoader fixDexClassLoader = new BaseDexClassLoader(
                    fixDexFile.getAbsolutePath(),//dex路径
                    optimizeDirectory,//解压路径
                    null,
                    applicantClassLoader);
            Object fixDexElements = getDexElementByClassLoader(fixDexClassLoader);
            dexelement = combineArray(fixDexElements, dexelement);
        }
        //把合并的数组合并到applicantClassLoader中
        injectDexElement(applicantClassLoader, dexelement);
    }
}
