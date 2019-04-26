package com.example.secondapk;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.FillEventHistory;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;
import java.util.List;

import listview.myBean;

public class MainActivity extends AppCompatActivity {
    private String data[] = {"aa","bb","cc","dd","aa","bb","cc","dd","aa","bb","cc","dd","aa","bb","cc","dd"};
    private List<myBean> myBeanList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        SlidingMenu menu = new SlidingMenu( this );     //获取Slidingmenu的实例

        init();



        menu.setMode( SlidingMenu.LEFT );

        menu.setTouchModeAbove( SlidingMenu.TOUCHMODE_FULLSCREEN );

        menu.setTouchModeAbove( SlidingMenu.TOUCHMODE_FULLSCREEN );
        menu.setShadowWidthRes( R.dimen.shadow_width );
        menu.setShadowDrawable( R.color.colorAccent );

        // 设置滑动菜单视图的宽度
        menu.setBehindOffsetRes( R.dimen.slidingmenu_offset );
        Log.d( "dddddddd", "onCreate: " + menu.getHeight() );
//        ( 1 );
        // 设置渐入渐出效果的值
        menu.setFadeDegree( 0.35f );
        /**
         * SLIDING_WINDOW will include the Title/ActionBar in the content
         * section of the SlidingMenu, while SLIDING_CONTENT does not.
         */
        menu.attachToActivity( this, SlidingMenu.SLIDING_CONTENT );
        //为侧滑菜单设置布局
        menu.setMenu( R.layout.listview_layout );

        ListView listView = (ListView) findViewById(R.id.listview);//在视图中找到ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);//新建并配置ArrayAapeter
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        Toast.makeText(MainActivity.this,"你点击了"+i+"按钮",Toast.LENGTH_SHORT).show();
                        break;//当我们点击某一项就能吐司我们点了哪一项

                    case 1:
                        Toast.makeText(MainActivity.this,"你点击了"+i+"按钮",Toast.LENGTH_SHORT).show();
                        break;

                    case 2:
                        Toast.makeText(MainActivity.this,"你点击了"+i+"按钮",Toast.LENGTH_SHORT).show();
                        break;

                    case 3:
                        Toast.makeText(MainActivity.this,"你点击了"+i+"按钮",Toast.LENGTH_SHORT).show();
                        break;

                    case 4:
                        Toast.makeText(MainActivity.this,"你点击了"+i+"按钮",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });


    }

    private void init() {//初始化数据
        myBean bean1 = new myBean( "aa", R.mipmap.ic_launcher );
        myBeanList.add( bean1 );

        myBean bean2 = new myBean( "ss", R.mipmap.ic_launcher );
        myBeanList.add( bean2 );

        myBean bean3 = new myBean( "jj", R.mipmap.ic_launcher );
        myBeanList.add( bean3 );

        myBean bean4 = new myBean( "hh", R.mipmap.ic_launcher );
        myBeanList.add( bean4 );

        myBean bean5 = new myBean( "dd", R.mipmap.ic_launcher );
        myBeanList.add( bean5 );

        myBean bean6 = new myBean( "cc", R.mipmap.ic_launcher );
        myBeanList.add( bean6 );

        myBean bean7 = new myBean( "bb", R.mipmap.ic_launcher );
        myBeanList.add( bean7 );
        myBean bean8 = new myBean( "jj", R.mipmap.ic_launcher );
        myBeanList.add( bean8 );
        myBean bean9 = new myBean( "kk", R.mipmap.ic_launcher );
        myBeanList.add( bean9 );

    }

    public void test(View view) {
        Toast.makeText( this, "dddd", Toast.LENGTH_SHORT ).show();
        Log.d( "ddddd", "test: " );
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
