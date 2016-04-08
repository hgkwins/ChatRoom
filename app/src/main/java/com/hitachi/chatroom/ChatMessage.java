package com.hitachi.chatroom;

/**
 * Created by ghe on 2016/3/31.
 */
public class ChatMessage {

    int userId;
    String userName;
    String message;

    public ChatMessage(int userId, String userName, String message) {
        this.userId = userId;
        this.userName = userName;
        this.message = message;
    }

    public ChatMessage() {
    }
}
