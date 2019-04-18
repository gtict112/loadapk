/**
 * Created by white on 15-8-11.
 */
package com.example.secondapk.flow;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.secondapk.plugin.LogMgr;

import java.lang.reflect.Method;

public class ResourceMgr {
    private static Context sContext = null;
    private static Resources sResource = null;
    private static String sWorkDir = null;
    private static AssetManager sAssetMgr = null;

    public static void init(Context context, String workDir) {
        sContext = context;
        sWorkDir = workDir;
        InitAssetManager();
    }

    private static void InitAssetManager() {
        try {
            sAssetMgr = AssetManager.class.newInstance();
            Method addAssetPathMethod = sAssetMgr.getClass().getDeclaredMethod( "addAssetPath", String.class );
            addAssetPathMethod.invoke( sAssetMgr, sWorkDir + "plugin_game.apk" );

            Resources superResources = sContext.getResources();
            sResource = new Resources( sAssetMgr, superResources.getDisplayMetrics(), superResources.getConfiguration() );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 兼容在apk中加载layout不能解析@drawable，@color等资源引用问题
     */
    public static View inflate(int layoutId, ViewGroup viewGroup) {
        View retView = null;
        if (sContext == null || sResource == null || layoutId <= 0) {
            LogMgr.LogError( "View inflate return 1 sContext = " + sContext + " sResource = " + sResource );
            return null;
        }
        synchronized (ResourceMgr.class) {
            ThemeModifier modifier = ThemeModifier.getInstance();
            modifier.onViewInflateBegin( sContext, sResource );
            retView = LayoutInflater.from( sContext ).inflate( sResource.getLayout( layoutId ), viewGroup );
            modifier.onViewInflateEnd();
        }

        return retView;
    }

    public static Drawable FindResourceById(int resId) {
        return sResource.getDrawable( resId );
    }
}
