package com.lcb.augustthree;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import com.warkiz.widget.IndicatorSeekBar;
import com.warkiz.widget.OnSeekChangeListener;
import com.warkiz.widget.SeekParams;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FourActivity extends AppCompatActivity {

    @BindView(R.id.center)
    ImageView center;
    @BindView(R.id.content)
    TextView content;
    @BindView(R.id.laytime)
    TextView laytime;
    @BindView(R.id.ns)
    NestedScrollView ns;
    @BindView(R.id.ns2)
    NestedScrollView ns2;
    @BindView(R.id.layout_rl)
    RelativeLayout layoutRl;
    @BindView(R.id.ll_light)
    LinearLayout llLight;
    @BindView(R.id.switch_1)
    Switch switch1;
    @BindView(R.id.switch_2)
    Switch switch2;
    @BindView(R.id.seek_bar1)
    IndicatorSeekBar seekBar1;
    @BindView(R.id.seek_bar2)
    IndicatorSeekBar seekBar2;
    @BindView(R.id.seek_bar3)
    IndicatorSeekBar seekBar3;
    @BindView(R.id.seek_bar4)
    IndicatorSeekBar seekBar4;

    private final static int DELAY_MSG = 1;
    private final static int SCROLL_MSG = 2;
    private String[] bgColor = {"#000000", "#FFFFFF", "#B7F472", "#B9BACC", "#86D9CF"};
    private String[] tvCColor = {"#000000", "#FFFFFF", "#CFE82B", "#B9BACC", "#86D9CF", "#0391CD", "#FFFE32", "#FD5308", "#E3203B", "#66B032"};
    private int layTimeS = 5;//倒数
    private int scrollTimeS = 5;
    private Handler handler;
    private int lastY = 1;
    private int currentY;
    private float density;
    private boolean isChangerAtDelay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four);
        ButterKnife.bind(this);
        density = Tip.getDensity(this) * 25;
        ns.setSmoothScrollingEnabled(true);
        ns.fling((int) density);//添加上这句滑动才有效
        initHandler();
        initSeekBar();
    }

    private void initSeekBar() {
        seekBar1.setOnSeekChangeListener(new OnSeekChangeListener() {
            @Override
            public void onSeeking(SeekParams seekParams) {
                content.setTextSize(seekParams.progress);
            }

            @Override
            public void onStartTrackingTouch(IndicatorSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(IndicatorSeekBar seekBar) {

            }
        });
        seekBar2.setOnSeekChangeListener(new OnSeekChangeListener() {
            @Override
            public void onSeeking(SeekParams seekParams) {
                content.setLineSpacing(seekParams.progress * 2 ,1);
            }

            @Override
            public void onStartTrackingTouch(IndicatorSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(IndicatorSeekBar seekBar) {

            }
        });
        seekBar3.setOnSeekChangeListener(new OnSeekChangeListener() {
            @Override
            public void onSeeking(SeekParams seekParams) {
                if (seekParams.progress == 0){
                    scrollTimeS = 5;
                }else {
                    scrollTimeS = seekParams.progress * 5;
                }
            }

            @Override
            public void onStartTrackingTouch(IndicatorSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(IndicatorSeekBar seekBar) {

            }
        });
        seekBar4.setOnSeekChangeListener(new OnSeekChangeListener() {
            @Override
            public void onSeeking(SeekParams seekParams) {
                isChangerAtDelay = true;
                layTimeS = seekParams.progress;
            }

            @Override
            public void onStartTrackingTouch(IndicatorSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(IndicatorSeekBar seekBar) {

            }
        });
        ns.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                currentY = scrollY;
            }
        });
    }

    @SuppressLint("HandlerLeak")
    private void initHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case DELAY_MSG:
                        laytime.setText(String.valueOf(layTimeS));
                        handler.sendEmptyMessageDelayed(DELAY_MSG, 1000);
                        if (layTimeS <= 0) {
                            Tip.log("/////");
                            laytime.setVisibility(View.GONE);
                            handler.removeMessages(DELAY_MSG);
                            handler.sendEmptyMessage(SCROLL_MSG);
                            center.setSelected(true);
                        }
                        layTimeS--;
                        break;
                    case SCROLL_MSG:
                        Tip.log("scrollTimeS: " + scrollTimeS);
                        ns.smoothScrollBy(0, 1);
                        if (lastY != currentY) {
                            handler.sendEmptyMessageDelayed(SCROLL_MSG,scrollTimeS);
                        }else {
                            center.setSelected(false);
                        }
                        lastY = currentY;
                        break;
                }
            }
        };
    }

    @OnClick({R.id.center, R.id.left, R.id.right, R.id.bg_one, R.id.bg_two, R.id.bg_three,
            R.id.bg_four, R.id.bg_five, R.id.tv_c_one, R.id.tv_c_two, R.id.tv_c_three,
            R.id.tv_c_four, R.id.tv_c_five, R.id.tv_c_six, R.id.tv_c_seven, R.id.tv_c_eight,
            R.id.tv_c_nine, R.id.tv_c_ten, R.id.switch_1, R.id.switch_2, R.id.ll_dismiss})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.center:
                if (center.isSelected()){
                    center.setSelected(false);
                    handler.removeMessages(SCROLL_MSG);
                }else {
                    center.setSelected(true);
                    handler.sendEmptyMessage(SCROLL_MSG);
                }
                break;
            case R.id.left:
                if (ns2.getVisibility() == View.VISIBLE){
                    ns2.setVisibility(View.GONE);
                    if (isChangerAtDelay) {
                        handler.removeMessages(SCROLL_MSG);
                        laytime.setVisibility(View.VISIBLE);
                        handler.sendEmptyMessage(DELAY_MSG);
                    }
                }else {
                    finish();
                }
                break;
            case R.id.right:
                isChangerAtDelay = false;
                ns2.setVisibility(View.VISIBLE);
                break;
            case R.id.ll_dismiss:
                ns2.setVisibility(View.GONE);
                if (isChangerAtDelay) {
                    handler.removeMessages(SCROLL_MSG);
                    laytime.setVisibility(View.VISIBLE);
                    handler.sendEmptyMessage(DELAY_MSG);
                }
                break;
            case R.id.bg_one:
                layoutRl.setBackgroundColor(Color.parseColor(bgColor[0]));
                break;
            case R.id.bg_two:
                layoutRl.setBackgroundColor(Color.parseColor(bgColor[1]));
                break;
            case R.id.bg_three:
                layoutRl.setBackgroundColor(Color.parseColor(bgColor[2]));
                break;
            case R.id.bg_four:
                layoutRl.setBackgroundColor(Color.parseColor(bgColor[3]));
                break;
            case R.id.bg_five:
                layoutRl.setBackgroundColor(Color.parseColor(bgColor[4]));
                break;
            case R.id.tv_c_one:
                content.setTextColor(Color.parseColor(tvCColor[0]));
                break;
            case R.id.tv_c_two:
                content.setTextColor(Color.parseColor(tvCColor[1]));
                break;
            case R.id.tv_c_three:
                content.setTextColor(Color.parseColor(tvCColor[2]));
                break;
            case R.id.tv_c_four:
                content.setTextColor(Color.parseColor(tvCColor[3]));
                break;
            case R.id.tv_c_five:
                content.setTextColor(Color.parseColor(tvCColor[4]));
                break;
            case R.id.tv_c_six:
                content.setTextColor(Color.parseColor(tvCColor[5]));
                break;
            case R.id.tv_c_seven:
                content.setTextColor(Color.parseColor(tvCColor[6]));
                break;
            case R.id.tv_c_eight:
                content.setTextColor(Color.parseColor(tvCColor[7]));
                break;
            case R.id.tv_c_nine:
                content.setTextColor(Color.parseColor(tvCColor[8]));
                break;
            case R.id.tv_c_ten:
                content.setTextColor(Color.parseColor(tvCColor[9]));
                break;
            case R.id.switch_1:
                Tip.log("2: " + switch1.isChecked());
                if (switch1.isChecked()) {
                    switch1.setChecked(true);
                    llLight.setVisibility(View.VISIBLE);
                } else {
                    switch1.setChecked(false);
                    llLight.setVisibility(View.GONE);
                }
                break;
            case R.id.switch_2:
                if (switch2.isChecked()) {
                    switch2.setChecked(true);
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                } else {
                    switch2.setChecked(false);
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                }
                break;
        }
    }

}