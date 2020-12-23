package com.lcb.augustthree;

import android.content.Context;
import android.content.Intent;
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
class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchHolder> {

    private Context mContext;
    private List<SearchBean> list;
    private OnItemClickListener listener;

    public SearchAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setList(List<SearchBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(mContext).inflate(R.layout.item_search, parent, false);
        return new SearchHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchHolder holder, int position) {
        SearchBean searchBean = list.get(position);
        holder.time.setText(searchBean.getTime());
        holder.content.setText(searchBean.getContent());
        holder.title.setText(searchBean.getTitle());
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


    class SearchHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.content)
        TextView content;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.title)
        TextView title;

        public SearchHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }


        @OnClick({R.id.edit, R.id.delete,R.id.changer,R.id.window})
        public void onViewClicked(View view) {
            switch (view.getId()) {
                case R.id.edit:
                    mContext.startActivity(new Intent(mContext,TwoActivity.class)
                            .putExtra("title",list.get(getAdapterPosition()).getTitle())
                            .putExtra("data",list.get(getAdapterPosition()).getContent())
                    );
                    break;
                case R.id.delete:
                    list.remove(getAdapterPosition());
                    notifyDataSetChanged();
                    if (list.size() == 0){
                        if (listener != null){
                            listener.dataNull();
                        }
                    }
                    break;
                case R.id.changer:
                    mContext.startActivity(new Intent(mContext,FourActivity.class)
                            .putExtra("title",list.get(getAdapterPosition()).getTitle())
                            .putExtra("data",list.get(getAdapterPosition()).getContent())
                    );
                    break;
                case R.id.window:
                    if (listener != null){
                        SearchBean searchBean = list.get(getAdapterPosition());
                        listener.onClick(searchBean.getContent());
                    }
                    break;
            }
        }
    }
}
