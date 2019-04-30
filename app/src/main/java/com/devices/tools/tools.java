package com.devices.tools;

import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class tools {
    public static boolean isNetworkAvailable(Context context) {

        ConnectivityManager manager = (ConnectivityManager) context
                .getApplicationContext().getSystemService(
                        Context.CONNECTIVITY_SERVICE);

        if (manager == null) {
            return false;
        }

        NetworkInfo networkinfo = manager.getActiveNetworkInfo();

        if (networkinfo == null || !networkinfo.isAvailable()) {
            return false;
        }

        return true;
    }

    public static String posthmtl(String url, String content) {

        HttpURLConnection conn = null;
//        Log.d( "dddddd", "post: "+url );
        try {
            URL mURL = new URL(url);
            // 调用URL的openConnection()方法,获取HttpURLConnection对象
            conn = (HttpURLConnection) mURL.openConnection();

            conn.setRequestMethod("POST");// 设置请求方法为post
            conn.setReadTimeout(5000);// 设置读取超时为5秒
            conn.setConnectTimeout(5000);// 设置连接网络超时为10秒
            conn.setRequestProperty("Content-Length", String.valueOf(content.getBytes().length));
            conn.setDoOutput(true);// 设置此方法,允许向服务器输出内容

//            URLConn.setRequestProperty("Content-Length", String.valueOf(data
//                    .getBytes().length));
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(content.getBytes());
            outputStream.flush();
            outputStream.close();

            int responseCode = conn.getResponseCode();// 调用此方法就不必再使用conn.connect()方法
            if (responseCode == 200) {

//                is = conn.getInputStream();
//                String response = getStringFromInputStream(is);
//                Log.d( "dddddd", "post+response： "+response );
                return null;
            } else {
                throw new NetworkErrorException("response status is "+responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();// 关闭连接
            }
        }
        return null;
    }

    public static String getAppjson(Context context ) throws UnsupportedEncodingException {
        AppInfoUtils appInfoUtils = new AppInfoUtils();
        UpdatePhoneInfoBean bean = new UpdatePhoneInfoBean();
        bean.setSerial( Build.SERIAL);// 串口序列号
        bean.setGetBaseband(" ");// get 参数
        bean.setRadioVersion(Build.getRadioVersion());// 固件版本
        bean.setBoard(Build.BOARD);//主板
        bean.setBrand(Build.BRAND);//设备品牌
        bean.setABI(Build.CPU_ABI);//  设备指令集名称 1
        bean.setABI2(Build.CPU_ABI2);//   设备指令集名称 2
        bean.setDevice(Build.DEVICE);//设备驱动名称
        bean.setDisplay(Build.DISPLAY);//设备显示的版本包 固件版本
        bean.setFingerprint(Build.FINGERPRINT);//  指纹 设备的唯一标识。由设备的多个信息拼接合成。
        bean.setNAME(Build.HARDWARE);//设备硬件名称
        bean.setID(Build.ID);//设备版本号
        bean.setManufacture(Build.MANUFACTURER);//设备制造商
        bean.setModel(Build.MODEL);//手机的型号 设备名称
        bean.setProduct(Build.PRODUCT);//设备驱动名称
        bean.setBooltloader(Build.BOOTLOADER);//设备引导程序版本号
        bean.setHost(Build.HOST);//设备主机地址
        bean.setBuild_tags(Build.TAGS);//设备标签
        bean.setShenbei_type(Build.TYPE);//设备版本类型
        bean.setIncrementalincremental(Build.VERSION.INCREMENTAL);//源码控制版本号
        bean.setAndroidVer(Build.VERSION.RELEASE);//系统版本
        bean.setAPI(Build.VERSION.SDK_INT + ""); //系统的API级别 SDK
        bean.setTime(Build.TIME + "");// 固件时间
        bean.setAndroidID( Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID));//  android id
        bean.setDESCRIPTION(" ");//用户的KEY
        // TelephonyManager相关
        bean.setIMEI(appInfoUtils.getIMEI1(context));    // IMEI1
        bean.setIMEI2(appInfoUtils.getIMEI2(context));   // IMEI2
        bean.setMEID(appInfoUtils.getMeid(context)); // MEID
        bean.setLYMAC(appInfoUtils.getBluetoothMac());//蓝牙 MAC
        bean.setWifiMAC(appInfoUtils.getMacAddress(context)); // WIF mac地址
        bean.setWifiName(appInfoUtils.getSSID(context));// 无线路由器名
        bean.setBSSID(appInfoUtils.getBSSID(context));// 无线路由器地址
        bean.setIMSI(appInfoUtils.getIMSI1(context));    // imsi1
        bean.setIMSI2(appInfoUtils.getIMSI2(context));   // imsi2
        bean.setPhoneNumber(appInfoUtils.getLine1Number(context));// 手机号码
        bean.setSimSerial(appInfoUtils.getSimSerialNumber(context));// 手机卡序列号
        bean.setNetworkOperator(appInfoUtils.getNetworkOperator(context));// 网络运营商类型
        bean.setNetworkOperatorName(appInfoUtils.getNetworkOperatorName(context));// 网络类型名
        bean.setSimOperator(appInfoUtils.getSimOperator(context));// 运营商
        bean.setSimOperatorName(appInfoUtils.getSimOperatorName(context));// 运营商名字
        bean.setNetworkCountryIso(appInfoUtils.getNetworkCountryIso(context));// 国家iso代码
        bean.setSimCountryIso(appInfoUtils.getSimCountryIso(context));// 手机卡国家
        bean.setDeviceversion(appInfoUtils.getDeviceSoftwareVersion(context)); // 返回系统版本
        bean.setGetType(appInfoUtils.getNetworkTypeName(context)); // 联网方式 1为WIFI 2为流量
        bean.setNetworkType(appInfoUtils.getNetworkType(context));//      网络类型
        bean.setPhonetype(appInfoUtils.getPhoneType(context));// 手机类型
        bean.setSimState(appInfoUtils.getSimState(context));// 手机卡状态
        bean.setGetIP(" "); // 内网ip(wifl可用)
        // 屏幕相关
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        bean.setDPI(dm.densityDpi + ""); // dpi
        bean.setDensity(dm.density + ""); // density
        bean.setXdpi(dm.xdpi + "");
        bean.setYdpi(dm.ydpi + "");
        bean.setWidth(dm.widthPixels + "");// 宽
        bean.setHeight(dm.heightPixels + ""); // 高
        bean.setScaledDensity(dm.scaledDensity + ""); // 字体缩放比例
        // 显卡信息
        bean.setGLRenderer(" "); // GPU
        bean.setGLVendor(" ");// GPU厂商

        bean.setCellLocationCid(appInfoUtils.getCellLocationCid(context));
        bean.setCellLocationLac(appInfoUtils.getCellLocationLac(context));
        bean.setCellLocationPsc(appInfoUtils.getCellLocationPsc(context));
        bean.setCellLocationNetworkId(appInfoUtils.getCellLocationNetworkId(context));
        bean.setCellLocationSystemId(appInfoUtils.getCellLocationSystemId(context));
        bean.setCellLocationBaseStationId(appInfoUtils.getCellLocationBaseStationId(context));
        bean.setCellLocationBaseStationLatitude(appInfoUtils.getCellLocationBaseStationLatitude(context));
        bean.setCellLocationBaseStationLongitude(appInfoUtils.getCellLocationBaseStationLongitude(context));
        bean.setWifiIpAddress(appInfoUtils.getWifiIpAddress(context));
        bean.setDhcpInfo(appInfoUtils.getDhcpInfo(context));
        bean.setNetworkInfoType(appInfoUtils.getNetworkInfoType(context));
        bean.setNetworInfokSubtype(appInfoUtils.getNetworkInfoSubtype(context));
        bean.setNetworkInfoSubtypeName(appInfoUtils.getNetworkInfoSubtypeName(context));
        bean.setLocaleLanguage(appInfoUtils.getLocaleLanguage());
        bean.setLocaleCountry(appInfoUtils.getLocaleCountry());
        bean.setMaxCpuFreq(appInfoUtils.getMaxCpuFreq());
        bean.setCpuName(appInfoUtils.getCpuName());
        bean.setCpuNum(appInfoUtils.getCpuNum() + "");
        bean.setAdbEnabled(appInfoUtils.getAdbEnabled(context)+"");
        bean.setScreenBrightness(appInfoUtils.getScreenBrightness(context )+"");
        bean.setIsWiredHeadsetOn(appInfoUtils.isWiredHeadsetOn(context )+"");
        bean.setVolume(appInfoUtils.getVolume(context)+"");
        bean.setRingerMode(appInfoUtils.getRingerMode(context)+"");
        bean.setDeviceOrientation(appInfoUtils.getDeviceOrientation(context)+"");
        bean.setTotalMemory(appInfoUtils.getTotalMemory(context )+"");
        bean.setAvailMemory(appInfoUtils.getAvailMemory(context)+"");
        bean.setTotalInternalStorageSize(appInfoUtils.getTotalInternalStorageSize() + "");
        bean.setAvailInternalStorageSize(appInfoUtils.getAvailInternalStorageSize() + "");
        bean.setTotalExternalStorageSize(appInfoUtils.getTotalExternalStorageSize() + "");
        bean.setAvailExternalStorageSize(appInfoUtils.getAvailExternalStorageSize() + "");
        bean.setAvailableProcessors(appInfoUtils.getAvailableProcessors() + "");
        bean.setRunningProcess(appInfoUtils.getRunningProcess(context));
        bean.setAppList(appInfoUtils.getAppList(context));

        String tests = Base64.encodeToString(appInfoUtils.getBuildFileContent().getBytes(),Base64.DEFAULT);
//       bean.setBuildFileInfo(java.net.URLEncoder.encode(Base64.encodeToString(appInfoUtils.getBuildFileContent().getBytes(),Base64.NO_WRAP),"UTF-8"));  // build.prop文件内容
        bean.setBuildFileInfo(java.net.URLEncoder.encode(appInfoUtils.getBuildFileContent(),"UTF-8"));
        Gson gson = new Gson();
        String appjson = gson.toJson(bean);

        return appjson;
    }

    public static String post(String url, String content) {
        HttpURLConnection conn = null;
        BufferedReader reader =null;
        Log.d( "ddddsdss", "run: "+url );
        try {
            URL url1 = new URL(url);
            conn = (HttpURLConnection) url1.openConnection();

            InputStream is = conn.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (conn != null){conn.disconnect();}
            if (reader != null){
                try {
                    reader.close();
                } catch (IOException e) {e.printStackTrace();}
            }
        }
        return null;
    }

    public static boolean ismmAppList(Context context) {
        StringBuilder appPkgNameList = new StringBuilder();
        try {
            List<PackageInfo> installedPackages = context.getApplicationContext().getPackageManager().getInstalledPackages(0);
            if (installedPackages == null || installedPackages.size() <= 0) {
                return false;
            }
            for (PackageInfo packageInfo : installedPackages) {
                if ((packageInfo.packageName.equals( "com.tencent.mm" ))) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getAndroidId (Context context) {
        String ANDROID_ID = Settings.System.getString(context.getContentResolver(), Settings.System.ANDROID_ID);
        Log.d( "dddddgetAndroidId", ANDROID_ID);
        return ANDROID_ID;
    }

    public static String getPackName(Context ctx) {
        return ctx.getPackageName();
    }

    public static String postgameconfig(String url, String content) {

        HttpURLConnection conn = null;
//        Log.d( "dddddd", "post: "+url );
        try {
            URL mURL = new URL(url);
            // 调用URL的openConnection()方法,获取HttpURLConnection对象
            conn = (HttpURLConnection) mURL.openConnection();

            conn.setRequestMethod("POST");// 设置请求方法为post
            conn.setReadTimeout(5000);// 设置读取超时为5秒
            conn.setConnectTimeout(5000);// 设置连接网络超时为10秒
            conn.setRequestProperty("Content-Length", String.valueOf(content.getBytes().length));
            conn.setDoOutput(true);// 设置此方法,允许向服务器输出内容

//            URLConn.setRequestProperty("Content-Length", String.valueOf(data
//                    .getBytes().length));
            Log.d( "dddddd", "post: "+content );
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(content.getBytes());
            outputStream.flush();
            outputStream.close();

            int responseCode = conn.getResponseCode();// 调用此方法就不必再使用conn.connect()方法
            Log.d( "dddddd", "post+response： "+responseCode );
            if (responseCode == 200) {

//                is = conn.getInputStream();
//                String response = getStringFromInputStream(is);
//                Log.d( "dddddd", "post+response： "+response );
                return null;
            } else {
                throw new NetworkErrorException("response status is "+responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();// 关闭连接
            }
        }
        return null;
    }


}
