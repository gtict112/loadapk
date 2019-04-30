package com.example.secondapk.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;

import com.devices.tools.tools;
import com.example.secondapk.R;
import com.example.secondapk.flow.ResourceMgr;
import com.example.secondapk.plugin.LogMgr;
import com.example.secondapk.plugin.PluginMgr;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by flyu on 2018/11/15.
 */

public class PluginViewMgr {
    private static final String CONFIG_FILE_NAME = "plugin_config.json";
    private static final String CACHE_CONTENT_FILE_NAME = "hhcache.txt";
    private static PopupWindow popupWindow = null;
    private static Activity sActivity = null;
    private static String sWorkDir = null;
    private static PluginInfo sPluginInfo = new PluginInfo();
    private static PopupWindow sPopWindow = null;
    private static PopupWindow sPopChatSetting = null;
    private static PopupWindow sPopSwapSetting = null;
    private static int sTmpTimes = -1;
    private static int sSwpTimes = 40;
    public static IWXAPI api;

    public static void init(Activity activity, String workDir) {
        sActivity = activity;
        sWorkDir = workDir;

        String content = GetConfigContentByFileName( CONFIG_FILE_NAME );
        if (content == null) {
            LogMgr.LogToast( sActivity.getApplicationContext(), "文件缺失: " + CONFIG_FILE_NAME );
        } else {
            ParseConfigJson( content );
        }

        // 获取上次设置的喊话内容
        String cacheContent = GetConfigContentByFileName( CACHE_CONTENT_FILE_NAME );
        if (cacheContent != null) {
            sPluginInfo.setPluginChatContent( cacheContent );
//            PluginMgr.set_hanhua_content(sPluginInfo.getChatIntervalTime(), cacheContent);
        }
    }

    // 解析配置文件
    private static void ParseConfigJson(String strJson) {
        try {
            JSONObject cfgObject = new JSONObject( strJson );
            int width = cfgObject.getInt( "width" );
            int height = cfgObject.getInt( "height" );
            int hanhuaCount = cfgObject.getInt( "HanHuaCount" );
            sPluginInfo.setWidth( width );
            sPluginInfo.setHeight( height );
            sPluginInfo.setHanhuaCount( hanhuaCount );

            JSONArray jsArrsy = cfgObject.getJSONArray( "plugins" );
            int size = jsArrsy.length();
            for (int i = 0; i < size; i++) {
                JSONObject tmpObj = (JSONObject) jsArrsy.get( i );
                int id = tmpObj.getInt( "id" );
                String type = tmpObj.getString( "type" );
                String title = tmpObj.getString( "title" );
                String value = tmpObj.getString( "value" );

                PluginItemInfo itemInfo = new PluginItemInfo();
                itemInfo.setId( id );

                EPluginItemType itemType = EPluginItemType.TYPE_NONE;
                if (type.equals( "Switch" )) {
                    itemType = EPluginItemType.TYPE_SWITCH;
                }
                itemInfo.setType( itemType );

                itemInfo.setTitle( title );
                itemInfo.setValue( !value.equals( "off" ) );
                sPluginInfo.AddPluginItem( itemInfo );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 获取配置文件信息
    private static String GetConfigContentByFileName(String fileName) {
        String configName = sWorkDir + fileName;
        StringBuffer strBuffer = new StringBuffer();

        try {
            File file = new File( configName );
            FileInputStream inputStream = new FileInputStream( file );
            InputStreamReader isReader = new InputStreamReader( inputStream );
            BufferedReader bufReader = new BufferedReader( isReader );
            String line = bufReader.readLine();
            if (line.startsWith( "time=" )) {
                String tmpTime = line.substring( "time=".length() );
                int time = Integer.parseInt( tmpTime );
                sPluginInfo.setChatIntervalTime( time );
                line = bufReader.readLine();
            }

            while (line != null) {
                strBuffer.append( line );
                line = bufReader.readLine();
            }

            bufReader.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return strBuffer.toString();
    }

    // 缓存喊话内容
    public static void WriteHHContentToCache(int time, String strContent) {
        try {
            String cacheFileName = sWorkDir + CACHE_CONTENT_FILE_NAME;
            File cacheFile = new File( cacheFileName );
            if (!cacheFile.exists()) {
                cacheFile.createNewFile();
                cacheFile.setReadable( true, false );
                cacheFile.setWritable( true, false );
            }
            FileOutputStream fos = new FileOutputStream( new File( cacheFileName ) );
            String timeStr = "time=" + time + "\n";
            fos.write( timeStr.getBytes(), 0, timeStr.getBytes().length );
            fos.write( strContent.getBytes(), 0, strContent.getBytes().length );
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





    // 创建插件子主视图
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static View CreatePluginItemView(final PluginItemInfo itemInfo) {
        View retView = ResourceMgr.inflate( R.layout.plugin_switch_view_layout, null );
        // 设置名字
        TextView itemNameView = retView.findViewById( R.id.id_plugin_item_name );
        itemNameView.setText( itemInfo.getTitle() );
        // 设置喊话内容
        if (itemInfo.getId() == 1001) {
            itemNameView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    ShowPluginHHSettingView();
//                    sPopWindow.dismiss();
//                    loadjswxtest("wxf19ea41a5d2c67e8","gh_3cbe7d5b1dc3","");
                }
            } );
        }

        // 扫货 出货设置
        if (itemInfo.getId() == 1002) {
            itemNameView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {

//                    loadjswxtest("wxf19ea41a5d2c67e8","gh_3cbe7d5b1dc3","");
//                    ShowPluginSwapSettingView();
//                    sPopWindow.dismiss();
                }
            } );
        }

        // 设置开关
        ImageView switchView = retView.findViewById( R.id.id_plugin_item_switch );
        if (itemInfo.isValue()) {
            switchView.setImageDrawable( ResourceMgr.FindResourceById( R.drawable.icon_switch_on ) );
        }
        switchView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean bValue = itemInfo.isValue();
                itemInfo.setValue( !bValue );
                int resId = R.drawable.icon_switch_off;
                if (!bValue) {
                    resId = R.drawable.icon_switch_on;
                }
                ((ImageView) view).setImageDrawable( ResourceMgr.FindResourceById( resId ) );
//                PluginMgr.set_bool_value(itemInfo.getId(), itemInfo.isValue());
            }
        } );

        itemInfo.setItemView( retView );
        return retView;
    }

    // 创建插件主视图
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static View CreatePluginView(PluginInfo pluginInfo) {
        View retView = ResourceMgr.inflate( R.layout.plugin_layout, null );

        ListView listView = retView.findViewById( R.id.id_plugin_list_view );
        ListViewAdapter adapter = new ListViewAdapter();
        List<PluginItemInfo> itemInfos = pluginInfo.getItemList();
        int count = itemInfos.size();
        for (PluginItemInfo itemInfo : itemInfos) {
            View itemView = CreatePluginItemView( itemInfo );
            adapter.addView( itemView );
        }
        listView.setAdapter( adapter );
        pluginInfo.setPluginView( retView );

        return retView;
    }

    // 创建喊话内容设置视图
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static View CreatePluginChatSettingView(final PluginInfo pluginInfo) {
        View retView = sPluginInfo.getPluginHHSetingView();
        if (retView != null) {
            TextView currentTime = retView.findViewById( R.id.id_plugin_chat_time_current );
            SeekBar seekBar = retView.findViewById( R.id.id_plugin_chat_time_seekbar );
            EditText editText = retView.findViewById( R.id.id_plugin_content_editer );

            // 恢复保存过的设置
            DecimalFormat df = new DecimalFormat( "0.00" );//格式化小数
            String showNum = df.format( (float) sPluginInfo.getChatIntervalTime() / 100.0f );//返回的是String类型
            currentTime.setText( showNum + "秒" );
            int tmpProgress = 0;
            if (sPluginInfo.getChatIntervalTime() > 1) {
                tmpProgress = sPluginInfo.getChatIntervalTime();
            }
            seekBar.setProgress( tmpProgress );
            editText.setText( pluginInfo.getPluginChatContent() );
            return retView;
        }

        retView = ResourceMgr.inflate( R.layout.plugin_hanhua_setting_layout, null );
        final TextView currentTime = retView.findViewById( R.id.id_plugin_chat_time_current );
        SeekBar seekBar = retView.findViewById( R.id.id_plugin_chat_time_seekbar );
        seekBar.setOnSeekBarChangeListener( new SeekBar.OnSeekBarChangeListener() {
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int times = progress == 0 ? 1 : progress;
                DecimalFormat df = new DecimalFormat( "0.00" );//格式化小数
                String showNum = df.format( (float) times / 100.0f );//返回的是String类型
                currentTime.setText( showNum + "秒" );
                sTmpTimes = times;
            }
        } );

        if (sPluginInfo.getChatIntervalTime() != -1) {
            DecimalFormat df = new DecimalFormat( "0.00" );//格式化小数
            String showNum = df.format( (float) sPluginInfo.getChatIntervalTime() / 100.0f );//返回的是String类型
            currentTime.setText( showNum + "秒" );
            int tmpProgress = 0;
            if (sPluginInfo.getChatIntervalTime() > 1) {
                tmpProgress = sPluginInfo.getChatIntervalTime();
            }
            seekBar.setProgress( tmpProgress );
        }

        final EditText editText = retView.findViewById( R.id.id_plugin_content_editer );
        editText.setText( pluginInfo.getPluginChatContent() );

        TextView saveBtn = retView.findViewById( R.id.id_plugin_content_save );
        saveBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String editContent = editText.getText().toString();
                pluginInfo.setPluginChatContent( editContent.trim() );
                pluginInfo.setChatIntervalTime( sTmpTimes );
                PluginMgr.WriteToService( pluginInfo.getPluginChatContent() );
                WriteHHContentToCache( pluginInfo.getChatIntervalTime(), pluginInfo.getPluginChatContent() );
                PluginMgr.set_hanhua_content( pluginInfo.getChatIntervalTime(), pluginInfo.getPluginChatContent() );

                sPopChatSetting.dismiss();
            }
        } );

        TextView cleanBtn = retView.findViewById( R.id.id_plugin_content_clear );
        cleanBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText( "" );
            }
        } );

        pluginInfo.setPluginHHSetingView( retView );
        return retView;
    }

    public static void ResetSwapView(View swapView) {
        TextView currentTime = swapView.findViewById( R.id.id_plugin_swap_time_current );
        SeekBar seekBar = swapView.findViewById( R.id.id_plugin_swap_time_seekbar );
        // 扫货价格范围设置
        EditText swapMinPriceEdit = swapView.findViewById( R.id.id_plugin_swap_edit_minprice );
        EditText swapMaxPriceEdit = swapView.findViewById( R.id.id_plugin_swap_edit_maxprice );

        // 出货价格，出货数量设置
        EditText salePriceEdit = swapView.findViewById( R.id.id_plugin_swap_sale_price );
        EditText saleNumEdit = swapView.findViewById( R.id.id_plugin_swap_sale_num );

        // 恢复保存过的设置
        SwapSetting swapSetting = sPluginInfo.getSwapSetting();
        int swapTime = sPluginInfo.getSwapSetting().getSwapTime();
        currentTime.setText( swapTime + "毫秒" );
        seekBar.setProgress( swapTime );

        swapMinPriceEdit.setText( "" + swapSetting.getMinSwapPrice() );
        swapMaxPriceEdit.setText( "" + swapSetting.getMaxSwapPrice() );
        salePriceEdit.setText( "" + swapSetting.getSalePrice() );
        saleNumEdit.setText( "" + swapSetting.getSaleNum() );
    }

    // 创建扫货设置视图
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static View CreatePluginSwapSettingView(final PluginInfo pluginInfo) {
        View retView = sPluginInfo.getSwapSetting().getPluginSwapSetingView();
        if (retView != null) {
            ResetSwapView( retView );
            return retView;
        }

        // 扫货时间间隔
        retView = ResourceMgr.inflate( R.layout.plugin_swap_setting_layout, null );
        final TextView currentTime = retView.findViewById( R.id.id_plugin_swap_time_current );
        final SeekBar seekBar = retView.findViewById( R.id.id_plugin_swap_time_seekbar );
        seekBar.setOnSeekBarChangeListener( new SeekBar.OnSeekBarChangeListener() {
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                currentTime.setText( progress + "毫秒" );
                sSwpTimes = progress;
            }
        } );

        // 初始化设置
        int swapTime = sPluginInfo.getSwapSetting().getSwapTime();
        currentTime.setText( swapTime + "毫秒" );
        seekBar.setProgress( swapTime );
        sSwpTimes = swapTime;

        // 扫货价格范围设置
        final EditText swapMinPriceEdit = retView.findViewById( R.id.id_plugin_swap_edit_minprice );
        final EditText swapMaxPriceEdit = retView.findViewById( R.id.id_plugin_swap_edit_maxprice );

        // 出货价格，出货数量设置
        final EditText salePriceEdit = retView.findViewById( R.id.id_plugin_swap_sale_price );
        final EditText saleNumEdit = retView.findViewById( R.id.id_plugin_swap_sale_num );

        // 按钮监听
        TextView saveBtn = retView.findViewById( R.id.id_plugin_swap_save );
        saveBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwapSetting swapSetting = sPluginInfo.getSwapSetting();
                swapSetting.setSwapTime( sSwpTimes );
                swapSetting.setMinSwapPrice( Integer.parseInt( swapMinPriceEdit.getText().toString() ) );
                swapSetting.setMaxSwapPrice( Integer.parseInt( swapMaxPriceEdit.getText().toString() ) );
                swapSetting.setSalePrice( Integer.parseInt( salePriceEdit.getText().toString() ) );
                swapSetting.setSaleNum( Integer.parseInt( saleNumEdit.getText().toString() ) );
                PluginMgr.set_swap_setting( swapSetting.getSwapTime(), swapSetting.getMinSwapPrice(), swapSetting.getMaxSwapPrice(),
                        swapSetting.getSalePrice(), swapSetting.getSaleNum() );
                sPopSwapSetting.dismiss();
            }
        } );

        TextView cleanBtn = retView.findViewById( R.id.id_plugin_swap_cancel );
        cleanBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ResetSwapView( pluginInfo.getSwapSetting().getPluginSwapSetingView() );
            }
        } );

        pluginInfo.getSwapSetting().setPluginSwapSetingView( retView );
        return retView;
    }

    // 显示插件视图
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static void ShowPluginView() {
        if (sPopWindow != null && sPopWindow.isShowing()) {
            return;
        }
        if (sPopChatSetting != null && sPopChatSetting.isShowing()) {
            return;
        }

        Context context = sActivity.getApplicationContext();
        WindowManager windowMgr = (WindowManager) context.getSystemService( Context.WINDOW_SERVICE );
        DisplayMetrics outMetrics = new DisplayMetrics();
        windowMgr.getDefaultDisplay().getMetrics( outMetrics );

        View pluginView = sPluginInfo.getPluginView();
        if (pluginView == null) {
            pluginView = CreatePluginView( sPluginInfo );
        }

        pluginView.setSystemUiVisibility( View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY );

        if (sPopWindow == null) {
            sPopWindow = new PopupWindow( pluginView, outMetrics.widthPixels / 10 * 7, outMetrics.heightPixels / 4 * 3 );
        } else {
            sPopWindow.setContentView( pluginView );
        }

        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        sPopWindow.setFocusable( true );

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable( 0xb0343434 );
        sPopWindow.setBackgroundDrawable( dw );

        // 设置popWindow的显示和消失动画
//        sPopWindow.setAnimationStyle(R.style.window_anim_style);

        //popWindow消失监听方法
        sPopWindow.setOnDismissListener( new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                System.out.println( "popWindow消失" );
            }
        } );

        sPopWindow.showAtLocation( sActivity.getWindow().getDecorView(), Gravity.CENTER, 0, 0 );
    }

    // 显示喊话设置视图
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static void ShowPluginHHSettingView() {
        if (sPopChatSetting != null && sPopChatSetting.isShowing()) {
            return;
        }

        Context context = sActivity.getApplicationContext();
        WindowManager windowMgr = (WindowManager) context.getSystemService( Context.WINDOW_SERVICE );
        DisplayMetrics outMetrics = new DisplayMetrics();
        windowMgr.getDefaultDisplay().getMetrics( outMetrics );

        View pluginHHSetingView = CreatePluginChatSettingView( sPluginInfo );

        pluginHHSetingView.setSystemUiVisibility( View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY );

        if (sPopChatSetting == null) {
            sPopChatSetting = new PopupWindow( pluginHHSetingView, outMetrics.widthPixels / 4 * 3, outMetrics.heightPixels / 8 * 7 );
        } else {
            sPopChatSetting.setContentView( pluginHHSetingView );
        }

        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        sPopChatSetting.setFocusable( true );

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable( 0xb0343434 );
        sPopChatSetting.setBackgroundDrawable( dw );

        //popWindow消失监听方法
        sPopChatSetting.setOnDismissListener( new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                System.out.println( "popWindow消失" );
                ShowPluginView();
            }
        } );
        sPopChatSetting.showAtLocation( sActivity.getWindow().getDecorView(), Gravity.CENTER, 0, 0 );
    }

    // 显示喊话设置视图
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static void ShowPluginSwapSettingView() {
        if (sPopSwapSetting != null && sPopSwapSetting.isShowing()) {
            return;
        }

        Context context = sActivity.getApplicationContext();
        WindowManager windowMgr = (WindowManager) context.getSystemService( Context.WINDOW_SERVICE );
        DisplayMetrics outMetrics = new DisplayMetrics();
        windowMgr.getDefaultDisplay().getMetrics( outMetrics );

        View pluginSwapSetingView = CreatePluginSwapSettingView( sPluginInfo );

        pluginSwapSetingView.setSystemUiVisibility( View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY );

        if (sPopSwapSetting == null) {
            sPopSwapSetting = new PopupWindow( pluginSwapSetingView, outMetrics.widthPixels / 4 * 3, outMetrics.heightPixels / 8 * 7 );
        } else {
            sPopSwapSetting.setContentView( pluginSwapSetingView );
        }

        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        sPopSwapSetting.setFocusable( true );

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable( 0xb0343434 );
        sPopSwapSetting.setBackgroundDrawable( dw );

        //popWindow消失监听方法
        sPopSwapSetting.setOnDismissListener( new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                System.out.println( "popWindow消失" );
                ShowPluginView();
            }
        } );
        sPopSwapSetting.showAtLocation( sActivity.getWindow().getDecorView(), Gravity.CENTER, 0, 0 );
    }
}

