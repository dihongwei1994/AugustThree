package com.lcb.augustthree;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TwoActivity extends AppCompatActivity {

    @BindView(R.id.input)
    EditText input;
    @BindView(R.id.input2)
    EditText input2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        ButterKnife.bind(this);
        String data = getIntent().getStringExtra("data");
        String title = getIntent().getStringExtra("title");
        if (data != null && !data.isEmpty()) {
            input.setText(title);
            input2.setText(data);
        }
}

    @OnClick({R.id.back, R.id.submit,R.id.read})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                Tip.log("back");
                finish();
                break;
            case R.id.submit:
                String title = input.getText().toString();
                String content = input2.getText().toString();
                if (content == null || content.isEmpty()) {
                    Toast.makeText(this, "请输入内容!", Toast.LENGTH_SHORT);
                } else if (title == null || title.isEmpty()) {
                    Toast.makeText(this, "请输入标题!", Toast.LENGTH_SHORT);
                } else {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date(System.currentTimeMillis());
                    String format = simpleDateFormat.format(date);
                    MyApp.getInstance().getList().add(new SearchBean(title,content,format));
                    finish();
                }
                break;
            case R.id.read:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    String[] permissions = new String[]{
                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE};
                    int write = ContextCompat.checkSelfPermission(this,permissions[0]);
                    int read = ContextCompat.checkSelfPermission(this,permissions[1]);
                    int granted = PackageManager.PERMISSION_GRANTED;
                    if (write != granted || read != granted ){
                        //如果没有申请到权限
                        requestPermissions(permissions,2);
                    }
                }

                DialogFile dialogFile = new DialogFile(this);
                dialogFile.setListener(new OnItemClickListener() {
                    @Override
                    public void onClick(String content) {
                        input2.setText(content);
                        dialogFile.dismiss();
                    }

                    @Override
                    public void dataNull() {

                    }
                });
                dialogFile.show();
                break;
        }
    }
}