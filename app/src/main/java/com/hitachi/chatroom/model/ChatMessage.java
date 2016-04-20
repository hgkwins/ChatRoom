package com.hitachi.chatroom.model;

import org.jivesoftware.smack.packet.Message;

/**
 * Created by ghe on 2016/3/31.
 */
public class ChatMessage {

    public Message message;
    public String date;

    public ChatMessage(Message message, String date) {
        this.message = message;
        this.date = date;
    }

    public ChatMessage() {
    }
}
