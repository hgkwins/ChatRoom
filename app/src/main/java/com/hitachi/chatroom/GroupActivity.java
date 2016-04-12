package com.hitachi.chatroom;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hitachi.chatroom.util.XmppTool;

import org.jivesoftware.smackx.muc.MultiUserChat;

/**
 * Created by ghe on 2016/4/12.
 */
public class GroupActivity extends Activity implements View.OnClickListener{

    private Button btn_add_group, btn_join_group;
    private EditText edit_group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        btn_add_group = (Button) this.findViewById(R.id.btn_add_group);
        btn_join_group = (Button) this.findViewById(R.id.btn_join_group);
        edit_group = (EditText) this.findViewById(R.id.edit_group);

        btn_add_group.setOnClickListener(this);
        btn_join_group.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String roomName = edit_group.getText().toString();
        switch (v.getId()) {
            case R.id.btn_add_group:
                MultiUserChat createMultiUserChat = XmppTool.createRoom(XmppTool.getConnection().getUser(), roomName, "");
                Log.i("cdc hitachi", "createMultiUserChat = " + createMultiUserChat);
                break;
            case R.id.btn_join_group:
                Intent intent = new Intent(this, ChatRoomActivity.class);
                intent.putExtra("roomName", roomName);
                startActivity(intent);
                finish();
                break;
        }
    }
}
