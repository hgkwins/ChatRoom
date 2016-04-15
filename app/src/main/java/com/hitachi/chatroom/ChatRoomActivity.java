package com.hitachi.chatroom;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.hitachi.chatroom.util.TimeRender;
import com.hitachi.chatroom.util.XmppTool;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterGroup;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smackx.Form;
import org.jivesoftware.smackx.FormField;
import org.jivesoftware.smackx.muc.DiscussionHistory;
import org.jivesoftware.smackx.muc.MultiUserChat;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ghe on 2016/3/31.
 */
public class ChatRoomActivity extends Activity implements AdapterView.OnItemClickListener, View.OnClickListener , PacketListener {

    private ListView list_chating;
    private List<ChatMessage> datas;
    private ChatingAdapter chatingAdapter;
    private Button btn_send;
    private EditText edit_message;
    MultiUserChat multiUserChat;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            chatingAdapter.notifyDataSetChanged();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_room_list);
        String roomName = getIntent().getStringExtra("roomName");
        multiUserChat = XmppTool.joinMultiUserChat(XmppTool.getConnection().getUser(), roomName, "");
        Log.i("cdc hitachi", "joinMultiUserChat1 = " + multiUserChat);
        if (multiUserChat == null) {
            Toast.makeText(this, "加入会议失败", Toast.LENGTH_LONG).show();
            XmppTool.closeConnection();
            finish();
            return;
        }
        Log.i("hitachi", "multiUserChat = " + multiUserChat);
        multiUserChat.addMessageListener(this);
        list_chating = (ListView) this.findViewById(R.id.list_chating);
        btn_send = (Button) findViewById(R.id.btn_send);
        btn_send.setOnClickListener(this);
        edit_message = (EditText) this.findViewById(R.id.edit_message);
        datas = new ArrayList<ChatMessage>();
        chatingAdapter = new ChatingAdapter(this, datas, multiUserChat);
        list_chating.setAdapter(chatingAdapter);
        Roster roster = XmppTool.getConnection().getRoster();
        List<RosterGroup> tempDatas = XmppTool.getGroups(roster);

        boolean addFlag = XmppTool.addUser(roster, XmppTool.getConnection().getUser(),"kk");
        Log.i("tempDatas", "tempDatas = " + tempDatas + ", addFlag = " + addFlag);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send:
                String msg = edit_message.getText().toString();
                if (multiUserChat != null) {
                    try {
                        if (msg != null && !msg.trim().equals("")) {
                            multiUserChat.sendMessage(msg);
                        }
                    } catch (XMPPException e) {
                        e.printStackTrace();
                    }
                }
                edit_message.setText("");
//                if(msg.length() > 0){
//                    listMsg.add(new Msg(pUSERID, msg, TimeRender.getDate(), "OUT"));
//                    adapter.notifyDataSetChanged();
//                    try {
//                        newchat.sendMessage(msg);
//                    } catch (XMPPException e) {
//                        e.printStackTrace();
//                    }
//                }
                break;
        }
    }

    @Override
    public void processPacket(Packet packet) {
        Message message = (Message) packet;
        String body = message.getBody();
        datas.add(new ChatMessage(message, TimeRender.getDate()));
        Log.i("hitachi", "messagee body = " + body);
        mHandler.sendEmptyMessage(0);
//        list_chating.smoothScrollToPosition(list_chating.getCount() - 1);
    }
}
