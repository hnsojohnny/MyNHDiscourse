package com.nhdiscourse;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by hs-johnny
 * Created on 2018/12/29
 */
public class CheckBox extends LinearLayout {

    public CheckBox(Context context) {
        super(context);
    }

    public CheckBox(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CheckBox(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initView(Context context){
        View rootView = View.inflate(context, R.layout.check_layout, null);
    }

}
