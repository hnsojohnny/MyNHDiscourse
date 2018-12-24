package com.alertdialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import java.lang.ref.WeakReference;

/**
 * Created by hs-johnny
 * Created on 2018/12/20
 */
public class AlertController {

    private Dialog mDialog;
    private Window mWindow;
    private View mView;

    public AlertController( Dialog dialog, Window window) {
        this.mDialog = dialog;
        this.mWindow = window;
    }

    public void setmView(View mView) {
        this.mView = mView;
    }

    public Window getmWindow() {
        return mWindow;
    }

    public void installContent() {
        if(mView != null){
            mDialog.setContentView(mView);
        }
    }

    public static class AlertParams{

        public View view;
        public Context context;
        /**比HashMap要高效*/
        public SparseArray<CharSequence> textArray = new SparseArray<>();
        /**防止内存泄漏添加软引用*/
        public SparseArray<WeakReference<View.OnClickListener>> listenerArray = new SparseArray<>();
        public int themeResId;
        public boolean mCancelable = true;
        public DialogInterface.OnCancelListener mOnCancelListener;
        public DialogInterface.OnDismissListener mOnDismissListener;
        public DialogInterface.OnKeyListener mOnKeyListener;
        public int mGravity = Gravity.CENTER;
        public int viewLayoutId;
        public float widthPercent = 0;

        public AlertParams(Context mContext, int themeResId) {
            this.context = mContext;
            this.themeResId = themeResId;
        }

        @SuppressLint("ResourceType")
        public void apply(AlertController dialog){
            if(view == null){
                view = LayoutInflater.from(context).inflate(viewLayoutId, null);
            }
            if(textArray.size() > 0){
                for(int i = 0;i<textArray.size();i++){
                    ((TextView)view.findViewById(textArray.keyAt(i))).setText(textArray.get(textArray
                    .keyAt(i)));
                }
            }
            if(listenerArray.size() > 0){
                for(int i = 0;i<listenerArray.size();i++){
                    view.findViewById(listenerArray.keyAt(i)).setOnClickListener(
                            listenerArray.get(listenerArray.keyAt(i)).get());
                }
            }
            dialog.setmView(view);
            Window window = dialog.getmWindow();
            window.setGravity(mGravity);
            window.setWindowAnimations(R.style.AlertDialogAnim);
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setMinimumWidth((int) (window.getWindowManager().getDefaultDisplay().getWidth() * widthPercent));
        }

    }

}
