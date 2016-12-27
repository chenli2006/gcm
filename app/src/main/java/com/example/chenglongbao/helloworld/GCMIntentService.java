package com.example.chenglongbao.helloworld;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.chenglongbao.helloworld.activity.MessageListActivity;
import com.example.chenglongbao.helloworld.bean.MessageInfo;
import com.example.chenglongbao.helloworld.common.Constants;
import com.example.chenglongbao.helloworld.util.CommonUtility;
import com.example.chenglongbao.helloworld.util.DbUtility;
import com.google.android.gcm.GCMBaseIntentService;

import java.util.ArrayList;
import java.util.List;

public class GCMIntentService extends GCMBaseIntentService {

    public GCMIntentService() {
        super(CommonUtility.getInstance().getSenderId());
    }

    @Override
    protected void onRegistered(Context context, String registrationId) {

        Log.i(Constants.TAG_SERVICE, "Device registered: regId = " + registrationId);



        CommonUtility.getInstance().SendMessageToMessageText(context, "From GCM: device successfully registered!");

    }

    @Override
    protected void onUnregistered(Context context, String registrationId) {

        Log.i(Constants.TAG_SERVICE, "Device unregistered");
        CommonUtility.getInstance().SendMessageToMessageText(context, "From GCM: device successfully unregistered!");

    }

    @Override
    protected void onMessage(Context context, Intent intent) {

        Log.i(Constants.TAG_SERVICE, "Received message");
        String message = intent.getStringExtra("message");

        generateNotification(context, message);
    }

    @Override
    public void onError(Context context, String errorId) {

        Log.i(Constants.TAG_SERVICE, "Received error: " + errorId);
        CommonUtility.getInstance().SendMessageToMessageText(context, "From GCM: error (%1$s)." + errorId);
    }

    @Override
    protected boolean onRecoverableError(Context context, String errorId) {

        Log.i(Constants.TAG_SERVICE, "Received recoverable error: " + errorId);
        CommonUtility.getInstance().SendMessageToMessageText(context, "From GCM: recoverable error (%1$s)." + errorId);
        return super.onRecoverableError(context, errorId);
    }

    /**
     * サーバからメッセージを受け取る
     */
    private static void generateNotification(Context context, String message) {

        int icon = R.drawable.ic_stat_gcm;
        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        String title = context.getString(R.string.title_name);
        Intent notificationIntent = new Intent(context, MessageListActivity.class);

        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent intent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
      //  Notification notification = new Notification();
//        Notification.Builder builder = new Notification.Builder(context)
//                .setAutoCancel(true)
//                .setContentTitle(title)
//                .setContentText(message)
//                .setContentIntent(intent)
//                .setSmallIcon(icon)
//                .setWhen(when)
//                .setOngoing(true);
//        notification = builder.getNotification();

//        notification.flags |= Notification.FLAG_AUTO_CANCEL;
//        notificationManager.notify(0, notification);
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(icon)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setContentIntent(intent)
                        .setAutoCancel(true)
                        .setDefaults(Notification.DEFAULT_SOUND)
                        .setOngoing(true)
                        .setWhen(when);

        notificationManager.notify((int) when, builder.build());

        String time = when + "";

        if(message != null && !message.isEmpty()){

            List<MessageInfo> messageInfoList = new ArrayList<>();
            MessageInfo messageInfo = getMessage(title, time, message);
            messageInfoList.add(messageInfo);
            setMessageData(context, messageInfoList);
        }

    }

    private static void setMessageData(Context context, List<MessageInfo> messageInfoList) {

        // DBにメッセージを登録する
        DbUtility.insertMessageData(context, messageInfoList);
    }

    private static MessageInfo getMessage(String title, String when, String message) {

        List<MessageInfo> messageInfoList = new ArrayList<>();
        MessageInfo messageInfo = new MessageInfo();
        messageInfo.setMessage_title(title);
        messageInfo.setMessage_date(when + "");
        messageInfo.setMessage_context(message);
        messageInfoList.add(messageInfo);

        return messageInfo;
    }
}

	
	
	