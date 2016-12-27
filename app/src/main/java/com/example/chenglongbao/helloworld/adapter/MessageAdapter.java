package com.example.chenglongbao.helloworld.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chenglongbao.helloworld.R;
import com.example.chenglongbao.helloworld.bean.MessageInfo;

import java.util.List;

/**
 * メッセージの容器クラス
 */
public class MessageAdapter extends BaseAdapter {

    private Context context;
    private List<MessageInfo> messageInfoList;
    private LayoutInflater noticeContainer;

    public MessageAdapter(Context context, List<MessageInfo> messageInfoList) {

        this.context = context;
        this.messageInfoList = messageInfoList;
        noticeContainer = LayoutInflater.from(context);
    }

    public int getCount() {

        // ListViewのItem件数
        return messageInfoList.size();
    }

    public Object getItem(int position) {

        // ListViewのItem位置
        return messageInfoList.get(position);
    }

    public long getItemId(int position) {

        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        MessageInfo messageInfo = messageInfoList.get(position);
        if (convertView == null) {
            holder = new ViewHolder();

            // 容器のレイアウトを設定する
            convertView = noticeContainer.inflate(R.layout.item_message, null);

            // メッセージアイコン部品を取得する
            holder.message_img = (ImageView) convertView.findViewById(R.id.img_message);
            // メッセージタイトル部品を取得する
            holder.message_title = (TextView) convertView.findViewById(R.id.message_title);
            // メッセージの時間部品を取得する
            holder.message_date = (TextView) convertView.findViewById(R.id.message_date);
            // メッセージ内容部品を取得する
            holder.message_context = (TextView) convertView.findViewById(R.id.message_context);

            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();
        }

        //String date = CommonUtility.formatDate(messageInfo.getMessage_date(), "", "");
        // メッセージのデータを設定する
        holder.message_title.setText(messageInfo.getMessage_title());
        holder.message_date.setText("");
        holder.message_context.setText(messageInfo.getMessage_context());

        return convertView;
    }

    static class ViewHolder {

        TextView message_title;

        ImageView message_img;

        TextView message_date;

        TextView message_context;

    }

}
