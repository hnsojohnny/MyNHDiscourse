package com.navigationbar;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by hs-johnny
 * Created on 2018/12/26
 */
public abstract class AbsNavigationBar<P extends AbsNavigationBar.Builder.AbsNavigationBarParams> implements INavigationBar {

    private P mParams;
    private View mNavigationView;

    protected AbsNavigationBar(P params) {
        this.mParams = params;
        createAndBindView();
    }

    public P getParams() {
        return mParams;
    }

    protected void setText(int viewID, String text) {
        TextView textView = findViewById(viewID);
        if (!TextUtils.isEmpty(text)) {
            textView.setText(text);
        }
    }

    protected void setOnClickListener(int viewID, View.OnClickListener onClickListener){
        findViewById(viewID).setOnClickListener(onClickListener);
    }

    private <T extends View> T findViewById(int viewId) {
        return (T) mNavigationView.findViewById(viewId);
    }

    private void createAndBindView() {
        if(mParams.viewGroup == null){
            ViewGroup activityRoot = ((Activity)mParams.context).findViewById(android.R.id.content);
            mParams.viewGroup = (ViewGroup) activityRoot.getChildAt(0);
        }
        if(mParams.viewGroup == null){
            return;
        }
        mNavigationView = LayoutInflater.from(mParams.context).inflate(bindLayoutID(),
                mParams.viewGroup, false);
        mParams.viewGroup.addView(mNavigationView, 0);
        applyView();
    }

    public static abstract class Builder {
        AbsNavigationBarParams params;

        public Builder(Context context, ViewGroup parent) {
            params = new AbsNavigationBarParams(context, parent);
        }

        public abstract AbsNavigationBar builder();

        public class AbsNavigationBarParams {

            public Context context;
            public ViewGroup viewGroup;

            public AbsNavigationBarParams(Context context, ViewGroup parent) {
                this.context = context;
                this.viewGroup = parent;
            }
        }
    }
}
