package com.example.chenglongbao.helloworld.util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CommonUtility {

    // ServiceUrl
    private static final String SERVICE_URL = "http://192.168.253.1:8080/ShinkinBank-web/token/updateTokenInfo";
    //SenderId
    private static final String SENDER_ID = "661535240599";

    private static final String SEND_BROAD_ACTION = "textchangemessage";

    private static final String INTENT_MESSAGE = "com.example.chenglongbao.helloworld.INTENTMESSAGE";
    // RegistrationId
    public static final String REG_ID = "regId";

    private static CommonUtility instance = null;

    public static CommonUtility getInstance() {

        if (instance == null) {
            synchronized (CommonUtility.class) {
                if (instance == null) {
                    instance = new CommonUtility();
                }
            }
        }
        return instance;
    }

    /**
     * INTENT_MESSAGEを取得する
     *
     * @return INTENT_MESSAGE
     */
    public String getIntentMessage() {
        return INTENT_MESSAGE;
    }

    /**
     * SEND_BROAD_ACTIONを取得する
     *
     * @return SEND_BROAD_ACTION
     */
    public String getSendBroadAction() {
        return SEND_BROAD_ACTION;
    }

    /**
     * SERVICE_URLを取得する
     *
     * @return SERVICE_URL
     */
    public String getServiceUrl() {
        return SERVICE_URL;
    }

    /**
     * SENDER_IDを取得する
     *
     * @return SENDER_ID
     */
    public String getSenderId() {
        return SENDER_ID;
    }

    public void SendMessageToMessageText(Context context, String message) {
        Intent intent = new Intent(SEND_BROAD_ACTION);
        intent.putExtra(INTENT_MESSAGE, message);
        context.sendBroadcast(intent);
    }

    /**
     * RegistrationIdをSharedPreferencesに保存する
     *
     * @param regid
     */
    public static void saveStaffVer(Context context, String regid) {

        SharedPreferences sharedPreferences = context.getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(CommonUtility.REG_ID, regid);
        editor.commit();
    }

    /**
     * SharedPreferencesからRegistrationIdを取得する
     */
    public static String getStaffVer(Context context) {

        SharedPreferences sharedPreferences = context.getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString(CommonUtility.REG_ID, "");
    }

    /**
     * 日期フォーマット化
     *
     * @param strDate        変更前日期
     * @param fromDateFormat 変更前フォーマット
     * @param toDateFormat   変更後フォーマット
     * @return 変更後日期
     */
    public static String formatDate(String strDate, String fromDateFormat,
                                    String toDateFormat) {
        String formatDate = "";
        SimpleDateFormat fromFormat = new SimpleDateFormat(fromDateFormat,
                Locale.getDefault());
        SimpleDateFormat toFormat = new SimpleDateFormat(toDateFormat,
                Locale.getDefault());
        Date date = null;
        try {
            date = fromFormat.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        formatDate = toFormat.format(date);
        return formatDate;
    }
}
