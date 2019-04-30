package com.devices.tools;

import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;

import java.util.List;

public class QLApplication extends Application {
    private static QLApplication mApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
    }

    public static QLApplication getApplication() {
        synchronized (QLApplication.class) {
            if (mApplication == null) {
                mApplication = new QLApplication();
            }
        }
        return mApplication;
    }

    private  boolean isTopActivity()
    {
        boolean isTop = false;
        String packageName = "com.example.kwing.wxsdktest";
        if (Build.VERSION.SDK_INT <=Build.VERSION_CODES.KITKAT)
        {
            ActivityManager am = (ActivityManager)getSystemService(ACTIVITY_SERVICE);
            ComponentName cn = am.getRunningTasks(1).get(0).topActivity;

            if (cn.getClassName().contains("com.example.kwing.wxsdktest.Main2Activity"))
            {
                isTop = true;
            }

            return isTop;
        }
        else
        {
            ActivityManager activityManager = (ActivityManager) getSystemService( Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> tasksInfo = activityManager.getRunningTasks(1);
            if (tasksInfo.size() > 0) {
                if (packageName.equals(tasksInfo.get(0).topActivity.getPackageName())) {
                    return true;
                }
            }
            return false;
        }
    }
}
