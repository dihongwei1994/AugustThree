package com.lcb.speechrecognition;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.aiuisdk.BaseSpeechCallback;
import com.aiuisdk.SpeechManager;
import com.lcb.augustthree.R;
import com.tbruyelle.rxpermissions2.RxPermissions;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import io.reactivex.disposables.Disposable;


/**
 * 语义理解demo。
 *
 * @author ydong
 */
public class XFDemo extends Activity {


    private static final String TAG = "ydong";
    private TextView question;
    private TextView answer;
    private Button button, bt_qq;

    @Override
    @SuppressLint("ShowToast")
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nlpdemo);
        initPermission();
        initView();

    }

    private void initView() {
        question = findViewById(R.id.question);
        answer = findViewById(R.id.answer);
        button = findViewById(R.id.btn);
        bt_qq = findViewById(R.id.bt_qq);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initChatSDK();
                button.setText("正在录音...");
            }
        });
        bt_qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Platform plat = ShareSDK.getPlatform(QQ.NAME);
//移除授权状态和本地缓存，下次授权会重新授权
                plat.removeAccount(true);
//SSO授权，传false默认是客户端授权
                plat.SSOSetting(false);
//授权回调监听，监听oncomplete，onerror，oncancel三种状态
                plat.setPlatformActionListener(null);
//抖音登录适配安卓9.0
//ShareSDK.setActivity(MainActivity.this);
//要数据不要功能，主要体现在不会重复出现授权界面
                plat.showUser(null);
            }
        });
    }


    private void initChatSDK() {
        SpeechManager.CreateInstance(getApplicationContext());
        SpeechManager.getInstance().setBaseSpeechCallback(speechCallback);
    }

    BaseSpeechCallback speechCallback = new BaseSpeechCallback() {
        @Override
        public void recognizeResult(String text) {
            Log.d(TAG, "recognizeResult::" + text);
            question.setText(text);
            button.setText("录音");
        }

        @Override
        public void nlpResult(String text, String json) {
            Log.d(TAG, "nlpResult::" + text);
            answer.setText(text);
            SpeechManager.onSpeaking(text);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SpeechManager.getInstance().onDestroy();
    }

    @SuppressLint("CheckResult")
    private void initPermission() {
        Disposable subscribe = new RxPermissions(this).request(Manifest.permission.RECORD_AUDIO,
                Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.CHANGE_NETWORK_STATE, Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.ACCESS_NETWORK_STATE)
                .subscribe(granted -> {
                    if (granted) {
                        Toast.makeText(this, "获取权限成功", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(this, "请先获取权限后使用", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }

}
