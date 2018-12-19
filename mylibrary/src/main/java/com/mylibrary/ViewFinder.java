package com.mylibrary;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;

public class ViewFinder {

    private Activity mActivity;
    private View mView;

    public ViewFinder(Activity mActivity) {
        this.mActivity = mActivity;
    }

    public ViewFinder(View mView) {
        this.mView = mView;
    }

    public void setContentView(int layoutId){
        if(mActivity != null){
            mActivity.setContentView(layoutId);
        }else{
            LayoutInflater.from(mView.getContext()).inflate(layoutId,null);
        }
    }

    public View findViewById(int viewId){
        return mActivity!=null?mActivity.findViewById(viewId):mView.findViewById(viewId);
    }

}
