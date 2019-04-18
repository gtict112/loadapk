package com.example.secondapk.plugin;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by flyu on 2018/11/13.
 */

public class LogMgr {
    public static String tag = "INJECT";

    public static void LogInfo(String msg) {
        Log.i( tag, msg );
    }

    public static void LogError(String msg) {
        Log.e( tag, msg );
    }

    public static void LogToast(final Context context, final String str) {
        new Handler( Looper.getMainLooper() ).post( new Runnable() {
            @Override
            public void run() {
                Toast.makeText( context, str, Toast.LENGTH_SHORT ).show();
            }
        } );
    }
}
