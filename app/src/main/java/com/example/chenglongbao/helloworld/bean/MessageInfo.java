package com.example.chenglongbao.helloworld.bean;

/**
 * メッセージDTOクラス
 */
public class MessageInfo {

    /**
     * シリアルバージョンID
     */
    private static final long serialVersionUID = 1L;

    // テーブル名
    public static final String TABLE_NAME = "message";
    // メッセージタイトル
    public static final String COLUMN_MESSAGE_TITLE = "message_title";
    // メッセージ内容
    public static final String COLUMN_MESSAGE_CONTEXT = "message_context";
    // メッセージの時間
    public static final String COLUMN_MESSAGE_DATE = "message_date";

    // メッセージタイトル
    private String message_title;
    // メッセージ内容
    private String message_context;
    // メッセージの時間
    private String message_date;

    /**
     * メッセージタイトルを取得する
     *
     * @return メッセージタイトル
     */
    public String getMessage_title() {
        return message_title;
    }

    /**
     * メッセージ内容を取得する
     *
     * @return メッセージ内容
     */
    public String getMessage_context() {
        return message_context;
    }

    /**
     * メッセージの時間を取得する
     *
     * @return メッセージの時間
     */
    public String getMessage_date() {
        return message_date;
    }

    /**
     * メッセージの時間を設定する
     *
     * @param message_date
     */
    public void setMessage_date(String message_date) {
        this.message_date = message_date;
    }

    /**
     * メッセージタイトルを設定する
     *
     * @param message_title
     */
    public void setMessage_title(String message_title) {
        this.message_title = message_title;
    }

    /**
     * メッセージ内容を設定する
     *
     * @param message_context
     */
    public void setMessage_context(String message_context) {
        this.message_context = message_context;
    }
}
