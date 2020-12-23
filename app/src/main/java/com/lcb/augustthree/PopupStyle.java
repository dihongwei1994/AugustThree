package com.lcb.augustthree;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.warkiz.widget.IndicatorSeekBar;
import com.warkiz.widget.OnSeekChangeListener;
import com.warkiz.widget.SeekParams;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * created by: Eroch
 * time: 2020/8/21
 * introduce:
 */
class PopupStyle extends PopupWindow {

    @BindView(R.id.seek_bar0)
    IndicatorSeekBar seekBar0;
    @BindView(R.id.seek_bar)
    IndicatorSeekBar seekBar;
    @BindView(R.id.seek_bar2)
    IndicatorSeekBar seekBar2;
    @BindView(R.id.seek_bar3)
    IndicatorSeekBar seekBar3;
    @BindView(R.id.rl)
    RelativeLayout rl;
    @BindView(R.id.tv_base)
    RadioButton tvBase;
    @BindView(R.id.tv_changer)
    RadioButton tvChanger;
    @BindView(R.id.cancel)
    ImageView cancel;
    @BindView(R.id.recycle)
    RecyclerView recycle;

    private Context mContext;
    private TextView tv;
    private OnChangerListener listener;
    private ChangerAdapter adapter;

    public void setListener(OnChangerListener listener) {
        this.listener = listener;
    }

    public PopupStyle(Context context, TextView tv, RelativeLayout layout) {
        super(context);
        this.tv = tv;
        this.mContext = context;
        RelativeLayout inflate = (RelativeLayout) LayoutInflater
                .from(context).inflate(R.layout.popup_set, null);
        setContentView(inflate);
        ButterKnife.bind(this, inflate);
        setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);
        setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
        setBackgroundDrawable(null);
        seekBar.setProgress(tv.getTextSize() / Tip.getDensity((Activity) context));
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
                    background.setColor(context.getResources().getColor(Color.parseColor(color)));
                    layout.setBackground(background);
//                    int color1 = context.getResources().getColor(R.color.color_translation);
////                    layout.setback.setBackgroundColor(Color.parseColor(color));
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
                if (listener != null) {
                    listener.onChanger(seekParams.progress * 5);
                }
            }

            @Override
            public void onStartTrackingTouch(IndicatorSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(IndicatorSeekBar seekBar) {

            }
        });
        recycle.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
    }

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

    @OnClick({R.id.tv_base, R.id.tv_changer, R.id.cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_base:
                tvChanger.setTextSize(13);
                tvBase.setTextSize(15);
                recycle.setVisibility(View.GONE);
                break;
            case R.id.tv_changer:
                tvChanger.setTextSize(15);
                tvBase.setTextSize(13);
                recycle.setVisibility(View.GONE);
                if (adapter == null){
                    adapter = new ChangerAdapter(mContext,tv.getText().toString(),null);
                    recycle.setAdapter(adapter);
                }
                adapter.setList(MyApp.getInstance().getList());
                break;
            case R.id.cancel:
                dismiss();
                break;
        }
    }
}
