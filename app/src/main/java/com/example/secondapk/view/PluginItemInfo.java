package com.example.secondapk.view;

import android.view.View;

/**
 * Created by flyu on 2018/11/15.
 */

public class PluginItemInfo {
    private int id = 0;
    private EPluginItemType type = EPluginItemType.TYPE_NONE;
    private String title = null;
    private boolean value = false;
    private View itemView = null;
    private View hhSettingView = null;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public EPluginItemType getType() {
        return type;
    }

    public void setType(EPluginItemType type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    public View getItemView() {
        return itemView;
    }

    public void setItemView(View itemView) {
        this.itemView = itemView;
    }

    public View getHhSettingView() {
        return hhSettingView;
    }

    public void setHhSettingView(View hhSettingView) {
        this.hhSettingView = hhSettingView;
    }
}
