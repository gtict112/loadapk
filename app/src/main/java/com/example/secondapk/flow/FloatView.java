package com.example.secondapk.flow;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.example.secondapk.R;
import com.example.secondapk.view.PluginViewMgr;

import static com.example.secondapk.flow.ResourceMgr.inflate;


public class FloatView implements IFloatUI, OnClickListener {
    /**
     * 记录系统状态栏的高度
     */
    private static int statusBarHeight;
    private static WindowManager wManager;
    /**
     * 工作目录
     */
    String workDir;
    /**
     * 记录当前手指位置在屏幕上的横坐标值
     */
    private float xInScreen;
    /**
     * 记录当前手指位置在屏幕上的纵坐标值
     */
    private float yInScreen;
    /**
     * 记录手指按下时在屏幕上的横坐标的值
     */
    private float xDownInScreen;
    /**
     * 记录手指按下时在屏幕上的纵坐标的值
     */
    private float yDownInScreen;
    /**
     * 记录手指按下时在小悬浮窗的View上的横坐标的值
     */
    private float xInView;
    /**
     * 记录手指按下时在小悬浮窗的View上的纵坐标的值
     */
    private float yInView;
    private Activity mActivity;
    private WindowManager.LayoutParams wmParams;
    /**
     * 悬浮窗
     */
    private ImageView floatIV;
    /**
     * 悬浮窗提示的小点
     */
    private TextView floatDot;
    /**
     * 消息提示的小点
     */
    private TextView notiftyDot;
    private int screenWidth;
    /**
     * float menu
     */
    private PopupWindow mPopupWindow;
    /**
     * 放置按钮的View
     */
    private CountDownTimer mHideTimer;
    /**
     * 悬浮窗的父控件
     */
    private View mParentView;
    private OnTouchListener touchListener;
    private int LEFT = 0;
    private int RIGHT = 1;
    /**
     * 悬浮窗要隐藏的位置
     */
    private int mHintLocation = LEFT;
    /**
     * 右边的图标
     */
    private ImageView rightIconIV;
    /**
     * 左边的图标
     */
    private ImageView leftIconIV;
    /**
     * 悬浮窗隐藏的距离
     */
    private int length;
    /**
     * 悬浮窗菜单栏的父控件
     */
    private View popMainView;
    /**
     * 小红点的状态
     */
    private boolean dotStatus;
    /**
     * float menu的高度
     */
    private int viewHeight = 30; //
    private int viewHeight2 =20;

    public FloatView(Activity activity, String workDir) {
        this.mActivity = activity;
        this.workDir = workDir;
        initView();
    }

    private void initView() {
        new Handler( Looper.getMainLooper() ).post( new Runnable() {
            @Override
            public void run() {
                initFloatWindowManage();
                initFloat();
                floatBtnEvent();
                initTimer();
                initFloatLaout();
                PluginViewMgr.init( mActivity, workDir );
            }
        } );
    }

    @SuppressWarnings("deprecation")
    private void initFloatWindowManage() {
        wManager = mActivity.getWindowManager();
        screenWidth = wManager.getDefaultDisplay().getWidth();
        int screenHeigth = wManager.getDefaultDisplay().getHeight();
        wmParams = new WindowManager.LayoutParams();
        wmParams.format = PixelFormat.RGBA_8888;
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS
                | WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        wmParams.gravity = Gravity.LEFT | Gravity.TOP;
        wmParams.x = screenWidth;
        wmParams.y = screenHeigth / 2;
        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
    }

    private void initFloat() {
        viewHeight = XYGameSDKUtil.dip2px( mActivity, viewHeight );//转化成px
        mParentView = inflate( R.layout.xygame_float_layout, null );

        floatIV = mParentView.findViewById( R.id.xygame_float_iv );
        floatDot = mParentView.findViewById( R.id.xygame_float_dot_tv );
        //判断状态栏是否显示 如果不显示则statusBarHeight为0
        WindowManager.LayoutParams attrs = mActivity.getWindow().getAttributes();
        statusBarHeight = 0;
        wManager.addView( mParentView, wmParams );
    }

    private void initTimer() {
        length = viewHeight / 3;    //隐藏一半
        mHideTimer = new CountDownTimer( 2000, 2000 ) {        //悬浮窗超过5秒没有操作的话会自动隐藏
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {        //隐藏
                if (!dotStatus) {    //如果有显示小红点的话则不隐藏
                    if (mHintLocation == LEFT) {
                        TranslateAnimation translateAnimation = new TranslateAnimation( -length, -length, 0, 0 );
                        translateAnimation.setDuration( 10000 );
                        translateAnimation.setFillAfter( true );
                        floatIV.startAnimation( translateAnimation );
                        floatIV.setOnTouchListener( null );//隐藏touch事件
                        wManager.updateViewLayout( mParentView, wmParams );
                    } else {
                        TranslateAnimation translateAnimation = new TranslateAnimation( length, length, 0, 0 );
                        translateAnimation.setDuration( 10000 );
                        translateAnimation.setFillAfter( true );
                        floatIV.startAnimation( translateAnimation );
                        floatIV.setOnTouchListener( null );//隐藏touch事件
                        wManager.updateViewLayout( mParentView, wmParams );
                    }
                }
            }
        };
    }

    /**
     * 悬浮窗的点击事件
     */
    @SuppressLint("ClickableViewAccessibility")
    private void floatBtnEvent() {
        touchListener = new OnTouchListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        floatEventDown( event );
                        break;
                    case MotionEvent.ACTION_MOVE:
                        floatEventMove( event );
                        break;
                    case MotionEvent.ACTION_UP:
                        floatEventUp();
                        break;
                }
                return true;
            }
        };
        floatIV.setOnTouchListener( touchListener );

        floatIV.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mHintLocation == LEFT) {
                    TranslateAnimation translateAnimation = new TranslateAnimation( 0, 0, 0, 0 );
                    translateAnimation.setDuration( 10000 );
                    translateAnimation.setFillAfter( true );
                    floatIV.startAnimation( translateAnimation );
                    floatIV.setOnTouchListener( touchListener );//隐藏touch事件
                } else {
                    TranslateAnimation translateAnimation = new TranslateAnimation( 0, 0, 0, 0 );
                    translateAnimation.setDuration( 10000 );
                    translateAnimation.setFillAfter( true );
                    floatIV.startAnimation( translateAnimation );
                    floatIV.setOnTouchListener( touchListener );//隐藏touch事件
                }

                wManager.updateViewLayout( mParentView, wmParams );
                floatIV.setOnTouchListener( touchListener );//隐藏touch事件
            }
        } );
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void floatEventDown(MotionEvent event) {
        xInView = event.getX();
        yInView = event.getY();
        xDownInScreen = event.getRawX();
        yDownInScreen = event.getRawY() - statusBarHeight;
        xInScreen = event.getRawX();
        yInScreen = event.getRawY() - statusBarHeight;
        floatIV.setBackground( ResourceMgr.FindResourceById( R.mipmap.float_btn_show_icon ) );
        if (mHideTimer != null) mHideTimer.cancel();
    }

    private void floatEventMove(MotionEvent event) {
        xInScreen = event.getRawX();
        yInScreen = event.getRawY() - statusBarHeight;
        if (xInScreen != xDownInScreen && yInScreen != yDownInScreen) {
            removeBigWindow();
        }
        wmParams.x = (int) (xInScreen - xInView);
        wmParams.y = (int) (yInScreen - yInView);
        updateViewPosition(); // 手指移动的时候更新小悬浮窗的位置
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void floatEventUp() {
        if (xInScreen < screenWidth / 2) {   //在左边
            wmParams.x = 0;
            mHintLocation = LEFT;
        } else {                   //在右边
            wmParams.x = screenWidth + 100;
            mHintLocation = RIGHT;
        }
        floatIV.setBackground( ResourceMgr.FindResourceById( R.mipmap.float_btn_hide_icon ) );
        if (mHideTimer != null) mHideTimer.start();
        updateViewPosition();
        if (xInScreen == xDownInScreen && yInScreen == yDownInScreen) {        //点击
            Log.d( "dianji", "floatEventUp: " );
            PluginViewMgr.ShowPluginView();
        }
    }

    /**
     * 初始化float menu
     */
    private void initFloatLaout() {
        popMainView = inflate( R.layout.xygame_float_menu_layout, null );
        notiftyDot = popMainView.findViewById( R.id.float_menu_dot_tv );

        rightIconIV = popMainView.findViewById( R.id.float_menu_right_iv );
        leftIconIV = popMainView.findViewById( R.id.float_menu_left_iv );

        rightIconIV.setOnClickListener( this );
        leftIconIV.setOnClickListener( this );
//        personalCenterTV.setOnClickListener( this );
//        giftTV.setOnClickListener( this );
//        noticeTV.setOnClickListener( this );
//        helpTV.setOnClickListener( this );
    }

    @Override
    public void onClick(View v) {
    }

    /**
     * 初始化大窗口
     */
    @SuppressWarnings("deprecation")
    private void initMenu() {
        if (mPopupWindow == null && floatIV != null) {
            mPopupWindow = new PopupWindow( popMainView, LayoutParams.WRAP_CONTENT,
                    viewHeight2, true );
            mPopupWindow.setFocusable( true );
            mPopupWindow.setOutsideTouchable( true );
            mPopupWindow.setBackgroundDrawable( new BitmapDrawable() );
            mPopupWindow.setOnDismissListener( new OnDismissListener() {    //大悬浮窗的隐藏监听
                @Override
                public void onDismiss() {    //隐藏时候的操作
                    if (mHideTimer != null) mHideTimer.start();
                }
            } );
        }
    }

    /**
     * 显示左大悬浮窗
     */
    private void openLeftMenu() {
        mPopupWindow.showAtLocation( floatIV, Gravity.CENTER,
                0, 0 );
        rightIconIV.setVisibility( View.GONE );
        leftIconIV.setVisibility( View.VISIBLE );
    }

    /**
     * 显示右大悬浮窗
     */
    private void openRightMenuw() {
        mPopupWindow.showAtLocation( floatIV, Gravity.CENTER,
                0, 0 );
        rightIconIV.setVisibility( View.VISIBLE );
        leftIconIV.setVisibility( View.GONE );
    }

    /**
     * 更新小悬浮窗在屏幕中的位置。
     */
    private void updateViewPosition() {
        wManager.updateViewLayout( mParentView, wmParams );
    }

    /**
     * 移除大悬浮窗
     */
    private void removeBigWindow() {
        if (mPopupWindow != null) {
            mPopupWindow.dismiss();
            mPopupWindow = null;
        }
    }

    /**
     * 移除所有悬浮窗
     */
    public void removeAllWindow() {
        try {
            mHideTimer.cancel();
            removeBigWindow();
            mPopupWindow = null;
            //mParentView.removeAllViews();
            wManager.removeViewImmediate( mParentView );
        } catch (Exception e) {
        }
    }

    @Override
    public void destoryFloat() {
        removeAllWindow();
    }

    /**
     * 设置小红点的状态
     *
     * @param isShow 是否有新消息
     */
    public void setDotStatus(boolean isShow) {
        invalidataDot( isShow );
    }

    /**
     * 刷新红点状态
     *
     * @param isShow 是否显示
     */
    private void invalidataDot(boolean isShow) {
        if (isShow) {
            floatDot.setVisibility( View.VISIBLE );
            notiftyDot.setVisibility( View.VISIBLE );
        } else {
            floatDot.setVisibility( View.GONE );
            notiftyDot.setVisibility( View.GONE );
        }
        dotStatus = isShow;
    }
}
