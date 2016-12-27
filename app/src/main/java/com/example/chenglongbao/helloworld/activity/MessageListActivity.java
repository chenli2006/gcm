package com.example.chenglongbao.helloworld.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.chenglongbao.helloworld.BaseActivity;
import com.example.chenglongbao.helloworld.R;
import com.example.chenglongbao.helloworld.adapter.MessageAdapter;
import com.example.chenglongbao.helloworld.bean.MessageInfo;
import com.example.chenglongbao.helloworld.util.DbUtility;

import java.util.ArrayList;
import java.util.List;

/**
 * メッセージ表示Activityクラス
 */
public class MessageListActivity extends BaseActivity {

    private TextView view_title;
    private LinearLayout button_back;
    private Context context;
    private MessageInfo messageInfo = new MessageInfo();
    private ListView list_view_message;
    private MessageAdapter adapter;
    private List<MessageInfo> messageInfoList;
    private TextView no_message;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_activity);

        // 画面部品初期化
        initView();
        // 画面の事件
        setListener();
        // DBからデータを取得する
        getMessageData();
        // メッセージなし部品の状態設定
        setTextVisibility();

    }

    /**
     * 部品初期化
     */
    private void initView() {

        context = this;
        // 画面タイトル部品を取得する
        view_title = (TextView) findViewById(R.id.textview_title);
        // 画面タイトルのテキスト内容を設定する
        view_title.setText(getString(R.string.title_message));
        // 画面戻るボタン部品を取得する
        button_back = (LinearLayout) findViewById(R.id.button_back);
        // 画面メッセージなし部品を取得する
        no_message = (TextView) findViewById(R.id.no_message);
        // 画面ListView部品を取得する
        list_view_message = (ListView) findViewById(R.id.list_view_message);
        // メッセージデータを初期化
        messageInfoList = new ArrayList<>();
        // メッセージデータをメッセージ容器に渡す
        adapter = new MessageAdapter(this, messageInfoList);
        // 画面ListViewにメッセージ容器を設定する
        list_view_message.setAdapter(adapter);
    }

    /**
     * 画面閉じる事件
     */
    private void setListener() {
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
    }

    /**
     * DBからメッセージを取得する
     */
    private void getMessageData() {

        List<MessageInfo> messageInfosListData = DbUtility.selectMessageData(context);
        if (messageInfosListData != null) {

            messageInfoList.clear();
            messageInfoList.addAll(messageInfosListData);
            // 画面のデータを更新する。
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * メッセージなし部品の状態設定
     */
    private void setTextVisibility() {

        if (messageInfoList.size() > 0) {

            // 非表示
            no_message.setVisibility(View.GONE);

        } else {

            // 表示
            no_message.setVisibility(View.VISIBLE);

        }
    }
}
