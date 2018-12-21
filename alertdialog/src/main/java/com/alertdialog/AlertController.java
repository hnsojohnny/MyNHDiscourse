package com.alertdialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import java.lang.ref.WeakReference;

/**
 * Created by hs-johnny
 * Created on 2018/12/20
 */
public class AlertController {

    private Context mContext;
    private Dialog mDialog;
    private Window mWindow;
    private View mView;
    private int mViewLayoutId;

    public AlertController(Context context, Dialog dialog, Window window) {
        this.mContext = context;
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
        public int viewLayoutId;
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
        public boolean isFull = false;
        public float width = 0;

        public AlertParams(Context mContext, int themeResId) {
            this.context = mContext;
            this.themeResId = themeResId;
        }

        public void apply(AlertController dialog){
            if(view != null){
                dialog.setmView(view);
            }else if(viewLayoutId != 0){
                view = LayoutInflater.from(context).inflate(viewLayoutId, null);
                dialog.setmView(view);
            }
            Window window = dialog.getmWindow();
            window.setGravity(mGravity);
            if(isFull){
                view.setMinimumWidth(window.getWindowManager().getDefaultDisplay().getWidth());
            }else{
                view.setMinimumWidth((int) (window.getWindowManager().getDefaultDisplay().getWidth() * width));
            }
        }
    }
}
