package com.example.chenglongbao.helloworld.util;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.chenglongbao.helloworld.bean.MessageInfo;
import com.example.chenglongbao.helloworld.db.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class DbUtility {

    public static List<MessageInfo> selectMessageData(Context context) {

        //  メッセージを取得する
        String sql = "SELECT " +
                MessageInfo.COLUMN_MESSAGE_TITLE + "," +
                MessageInfo.COLUMN_MESSAGE_DATE + "," +
                MessageInfo.COLUMN_MESSAGE_CONTEXT +
                " FROM " + MessageInfo.TABLE_NAME;

        List<MessageInfo> messageInfoList = new ArrayList<MessageInfo>();
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            MessageInfo messageInfo = createStaffInfo(cursor);
            messageInfoList.add(messageInfo);
        }
        cursor.close();
        db.close();

        return messageInfoList;
    }

    private static MessageInfo createStaffInfo(Cursor cursor) {

        MessageInfo messageInfo = new MessageInfo();

        // メッセージタイトル
        messageInfo.setMessage_title(cursor.getString(cursor.getColumnIndex(messageInfo.COLUMN_MESSAGE_TITLE)));
        // メッセージの時間
        messageInfo.setMessage_date(cursor.getString(cursor.getColumnIndex(messageInfo.COLUMN_MESSAGE_DATE)));
        // メッセージ内容
        messageInfo.setMessage_context(cursor.getString(cursor.getColumnIndex(messageInfo.COLUMN_MESSAGE_CONTEXT)));

        return messageInfo;
    }

    public static void insertMessageData(Context context, List<MessageInfo> messageInfos) {

        SQLiteDatabase db = DatabaseHelper.getInstance(context).getWritableDatabase();
        db.execSQL("DELETE FROM message");

        // DBにメッセージを登録する。
        for (MessageInfo messageInfo : messageInfos) {
            String sql = "INSERT INTO " + MessageInfo.TABLE_NAME + "(" +
                    MessageInfo.COLUMN_MESSAGE_TITLE + "," +
                    MessageInfo.COLUMN_MESSAGE_DATE + "," +
                    MessageInfo.COLUMN_MESSAGE_CONTEXT +
                    ") VALUES (" +
                    "'" + messageInfo.getMessage_title() + "', " +
                    "'" + messageInfo.getMessage_date() + "', " +
                    "'" + messageInfo.getMessage_context() + "') ";
            db.execSQL(sql);
        }
        db.close();
    }

    public static void createMessageData(SQLiteDatabase db) {

        Log.d("info", "create table");

        // メッセージテーブルを作成する
        String sql = "CREATE TABLE IF NOT EXISTS " + MessageInfo.TABLE_NAME + "(id integer primary key autoincrement, " +
                MessageInfo.COLUMN_MESSAGE_TITLE + " char(20)," +
                MessageInfo.COLUMN_MESSAGE_DATE + " char(10)," +
                MessageInfo.COLUMN_MESSAGE_CONTEXT + " varchar(50)" +
                ")";

        Log.d("create table sql = ", "create table sql = " + sql);

        db.execSQL(sql);
    }
}
