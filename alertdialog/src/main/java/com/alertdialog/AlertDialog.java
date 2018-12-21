package com.alertdialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;

/**
 * Created by hs-johnny
 * Created on 2018/12/20
 */
public class AlertDialog extends Dialog{

    private AlertController alertController;

    protected AlertDialog( Context context, int themeResId) {
        super(context, themeResId);
        alertController = new AlertController(getContext(), this, getWindow());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        alertController.installContent();
    }

    public static class Builder {

        private AlertController.AlertParams p;

        public Builder(Context context, int themeResId) {
            p = new AlertController.AlertParams(context, themeResId);
        }

        public Builder setContentView(int layoutId){
            this.p.view = null;
            this.p.viewLayoutId = layoutId;
            return this;
        }

        public Builder setContentView(View contentView){
            this.p.viewLayoutId = 0;
            this.p.view = contentView;
            return this;
        }

        public Builder setText(int viewId, CharSequence text){
            this.p.textArray.put(viewId, text);
            return this;
        }

        public Builder setOnClickListener(int viewId, View.OnClickListener listener){
            this.p.listenerArray.put(viewId, new WeakReference<>(listener));
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            this.p.mCancelable = cancelable;
            return this;
        }

        public Builder setOnCancelListener(OnCancelListener onCancelListener) {
            this.p.mOnCancelListener = onCancelListener;
            return this;
        }

        public Builder setOnDismissListener(OnDismissListener onDismissListener) {
            this.p.mOnDismissListener = onDismissListener;
            return this;
        }

        public Builder setOnKeyListener(OnKeyListener onKeyListener) {
            this.p.mOnKeyListener = onKeyListener;
            return this;
        }

        public Builder fullWidth(boolean isFull){
            this.p.isFull = isFull;
            return this;
        }

        public Builder setWidth(float width){
            this.p.width = width;
            return this;
        }

        public Builder formBottom(){
            this.p.mGravity = Gravity.BOTTOM;
            return this;
        }

        public AlertDialog create(){
            AlertDialog dialog = new AlertDialog(this.p.context, this.p.themeResId);
            this.p.apply(dialog.alertController);
            dialog.setCancelable(this.p.mCancelable);
            if (this.p.mCancelable) {
                dialog.setCanceledOnTouchOutside(true);
            }

            dialog.setOnCancelListener(this.p.mOnCancelListener);
            dialog.setOnDismissListener(this.p.mOnDismissListener);
            if (this.p.mOnKeyListener != null) {
                dialog.setOnKeyListener(this.p.mOnKeyListener);
            }

            return dialog;
        }

        public AlertDialog show(){
            AlertDialog dialog = this.create();
            dialog.show();
            return dialog;
        }
    }
}
