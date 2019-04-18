package com.example.secondapk.plugin;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.example.secondapk.flow.FloatView;
import com.example.secondapk.flow.ResourceMgr;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by flyu on 2018/11/13.
 */

public class PluginMgr {
    //    public static Application sApp = null;
    public static Context sApp = null;
    public static String sWorkDir = null;
    public static Activity activity = null;
    public static List<Activity> activities = new ArrayList<Activity>();
    public Context context;

    public static void init(Context app, String workDir) {
        Log.d( "dddddd", "init: " + workDir + " " + app );
        activity = (Activity) app;
        sApp = activity.getApplicationContext();
        sWorkDir = workDir;
        Log.d( "dddddd", "init:2 " );
        ShowView();
    }

    public static void showtip(String tip) {
        LogMgr.LogToast( sApp.getApplicationContext(), tip );
    }

    public static void ShowView() {
        ResourceMgr.init( sApp.getApplicationContext(), sWorkDir );
//        activities =  getActivitiesByApplication(activity.getApplication());
//        Log.d( "dddddd", "ShowView: " +activities.size());
//        while(activities.size() <= 0) {
//            try {
//                Thread.sleep(1000);
//                Log.d( "dddddd", "ShowView: " +activities.size());
//                activities =  getActivitiesByApplication(activity.getApplication());
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//        for(final Activity tmpActivity : activities)
//        {
//            if(tmpActivity.isTaskRoot()) {
//                new Handler( Looper.getMainLooper()).post( new Runnable() {
//                    @Override
//                    public void run() {
//                        tmpActivity.getWindow().addFlags( WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//                    }
//                });

        FloatView view = new FloatView( activity, sWorkDir );
//                break;
//            }
//        }
    }

    private static List<Activity> getActivitiesByApplication(Application application) {
        List<Activity> list = new ArrayList<>();
        try {
            Class<Application> applicationClass = Application.class;
            Field mLoadedApkField = applicationClass.getDeclaredField( "mLoadedApk" );

            mLoadedApkField.setAccessible( true );
            Object mLoadedApk = mLoadedApkField.get( application );
            Class<?> mLoadedApkClass = mLoadedApk.getClass();
            Field mActivityThreadField = mLoadedApkClass.getDeclaredField( "mActivityThread" );
            mActivityThreadField.setAccessible( true );
            Object mActivityThread = mActivityThreadField.get( mLoadedApk );
            Class<?> mActivityThreadClass = mActivityThread.getClass();
            Field mActivitiesField = mActivityThreadClass.getDeclaredField( "mActivities" );
            mActivitiesField.setAccessible( true );
            Object mActivities = mActivitiesField.get( mActivityThread );
            Log.d( "dddddd", "getActivitiesByApplication: " + " " + mLoadedApkClass + ">>> " + mActivitiesField );
            // 注意这里一定写成Map，低版本这里用的是HashMap，高版本用的是ArrayMap
            if (mActivities instanceof Map) {
                @SuppressWarnings("unchecked")
                Map<Object, Object> arrayMap = (Map<Object, Object>) mActivities;
                for (Map.Entry<Object, Object> entry : arrayMap.entrySet()) {
                    Object value = entry.getValue();
                    Class<?> activityClientRecordClass = value.getClass();
                    Field activityField = activityClientRecordClass.getDeclaredField( "activity" );
                    activityField.setAccessible( true );
                    Object o = activityField.get( value );
                    list.add( (Activity) o );
                }
            }
        } catch (Exception e) {
            Log.d( "dddddd", "getActivitiesByApplication: " );
            e.printStackTrace();
            list = null;
        }

        return list;
    }

//    private static String GetBieMing() {
//        int index = sWorkDir.indexOf("abcdefghijklmnop") + "abcdefghijklmnop".length();
//        return sWorkDir.substring(index, sWorkDir.length() - 1);
//    }

    public static void WriteToService(final String strContent) {
//        final String pkgName = sApp.getPackageName();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    String bieMin = GetBieMing();
//                    Map<String, String> params = new HashMap<String, String>();
//                    params.put("packageName", bieMin);
//                    params.put("data", strContent);
//                    StringBuffer buffer = new StringBuffer();
//
//                    if(params != null && !params.isEmpty()){
//                        for(Map.Entry<String, String> entry : params.entrySet()){
//                            buffer.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), "utf-8")).append("&");
//                        }
//                    }
//                    System.out.println(buffer.toString());
//                    buffer.deleteCharAt(buffer.length()-1);
//                    byte[] mydata = buffer.toString().getBytes();
//
//                    DoPost(mydata);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
    }

    private static void DoPost(byte[] mydata) {
//        try {
//            URL url = new URL("http://47.52.204.166/appdata");
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setConnectTimeout(3000);
//            connection.setDoOutput(true);//表示向服务器写数据
//            //获得上传信息的字节大小以及长度
//            connection.setRequestMethod("POST");
//
//            //是否使用缓存
//            connection.setUseCaches(false);
//            //表示设置请求体的类型是文本类型
//            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//            connection.setRequestProperty("Content-Length", String.valueOf(mydata.length));
//            connection.connect();
//
//            //获得输出流，向服务器输出数据
//            OutputStream outputStream = connection.getOutputStream();
//            outputStream.write(mydata, 0, mydata.length);
//
//            outputStream.flush();
//            //通常叫做内存流，写在内存中的
//            InputStream is = connection.getInputStream();
//            ByteArrayOutputStream os = new ByteArrayOutputStream();
//            byte[] data = new byte[1024];
//            int len = 0;
//            String result = "null";
//            if(is != null) {
//                while ((len = is.read(data)) != -1) {
//                    data.toString();
//
//                    os.write(data, 0, len);
//                }
//                result = new String(os.toByteArray(), "utf-8");
//                os.flush();
//            }
//            os.close();
//
//            outputStream.close();
//            connection.disconnect();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public static native void set_bool_value(int id, boolean bValue);

    public static native void set_hanhua_content(int time, String str);

    public static native void set_swap_setting(int time, int minPrice, int maxPrice, int salePrice, int saleNum);
}