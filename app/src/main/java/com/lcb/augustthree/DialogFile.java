package com.lcb.augustthree;

import android.app.Dialog;
import android.content.Context;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * created by: Eroch
 * time: 2020/8/25
 * introduce:http://www.manongjc.com/detail/5-kdjmovnmedownop.html
 */
class DialogFile extends Dialog {

    @BindView(R.id.recycle)
    RecyclerView recycle;
    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.null_data)
    TextView nullData;

    private FileAdapter adapter;
    private OnItemClickListener listener;
    private List<FileBean> list;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
        adapter.setListener(listener);
    }

    public DialogFile(@NonNull Context context) {
        super(context, R.style.MyDialogStyle);
        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_file, null);
        setContentView(inflate);
        ButterKnife.bind(this,inflate);
        list = new ArrayList<>();
        adapter = new FileAdapter(context);
        recycle.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        recycle.setAdapter(adapter);
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File path = Environment.getExternalStorageDirectory();// 获得SD卡路径
            File[] files = path.listFiles();// 读取
            getFileName(files);
        }
    }

    //遍历文件夹
    private void getFileName(File[] files) {
        if (files != null) {// 先判断目录是否为空，否则会报空指针
            for (File file : files) {
                if (file.isDirectory()) {
                    getFileName(file.listFiles());
                } else {
                    String fileName = file.getName();
                    if (fileName.endsWith(".txt") || fileName.endsWith(".doc")) {
                        String string = fileName.substring(0, fileName.lastIndexOf("."));
                        list.add(new FileBean(string,file.getAbsoluteFile()));
                        Tip.log("path: " + file.getAbsolutePath());
                    }
                }
            }
            progress.setVisibility(View.GONE);
            adapter.setList(list);
            Tip.log("size: "+ list.size() + "\t\t" + (list.size() == 0));
            if (list.size() == 0){
//                nullData.setVisibility(View.VISIBLE);
//                nullData.setText("本地无内容");
            }
            Tip.log("size: "+ list.size() + "\t\t" + (list.size() == 0));
        }
    }

}
