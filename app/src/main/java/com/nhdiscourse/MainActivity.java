package com.nhdiscourse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alertdialog.AlertDialog;
import com.dexfixlib.FixDexUtils;
import com.mylibrary.LayoutById;
import com.mylibrary.ViewById;
import com.mylibrary.ViewOnClick;
import com.mylibrary.ViewUtils;
import org.sufficientlysecure.htmltextview.HtmlTextView;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

@LayoutById(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @ViewById(R.id.text_tv)
    private HtmlTextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtils.inject(this);
        initData();
        FixDexUtils.fixSingleDexBug(this);

        new DefaultNavigationBar.Builder(this)
                .setTitle("这是标题")
                .builder();
    }

    private void initData(){
        File file = ExceptionCrashHandler.getInstance().getCrashFile();
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file));
            int leng = 0;
            char[] str = new char[1024];
            String message = "";
            while ((leng = inputStreamReader.read(str)) != -1){
                message = new String(str, 0, leng);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ViewOnClick(R.id.text_tv)
    private void setOnClick(View view){
        switch (view.getId()){
            case R.id.text_tv:
                new AlertDialog.Builder(MainActivity.this, R.style.MyDialog)
                        .setContentView(R.layout.dialog_layout)
                        .setText(R.id.title_tv, "请输入账号")
                        .setText(R.id.centent_tv, "请输入密码")
                        .setOnClickListener(R.id.title_tv, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this, "这是账号", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setOnClickListener(R.id.centent_tv, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this, "这是密码", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .formBottom()
                        .setWidthPercent(1)
                        .show();
                break;
            default:
                break;
        }
    }

}
