package com.devices.tools;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.bluetooth.BluetoothAdapter;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings;
import android.telephony.CellLocation;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;


import com.example.secondapk.BuildConfig;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import static android.text.TextUtils.isEmpty;

/**
 * @author shihuajian
 * @depict 程序信息工具类
 * @since 9/30/16 3:51 PM
 */

public class AppInfoUtils {

    private static final String TAG = AppInfoUtils.class.getSimpleName();

    public static final long ACTION_PLAY_FROM_MEDIA_ID = 1 << 10;

    public String printDeviceInfo(Context ctx) {

        AppInfoUtils appInfoUtils = new AppInfoUtils();
        UpdatePhoneInfoBean bean = new UpdatePhoneInfoBean();
        // Build相关
        bean.setSerial(Build.SERIAL);// 串口序列号
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
        bean.setAndroidID(Settings.Secure.getString(QLApplication.getApplication().getContentResolver(), Settings.Secure.ANDROID_ID));//  android id
        bean.setDESCRIPTION(" ");//用户的KEY
        // TelephonyManager相关
        bean.setIMEI(appInfoUtils.getIMEI1(QLApplication.getApplication()));    // IMEI1
        bean.setIMEI2(appInfoUtils.getIMEI2(QLApplication.getApplication()));   // IMEI2
        bean.setMEID(appInfoUtils.getMeid(QLApplication.getApplication())); // MEID
        bean.setLYMAC(appInfoUtils.getBluetoothMac());//蓝牙 MAC
        bean.setWifiMAC(appInfoUtils.getMacAddress(QLApplication.getApplication())); // WIF mac地址
        bean.setWifiName(appInfoUtils.getSSID(QLApplication.getApplication()));// 无线路由器名
        bean.setBSSID(appInfoUtils.getBSSID(QLApplication.getApplication()));// 无线路由器地址
        bean.setIMSI(appInfoUtils.getIMSI1(QLApplication.getApplication()));    // imsi1
        bean.setIMSI2(appInfoUtils.getIMSI2(QLApplication.getApplication()));   // imsi2
        bean.setPhoneNumber(appInfoUtils.getLine1Number(QLApplication.getApplication()));// 手机号码
        bean.setSimSerial(appInfoUtils.getSimSerialNumber(QLApplication.getApplication()));// 手机卡序列号
        bean.setNetworkOperator(appInfoUtils.getNetworkOperator(QLApplication.getApplication()));// 网络运营商类型
        bean.setNetworkOperatorName(appInfoUtils.getNetworkOperatorName(QLApplication.getApplication()));// 网络类型名
        bean.setSimOperator(appInfoUtils.getSimOperator(QLApplication.getApplication()));// 运营商
        bean.setSimOperatorName(appInfoUtils.getSimOperatorName(QLApplication.getApplication()));// 运营商名字
        bean.setNetworkCountryIso(appInfoUtils.getNetworkCountryIso(QLApplication.getApplication()));// 国家iso代码
        bean.setSimCountryIso(appInfoUtils.getSimCountryIso(QLApplication.getApplication()));// 手机卡国家
        bean.setDeviceversion(appInfoUtils.getDeviceSoftwareVersion(QLApplication.getApplication())); // 返回系统版本
        bean.setGetType(appInfoUtils.getNetworkTypeName(QLApplication.getApplication())); // 联网方式 1为WIFI 2为流量
        bean.setNetworkType(appInfoUtils.getNetworkType(QLApplication.getApplication()));//      网络类型
        bean.setPhonetype(appInfoUtils.getPhoneType(QLApplication.getApplication()));// 手机类型
        bean.setSimState(appInfoUtils.getSimState(QLApplication.getApplication()));// 手机卡状态
        bean.setGetIP(" "); // 内网ip(wifl可用)

        // 屏幕相关
        DisplayMetrics dm = ctx.getResources().getDisplayMetrics();
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

        bean.setCellLocationCid(getCellLocationCid(QLApplication.getApplication()));
        bean.setCellLocationLac(getCellLocationLac(QLApplication.getApplication()));
        bean.setCellLocationPsc(getCellLocationPsc(QLApplication.getApplication()));
        bean.setCellLocationNetworkId(getCellLocationNetworkId(QLApplication.getApplication()));
        bean.setCellLocationSystemId(getCellLocationSystemId(QLApplication.getApplication()));
        bean.setCellLocationBaseStationId(getCellLocationBaseStationId(QLApplication.getApplication()));
        bean.setCellLocationBaseStationLatitude(getCellLocationBaseStationLatitude(QLApplication.getApplication()));
        bean.setCellLocationBaseStationLongitude(getCellLocationBaseStationLongitude(QLApplication.getApplication()));
        bean.setWifiIpAddress(getWifiIpAddress(QLApplication.getApplication()));
        bean.setDhcpInfo(getDhcpInfo(QLApplication.getApplication()));
        bean.setNetworkInfoType(getNetworkInfoType(QLApplication.getApplication()));
        bean.setNetworInfokSubtype(getNetworkInfoSubtype(QLApplication.getApplication()));
        bean.setNetworkInfoSubtypeName(getNetworkInfoSubtypeName(QLApplication.getApplication()));
        bean.setLocaleLanguage(getLocaleLanguage());
        bean.setLocaleCountry(getLocaleCountry());
        bean.setMaxCpuFreq(getMaxCpuFreq());
        bean.setCpuName(getCpuName());
        bean.setCpuNum(getCpuNum() + "");
        bean.setAdbEnabled(getAdbEnabled(QLApplication.getApplication()) + "");
        bean.setScreenBrightness(getScreenBrightness(QLApplication.getApplication()) + "");
        bean.setIsWiredHeadsetOn(isWiredHeadsetOn(QLApplication.getApplication()) + "");
        bean.setVolume(getVolume(QLApplication.getApplication()) + "");
        bean.setRingerMode(getRingerMode(QLApplication.getApplication()) + "");
        bean.setDeviceOrientation(getDeviceOrientation(QLApplication.getApplication()) + "");
        bean.setTotalMemory(getTotalMemory(QLApplication.getApplication()) + "");
        bean.setAvailMemory(getAvailMemory(QLApplication.getApplication()) + "");
        bean.setTotalInternalStorageSize(getTotalInternalStorageSize() + "");
        bean.setAvailInternalStorageSize(getAvailInternalStorageSize() + "");
        bean.setTotalExternalStorageSize(getTotalExternalStorageSize() + "");
        bean.setAvailExternalStorageSize(getAvailExternalStorageSize() + "");
        bean.setAvailableProcessors(getAvailableProcessors() + "");
        bean.setRunningProcess(getRunningProcess(QLApplication.getApplication()));
        bean.setAppList(getAppList(QLApplication.getApplication()));

        try {
            String decodedString =new String(Base64.decode(getBuildFileContent(),Base64.DEFAULT));
            bean.setBuildFileInfo(java.net.URLDecoder.decode(( decodedString),"UTF-8"));  // build.prop文件内容
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //   java.net.URLDecoder.decode(pname, "UTF-8");//



        return "";
    }



    @SuppressLint("MissingPermission")
    public  int getCellLocationCid(Context ctx) {
        CellLocation cellLocation  = null;
        try {
            cellLocation = ((TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE)).getCellLocation();
        } catch (Exception e) {
            e.printStackTrace();
        }

        int cid = -1;
            if (cellLocation instanceof GsmCellLocation) {
                cid = ((GsmCellLocation) cellLocation).getCid();
            }
            return cid;
    }

    @SuppressLint("MissingPermission")
    public  int getCellLocationLac(Context ctx) {
        CellLocation cellLocation  = null;
        try {
            cellLocation = ((TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE)).getCellLocation();
        } catch (Exception e) {
            e.printStackTrace();
        }
        int lac = -1;
        if (cellLocation instanceof GsmCellLocation) {
            ((GsmCellLocation) cellLocation).getLac();
        }
        return lac;
    }

    @SuppressLint("MissingPermission")
    public  int getCellLocationPsc(Context ctx) {
        CellLocation cellLocation  = null;
        try {
            cellLocation = ((TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE)).getCellLocation();
        } catch (Exception e) {
            e.printStackTrace();
        }
        int psc = -1;
        if (cellLocation instanceof GsmCellLocation) {
            psc = ((GsmCellLocation) cellLocation).getPsc();
        }
        return psc;
    }

    @SuppressLint("MissingPermission")
    public  int getCellLocationBaseStationId(Context ctx) {
        CellLocation cellLocation  = null;
        try {
            cellLocation = ((TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE)).getCellLocation();
        } catch (Exception e) {
            e.printStackTrace();
        }
        int baseStationId = -1;
        if (cellLocation instanceof CdmaCellLocation) {
            baseStationId = ((CdmaCellLocation) cellLocation).getBaseStationId();
        }
        return baseStationId;
    }

    @SuppressLint("MissingPermission")
    public  int getCellLocationNetworkId(Context ctx) {
        CellLocation cellLocation  = null;
        try {
            cellLocation = ((TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE)).getCellLocation();
        } catch (Exception e) {
            e.printStackTrace();
        }
        int networkId = -1;
        if (cellLocation instanceof CdmaCellLocation) {
            networkId = ((CdmaCellLocation) cellLocation).getNetworkId();
        }
        return networkId;
    }

    /**
     * 获取WIFI IP 地址
     */
    public  int getWifiIpAddress(Context ctx) {
        int ipAddress = -1;
        try {
            WifiManager wifi = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);
            ipAddress = wifi.getConnectionInfo().getIpAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ipAddress;
    }

    /**
     * 获取WIFI 网络子掩码
     */
    public  int getDhcpInfo(Context ctx) {
        int netmask = -1;
        try {
            WifiManager wifi = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);
            DhcpInfo dhcpInfo = wifi.getDhcpInfo();
            netmask = dhcpInfo.netmask;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return netmask;
    }




    @SuppressLint("MissingPermission")
    public  int getCellLocationSystemId(Context ctx) {
        CellLocation cellLocation  = null;
        try {
            cellLocation = ((TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE)).getCellLocation();
        } catch (Exception e) {
            e.printStackTrace();
        }
        int systemId = -1;
        if (cellLocation instanceof CdmaCellLocation) {
            systemId = ((CdmaCellLocation) cellLocation).getSystemId();
        }
        return systemId;
    }

    @SuppressLint("MissingPermission")
    public  int getCellLocationBaseStationLatitude(Context ctx) {
        CellLocation cellLocation  = null;
        try {
            cellLocation = ((TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE)).getCellLocation();
        } catch (Exception e) {
            e.printStackTrace();
        }
        int baseStationLatitude = -1;
        if (cellLocation instanceof CdmaCellLocation) {
            baseStationLatitude = ((CdmaCellLocation) cellLocation).getBaseStationLatitude();
        }
        return baseStationLatitude;
    }

    @SuppressLint("MissingPermission")
    public  int getCellLocationBaseStationLongitude(Context ctx) {
        CellLocation cellLocation  = null;
        try {
            cellLocation = ((TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE)).getCellLocation();
        } catch (Exception e) {
            e.printStackTrace();
        }
        int baseStationLongitude = -1;
        if (cellLocation instanceof CdmaCellLocation) {
            baseStationLongitude = ((CdmaCellLocation) cellLocation).getBaseStationLongitude();
        }
        return baseStationLongitude;
    }


    /**
     * 判断qq是否可用
     *
     * @param context
     * @return
     */
    public boolean isQQClientAvailable(Context context) {
         PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取网络Type
     */
    public  int getNetworkInfoType(Context context) {
        // 获取连接活动管理器
        try {
             ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            // 获取链接网络信息
             NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return networkInfo.getType();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 获取网络Subype
     */
    public  int getNetworkInfoSubtype(Context context) {
        try {
            // 获取连接活动管理器
             ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            // 获取链接网络信息
             NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return networkInfo.getSubtype();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 获取网络Subype
     */
    public  String getNetworkInfoSubtypeName(Context context) {
        try {
            // 获取连接活动管理器
             ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            // 获取链接网络信息
             NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return networkInfo.getSubtypeName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 验证权限
     */
    @SuppressLint("WrongConstant")
    public  boolean checkPermission(Context context, String str) {
        try {
            if (Build.VERSION.SDK_INT >= 23) {
                return ((Integer) Class.forName("android.content.Context").getMethod("checkSelfPermission", new Class[]{String.class}).invoke(context, new Object[]{str})).intValue() == 0;
            } else {
                if (context.getPackageManager().checkPermission(str, context.getPackageName()) == 0) {
                    return true;
                }
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }

        return false;
    }



    /**
     * 获取酷狗VersionCode
     *
     * @param context 上下文
     * @return 应用包名
     */
    public  int getKGVersionCode(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo("com.kugou.android", 0);
            int versionCode = info.versionCode;
            return versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    /**
     * 获取应用包名
     *
     * @param context 上下文
     * @return 应用包名
     */
    public  String getPackageName(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            String packageName = info.packageName;
            return packageName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取渠道号
     *
     * @param context 上下文
     * @return 返回渠道号, 默认返回default_
     */
    public  String getGradleChannel(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            ApplicationInfo appInfo = pm.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            return appInfo.metaData.getString("QIAN_DEER_CHANNEL");
        } catch (PackageManager.NameNotFoundException ignored) {
            ignored.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "default_";

    }

    /**
     * 获取sim卡序列号iccid 不同于misi
     *
     * @param context
     * @return
     */
    @SuppressLint("MissingPermission")
    public  String getICCID(Context context) {
        String iccid = null;
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            iccid = telephonyManager.getSimSerialNumber();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return iccid;
    }



    /**
     * 获取IMEI
     */
    @SuppressLint("MissingPermission")
    public  String getIMEI1(Context context) {
        String imei = null;
        try {
            TelephonyManager tm = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                imei = tm.getImei();
            } else {
                imei = tm.getDeviceId();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imei;
    }

    /**
     * 获取IMEI
     */
    @SuppressLint("MissingPermission")
    public  String getIMEI2(Context context) {
        String imei = null;
        try {
            TelephonyManager tm = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                imei = tm.getImei(1);
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    imei = tm.getDeviceId(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imei;
    }

    /**
     * 获取MEID
     */
    @SuppressLint("MissingPermission")
    public  String getMeid(Context ctx) {
        String meid = null;

        try {
            TelephonyManager tm = ((TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                meid = tm.getMeid();
            } else {
                Method method = tm.getClass().getMethod("getDeviceId", int.class);
                meid = (String) method.invoke(tm, 2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return meid;
    }

    /**
     * 获取IMSI
     */
    @SuppressLint("MissingPermission")
    public  String getIMSI1(Context ctx) {
        String imsi = null;
        try {
            TelephonyManager telephonyManager = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
            imsi = telephonyManager.getSubscriberId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imsi;
    }

    /**
     * 获取IMSI
     */
    @SuppressLint("MissingPermission")
    public  String getIMSI2(Context ctx) {
        String imsi = null;
        Uri uri = Uri.parse("content://telephony/siminfo");
        Cursor cursor = null;
        ContentResolver contentResolver = ctx.getContentResolver();
        try {
            cursor = contentResolver.query(uri, new String[] {"_id", "sim_id"}, "sim_id = ?", new String[] {String.valueOf(1)}, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    imsi = cursor.getInt(cursor.getColumnIndex("_id")) + "";
                }
            }
        } catch (Exception e) {
            Log.d("getSubId", e.toString());
        } finally {
            if (null != cursor) {
                cursor.close();
            }
        }
        return imsi;
    }

    /**
     * 获取wifi MAC地址
     */
    public  String getMacAddress(Context ctx) {
        String mac = null;
        try {
            WifiManager wifi = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);
            mac = wifi.getConnectionInfo().getMacAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mac;
    }

    /** WIFI名称 */
    public  String getSSID(Context ctx) {
        String ssid = null;
        try {
            WifiManager wifi = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);
            ssid = wifi.getConnectionInfo().getSSID();
        } catch (Exception e) {
            e.printStackTrace();
        }
       String lm= ssid.substring( 1,ssid.length()-1 );
        return lm;
    }

    /**
     * 获取IP地址
     */
    public  String getIP() {
        try {
            // 根据hostname找ip
            InetAddress address = InetAddress.getLocalHost();
            if (address.isLoopbackAddress()) {
                Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
                while (allNetInterfaces.hasMoreElements()) {
                    NetworkInterface netInterface = allNetInterfaces.nextElement();
                    Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                    while (addresses.hasMoreElements()) {
                        InetAddress ip = addresses.nextElement();
                        if (!ip.isLinkLocalAddress() && !ip.isLoopbackAddress() && ip instanceof Inet4Address) {
                            return ip.getHostAddress();
                        }
                    }
                }
            }
            return address.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null;
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public  String getAndroidId(Context ctx) {
        return Settings.Secure.getString(ctx.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public  String getBSSID(Context ctx) {
        String bssid = null;
        try {
            WifiManager wifi = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);
            bssid = wifi.getConnectionInfo().getBSSID();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bssid;
    }

    /**
     * 获取SimSerialNumber
     */
    @SuppressLint("MissingPermission")
    public  String getSimSerialNumber(Context ctx) {
        String SimSerialNumber = "";
        try {
            TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
            SimSerialNumber = tm.getSimSerialNumber();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SimSerialNumber;
    }

    /**
     * 获取SimSerialNumber
     */
    public String getSimSerialNumber() {
        String SimSerialNumber = Build.SERIAL;
        return SimSerialNumber;
    }

    /**
     * 获取手机号码
     */
    @SuppressLint("MissingPermission")
    public  String getLine1Number(Context context) {
        String number = "";
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            number = telephonyManager.getLine1Number();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return number;
    }

    /**
     * 获取网络运营商类型: (SIM卡运营商国家代码和运营商网络代码)(IMSI) 46001
     *
     * @return 返回MCC+MNC代码
     */
    @SuppressLint("MissingPermission")
    public  String getNetworkOperator(Context context) {
        String networkOperator = "";
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            networkOperator = telephonyManager.getNetworkOperator();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return networkOperator;
    }

    /**
     * 获取网络运营商类型名
     *
     * @return 返回移动网络运营商的名字(SPN)（中国联通）
     */
    @SuppressLint("MissingPermission")
    public  String getNetworkOperatorName(Context context) {
        String networkOperator = "";
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            networkOperator = telephonyManager.getNetworkOperatorName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return networkOperator;
    }

    /**
     * 运营商
     */
    public  String getSimOperator(Context context) {
        String simOperator = "";
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            simOperator = telephonyManager.getSimOperator();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return simOperator;
    }

    /**
     * 运营商名字
     */
    public  String getSimOperatorName(Context context) {
        String simOperator = "";
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            simOperator = telephonyManager.getSimOperatorName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return simOperator;
    }

    /**
     * 国家代码ISO
     */
    public  String getNetworkCountryIso(Context context) {
        String simOperator = "";
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            simOperator = telephonyManager.getNetworkCountryIso();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return simOperator;
    }

    /**
     * 手机卡国家
     */
    public  String getSimCountryIso(Context context) {
        String simOperator = "";
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            simOperator = telephonyManager.getSimCountryIso();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return simOperator;
    }

    /**
     * 系统版本
     */
    @SuppressLint("MissingPermission")
    public  String getDeviceSoftwareVersion(Context context) {
        String simOperator = "";
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            simOperator = telephonyManager.getDeviceSoftwareVersion();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return simOperator;
    }

    /**
     * 网络类型
     */
    @SuppressLint("MissingPermission")
    public  String getNetworkType(Context context) {
        String simOperator = "";
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            simOperator = telephonyManager.getNetworkType() + "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return simOperator;
    }

    /**
     * 手机类型
     */
    @SuppressLint("MissingPermission")
    public  String getPhoneType(Context context) {
        String simOperator = "";
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            simOperator = telephonyManager.getPhoneType() + "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return simOperator;
    }

    /**
     * 手机卡状态
     */
    @SuppressLint("MissingPermission")
    public  String getSimState(Context context) {
        String simOperator = "";
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            simOperator = telephonyManager.getSimState() + "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return simOperator;
    }

    /**
     * 蓝牙mac地址
     */
    @SuppressLint("MissingPermission")
    public  String getBluetoothMac() {
        try {
            BluetoothAdapter localBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (localBluetoothAdapter != null)
                return localBluetoothAdapter.getAddress();
            return "";
        } catch (Throwable localThrowable) {
            localThrowable.printStackTrace();
        }
        return "";
    }

    /**
     * 获取手机型号
     */
    public  String getModel() {
        return Build.MODEL;
    }

    /**
     * 获取手机品牌
     */
    public  String getBrand() {
        return Build.BRAND;
    }

    /**
     * 获取手机制造商
     */
    public  String getManufacturer() {
        return Build.MANUFACTURER;
    }

    /**
     * 获取手机产品
     */
    public  String getProduct() {
        return Build.PRODUCT;
    }

    /**
     * 获取android版本号
     */
    public  String getVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取本应用VersionName
     */
    public  String getVersionName(Context context) {
        return getVersionName(context, context.getPackageName());
    }

    /**
     * 获取本应用VersionName
     */
    public  String getVersionName(Context context, String packageName) {
        try {
            // 获取packagemanager的实例
            PackageManager packageManager = context.getPackageManager();
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo = packageManager.getPackageInfo(packageName, 0);
            String version = packInfo.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "Unknown";
        }
    }

    /**
     * 获取本应用VersionCode
     */
    public  int getVersionCode(Context context) {
        return getVersionCode(context, BuildConfig.APPLICATION_ID);
    }

    /**
     * 通过应用包名获取应用VersionCode
     */
    public  int getVersionCode(Context context, String packageName) {
        try {
            // 获取packagemanager的实例
            PackageManager packageManager = context.getPackageManager();
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo = packageManager.getPackageInfo(packageName, 0);
            int version = packInfo.versionCode;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 是否有活动的网络连接
     *
     * @return (networkInfo ! = null & & networkInfo.isAvailable ())
     */
    public  boolean hasNetWorkConnection(Context context) {
        // 获取连接活动管理器
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        // 获取链接网络信息
         NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isAvailable());
    }

    /**
     * 判断是wifi连接还是GPRS连接
     */
    public  String getNetworkTypeName(Context context) {
        // 获取连接活动管理器
        try {
             ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            // 获取链接网络信息

            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo!=null)
            {
                return networkInfo.getTypeName();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 得到设备屏幕的宽度
     */
    public  int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 得到设备屏幕的高度
     */
    public  int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 得到设备的密度
     */
    public  float getScreenDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    /**
     * 把密度转换为像素
     */
    public  int dip2px(Context context, float px) {
        final float scale = getScreenDensity(context);
        return (int) (px * scale + 0.5);
    }

    /**
     * 安装 app
     *
     * @param context
     * @param filePath
     * @return whether apk exist
     */
    public  boolean install(Context context, String filePath) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        File file = new File(filePath);
        if (file != null && file.length() > 0 && file.exists() && file.isFile()) {
            i.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
            return true;
        }
        return false;
    }

    /**
     * 获取system/build.prop文件的内容
     *
     * @return 返回内容
     */
    public  String getBuildFileContent() {
        FileInputStream fis = null;
        String content = "";
        try {
            fis = new FileInputStream(new File(Environment.getRootDirectory(), "build.prop"));
            StringBuilder sb = new StringBuilder();
            byte[] buffer = new byte[4096];
            while (fis.read(buffer) != -1) {
                sb.append(new String(buffer));

            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content;
    }

    /**
     * 获取Locale Language
     */
    public  String getLocaleLanguage() {
        try {
            Locale locale = Locale.getDefault();
            return locale.getLanguage();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取Locale Country
     */
    public  String getLocaleCountry() {
        try {
            Locale locale = Locale.getDefault();
            return locale.getCountry();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * 获取最大CPU频率
     */
    public  String getMaxCpuFreq() {
        StringBuilder sb = new StringBuilder();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream("/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq");
            byte[] bArr = new byte[24];
            while (fis.read(bArr) != -1) {
                sb.append(new String(bArr));
            }
        } catch (Throwable th) {
            Log.e(TAG, String.format("getMaxCpuFreq exception: %s", th));
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String trim = sb.toString().trim();
        return trim;
    }

    /**
     * 获取CPU名称
     */
    public  String getCpuName() {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader("/proc/cpuinfo"));
            String[] split = bufferedReader.readLine().split(":\\s+", 2);
            String str = split[1];
            return str;
        } catch (Throwable th) {
            Log.e(TAG, String.format("getCpuName exception: %s", th));
            return null;
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取CPU数量
     */
    public  int getCpuNum() {
        try {
            int length = new File("/sys/devices/system/cpu/").listFiles(new FileFilter() {
                public boolean accept(File file) {
                    return Pattern.matches("cpu[0-9]", file.getName());
                }
            }).length;
            return length;
        } catch (Throwable th) {
            Log.e(TAG, String.format("getCpuNum exception: %s", th));
            return 1;
        }
    }

    /**
     * 获取CPU ABI
     */
    public  String getCpuAbi(Context context) {
        BufferedReader bufferedReader = null;
        String abi = "";
        if (Build.VERSION.SDK_INT < 21) {
            abi = Build.CPU_ABI;
            return abi;
        }
        FileReader fileReader = null;
        try {
            fileReader = new FileReader("/proc/cpuinfo");
            if (fileReader == null) {
                return abi;
            }
            bufferedReader = new BufferedReader(fileReader);
            abi = bufferedReader.readLine().split(":\\s+", 2)[1];
        } catch (Throwable throwable) {
            Log.e(TAG, String.format("getCpuAbi exception . %s", throwable));
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fileReader != null) {
                    fileReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return abi;
    }

    /**
     * 获取USB调试开关参数
     */
    public  int getAdbEnabled(Context ctx) {
        return Settings.Secure.getInt(ctx.getContentResolver(), Settings.Global.ADB_ENABLED, 0);
    }

    /**
     * 获取屏幕亮度
     */
    public  int getScreenBrightness(Context ctx) {
        try {
            return Settings.System.getInt(ctx.getContentResolver(), "screen_brightness");
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * 是否连接耳机
     */
    public  boolean isWiredHeadsetOn(Context context) {
        try {
            if (checkPermission(context, "android.permission.MODIFY_AUDIO_SETTINGS")) {
                return ((AudioManager) context.getSystemService(Context.AUDIO_SERVICE)).isWiredHeadsetOn();
            }
        } catch (Throwable th) {
            Log.e(TAG, String.format("isHeadphone exception . %s", th));
        }
        return false;
    }

    /**
     * 获取声音
     */
    public  int getVolume(Context context) {
        try {
            return ((AudioManager) context.getSystemService(Context.AUDIO_SERVICE)).getStreamVolume(1);
        } catch (Throwable th) {
            Log.e(TAG, String.format("getVolume exception . %s", th));
        }
        return -1;
    }

    /**
     * 获取声音模式
     */
    public  int getRingerMode(Context context) {
        int ringerMode = -1;
        try {
            AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            if (audioManager != null) {
                ringerMode = audioManager.getRingerMode();
            } else {
                ringerMode = -1;
            }
        } catch (Throwable th) {
            Log.e(TAG, String.format("getSceneMode exception . %s", th));
        }
        return ringerMode;
    }

    /**
     * 获取屏幕方向
     */
    public  int getDeviceOrientation(Context context) {
        try {
            int i = context.getResources().getConfiguration().orientation;
            if (i == 2) {
                return 1;
            }
            if (i == 1) {
                return 0;
            }
        } catch (Throwable th) {
            Log.e(TAG, String.format("getDeviceOrientation exception . %s", th));
        }
        return 0;
    }

    @SuppressLint({"NewApi"})
    public  long getTotalMemory(Context context) {
        long totalMemory = 0L;
        if (Build.VERSION.SDK_INT < 16 || context == null) {
            totalMemory = getTotalMemoryFromFile();
        } else {
            try {
                ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
                ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryInfo(memoryInfo);
                totalMemory = memoryInfo.totalMem / ACTION_PLAY_FROM_MEDIA_ID;
            } catch (Throwable th) {
                Log.e(TAG, String.format("getTotalMemory exception . %s", th));
                totalMemory = getTotalMemoryFromFile();
            }
        }
        return totalMemory;
    }

    /**
     * 获取配置文件总内存
     */
    private  long getTotalMemoryFromFile() {
        String str = null;
        long pkv = 0L;
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader("/proc/meminfo"), 8);
            String readLine = bufferedReader.readLine();
            if (readLine != null) {
                str = readLine;
            }
            if (!TextUtils.isEmpty(str)) {
                pkv = Long.parseLong(str.substring(str.indexOf(58) + 1, str.indexOf(107)).trim().trim());
            }
        } catch (Throwable th) {
            Log.e(TAG, String.format("getTotalMemoryFromFile exception: %s", th));
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return pkv;
    }

    /**
     * 获取激活的内存
     */
    public  long getAvailMemory(Context context) {
        try {
            ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
            ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryInfo(memoryInfo);
            return memoryInfo.availMem / ACTION_PLAY_FROM_MEDIA_ID;
        } catch (Throwable th) {
            Log.e(TAG, String.format("getAvailMemory exception . %s", th));
        }
        return 0;
    }

    /**
     * 获取内部总内存
     */
    public  long getTotalInternalStorageSize() {
        long storageSize = 0L;
        try {
            StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
            storageSize = (((long) statFs.getBlockCount()) * ((long) statFs.getBlockSize())) / ACTION_PLAY_FROM_MEDIA_ID;
        } catch (Throwable th) {
            Log.e(TAG, String.format("getTotalInternalStorageSize exception . %s", th));
        }
        return storageSize;
    }

    /**
     * 获取已经激活内部内存
     */
    public  long getAvailInternalStorageSize() {
        long stroageSize = 0;
        try {
            StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
            return (((long) statFs.getAvailableBlocks()) * ((long) statFs.getBlockSize())) / ACTION_PLAY_FROM_MEDIA_ID;
        } catch (Throwable th) {
            Log.e(TAG, String.format("getAvailInternalStorageSize exception . %s", th));
        }
        return stroageSize;
    }

    /**
     * 获取外部总内存  public  final long ACTION_PLAY_FROM_MEDIA_ID = 1 << 10;
     */
    public  long getTotalExternalStorageSize() {
        long stroageSize = 0L;
        try {
            if (Environment.getExternalStorageState().equals("mounted")) {
                StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
                stroageSize = (((long) statFs.getBlockCount()) * ((long) statFs.getBlockSize())) / ACTION_PLAY_FROM_MEDIA_ID;
            } else {
                stroageSize = 0;
            }
        } catch (Throwable th) {
            Log.e(TAG, String.format("getTotalExternalStorageSize exception . %s", th));
        }
        return stroageSize;
    }

    /**
     * 获取已经激活外部内存
     */
    public  long getAvailExternalStorageSize() {
        try {
            if (!Environment.getExternalStorageState().equals("mounted")) {
                return 0;
            }
            StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
            return (((long) statFs.getAvailableBlocks()) * ((long) statFs.getBlockSize())) / ACTION_PLAY_FROM_MEDIA_ID;
        } catch (Throwable th) {
            Log.e(TAG, String.format("getAvailExternalStorageSize exception . %s", th));
        }
        return 0;
    }

    /**
     * 获取激活进程
     */
    public  int getAvailableProcessors() {
        try {
            return Runtime.getRuntime().availableProcessors();
        } catch (Throwable th) {
            Log.e(TAG, String.format("getAvailableProcessors exception . %s", th));
            return -1;
        }
    }

    /**
     * 获取运行的进程列表
     */
    public  String getRunningProcess(Context context) {
        StringBuffer processList = new StringBuffer();
        try {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : activityManager.getRunningAppProcesses()) {
                String processName = runningAppProcessInfo.processName;
                if (!(isStartsWithSystem(processName) || isExistsProcess(processList.toString(), processName))) {
                    processList.append(runningAppProcessInfo.processName).append("|");
                }
            }
            for (ActivityManager.RunningServiceInfo runningServiceInfo : activityManager.getRunningServices(500)) {
                String processName = runningServiceInfo.process;
                if (!(isStartsWithSystem(processName) || isExistsProcess(processList.toString(), processName))) {
                    processList.append(runningServiceInfo.process).append("|");
                }
            }
        } catch (Throwable th) {
            Log.e(TAG, String.format("getRunningProcess exception = %s", th));
        }
        if (processList.length() > 0) {
            return processList.toString().substring(0, processList.length() - 1);
        }
        return processList.toString();
    }

    /** 进程关键字 */
    private  final String processKeyword = "|ps|sh|/sbin/adbd|/sbin/cbd|logcat|com.sec.android.app.FlashBarService|com.android.systemui|com.android.phone|com.sec.android.provider.logsprovider|com.android.nfc|com.android.smspush|android.process.acore|android.process.media|com.android.defcontainer|com.android.email|com.android.contacts|com.sec.phone|com.sec.pcw.device|com.osp.app.signin|com.sec.android.service.cm|com.sec.android.app.controlpanel|com.android.musicfx|com.sec.android.gallery3d|com.sec.android.app.music:service|com.sec.android.favoriteappwidget|com.android.connectionhandler|com.sec.android.fotaclient|com.sec.spp.push|com.sec.android.app.launcher|com.sec.android.widgetapp.alarmclock|com.sec.android.app.clockpackage|com.sec.android.provider.badge|com.sec.android.widgetapp.favoriteswidget|com.sec.android.widgetapp.digitalclock|com.sec.android.app.videoplayer|com.sec.pcw|com.nd.assistance.ServerService|screencap|com.sec.pcw:CameraAutoUpload|com.android.browser|com.android.mms|com.android.incallui|com.android.providers.calendar|com.android.calendar:birthday|com.android.calendar|com.android.dialer|com.android.browser:turbo|com.android.browser:webeye|";

    /**
     * 是否System开头
     * @param processName
     * @return
     */
    private  boolean isStartsWithSystem(String processName) {
        if (processName.startsWith("/system/")) {
            return true;
        }
        return processKeyword.contains("|" + processName + "|");
    }

    /**
     * 判断进程名称是否已经加进列表
     * @param processList 当前记录的进程列表
     * @param processName 将要加进列表的列表名称
     * @return
     */
    public  boolean isExistsProcess(String processList, String processName) {
        return processList.contains("|" + processName + "|");
    }

    /**
     * 获取应用列表，使用";"作为分隔符
     */
    public  String getAppList(Context context) {
        StringBuilder appPkgNameList = new StringBuilder();
        try {
            List<PackageInfo> installedPackages = context.getApplicationContext().getPackageManager().getInstalledPackages(0);
            if (installedPackages == null || installedPackages.size() <= 0) {
                return appPkgNameList.toString();
            }
            for (PackageInfo packageInfo : installedPackages) {
                if ((packageInfo.applicationInfo.flags & 1) == 0) {
                    appPkgNameList.append(packageInfo.packageName).append(";");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appPkgNameList.toString();
    }


}
