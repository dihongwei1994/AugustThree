package com.lcb.augustthree;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OneActivity extends AppCompatActivity {

    @BindView(R.id.add)
    ImageView add;
    @BindView(R.id.recycle)
    RecyclerView recycle;
    @BindView(R.id.time_out)
    TextView timeOut;
    @BindView(R.id.rl)
    RelativeLayout rl;

    private final static int SCROLL_MSG = 1;
    private SearchAdapter adapter;
    private WindowManager mWindowMgr;
    private WindowManager.LayoutParams wmParams;
    private XFloatView mFloatView;
    private Handler handler;
    private NestedScrollView scrollView;
    private RelativeLayout layoutRl;
    private TextView textView;
    private ImageView play;
    private int lastY = 1;
    private int currentY;
    private Set2View setView;
    private WindowManager.LayoutParams layoutParams;
    private XFloatView.OnDirectionLister lister;
    private boolean isSetShow = false;
    private int longTime = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);
        ButterKnife.bind(this);
        init();
    }


    //初始化
    private void init() {
        recycle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new SearchAdapter(this);
        recycle.setAdapter(adapter);
        adapter.setListener(new OnItemClickListener() {
            @Override
            public void onClick(String content) {
                if (! Settings.canDrawOverlays(OneActivity.this)) {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
                    startActivityForResult(intent,10);
                }
                showFloatingWindow(content);
            }

            @Override
            public void dataNull() {
                timeOut.setVisibility(View.VISIBLE);
            }
        });
    }

    //Handlr初始化
    private void setHandler() {
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case SCROLL_MSG:
                        if (scrollView != null) {
                            Tip.log("lastY" + lastY + "\t\tosy: " + currentY);
                            scrollView.smoothScrollBy(0, 1);
                        }
                        if (lastY != currentY) {
                            handler.sendEmptyMessageDelayed(SCROLL_MSG, longTime);
                        }else {
                            play.setSelected(false);
                        }
                        lastY = currentY;
                        break;
                    case 2:
                        break;
                }
                return false;
            }
        });

        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                currentY = scrollY;
            }
        });
    }

    //展示悬浮窗
    private void showFloatingWindow(String content) {
        mWindowMgr = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        mFloatView = new XFloatView(this);
        initXFloat(content);
        wmParams = new WindowManager.LayoutParams();
        wmParams.format = PixelFormat.RGBA_8888;
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        wmParams.gravity = Gravity.LEFT | Gravity.TOP;
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
        setHandler();
    }

    //初始化悬浮框内的视图
    private void initXFloat(String content) {
        scrollView = mFloatView.getScrollView();
        layoutRl = mFloatView.getLayoutRl();
        textView = mFloatView.getTV();
        play = mFloatView.getPlay();
        textView.setText(content);
        lister = new XFloatView.OnDirectionLister() {
            @Override
            public void changerTextContent(String content) {
                textView.setText(content);
            }

            @Override
            public void changer() {
                //判断当前屏幕方向
                if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                    //切换竖屏
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                } else {
                    //切换横屏
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                }
            }

            @Override
            public void scroll(boolean isScroll) {
                if (isScroll) {
                    handler.sendEmptyMessage(SCROLL_MSG);
                } else {
                    handler.removeMessages(SCROLL_MSG);
                }
            }

            @Override
            public void showSet(boolean isShow) {
                Tip.log("set2--" + isShow);
                if (isShow && !isSetShow) {
                    OneActivity.this.showSet();
                    isSetShow = true;
                } else {
                    isSetShow = false;
                    mWindowMgr.removeView(setView);
                }
            }

            @Override
            public void onChanger(int time) {
                longTime = time;
            }
        };
        mFloatView.setLister(lister);
    }

    //展示SetView（是一个悬浮窗，因为需要在别的应用上也能弹出来）
    private void showSet(){
        if (layoutParams == null){
            layoutParams = new WindowManager.LayoutParams();
            layoutParams.format = PixelFormat.RGBA_8888;
            layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
            layoutParams.gravity = Gravity.CENTER;
            wmParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            if (Build.VERSION.SDK_INT >= 26) {
                layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
            } else if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 23) {
                layoutParams.type = WindowManager.LayoutParams.TYPE_TOAST;
            } else {
                layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
            }
        }
        if (setView == null){
            setView = new Set2View(this);
            setView.initSeekBar(textView,layoutRl,lister);
        }
        Tip.log("set 6--");
        mWindowMgr.addView(setView, layoutParams);
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<SearchBean> list = MyApp.getInstance().getList();
        if (list.size() != 0) {
            adapter.setList(list);
            timeOut.setVisibility(View.GONE);
        } else {
            timeOut.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.add, R.id.direction,R.id.right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.direction:
            case R.id.right:
                startActivity(new Intent(this, ThreeActivity.class));
                break;
            case R.id.add:
                startActivity(new Intent(this, TwoActivity.class));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeMessages(SCROLL_MSG);
        handler.removeMessages(2);
    }

}