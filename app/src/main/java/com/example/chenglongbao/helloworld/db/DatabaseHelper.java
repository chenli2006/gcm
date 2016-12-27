package com.example.chenglongbao.helloworld.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.chenglongbao.helloworld.common.Constants;
import com.example.chenglongbao.helloworld.util.DbUtility;

/**
 * DatabaseHelpクラス
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper instance;

    public DatabaseHelper(Context context) {
        super(context, Constants.name, null, Constants.version);
    }

    public static synchronized DatabaseHelper getInstance(Context context) {

        if (instance == null) {
            instance = new DatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // メッセージテーブルを作成する
        DbUtility.createMessageData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void closeDB() {

        if (instance != null) {
            try {
                SQLiteDatabase db = instance.getWritableDatabase();
                db.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            instance = null;
        }
    }

}
