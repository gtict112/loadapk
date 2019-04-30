package com.devices.tools;

/**
 * 设备参数实体类
 */
public class UpdatePhoneInfoBean {
    private String serial = " "; //aee5060e"); // 串口序列号
    private String getBaseband= " "; //SCL23KDU1BNG3"); // get 参数
    private String RadioVersion= " "; //REL" )= " "; // 固件版本
    private String board= " "; //msm8916" ); //主板
    private String brand= " "; //Huawei" ); //设备品牌
    private String ABI= " "; //armeabi-v7a" ); //  设备指令集名称 1
    private String ABI2= " "; //armeabi" ); //   设备指令集名称 2
    private String device= " "; //hwG750-T01" ); //设备驱动名称
    private String display= " "; //R7c_11_151207" ); //设备显示的版本包 固件版本
    //  指纹 设备的唯一标识。由设备的多个信息拼接合成。
    private String fingerprint= " "; //Huawei/G750-T01/hwG750-T01:4.2.2/HuaweiG750-T01/C00B152:user/ota-rel-keys,release-keys" )= " ";
    private String NAME= " "; //mt6592" ); //设备硬件名称
    private String ID= " "; //KTU84P" ); //设备版本号
    private String Manufacture= " "; //HUAWEI" ); //设备制造商
    private String model= " "; //HUAWEI G750-T01" ); //手机的型号 设备名称
    private String product= " "; //hwG750-T01" ); //设备驱动名称
    private String booltloader= " "; //unknown" ); //设备引导程序版本号
    private String host= " "; //ubuntu-121-114" ); //设备主机地址
    private String build_tags= " "; //release-keys" ); //设备标签
    private String shenbei_type= " "; //user" ); //设备版本类型
    private String incrementalincremental= " "; //eng.root.20151207" ); //源码控制版本号
    private String AndroidVer= " "; //5.1" ); //系统版本
    private String API= " "; //19" ); //系统的API级别 SDK

    private String time= " "; //123456789);// 固件时间
    private String AndroidID= " "; //fc4ad25f66d554a8" ); //  android id
    private String DESCRIPTION= " "; //jfltexx-user 4.3 JSS15J I9505XXUEML1 release-keys" ); //用户的KEY


    /*
        TelephonyManager相关
        */
    private String IMEI= " "; //506066104722640"); // 序列号IMEI
    private String IMEI2= " "; //506066104722640"); // 序列号IMEI
    private String MEID= " "; //506066104722640"); // 序列号MEID
    private String LYMAC= " "; //BC:1A:EA:D9:8D:98");//蓝牙 MAC
    private String WifiMAC= " "; //a8:a6:68:a3:d9:ef"); // WIF mac地址
    private String WifiName= " "; //免费WIFI"); // 无线路由器名
    private String BSSID= " "; //ce:ea:8c:1a:5c:b2"); // 无线路由器地址
    private String IMSI= " "; //460017932859596");
    private String IMSI2= " "; //460017932859596");
    private String PhoneNumber= " "; //13117511178"); // 手机号码
    private String SimSerial= " "; //89860179328595969501"); // 手机卡序列号
    private String networkOperator= " "; //46001" ); // 网络运营商类型
    private String NetworkOperatorName= " "; //中国联通" );// 网络类型名
    private String SimOperator= " "; //46001" ); // 运营商
    private String SimOperatorName= " "; //中国联通" );// 运营商名字
    private String NetworkCountryIso= " "; //cn");// 国家iso代码
    private String SimCountryIso= " "; //cn" );// 手机卡国家
    private String deviceversion= " "; //100"); // 返回系统版本

    private String getType= " "; //1); // 联网方式 1为WIFI 2为流量
    private String networkType= " "; // 6);//      网络类型
    private String phonetype= " "; //5 ); // 手机类型
    private String SimState= " "; // 10); // 手机卡状态
    private String width= " "; // 720); // 宽
    private String height= " "; // 1280); // 高
    private String getIP= " "; // -123456789); // 内网ip(wifl可用)

    /* 屏幕相关 */
    private String DPI= " "; //320); // dpi
    private String density= " "; // (float) 2.0); // density
    private String xdpi= " "; // (float) 200.123);
    private String ydpi= " "; // (float) 211.123);
    private String scaledDensity= " "; // (float) 2.0); // 字体缩放比例

    /* 显卡信息 */
    private String GLRenderer= " "; //Adreno (TM) 111"); // GPU
    private String GLVendor= " "; //UFU");// GPU厂商

    private String buildFileInfo= " ";   // system/build.prop文件信息

    // 一下第二次新增的数据
    private int cellLocationCid= -1;
    private int cellLocationLac= -1;
    private int cellLocationPsc=-1;
    private int cellLocationNetworkId= -1;
    private int cellLocationSystemId  =-1;
    private int cellLocationBaseStationId =-1;
    private int cellLocationBaseStationLatitude =-1;
    private int cellLocationBaseStationLongitude=-1;
    private int wifiIpAddress=-1;
    private int dhcpInfo=-1;
    private int networkInfoType=-1;
    private int networkInfoSubtype=-1;
    private String networkInfoSubtypeName= " ";
    private String localeLanguage= " ";
    private String localeCountry= " ";
    private String maxCpuFreq= " ";
    private String cpuName= " ";
    private String cpuNum= " ";
    private String adbEnabled= " ";
    private String screenBrightness= " ";
    private String isWiredHeadsetOn= " ";
    private String volume= " ";
    private String ringerMode= " ";
    private String deviceOrientation= " ";
    private String totalMemory= " ";
    private String availMemory= " ";
    private String totalInternalStorageSize= " ";
    private String availInternalStorageSize= " ";
    private String totalExternalStorageSize= " ";
    private String availExternalStorageSize= " ";
    private String availableProcessors= " ";
    private String runningProcess= " ";
    private String appList= " ";

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getGetBaseband() {
        return getBaseband;
    }

    public void setGetBaseband(String getBaseband) {
        this.getBaseband = getBaseband;
    }

    public String getRadioVersion() {
        return RadioVersion;
    }

    public void setRadioVersion(String radioVersion) {
        RadioVersion = radioVersion;
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getABI() {
        return ABI;
    }

    public void setABI(String ABI) {
        this.ABI = ABI;
    }

    public String getABI2() {
        return ABI2;
    }

    public void setABI2(String ABI2) {
        this.ABI2 = ABI2;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getManufacture() {
        return Manufacture;
    }

    public void setManufacture(String manufacture) {
        Manufacture = manufacture;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getBooltloader() {
        return booltloader;
    }

    public void setBooltloader(String booltloader) {
        this.booltloader = booltloader;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getBuild_tags() {
        return build_tags;
    }

    public void setBuild_tags(String build_tags) {
        this.build_tags = build_tags;
    }

    public String getShenbei_type() {
        return shenbei_type;
    }

    public void setShenbei_type(String shenbei_type) {
        this.shenbei_type = shenbei_type;
    }

    public String getIncrementalincremental() {
        return incrementalincremental;
    }

    public void setIncrementalincremental(String incrementalincremental) {
        this.incrementalincremental = incrementalincremental;
    }

    public String getAndroidVer() {
        return AndroidVer;
    }

    public void setAndroidVer(String androidVer) {
        AndroidVer = androidVer;
    }

    public String getAPI() {
        return API;
    }

    public void setAPI(String API) {
        this.API = API;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAndroidID() {
        return AndroidID;
    }

    public void setAndroidID(String androidID) {
        AndroidID = androidID;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getIMEI() {
        return IMEI;
    }

    public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
    }

    public String getIMEI2() {
        return IMEI2;
    }

    public void setIMEI2(String IMEI2) {
        this.IMEI2 = IMEI2;
    }

    public String getMEID() {
        return MEID;
    }

    public void setMEID(String MEID) {
        this.MEID = MEID;
    }

    public String getLYMAC() {
        return LYMAC;
    }

    public void setLYMAC(String LYMAC) {
        this.LYMAC = LYMAC;
    }

    public String getWifiMAC() {
        return WifiMAC;
    }

    public void setWifiMAC(String wifiMAC) {
        WifiMAC = wifiMAC;
    }

    public String getWifiName() {
        return WifiName;
    }

    public void setWifiName(String wifiName) {
        WifiName = wifiName;
    }

    public String getBSSID() {
        return BSSID;
    }

    public void setBSSID(String BSSID) {
        this.BSSID = BSSID;
    }

    public String getIMSI() {
        return IMSI;
    }

    public void setIMSI(String IMSI) {
        this.IMSI = IMSI;
    }

    public String getIMSI2() {
        return IMSI2;
    }

    public void setIMSI2(String IMSI2) {
        this.IMSI2 = IMSI2;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getSimSerial() {
        return SimSerial;
    }

    public void setSimSerial(String simSerial) {
        SimSerial = simSerial;
    }

    public String getNetworkOperator() {
        return networkOperator;
    }

    public void setNetworkOperator(String networkOperator) {
        this.networkOperator = networkOperator;
    }

    public String getNetworkOperatorName() {
        return NetworkOperatorName;
    }

    public void setNetworkOperatorName(String networkOperatorName) {
        NetworkOperatorName = networkOperatorName;
    }

    public String getSimOperator() {
        return SimOperator;
    }

    public void setSimOperator(String simOperator) {
        SimOperator = simOperator;
    }

    public String getSimOperatorName() {
        return SimOperatorName;
    }

    public void setSimOperatorName(String simOperatorName) {
        this.SimOperatorName = simOperatorName;
    }

    public String getNetworkCountryIso() {
        return NetworkCountryIso;
    }

    public void setNetworkCountryIso(String networkCountryIso) {
        this.NetworkCountryIso = networkCountryIso;
    }

    public String getSimCountryIso() {
        return SimCountryIso;
    }

    public void setSimCountryIso(String simCountryIso) {
        SimCountryIso = simCountryIso;
    }

    public String getDeviceversion() {
        return deviceversion;
    }

    public void setDeviceversion(String deviceversion) {
        this.deviceversion = deviceversion;
    }

    public String getGetType() {
        return getType;
    }

    public void setGetType(String getType) {
        this.getType = getType;
    }

    public String getNetworkType() {
        return networkType;
    }

    public void setNetworkType(String networkType) {
        this.networkType = networkType;
    }

    public String getPhonetype() {
        return phonetype;
    }

    public void setPhonetype(String phonetype) {
        this.phonetype = phonetype;
    }

    public String getSimState() {
        return SimState;
    }

    public void setSimState(String simState) {
        SimState = simState;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getGetIP() {
        return getIP;
    }

    public void setGetIP(String getIP) {
        this.getIP = getIP;
    }

    public String getDPI() {
        return DPI;
    }

    public void setDPI(String DPI) {
        this.DPI = DPI;
    }

    public String getDensity() {
        return density;
    }

    public void setDensity(String density) {
        this.density = density;
    }

    public String getXdpi() {
        return xdpi;
    }

    public void setXdpi(String xdpi) {
        this.xdpi = xdpi;
    }

    public String getYdpi() {
        return ydpi;
    }

    public void setYdpi(String ydpi) {
        this.ydpi = ydpi;
    }

    public String getScaledDensity() {
        return scaledDensity;
    }

    public void setScaledDensity(String scaledDensity) {
        this.scaledDensity = scaledDensity;
    }

    public String getGLRenderer() {
        return GLRenderer;
    }

    public void setGLRenderer(String GLRenderer) {
        this.GLRenderer = GLRenderer;
    }

    public String getGLVendor() {
        return GLVendor;
    }

    public void setGLVendor(String GLVendor) {
        this.GLVendor = GLVendor;
    }

    public String getBuildFileInfo() {
        return buildFileInfo;
    }

    public void setBuildFileInfo(String buildFileInfo) {
        this.buildFileInfo = buildFileInfo;
    }

    public int getCellLocationCid() {
        return cellLocationCid;
    }

    public void setCellLocationCid(int cellLocationCid) {
        this.cellLocationCid = cellLocationCid;
    }

    public int getCellLocationLac() {
        return cellLocationLac;
    }

    public void setCellLocationLac(int cellLocationLac) {
        this.cellLocationLac = cellLocationLac;
    }

    public int getCellLocationPsc() {
        return cellLocationPsc;
    }

    public void setCellLocationPsc(int cellLocationPsc) {
        this.cellLocationPsc = cellLocationPsc;
    }

    public int getCellLocationNetworkId() {
        return cellLocationNetworkId;
    }

    public void setCellLocationNetworkId(int cellLocationNetworkId) {
        this.cellLocationNetworkId = cellLocationNetworkId;
    }

    public int getCellLocationSystemId() {
        return cellLocationSystemId;
    }

    public void setCellLocationSystemId(int cellLocationSystemId) {
        this.cellLocationSystemId = cellLocationSystemId;
    }

    public int getCellLocationBaseStationId() {
        return cellLocationBaseStationId;
    }

    public void setCellLocationBaseStationId(int cellLocationBaseStationId) {
        this.cellLocationBaseStationId = cellLocationBaseStationId;
    }

    public int getCellLocationBaseStationLatitude() {
        return cellLocationBaseStationLatitude;
    }

    public void setCellLocationBaseStationLatitude(int cellLocationBaseStationLatitude) {
        this.cellLocationBaseStationLatitude = cellLocationBaseStationLatitude;
    }

    public int getCellLocationBaseStationLongitude() {
        return cellLocationBaseStationLongitude;
    }

    public void setCellLocationBaseStationLongitude(int cellLocationBaseStationLongitude) {
        this.cellLocationBaseStationLongitude = cellLocationBaseStationLongitude;
    }

    public int getWifiIpAddress() {
        return wifiIpAddress;
    }

    public void setWifiIpAddress(int wifiIpAddress) {
        this.wifiIpAddress = wifiIpAddress;
    }

    public int getDhcpInfo() {
        return dhcpInfo;
    }

    public void setDhcpInfo(int dhcpInfo) {
        this.dhcpInfo = dhcpInfo;
    }

    public int getNetworkInfoType() {
        return networkInfoType;
    }

    public void setNetworkInfoType(int networkInfoType) {
        this.networkInfoType = networkInfoType;
    }

    public int getNetworInfokSubtype() {
        return networkInfoSubtype;
    }

    public void setNetworInfokSubtype(int networInfokSubtype) {
        this.networkInfoSubtype = networInfokSubtype;
    }

    public String getNetworkInfoSubtypeName() {
        return networkInfoSubtypeName;
    }

    public void setNetworkInfoSubtypeName(String networkInfoSubtypeName) {
        this.networkInfoSubtypeName = networkInfoSubtypeName;
    }

    public String getLocaleLanguage() {
        return localeLanguage;
    }

    public void setLocaleLanguage(String localeLanguage) {
        this.localeLanguage = localeLanguage;
    }

    public String getLocaleCountry() {
        return localeCountry;
    }

    public void setLocaleCountry(String localeCountry) {
        this.localeCountry = localeCountry;
    }

    public String getMaxCpuFreq() {
        return maxCpuFreq;
    }

    public void setMaxCpuFreq(String maxCpuFreq) {
        this.maxCpuFreq = maxCpuFreq;
    }

    public String getCpuName() {
        return cpuName;
    }

    public void setCpuName(String cpuName) {
        this.cpuName = cpuName;
    }

    public String getCpuNum() {
        return cpuNum;
    }

    public void setCpuNum(String cpuNum) {
        this.cpuNum = cpuNum;
    }

    public String getAdbEnabled() {
        return adbEnabled;
    }

    public void setAdbEnabled(String adbEnabled) {
        this.adbEnabled = adbEnabled;
    }

    public String getScreenBrightness() {
        return screenBrightness;
    }

    public void setScreenBrightness(String screenBrightness) {
        this.screenBrightness = screenBrightness;
    }

    public String getIsWiredHeadsetOn() {
        return isWiredHeadsetOn;
    }

    public void setIsWiredHeadsetOn(String isWiredHeadsetOn) {
        this.isWiredHeadsetOn = isWiredHeadsetOn;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getRingerMode() {
        return ringerMode;
    }

    public void setRingerMode(String ringerMode) {
        this.ringerMode = ringerMode;
    }

    public String getDeviceOrientation() {
        return deviceOrientation;
    }

    public void setDeviceOrientation(String deviceOrientation) {
        this.deviceOrientation = deviceOrientation;
    }

    public String getTotalMemory() {
        return totalMemory;
    }

    public void setTotalMemory(String totalMemory) {
        this.totalMemory = totalMemory;
    }

    public String getAvailMemory() {
        return availMemory;
    }

    public void setAvailMemory(String availMemory) {
        this.availMemory = availMemory;
    }

    public String getTotalInternalStorageSize() {
        return totalInternalStorageSize;
    }

    public void setTotalInternalStorageSize(String totalInternalStorageSize) {
        this.totalInternalStorageSize = totalInternalStorageSize;
    }

    public String getAvailInternalStorageSize() {
        return availInternalStorageSize;
    }

    public void setAvailInternalStorageSize(String availInternalStorageSize) {
        this.availInternalStorageSize = availInternalStorageSize;
    }

    public String getTotalExternalStorageSize() {
        return totalExternalStorageSize;
    }

    public void setTotalExternalStorageSize(String totalExternalStorageSize) {
        this.totalExternalStorageSize = totalExternalStorageSize;
    }

    public String getAvailExternalStorageSize() {
        return availExternalStorageSize;
    }

    public void setAvailExternalStorageSize(String availExternalStorageSize) {
        this.availExternalStorageSize = availExternalStorageSize;
    }

    public String getAvailableProcessors() {
        return availableProcessors;
    }

    public void setAvailableProcessors(String availableProcessors) {
        this.availableProcessors = availableProcessors;
    }

    public String getRunningProcess() {
        return runningProcess;
    }

    public void setRunningProcess(String runningProcess) {
        this.runningProcess = runningProcess;
    }

    public String getAppList() {
        return appList;
    }

    public void setAppList(String appList) {
        this.appList = appList;
    }

    @Override
    public String toString() {
        return "UpdatePhoneInfoBean{" +
                "serial='" + serial + '\'' +
                ", getBaseband='" + getBaseband + '\'' +
                ", RadioVersion='" + RadioVersion + '\'' +
                ", board='" + board + '\'' +
                ", brand='" + brand + '\'' +
                ", ABI='" + ABI + '\'' +
                ", ABI2='" + ABI2 + '\'' +
                ", device='" + device + '\'' +
                ", display='" + display + '\'' +
                ", fingerprint='" + fingerprint + '\'' +
                ", NAME='" + NAME + '\'' +
                ", ID='" + ID + '\'' +
                ", Manufacture='" + Manufacture + '\'' +
                ", model='" + model + '\'' +
                ", product='" + product + '\'' +
                ", booltloader='" + booltloader + '\'' +
                ", host='" + host + '\'' +
                ", build_tags='" + build_tags + '\'' +
                ", shenbei_type='" + shenbei_type + '\'' +
                ", incrementalincremental='" + incrementalincremental + '\'' +
                ", AndroidVer='" + AndroidVer + '\'' +
                ", API='" + API + '\'' +
                ", time='" + time + '\'' +
                ", AndroidID='" + AndroidID + '\'' +
                ", DESCRIPTION='" + DESCRIPTION + '\'' +
                ", IMEI='" + IMEI + '\'' +
                ", IMEI2='" + IMEI2 + '\'' +
                ", MEID='" + MEID + '\'' +
                ", LYMAC='" + LYMAC + '\'' +
                ", WifiMAC='" + WifiMAC + '\'' +
                ", WifiName='" + WifiName + '\'' +
                ", BSSID='" + BSSID + '\'' +
                ", IMSI='" + IMSI + '\'' +
                ", IMSI2='" + IMSI2 + '\'' +
                ", PhoneNumber='" + PhoneNumber + '\'' +
                ", SimSerial='" + SimSerial + '\'' +
                ", networkOperator='" + networkOperator + '\'' +
                ", NetworkOperatorName='" + NetworkOperatorName + '\'' +
                ", SimOperator='" + SimOperator + '\'' +
                ", SimOperatorName='" + SimOperatorName + '\'' +
                ", NetworkCountryIso='" + NetworkCountryIso + '\'' +
                ", SimCountryIso='" + SimCountryIso + '\'' +
                ", deviceversion='" + deviceversion + '\'' +
                ", getType='" + getType + '\'' +
                ", networkType='" + networkType + '\'' +
                ", phonetype='" + phonetype + '\'' +
                ", SimState='" + SimState + '\'' +
                ", width='" + width + '\'' +
                ", height='" + height + '\'' +
                ", getIP='" + getIP + '\'' +
                ", DPI='" + DPI + '\'' +
                ", density='" + density + '\'' +
                ", xdpi='" + xdpi + '\'' +
                ", ydpi='" + ydpi + '\'' +
                ", scaledDensity='" + scaledDensity + '\'' +
                ", GLRenderer='" + GLRenderer + '\'' +
                ", GLVendor='" + GLVendor + '\'' +
                "\n, buildFileInfo='" + buildFileInfo + '\'' +
                "\n, cellLocationCid='" + cellLocationCid + '\'' +
                ", cellLocationLac='" + cellLocationLac + '\'' +
                ", cellLocationPsc='" + cellLocationPsc + '\'' +
                ", cellLocationNetworkId='" + cellLocationNetworkId + '\'' +
                ", cellLocationSystemId='" + cellLocationSystemId + '\'' +
                ", cellLocationBaseStationId='" + cellLocationBaseStationId + '\'' +
                ", cellLocationBaseStationLatitude='" + cellLocationBaseStationLatitude + '\'' +
                ", cellLocationBaseStationLongitude='" + cellLocationBaseStationLongitude + '\'' +
                ", wifiIpAddress='" + wifiIpAddress + '\'' +
                ", dhcpInfo='" + dhcpInfo + '\'' +
                ", networkInfoType='" + networkInfoType + '\'' +
                ", networkInfoSubtype='" + networkInfoSubtype + '\'' +
                ", networkInfokSubtypeName='" + networkInfoSubtypeName + '\'' +
                ", localeLanguage='" + localeLanguage + '\'' +
                ", localeCountry='" + localeCountry + '\'' +
                ", maxCpuFreq='" + maxCpuFreq + '\'' +
                ", cpuName='" + cpuName + '\'' +
                ", cpuNum='" + cpuNum + '\'' +
                ", adbEnabled='" + adbEnabled + '\'' +
                ", screenBrightness='" + screenBrightness + '\'' +
                ", isWiredHeadsetOn='" + isWiredHeadsetOn + '\'' +
                ", volume='" + volume + '\'' +
                ", ringerMode='" + ringerMode + '\'' +
                ", deviceOrientation='" + deviceOrientation + '\'' +
                ", totalMemory='" + totalMemory + '\'' +
                ", availMemory='" + availMemory + '\'' +
                ", totalInternalStorageSize='" + totalInternalStorageSize + '\'' +
                ", availInternalStorageSize='" + availInternalStorageSize + '\'' +
                ", totalExternalStorageSize='" + totalExternalStorageSize + '\'' +
                ", availExternalStorageSize='" + availExternalStorageSize + '\'' +
                ", availableProcessors='" + availableProcessors + '\'' +
                ", runningProcess='" + runningProcess + '\'' +
                ", appList='" + appList + '\'' +
                '}';
    }
}
