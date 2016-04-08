package com.hitachi.chatroom;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ghe on 2016/3/31.
 */
public class ChatRoomActivity extends Activity implements AdapterView.OnItemClickListener{

    private ListView list_chating;
    private List<ChatMessage> datas;
    private ChatingAdapter chatingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_room_list);
        list_chating = (ListView) this.findViewById(R.id.list_chating);
        datas = new ArrayList<ChatMessage>();
        chatingAdapter = new ChatingAdapter(this, datas);
        list_chating.setAdapter(chatingAdapter);
        initChatMessageData();
    }


    private void initChatMessageData() {
        for (int i = 0;i < 29; i++) {
            ChatMessage message = new ChatMessage(i,"User" + i + ":", "Message" + i);
            if (i == 1) {
                message.userName = "Me:";
            }
            datas.add(message);
        }
        chatingAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
