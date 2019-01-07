package com.navigationbar;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by hs-johnny
 * Created on 2018/12/26
 */
public class DefaultNavigationBar extends AbsNavigationBar<DefaultNavigationBar.Builder.DefaultNavigationParams>{

    protected DefaultNavigationBar(DefaultNavigationBar.Builder.DefaultNavigationParams params) {
        super(params);
    }

    @Override
    public int bindLayoutID() {
        return R.layout.title_layout;
    }

    @Override
    public void applyView() {
        setText(R.id.title_tv, getParams().title);
        setOnClickListener(R.id.back_tv, getParams().leftOnlickListener);
    }

    public static class Builder extends AbsNavigationBar.Builder{

        DefaultNavigationParams params;

        public Builder(Context context, ViewGroup parent) {
            super(context, parent);
            params = new DefaultNavigationParams(context, parent);
        }

        public Builder(Context context){
            super(context, null);
            params = new DefaultNavigationParams(context, null);
        }

        @Override
        public DefaultNavigationBar builder() {
            DefaultNavigationBar navigationBar = new DefaultNavigationBar(params);
            return navigationBar;
        }

        public DefaultNavigationBar.Builder setTitle(String title){
            params.title = title;
            return this;
        }
        
        public DefaultNavigationBar.Builder setLeftTxt(String leftTxt){
            params.leftTxt = leftTxt;
            return this;
        }

        public DefaultNavigationBar.Builder setRightTxt(String rightTxt){
            params.rightTxt = rightTxt;
            return this;
        }

        public DefaultNavigationBar.Builder setLeftOnClickListener(View.OnClickListener leftOnlickListener){
            params.leftOnlickListener = leftOnlickListener;
            return this;
        }

        public class DefaultNavigationParams extends AbsNavigationBar.Builder.AbsNavigationBarParams{

            public String title;
            public String leftTxt;
            public String rightTxt;
            public View.OnClickListener leftOnlickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Activity) context).finish();
                }
            };

            public DefaultNavigationParams(Context context, ViewGroup parent) {
                super(context, parent);
            }
        }
    }
}
