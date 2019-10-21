package com.example.administrator.matata_android.homepage.adapters;

import android.content.Context;
import android.support.annotation.NonNull;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import android.widget.TextView;


import com.example.administrator.matata_android.R;


import java.util.List;

/**
 * 参与人数类型
 */
public class ArtBuyJoinPeopleAdapter  extends RecyclerView.Adapter <ArtBuyJoinPeopleAdapter.JoinPeopleViewHolder> {

    private Context mContext;
    private List<String> mData;

    private int selected = -1;//选中位置


    public ArtBuyJoinPeopleAdapter(Context mContext, List<String> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;//设置点击事件

    public void setOnItemClickLitener(OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener=mOnItemClickListener;
    }

    /**
     * 更新选中的位置
     * @param position
     */
    public void setSelection(int position){
        this.selected=position;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public JoinPeopleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.buy_rv_adapter,parent,false);
        return new JoinPeopleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JoinPeopleViewHolder holder, int position) {

        holder.textView.setText("\t"+mData.get(position)+"\t");
        if (selected==position){
            holder.textView.setTextColor(mContext.getResources().getColor(R.color.tv_service_code));
        }else {
            holder.textView.setTextColor(mContext.getResources().getColor(R.color.tv_text_style));
        }
        if (mOnItemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.itemView,holder.getAdapterPosition());
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

    class JoinPeopleViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public JoinPeopleViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.buy_rv_adapter_tv);
        }
    }
}
