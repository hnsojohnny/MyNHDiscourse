package com.nhdiscourse.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.nhdiscourse.R;

/**
 * Created by hs-johnny
 * Created on 2018/12/29
 */
public class MCheckBox extends LinearLayout {

    private CheckBox checkBox;
    private TextView tv;
    protected MCheckBox(Context context) {
        super(context);
        View view = inflate(context, R.layout.check_layout, this);
        checkBox = view.findViewById(R.id.text_cb);
        tv = view.findViewById(R.id.text_tv);
    }

    protected MCheckBox(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    protected MCheckBox(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public static class Builder {

        private CheckBoxParames p;
        private MCheckBox checkBox;

        public enum Status {
            //默认不选中
            NO_CHECK,
            //默认选中
            CHECK,
            //强制选中
            MUST_CHECK
        }

        public Builder(Context context) {
            checkBox = new MCheckBox(context);
            p = new CheckBoxParames(context, checkBox);
        }

        public Builder setContent(String content) {
            this.p.mContent = content;
            return this;
        }

        public Builder setStatus(Status status) {
            this.p.mStatus = status;
            return this;
        }

        public Builder setBackgroundDrawable(int drawable) {
            this.p.mBackgroundDrawable = drawable;
            return this;
        }

        public Builder setViewGroup(ViewGroup viewGroup){
            this.p.viewGroup = viewGroup;
            return this;
        }

        public Builder builder(){
            this.p.applyView();
            return this;
        }

        public class CheckBoxParames {

            private Context mContext;
            private String mContent;
            private Status mStatus;
            private ViewGroup viewGroup;
            private int mBackgroundDrawable;
            private MCheckBox mCheckBox;

            public CheckBoxParames(Context mContext, MCheckBox checkBox) {
                this.mContext = mContext;
                this.mCheckBox = checkBox;
            }

            private void applyView() {
                mCheckBox.checkBox.setButtonDrawable(ContextCompat.getDrawable(mContext, mBackgroundDrawable));
                switch (mStatus) {
                    case NO_CHECK:
                        mCheckBox.checkBox.setChecked(false);
                        break;
                    case CHECK:
                        mCheckBox.checkBox.setChecked(true);
                        break;
                    case MUST_CHECK:
                        mCheckBox.checkBox.setClickable(false);
                        break;
                    default: {
                    }
                }
                mCheckBox.tv.setText(mContent);
                viewGroup.addView(checkBox);
            }
        }
    }

}
