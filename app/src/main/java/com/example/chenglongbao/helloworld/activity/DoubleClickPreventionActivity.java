package com.example.chenglongbao.helloworld.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chenglongbao.helloworld.BaseActivity;
import com.example.chenglongbao.helloworld.NoDoubleClickListener;
import com.example.chenglongbao.helloworld.R;

/**
 * Created by chenglong.bao on 2016/12/14.
 */
public class DoubleClickPreventionActivity extends BaseActivity {
    private Context mCon;
    private TextView view_title;
    private LinearLayout button_back;
    private Button double_click;
    private Button double_click_prevention;
    private TextView count1;
    private TextView count2;
    private int index1 =0;
    private int index2 =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.double_click_prevention_activity);

        initView();
        setListener();
    }
    /**
     * 控件初期化
     */
    private void initView() {
        mCon = this;
        view_title = (TextView)findViewById(R.id.textview_title);
        button_back = (LinearLayout)findViewById(R.id.button_back);
        double_click = (Button)findViewById(R.id.double_click);
        double_click_prevention = (Button)findViewById(R.id.double_click_prevention);
        count1 = (TextView)findViewById(R.id.count1);
        count2 = (TextView)findViewById(R.id.count2);

        view_title.setText(R.string.title_doubleclickprevention);
    }

    /**
     * 页面监听
     */
    private void setListener() {

        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoubleClickPreventionActivity.this
                        , MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        double_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index1++;
                count1.setText(""+index1);

            }
        });

        double_click_prevention.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                index2++;
                count2.setText(""+index2);
            }
        });

    }
}