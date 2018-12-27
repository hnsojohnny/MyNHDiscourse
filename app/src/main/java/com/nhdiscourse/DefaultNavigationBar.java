package com.nhdiscourse;

import android.content.Context;
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
    }

    public static class Builder extends AbsNavigationBar.Builder{

        DefaultNavigationParams params;

        public Builder(Context context, ViewGroup parent) {
            super(context, parent);
            params = new DefaultNavigationParams(context, parent);
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

        public class DefaultNavigationParams extends AbsNavigationBar.Builder.AbsNavigationBarParams{

            public String title;
            public String leftTxt;
            public String rightTxt;

            public DefaultNavigationParams(Context context, ViewGroup parent) {
                super(context, parent);
            }
        }
    }
}
