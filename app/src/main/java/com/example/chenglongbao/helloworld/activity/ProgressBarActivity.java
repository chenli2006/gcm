package com.example.chenglongbao.helloworld.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.apkfuns.xprogressdialog.XProgressDialog;
import com.example.chenglongbao.helloworld.BaseActivity;
import com.example.chenglongbao.helloworld.R;

/**
 * Created by chenglong.bao on 2016/12/14.
 */
public class ProgressBarActivity  extends BaseActivity {
    private Context context;
    public XProgressDialog dialog1;
    public XProgressDialog dialog2;
    private TextView view_title;
    private Button progressBar;
    private Button progressBar1;
    private Button progressBar2;
    private Button progressBar3;
    private Button progressBar4;
    private LinearLayout back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progressbar_activity);

        initView();
        setListener();
    }

    /**
     * 控件初期化
     */
    private void initView() {
        view_title = (TextView) findViewById(R.id.textview_title);
        progressBar = (Button) findViewById(R.id.progressbar);
        progressBar1 = (Button) findViewById(R.id.progressbar1);
        progressBar2 = (Button) findViewById(R.id.progressbar2);
        progressBar3 = (Button) findViewById(R.id.progressbar3);
        progressBar4 = (Button) findViewById(R.id.progressbar4);
        back = (LinearLayout)findViewById(R.id.button_back);

        context =this;
        view_title.setText(R.string.title_progressbar);
    }

    /**
     * 页面监听
     */
    private void setListener() { 
        // ネットワーク申請
        progressBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressShow(context, R.string.message_synchronous_data);
            }
        });

        // 申請未完了
        progressBar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1 = progressShow(context,R.string.yi);
                dialog2 = progressShow(context,R.string.er);
            }
        });

        // 1回目申請完了
        progressBar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1 = progressShow(context,R.string.yi);
                dialog2 = progressShow(context,R.string.er);
                dialog1.dismiss();
            }
        });

        // 2回目申請完了
        progressBar3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1 = progressShow(context,R.string.yi);
                dialog2 = progressShow(context,R.string.er);
                dialog2.dismiss();
            }
        });

        // 申請全部完了
        progressBar4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1 = progressShow(context,R.string.yi);
                dialog2 = progressShow(context,R.string.er);
                dialog1.dismiss();
                dialog2.dismiss();
            }
        });

        // 戻るButton
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
