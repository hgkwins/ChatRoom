package com.hitachi.chatroom;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hitachi.chatroom.util.TimeRender;
import com.hitachi.chatroom.util.XmppTool;

import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smackx.muc.MultiUserChat;

import java.util.List;

/**
 * Created by ghe on 2016/3/31.
 */
public class ChatingAdapter extends BaseAdapter {

    private List<ChatMessage> datas;
    private Context mContext;
    private MultiUserChat multiUserChat;

    public ChatingAdapter(Context context , List<ChatMessage> datas, MultiUserChat multiUserChat) {
        mContext = context;
        this.datas = datas;
        this.multiUserChat = multiUserChat;
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
            holder.layout_top = (LinearLayout) convertView.findViewById(R.id.layout_top);
            holder.layout_bottom = (LinearLayout) convertView.findViewById(R.id.layout_bottom);
            holder.text_user = (TextView) convertView.findViewById(R.id.text_user);
            holder.text_date = (TextView) convertView.findViewById(R.id.text_date);
            holder.text_message = (TextView) convertView.findViewById(R.id.text_message);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        ChatMessage chatMessage = datas.get(position);
        Message message = chatMessage.message;
        String roomName = multiUserChat.getRoom();
        String username = message.getFrom().replace(roomName + "/", "");
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        if (username != null && username.equals(XmppTool.getConnection().getUser())) {
            layoutParams.gravity = Gravity.RIGHT;
            holder.layout_bottom.setBackgroundResource(R.drawable.outgoing);
        } else  {
            layoutParams.gravity = Gravity.LEFT;
            holder.layout_bottom.setBackgroundResource(R.drawable.incoming);
        }
        holder.layout_top.setLayoutParams(layoutParams);
        holder.layout_bottom.setLayoutParams(layoutParams);
        String[] str = username.split("@");
        holder.text_user.setText(str[0] + ":");
        holder.text_message.setText(message.getBody());
        holder.text_date.setText(TimeRender.getDate());


        return convertView;
    }

    class Holder {
        LinearLayout layout_top;
        LinearLayout layout_bottom;
        TextView text_user;
        TextView text_date;
        TextView text_message;
    }
}
