package com.example.secondapk.view;

import android.view.View;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by flyu on 2018/11/15.
 */

public class PluginInfo {
    private int width = 0;
    private int height = 0;
    private int hanhuaCount = 0;
    private View pluginView = null;
    private View pluginHHSetingView = null;
    private List<PluginItemInfo> itemList = new ArrayList<PluginItemInfo>();

    private PluginChatInfo chatInfo = new PluginChatInfo();
    private SwapSetting swapSetting = new SwapSetting();

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHanhuaCount() {
        return hanhuaCount;
    }

    public void setHanhuaCount(int hanhuaCount) {
        this.hanhuaCount = hanhuaCount;
    }

    public List<PluginItemInfo> getItemList() {
        return itemList;
    }

    public void AddPluginItem(PluginItemInfo itemInfo) {
        itemList.add( itemInfo );
    }


    public View getPluginView() {
        return pluginView;
    }

    public void setPluginView(View pluginView) {
        this.pluginView = pluginView;
    }

    public View getPluginHHSetingView() {
        return pluginHHSetingView;
    }

    public void setPluginHHSetingView(View pluginHHSetingView) {
        this.pluginHHSetingView = pluginHHSetingView;
    }

    public SwapSetting getSwapSetting() {
        return swapSetting;
    }

    public String getPluginChatContent() {
        return chatInfo.getChatContent();
    }

    public void setPluginChatContent(String strContent) {
        chatInfo.setChatContent( strContent );
    }

    public int getChatIntervalTime() {
        return chatInfo.getIntervalTime();
    }

    public void setChatIntervalTime(int time) {
        chatInfo.setIntervalTime( time );
    }
}
