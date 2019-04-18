package com.example.secondapk.view;

import android.view.View;

/**
 * Created by flyu on 2019/1/5.
 */

public class SwapSetting {
    private View pluginSwapSetingView = null;

    // 扫描时间间隔
    private int swapTime = 40;
    // 扫货最小价格
    private int minSwapPrice = 0;
    // 扫货最大价格
    private int maxSwapPrice = 2000;
    // 上架价格设置
    private int salePrice = 0;
    // 上架数量设置
    private int saleNum = 0;

    public View getPluginSwapSetingView() {
        return pluginSwapSetingView;
    }

    public void setPluginSwapSetingView(View pluginSwapSetingView) {
        this.pluginSwapSetingView = pluginSwapSetingView;
    }

    public int getSwapTime() {
        return swapTime;
    }

    public void setSwapTime(int swapTime) {
        this.swapTime = swapTime;
    }

    public int getMinSwapPrice() {
        return minSwapPrice;
    }

    public void setMinSwapPrice(int minSwapPrice) {
        this.minSwapPrice = minSwapPrice;
    }

    public int getMaxSwapPrice() {
        return maxSwapPrice;
    }

    public void setMaxSwapPrice(int maxSwapPrice) {
        this.maxSwapPrice = maxSwapPrice;
    }

    public int getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(int salePrice) {
        this.salePrice = salePrice;
    }

    public int getSaleNum() {
        return saleNum;
    }

    public void setSaleNum(int saleNum) {
        this.saleNum = saleNum;
    }
}
