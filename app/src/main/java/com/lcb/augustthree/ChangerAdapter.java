package com.lcb.augustthree;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * created by: Eroch
 * time: 2020/8/23
 * introduce:
 */
class ChangerAdapter extends RecyclerView.Adapter<ChangerAdapter.ChangerHolder> {

    private Context mContext;
    private List<SearchBean> list;
    private String content;
    private boolean isFirst;
    private XFloatView.OnDirectionLister lister;
    private int lastIndex;

    public ChangerAdapter(Context mContext,String content,XFloatView.OnDirectionLister lister) {
        this.mContext = mContext;
        this.content = content;
        this.lister = lister;
        isFirst = true;
    }

    public void setList(List<SearchBean> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ChangerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(mContext).inflate(R.layout.item_term, parent, false);
        return new ChangerHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull ChangerHolder holder, int position) {
        SearchBean searchBean = list.get(position);
        holder.time.setText(searchBean.getTime());
        holder.title.setText(searchBean.getTitle());

        if (isFirst){
            if (content.equals(searchBean.getContent())){
                holder.current.setSelected(true);
                holder.current.setText("当前");
                searchBean.setSelect(true);
                isFirst = false;
                lastIndex = position;
            }else {
                holder.current.setSelected(false);
                holder.current.setText("切换");
                searchBean.setSelect(false);
            }
        }else {
            if (searchBean.isSelect()){
                holder.current.setSelected(true);
                holder.current.setText("当前");
                searchBean.setSelect(true);
                lastIndex = position;
            }else {
                holder.current.setSelected(false);
                holder.current.setText("切换");
                searchBean.setSelect(false);
            }
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


    class ChangerHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.current)
        TextView current;

        public ChangerHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick({R.id.current})
        public void onViewClicked(View view) {
            switch (view.getId()) {
                case R.id.current:
                    if (!current.isSelected()){
                        SearchBean searchBean = list.get(getAdapterPosition());
                        searchBean.setSelect(true);
                        list.get(lastIndex).setSelect(false);
                        notifyDataSetChanged();
                        if (lister != null){
                            lister.changerTextContent(searchBean.getContent());
                        }
                    }
                    break;
            }
        }
    }
}
