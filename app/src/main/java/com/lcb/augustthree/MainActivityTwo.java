package com.lcb.augustthree;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

public class MainActivityTwo extends AppCompatActivity implements View.OnClickListener {

    private Button mShowBtn, mCloseBtn;
    private WindowManager mWindowMgr;
    private WindowManager.LayoutParams wmParams;
    private XFloatView mFloatView;
    private Handler handler;
    private NestedScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_two);
        mShowBtn = (Button) findViewById(R.id.show_btn);
        mCloseBtn = (Button) findViewById(R.id.close_btn);
        mShowBtn.setOnClickListener(this);
        mCloseBtn.setOnClickListener(this);
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                switch (msg.what){
                    case 1:
                        break;
                    case 2:
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.show_btn:
                showFloatingWindow();
                onBackPressed();
                break;
            case R.id.close_btn:
                if (null != mFloatView) {
                    mWindowMgr.removeView(mFloatView);
                    mFloatView = null;
                }
                break;
        }
    }

    public void showFloatingWindow() {
        mWindowMgr = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        mFloatView = new XFloatView(this);
        mFloatView.setOnClickListener(this);
        scrollView = mFloatView.getScrollView();
        wmParams = new WindowManager.LayoutParams();
        wmParams.format = PixelFormat.RGBA_8888;
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        wmParams.gravity = Gravity.LEFT | Gravity.TOP;
        wmParams.width = 200;
        wmParams.height = 300;
        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        if (Build.VERSION.SDK_INT >= 26) {
            wmParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 23) {
            wmParams.type = WindowManager.LayoutParams.TYPE_TOAST;
        } else {
            wmParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        }
        mFloatView.setWmParams(wmParams);
        mWindowMgr.addView(mFloatView, wmParams);
    }

}
