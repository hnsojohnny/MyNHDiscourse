package com.alertdialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;

/**
 * Created by hs-johnny
 * Created on 2018/12/20
 */
public class AlertController {
    private View mView;
    private int mViewLayoutId;

    public AlertController(Context context, Dialog dialog, Window window) {

    }

    public void setmView(View mView) {
        this.mView = mView;
    }

    public void setmViewLayoutId(int mViewLayoutId) {
        this.mViewLayoutId = mViewLayoutId;
    }

    public static class AlertParams{
        private View mView;
        private int mViewLayoutId;
        public void apply(AlertController dialog){
            if(mView != null){
                dialog.setmView(mView);
            }else if(mViewLayoutId != 0){
                dialog.setmViewLayoutId(mViewLayoutId);
            }
        }
    }
}
