package com.lcb.augustthree;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * created by: Eroch
 * time: 2020/8/23
 * introduce:
 */
class FileAdapter extends RecyclerView.Adapter<FileAdapter.ChangerHolder> {

    private Context mContext;
    private List<FileBean> list;
    private OnItemClickListener listener;
    private int lastIndex = -1;

    public FileAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setList(List<FileBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ChangerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(mContext).inflate(R.layout.item_file_name, parent, false);
        return new ChangerHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull ChangerHolder holder, int position) {
        holder.fileName.setText(list.get(position).getTitle());
        if (position == lastIndex){
            holder.itemView.setSelected(true);
        }else {
            holder.itemView.setSelected(false);
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class ChangerHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.file_name)
        TextView fileName;

        public ChangerHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.file_name)
        public void onViewClicked() {
            if (listener != null){
                File dir = list.get(getAdapterPosition()).getFile();
                try {
                    FileInputStream fis = new FileInputStream(dir);
                    //GB2312
                    //GBK
                    //UTF-8
                    BufferedReader br = new BufferedReader(new InputStreamReader(fis,"UTF-8"));
                    StringBuilder sb = new StringBuilder("");
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line);
                    }
                    Tip.log("读到的内容为：" + sb);
                    lastIndex = getAdapterPosition();
                    notifyDataSetChanged();
                    listener.onClick(sb.toString());
                } catch (FileNotFoundException e) {
                    listener.onClick("本地内容读取失败,失败原因为: " + e.getMessage());
                } catch (IOException e) {
                    listener.onClick("本地内容读取失败,失败原因为: " + e.getMessage());
                }
            }
        }

    }
}
