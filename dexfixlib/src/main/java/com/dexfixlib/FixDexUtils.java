package com.dexfixlib;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;

public class FixDexUtils {

    public static void fixDexBug(Context context){
        File fixFile = new File(Environment.getExternalStorageDirectory(), "fix.dex");
        if(fixFile.exists()){
            FixDexManager fixDexManager  = new FixDexManager(context);
            try {
                fixDexManager.loadFixDex();
                Toast.makeText(context, "修复成功", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(context, "修复失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static void fixSingleDexBug(Context context){
        File fixFile = new File(Environment.getExternalStorageDirectory(), "fix.dex");
        if(fixFile.exists()){
            FixDexManager fixDexManager  = new FixDexManager(context);
            try {
                fixDexManager.fixDex(fixFile.getAbsolutePath());
                Toast.makeText(context, "修复成功", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(context, "修复失败", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
