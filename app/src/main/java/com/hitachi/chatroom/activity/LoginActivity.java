package com.hitachi.chatroom.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hitachi.chatroom.R;
import com.hitachi.chatroom.util.XmppTool;

import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;

public class LoginActivity extends Activity implements View.OnClickListener{

    private Button btn_login, btn_register;
    private LinearLayout layout_login, layout_progress;
    private EditText edit_username, edit_password;

    private final int MSG_LOGIN_LOADING = 1001;
    private final int MSG_LOGIN_ERROR = 1002;

    private Handler mHandle = new Handler(){
        public void handleMessage(android.os.Message msg) {
            switch(msg.what){
                case MSG_LOGIN_LOADING:
                    layout_progress.setVisibility(View.VISIBLE);
                    layout_login.setVisibility(View.GONE);
                    break;
                case MSG_LOGIN_ERROR:
                    layout_progress.setVisibility(View.GONE);
                    layout_login.setVisibility(View.VISIBLE);
                    Toast.makeText(LoginActivity.this, "login failure", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout_login = (LinearLayout)this.findViewById(R.id.layout_login);
        layout_progress = (LinearLayout) this.findViewById(R.id.layout_progress);

        edit_username = (EditText) this.findViewById(R.id.edit_username);
        edit_password = (EditText) this.findViewById(R.id.edit_password);

        btn_login = (Button) this.findViewById(R.id.btn_login);
        btn_register = (Button) this.findViewById(R.id.btn_register);

        btn_login.setOnClickListener(this);
        btn_register.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        final String USERID = this.edit_username.getText().toString();
        final String PWD = this.edit_password.getText().toString();
        switch (v.getId()) {
            case R.id.btn_login:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mHandle.sendEmptyMessage(MSG_LOGIN_LOADING);
                        try {
                            XmppTool.getConnection().login(USERID, PWD);
                            Log.i("XMPPClient", "Logged in as " + XmppTool.getConnection().getUser());
                            // status
                            Presence presence = new Presence(Presence.Type.available);
                            XmppTool.getConnection().sendPacket(presence);
                            Intent intent = new Intent(LoginActivity.this, MainFrameActivity.class);
                            intent.putExtra("USERID", USERID);
                            startActivity(intent);
                            finish();
                        } catch (XMPPException e) {
                            XmppTool.closeConnection();
                            mHandle.sendEmptyMessage(MSG_LOGIN_ERROR);
                        }
                    }
                }).start();
                break;
            case R.id.btn_register:
                break;
        }
    }
}
