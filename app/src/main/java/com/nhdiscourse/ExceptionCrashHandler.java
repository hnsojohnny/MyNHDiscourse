package com.nhdiscourse;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;

/**
 * @author hs-johnny
 */
public class ExceptionCrashHandler implements Thread.UncaughtExceptionHandler {

    private static final String TAG = "ExceptionCrashHandler";
    private Context mContext;
    private static ExceptionCrashHandler mInstance;
    private Thread.UncaughtExceptionHandler mDefaultExceptionHandler;

    public static ExceptionCrashHandler getInstance(){
        if(mInstance == null){
            //解决多并发问题
            synchronized (ExceptionCrashHandler.class){
                mInstance = new ExceptionCrashHandler();
            }
        }
        return mInstance;
    }

    public void init(Context context){
        this.mContext = context;
        //设置全局的异常类为本类
        Thread.currentThread().setUncaughtExceptionHandler(this);
        mDefaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
    }

    /**
     * 异常处理方法
     * @param t
     * @param e
     */
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        String crashFileName = saveInfoToSd(e);
        cacheCrashFile(crashFileName);
        //让系统处理异常情况
        mDefaultExceptionHandler.uncaughtException(t, e);
    }

    private void cacheCrashFile(String crashFileName) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("crash", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("CRASH_FILE_NAME", crashFileName).commit();
    }

    public File getCrashFile(){
        String crashFileTime = mContext.getSharedPreferences("crash", Context.MODE_PRIVATE).getString("CRASH_FILE_NAME","");
        return new File(crashFileTime);
    }

    private String saveInfoToSd(Throwable e) {
        String fileName = null;
        StringBuffer sb = new StringBuffer();
        for(String key : obtainSimpleInfo(mContext).keySet()){
            sb.append(key).append(" = ").append(obtainSimpleInfo(mContext).get(key)).append("\n");
        }
        sb.append(obtainExceptionInfo(e));
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            File dir = new File(mContext.getFilesDir() + File.separator + "crash"
            + File.separator);
            if(dir.exists()){
                deleteDir(dir);
            }
            if(!dir.exists()){
                dir.mkdir();
            }
            fileName = dir.toString() + File.separator + getAssignTime("yyyy_MM_dd_HH_mm" ) + ".txt";
            try {
                FileOutputStream fos = new FileOutputStream(fileName);
                fos.write(sb.toString().getBytes());
                fos.flush();
                fos.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return fileName;
    }

    private boolean deleteDir(File dir){
        if(dir.isDirectory()){
            File[] childer = dir.listFiles();
            for (File file : childer) {
                file.delete();
            }
        }
        return true;
    }

    private String obtainExceptionInfo(Throwable e){
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        printWriter.close();
        return stringWriter.toString();
    }

    private HashMap<String, String> obtainSimpleInfo(Context context){
        HashMap<String, String> map = new HashMap<>();
        PackageManager manager = context.getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        map.put("versionName:", info.versionName);
        map.put("versionCode:", info.versionCode+"");
        map.put("MODEL:", Build.MODEL);
        map.put("SDK_INT:", Build.VERSION.SDK_INT+"");
        map.put("PRODUCT:", Build.PRODUCT);
        map.put("MOBLE_INFO", getMobileInfo());
        return map;
    }

    public static String getMobileInfo(){
        StringBuffer buffer = new StringBuffer();
        Field[] fields = Build.class.getDeclaredFields();
        for(Field field : fields){
            field.setAccessible(true);
            buffer.append(field.getName()+":");
            try {
                buffer.append(field.get(field.getName())+"\n");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return buffer.toString();
    }

    private String getAssignTime(String dateFormatStr){
        DateFormat dateFormat = new SimpleDateFormat(dateFormatStr);
        long currentTime = System.currentTimeMillis();
        return dateFormat.format(currentTime);
    }
}
