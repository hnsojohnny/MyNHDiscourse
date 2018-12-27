package com.nhdiscourse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by hs-johnny
 * Created on 2018/12/26
 */
public abstract class AbsNavigationBar implements INavigationBar{

    private  Builder.AbsNavigationBarParams mParams;

    protected AbsNavigationBar(Builder.AbsNavigationBarParams params){
        this.mParams = params;
        createAndBindView();
    }

    private void createAndBindView() {
        View titleView = LayoutInflater.from(mParams.context).inflate(bindLayoutID(),
                mParams.viewGroup, false);
        mParams.viewGroup.addView(titleView, 0);
    }

    public static abstract class Builder{
        AbsNavigationBarParams params;
        public Builder(Context context, ViewGroup parent) {
            params = new AbsNavigationBarParams(context, parent);
        }

        public abstract AbsNavigationBar builder();

        public class AbsNavigationBarParams{

            public Context context;
            public ViewGroup viewGroup;

            public AbsNavigationBarParams(Context context, ViewGroup parent) {
                this.context = context;
                this.viewGroup = parent;
            }
        }
    }
}
