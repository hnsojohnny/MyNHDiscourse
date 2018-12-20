package com.alertdialog;

import android.app.Dialog;
import android.content.Context;

/**
 * Created by hs-johnny
 * Created on 2018/12/20
 */
public class AlertDialog extends Dialog{
    private AlertController alertController;
    protected AlertDialog( Context context) {
        super(context);
        alertController = new AlertController(getContext(), this, getWindow());
    }

    protected AlertDialog( Context context, int themeResId) {
        super(context, themeResId);

    }

    public static class Builder{

    }
}
