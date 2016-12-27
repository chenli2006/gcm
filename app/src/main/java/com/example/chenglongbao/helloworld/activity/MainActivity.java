package com.example.chenglongbao.helloworld.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chenglongbao.helloworld.BaseActivity;

import com.example.chenglongbao.helloworld.R;

/**
 * Created by chenglong.bao on 2016/12/14.
 */
public class MainActivity extends BaseActivity {
    private TextView view_title;
    private LinearLayout button_back;
    private Button double_click_prevention;
    private Button progress_bar;
    private Button btn_push;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        initView();
        setListener();
    }

    /**
     * 控件初期化
     */
    private void initView() {
        view_title = (TextView) findViewById(R.id.textview_title);


        button_back = (LinearLayout) findViewById(R.id.button_back);
        double_click_prevention = (Button) findViewById(R.id.double_click_prevention);
        progress_bar = (Button) findViewById(R.id.progress_bar);
        btn_push = (Button) findViewById(R.id.btn_push);

        view_title.setText(R.string.title);
        button_back.setVisibility(View.INVISIBLE);
    }

    /**
     * 页面监听
     */
    private void setListener() {
        double_click_prevention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DoubleClickPreventionActivity.class);
                startActivity(intent);
            }
        });

        progress_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProgressBarActivity.class);
                startActivity(intent);
            }
        });

        btn_push.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PushActivity.class);
                startActivity(intent);
            }
        });

    }
}
