package com.hitachi.chatroom;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ghe on 2016/3/31.
 */
public class ChatingAdapter extends BaseAdapter {

    private List<ChatMessage> datas;
    private Context mContext;

    public ChatingAdapter(Context context , List<ChatMessage> datas) {
        this.datas = datas;
        mContext = context;
    }

    @Override
    public int getCount() {
        if (datas != null) {
            return datas.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            holder = new Holder();
            convertView = View.inflate(mContext , R.layout.list_item_chating , null);
            holder.layout_message = (LinearLayout) convertView.findViewById(R.id.layout_message);
            holder.text_user = (TextView) convertView.findViewById(R.id.text_user);
            holder.text_message = (TextView) convertView.findViewById(R.id.text_message);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        ChatMessage chatMessage = datas.get(position);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        if (chatMessage.userId == 1) {
            layoutParams.gravity = Gravity.RIGHT;
            holder.layout_message.setBackgroundColor(Color.GRAY);
        } else  {
            layoutParams.gravity = Gravity.LEFT;
            holder.layout_message.setBackgroundColor(Color.parseColor("#ff4081"));
        }
        holder.layout_message.setLayoutParams(layoutParams);
        holder.text_user.setText(chatMessage.userName);
        holder.text_message.setText(chatMessage.message);


        return convertView;
    }

    class Holder {
        LinearLayout layout_message;
        TextView text_user;
        TextView text_message;
    }
}
