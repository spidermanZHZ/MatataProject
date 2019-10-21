package com.example.administrator.matata_android.homepage.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.matata_android.R;

import java.util.List;

/**
 * 购买页面营地活动的日期
 */
public class ArtBuyJoinDateAdapter extends RecyclerView.Adapter<ArtBuyJoinDateAdapter.ViewHolder> {


    private Context mContext;
    private List<String> mData;

    private int select=-1;//选中位置

    /**
     * 更新选中位置
     * @param position
     */
    public void setSelect(int position){
        this.select=position;
        notifyDataSetChanged();
    }
    public interface  OnItemClickListener{
        void onItemClick(View view,int position);
        void onLongItemClick(View view,int position);
    }
    public OnItemClickListener mOnItemClickListenre;//设置点击事件

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.mOnItemClickListenre=onItemClickListener;
    }

    public ArtBuyJoinDateAdapter(Context mContext, List<String> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.art_buy_adapter_date,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(mData.get(position));
        if (select==position){
            holder.textView.setTextColor(mContext.getResources().getColor(R.color.tv_service_code));
        }else {
            holder.textView.setTextColor(mContext.getResources().getColor(R.color.tv_text_style));
        }
        if (mOnItemClickListenre!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListenre.onItemClick(holder.itemView,holder.getAdapterPosition());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (mData!=null){
            return mData.size();
        }
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);
            textView=(TextView)itemView.findViewById(R.id.tv_art_buy_date);
        }
    }
}
