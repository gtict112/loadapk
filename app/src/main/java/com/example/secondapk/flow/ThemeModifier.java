/**
 * Created by jammy on 15/8/10.
 */
package com.example.secondapk.flow;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.os.Build;

import java.lang.reflect.Field;

public class ThemeModifier {

    private static ThemeModifier instance;
    private Resources.Theme mHostTheme;
    private int mThemeRes;
    private Field mThemeField;
    private Object mBaseContextObject;

    @SuppressLint("InlinedApi")
    private ThemeModifier() {
        initResourcesMap();
        if (Build.VERSION.SDK_INT >= 14) {
            mThemeRes = android.R.style.Theme_DeviceDefault;
        } else {
            mThemeRes = android.R.style.Theme;
        }
    }

    public static ThemeModifier getInstance() {
        if (instance == null) {
            synchronized (ThemeModifier.class) {
                if (instance == null) {
                    instance = new ThemeModifier();
                }
            }
        }
        return instance;
    }

    private void initResourcesMap() {
    }

    /**
     * 设置为apk插件的theme，保存host本身的theme
     *
     * @param context
     * @param pluginRes
     */
    public void onViewInflateBegin(Context context, Resources pluginRes) {
        synchronized (this) {
            try {
                if (mHostTheme == null)
                    mHostTheme = context.getTheme();
                Resources.Theme pluginTheme = pluginRes.newTheme();
                //pluginTheme.setTo(mHostTheme);
                try {
                    pluginTheme.applyStyle( mThemeRes, false );
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // 先获取到ContextWrapper的mBase对象
                Field mBaseField = ContextWrapper.class.getDeclaredField( "mBase" );
                mBaseField.setAccessible( true );
                mBaseContextObject = mBaseField.get( context );

                // 再通过mBase去设置mTheme
                if (mBaseContextObject instanceof Context) {
                    mThemeField = mBaseContextObject.getClass().getDeclaredField( "mTheme" );
                    mThemeField.setAccessible( true );
                    mThemeField.set( mBaseContextObject, pluginTheme );
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 把theme设置回host的theme
     */
    public void onViewInflateEnd() {
        synchronized (this) {
            try {
                if (null != mHostTheme && null != mThemeField && null != mBaseContextObject) {
                    mThemeField.setAccessible( true );
                    mThemeField.set( mBaseContextObject, mHostTheme );
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
            //mHostTheme = null;
            mThemeField = null;
            mBaseContextObject = null;
        }
    }

    public Resources.Theme getTheme() {
        return mHostTheme;
    }
}
