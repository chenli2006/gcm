package com.example.chenglongbao.helloworld.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chenglongbao.helloworld.common.CheckGoogleService;
import com.example.chenglongbao.helloworld.R;
import com.example.chenglongbao.helloworld.common.SaveRegIdThread;

import com.example.chenglongbao.helloworld.common.Constants;
import com.example.chenglongbao.helloworld.util.CommonUtility;
import com.google.android.gcm.GCMRegistrar;

/**
 * プッシュ通知画面Activityクラス
 */
public class PushActivity extends Activity {

    private TextView view_title;
    private LinearLayout button_back;
    private Context context;
    private Button btn_message;
    private Intent intent;
    private SaveRegIdThread saveRegIdThread;
    private boolean supperGCM = false;
    private TextView message_text;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.push_activity);

        // 画面部品の初期化
        initView();
        // 画面部品の事件
        setListener();

        CheckGoogleService.mContext = getApplicationContext();

        // gcm環境判断を実施する
        if (CheckGoogleService.checkDevice()) {

            if (CheckGoogleService.checkManifest()) {
                supperGCM = true;
            }
        }
        if (supperGCM) {

            // RegistrationId登記を実施する
            doRegister();
        }
    }

    /**
     * 部品の初期化
     */
    private void initView() {

        context = this;
        intent = new Intent();
        // 画面タイトル部品を取得する
        view_title = (TextView) findViewById(R.id.textview_title);
        // 画面タイトルの内容を設定する
        view_title.setText(getString(R.string.title_push));
        // 画面戻るボタン部品を取得する
        button_back = (LinearLayout) findViewById(R.id.button_back);
        // 画面メッセージ一覧ボタン部品を取得する
        btn_message = (Button) findViewById(R.id.btn_message);
        // 画面進度テキスト部品を取得する
        message_text = (TextView) findViewById(R.id.message_text);

    }
    /**
     * 画面部品の事件
     */
    private void setListener() {

        // 画面戻るボタンの事件、画面閉じる
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        // メッセージ一覧画面遷移の事件
        btn_message.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                intent = new Intent(PushActivity.this
                        , MessageListActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * RegistrationId登記を実施
     */
    private void doRegister() {

        // ブロードキャストを登記する
        registerReceiver(mGcmConnectionStatusReceiver, new IntentFilter(CommonUtility.getInstance().getSendBroadAction()));
        // RegistrationIdを取得する
        String regId = GCMRegistrar.getRegistrationId(this);

        Log.v(Constants.TAG_PUSH, Constants.REGID + regId);

        if (regId.equals("")) {
         // RegistrationIdを登記する
            GCMRegistrar.register(this, CommonUtility.getInstance().getSenderId());

        } else {
            // 判断RegistrationIdをサーバ側保存するかとか
            if (GCMRegistrar.isRegisteredOnServer(this)) {
                // 保存する場合、メッセージを待ち
                message_text.setText("Device is already registered on server." + "\n");

            } else {
                // 保存しない場合、保存する
                saveRegIdThread = new SaveRegIdThread(this);
                // サーバ側にRegistrationIdを保存する
                saveRegIdThread.execute(regId);
            }
        }
    }

    /**
     * メッセージを取得する
     */
    private final BroadcastReceiver mGcmConnectionStatusReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // 処理の進度状況
            String message = intent.getExtras().getString(CommonUtility.getInstance().getIntentMessage());
            if(message == null){
                message = "No message";
            }
            message_text.setText(message_text.getText().toString() + message + "\n");
            Log.v(Constants.TAG_PUSH, message);

        }
    };

    /**
     * すべてインスタンスを取消す
     */
    @Override
    protected void onDestroy() {

        if (supperGCM) {
            if (saveRegIdThread != null) {
                saveRegIdThread.cancel(true);
            }
            unregisterReceiver(mGcmConnectionStatusReceiver);
            GCMRegistrar.onDestroy(this);
        }
        super.onDestroy();
    }

}
