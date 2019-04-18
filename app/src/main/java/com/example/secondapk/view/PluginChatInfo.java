package com.example.secondapk.view;

/**
 * Created by flyu on 2018/11/16.
 */

public class PluginChatInfo {
    private int intervalTime = 50;

    private String chatContent = "";

    public String getChatContent() {
        return chatContent;
    }

    public void setChatContent(String chatContent) {
        this.chatContent = chatContent;
    }

    public int getIntervalTime() {
        return intervalTime;
    }

    public void setIntervalTime(int intervalTime) {
        this.intervalTime = intervalTime;
    }
}
