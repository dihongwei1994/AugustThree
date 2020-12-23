package com.lcb.augustthree;

import android.animation.LayoutTransition;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.widget.NestedScrollView;

import java.util.List;

/**
 * Created by xp on 2017/4/5.
 */

public class XFloatView extends RelativeLayout implements View.OnClickListener {

    private Context mContext;
    private WindowManager wm;
    private WindowManager.LayoutParams wmParams;
    private float xInView;
    private float yInView;
    private float xInScreen;
    private float yInScreen;
    private float mStartX;
    private float mStartY;
    private float border = 30;
    private NestedScrollView scrollView;
    private TextView TV;
    private ImageView scale_,play;
    private RelativeLayout layoutRl;
    private LinearLayout ll;
    private float lastX,lastY;
    private float xValue,yValue;
    private String direction;

    public XFloatView(Context context) {
        this(context, null);
    }

    public XFloatView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XFloatView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initFloatView(context);
    }

    private void initFloatView(Context context) {
        this.mContext = context;
        border = border * Tip.getDensity((Activity) mContext);
        RelativeLayout inflate = (RelativeLayout) LayoutInflater.from(this.mContext).inflate(R.layout.layout_float, this);
        inflate.findViewById(R.id.camera).setOnClickListener(this::onClick);
        inflate.findViewById(R.id.meitu).setOnClickListener(this::onClick);
        inflate.findViewById(R.id.douyin).setOnClickListener(this::onClick);
        inflate.findViewById(R.id.kuaishou).setOnClickListener(this::onClick);
        inflate.findViewById(R.id.one).setOnClickListener(this::onClick);
        inflate.findViewById(R.id.two).setOnClickListener(this::onClick);
        inflate.findViewById(R.id.three).setOnClickListener(this::onClick);
        inflate.findViewById(R.id.four).setOnClickListener(this::onClick);
        inflate.findViewById(R.id.play).setOnClickListener(this::onClick);
        inflate.findViewById(R.id.set).setOnClickListener(this::onClick);

        scrollView = inflate.findViewById(R.id.scroll_view);
        TV = inflate.findViewById(R.id.content);
        scale_ = inflate.findViewById(R.id.scale_);
        play = inflate.findViewById(R.id.play);
        layoutRl = inflate.findViewById(R.id.layout_rl);
        ll = inflate.findViewById(R.id.ll);
        ll.setSelected(false);
        this.wm = (WindowManager) this.getContext().getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
       scale_.setOnTouchListener(new OnTouchListener() {
           @SuppressLint("ClickableViewAccessibility")
           @Override
           public boolean onTouch(View v, MotionEvent event) {
               Rect frame = new Rect();
               getWindowVisibleDisplayFrame(frame);
               int statusBar = frame.top;
               xInScreen = event.getRawX();
               yInScreen = event.getRawY() - (float) statusBar;
               switch (event.getAction()) {
                   case MotionEvent.ACTION_DOWN:
                       xInView = event.getX();
                       yInView = event.getY();
                       mStartX = xInScreen;
                       mStartY = yInScreen;
                       lastX = xInScreen;
                       lastY = yInScreen;
                       break;
                   case MotionEvent.ACTION_MOVE:
                       xInScreen = event.getRawX();
                       yInScreen = event.getRawY() - (float) statusBar;
                       xValue = lastX - xInScreen;
                       yValue = lastY - yInScreen;
                       wmParams.width = (int) (getWidth() - xValue);
                       wmParams.height = (int) (getHeight() - yValue);
                       wm.updateViewLayout(XFloatView.this, wmParams);
                       setLayoutTransition(new LayoutTransition());
                       lastX = xInScreen;
                       lastY = yInScreen;
                       break;
               }
               return true;
           }
       });
    }

    public NestedScrollView getScrollView() {
        return scrollView;
    }

    public TextView getTV() {
        return TV;
    }

    public ImageView getPlay() {
        return play;
    }

    public RelativeLayout getLayoutRl() {
        return layoutRl;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Rect frame = new Rect();
        this.getWindowVisibleDisplayFrame(frame);
        int statusBar = frame.top;
        this.xInScreen = event.getRawX();
        this.yInScreen = event.getRawY() - (float) statusBar;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                this.xInView = event.getX();
                this.yInView = event.getY();
                this.mStartX = this.xInScreen;
                this.mStartY = this.yInScreen;
                lastX = xInScreen;
                lastY = yInScreen;
                break;
            case MotionEvent.ACTION_MOVE:
                this.xInScreen = event.getRawX();
                this.yInScreen = event.getRawY() - (float) statusBar;
                wmParams.y = (int) (this.yInScreen - this.yInView);
                wmParams.x = (int) (this.xInScreen - this.xInView);
                wm.updateViewLayout(this, wmParams);
                setLayoutTransition(new LayoutTransition());
                lastX = xInScreen;
                lastY = yInScreen;
                break;
        }
        return true;
    }

    public void setWmParams(WindowManager.LayoutParams wmParams) {
        this.wmParams = wmParams;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.camera://相机
                Intent intent = new Intent();
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                mContext.startActivity(intent);
                break;
            case R.id.meitu://美图秀秀
                jump("com.mt.mtxx.mtxx");
                break;
            case R.id.douyin://抖音
                jump("com.ss.android.ugc.aweme");
                break;
            case R.id.kuaishou://快手
                jump("com.smile.gifmaker");
                break;
            case R.id.one://退出
                wm.removeView(this);
                break;
            case R.id.two://全屏
                //判断当前屏幕方向
                if (lister != null){
                    lister.changer();
                }
                break;
            case R.id.three://隐藏
                if (ll.isSelected()){
                    ll.setVisibility(GONE);
                    ll.setSelected(false);
                }else {
                    ll.setVisibility(VISIBLE);
                    ll.setSelected(true);
                }
                break;
            case R.id.four://缩小
                wmParams.width = (int) (150 * Tip.getDensity((Activity) mContext));
                wmParams.width = (int) (200 * Tip.getDensity((Activity) mContext));
                wm.updateViewLayout(this, wmParams);
                setLayoutTransition(new LayoutTransition());
                break;
            case R.id.play://播放
                if (lister != null){
                    if (play.isSelected()){
                        play.setSelected(false);
                        lister.scroll(false);
                    }else {
                        play.setSelected(true);
                        lister.scroll(true);
                    }
                }
                break;
            case R.id.set://设置
                Tip.log("set1 --" + (lister != null));
                if (lister != null){
                    lister.showSet(true);
                }
                break;
        }
    }

    private void jump(String packageName){
        if (hasApp(packageName)){
            mContext.startActivity(mContext.getPackageManager().getLaunchIntentForPackage(packageName));
        }else {
            Toast.makeText(mContext, "请先安装该软件", Toast.LENGTH_SHORT).show();
        }
    }

    //判断手机是否安装了该app
    public boolean hasApp(String packageName) {
        final PackageManager packageManager = mContext.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals(packageName)) {
                    return true;
                }
            }
        }
        return false;
    }

    public interface OnDirectionLister{
        void changerTextContent(String content);
        void changer();
        void scroll(boolean isScroll);
        void showSet(boolean isShow);
        void onChanger(int time);

    }

    private OnDirectionLister lister;

    public void setLister(OnDirectionLister lister) {
        this.lister = lister;
    }
}
