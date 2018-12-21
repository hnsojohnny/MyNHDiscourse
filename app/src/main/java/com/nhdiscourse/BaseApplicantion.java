package com.nhdiscourse;

import android.app.Application;

import com.dexfixlib.FixDexUtils;

public class BaseApplicantion extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        ExceptionCrashHandler.getInstance().init(this);
        FixDexUtils.fixDexBug(this);
    }
}
