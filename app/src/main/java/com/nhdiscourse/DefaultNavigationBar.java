package com.nhdiscourse;

import android.content.Context;
import android.view.ViewGroup;

/**
 * Created by hs-johnny
 * Created on 2018/12/26
 */
public class DefaultNavigationBar extends AbsNavigationBar{

    protected DefaultNavigationBar(Builder.AbsNavigationBarParams params) {
        super(params);
    }

    @Override
    public int bindLayoutID() {
        return R.layout.dialog_layout;
    }

    @Override
    public void applyView() {

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

        public class DefaultNavigationParams extends AbsNavigationBar.Builder.AbsNavigationBarParams{

            public DefaultNavigationParams(Context context, ViewGroup parent) {
                super(context, parent);
            }
        }
    }
}
