package com.lcb.augustthree;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.warkiz.widget.IndicatorSeekBar;
import com.warkiz.widget.OnSeekChangeListener;
import com.warkiz.widget.SeekParams;

/**
 * Created by xp on 2017/4/5.
 */

public class Set2View extends RelativeLayout implements View.OnClickListener {

    IndicatorSeekBar seekBar;
    IndicatorSeekBar seekBar0;
    IndicatorSeekBar seekBar2;
    IndicatorSeekBar seekBar3;
    RadioButton tvBase;
    RadioButton tvChanger;
    RecyclerView recycle;

    private XFloatView.OnDirectionLister lister;
    private Context mContext;
    private TextView tv;
    private ChangerAdapter adapter;

    public Set2View(Context context) {
        this(context, null);
    }

    public Set2View(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Set2View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initFloatView(context);
    }

    private void initFloatView(Context context) {
        this.mContext = context;
        RelativeLayout inflate = (RelativeLayout) LayoutInflater.from(this.mContext).inflate(R.layout.popup_set, this);
        recycle = inflate.findViewById(R.id.recycle);
        tvChanger = inflate.findViewById(R.id.tv_changer);
        tvBase = inflate.findViewById(R.id.tv_base);
        seekBar = inflate.findViewById(R.id.seek_bar);
        seekBar0 = inflate.findViewById(R.id.seek_bar0);
        seekBar2 = inflate.findViewById(R.id.seek_bar2);
        seekBar3 = inflate.findViewById(R.id.seek_bar3);
        inflate.findViewById(R.id.tv_base).setOnClickListener(this::onClick);
        inflate.findViewById(R.id.tv_changer).setOnClickListener(this::onClick);
        inflate.findViewById(R.id.cancel).setOnClickListener(this::onClick);
        recycle.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
    }

    public void initSeekBar(TextView tv, RelativeLayout layout,XFloatView.OnDirectionLister lister) {
        this.tv = tv;
        this.lister = lister;
        seekBar.setProgress(tv.getTextSize() / Tip.getDensity((Activity) mContext));
        seekBar2.setProgress(tv.getCurrentTextColor());
        seekBar.setOnSeekChangeListener(new OnSeekChangeListener() {
            @Override
            public void onSeeking(SeekParams seekParams) {
                tv.setTextSize(seekParams.progress);
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
                Tip.log("seekBar2: " + seekParams.progress);
                String color = getColor(seekParams.progress);
                Tip.log(color);
                if (color != null && !color.isEmpty()) {
                    tv.setTextColor(Color.parseColor(color));
                }
            }

            @Override
            public void onStartTrackingTouch(IndicatorSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(IndicatorSeekBar seekBar) {

            }
        });
        seekBar3.setOnSeekChangeListener(new OnSeekChangeListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onSeeking(SeekParams seekParams) {
                String color = getColor(seekParams.progress);
                Tip.log(color);
                if (color != null && !color.isEmpty()) {
                    GradientDrawable background = (GradientDrawable) layout.getBackground();
                    background.setColor(mContext.getResources().getColor(Color.parseColor(color)));
                    layout.setBackground(background);
                }
            }

            @Override
            public void onStartTrackingTouch(IndicatorSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(IndicatorSeekBar seekBar) {

            }
        });
        seekBar0.setOnSeekChangeListener(new OnSeekChangeListener() {
            @Override
            public void onSeeking(SeekParams seekParams) {
                if (lister != null) {
                    lister.onChanger(seekParams.progress * 5);
                }
            }

            @Override
            public void onStartTrackingTouch(IndicatorSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(IndicatorSeekBar seekBar) {

            }
        });
    }

    //获取颜色
    private String getColor(int progress) {
        String color = "";
        Tip.log("dafa: " + (progress / 2));
        switch (progress) {
            case 16:
                color = "#000000";
                break;
            case 15:
                color = "#D0000000";
                break;
            case 14:
                color = "#B0000000";
                break;
            case 13:
                color = "#90000000";
                break;
            case 12:
                color = "#70000000";
                break;
            case 11:
                color = "#50000000";
                break;
            case 10:
                color = "#30000000";
                break;
            case 9:
                color = "#10000000";
                break;
            case 8:
                color = "#ffffff";
                break;
            case 7:
                color = "#D0ffffff";
                break;
            case 6:
                color = "#B0ffffff";
                break;
            case 5:
                color = "#90ffffff";
                break;
            case 4:
                color = "#70ffffff";
                break;
            case 3:
                color = "#50ffffff";
                break;
            case 2:
                color = "#30ffffff";
                break;
            case 1:
                color = "#10ffffff";
                break;
        }
        Tip.log(color + "***");
        return color;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_base:
                tvChanger.setTextSize(13);
                tvBase.setTextSize(15);
                recycle.setVisibility(View.GONE);
                break;
            case R.id.tv_changer:
                tvChanger.setTextSize(15);
                tvBase.setTextSize(13);
                recycle.setVisibility(View.VISIBLE);
                if (adapter == null){
                    adapter = new ChangerAdapter(mContext,tv.getText().toString(),lister);
                    recycle.setAdapter(adapter);
                }
                adapter.setList(MyApp.getInstance().getList());
                break;
            case R.id.cancel:
                lister.showSet(false);
                break;
        }
    }
}
