package com.example.administrator.matata_android.growup.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.matata_android.R;
import com.example.administrator.matata_android.bean.ChildDetailsBean;
import com.example.administrator.matata_android.zhzbase.utils.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 加载日期列表
 */
public class DateAdapter extends RecyclerView.Adapter <DateAdapter.DateAdapterViewHolder>{

    private List<ChildDetailsBean.Plans> mData;
    private Context mContext;
    private final String URL="https://www.maiguanjy.com/";
    private int select=-1;//选中位置
    public DateAdapter(Context context,List<ChildDetailsBean.Plans> data) {
        this.mContext=context;
        this.mData=data;
    }

    /**
     * 更新选中位置
     * @param position
     */
    public void setSelect(int position){
        this.select=position;
        notifyDataSetChanged();
    }

    /**
     * 接口回调实现条目点击时间，变色
     */

    public interface  OnItemClickListener{
        void onItemClick(View view,int position);
        void onLongItemClick(View view,int position);
    }
    public OnItemClickListener mOnItemClickListenre;//设置点击事件

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.mOnItemClickListenre=onItemClickListener;
    }

    /**
     * 更新data
     * @param data
     */
    public void addData(List<ChildDetailsBean.Plans> data){
        mData=data;
    }

    @NonNull
    @Override
    public DateAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.adapter_plan_date,parent,false);
        return new DateAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DateAdapterViewHolder holder, int position) {

        if (select==position){
           // holder.itemView.setBackgroundColor(Color.parseColor("#FAFAFA"));
            holder.itemView.setBackground(mContext.getResources().getDrawable(R.drawable.linearlayout_bg_date));
        }else {
          //  holder.itemView.setBackgroundColor(Color.WHITE);
            holder.itemView.setBackground(mContext.getResources().getDrawable(R.drawable.linearlayout_bg_date_false));
        }

        holder.sunTime.setText(String.valueOf("周"+mData.get(position).getWeek()));


        try {
            //yyyy-MM-dd转换为MM-dd格式
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
            Date datel=fmt.parse(mData.get(position).getDay());
            String ss = DateUtils.getInstance().format(datel,"MM-dd");
            holder.time.setText(String.valueOf(ss));

        }catch (ParseException e){
            e.printStackTrace();
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

    class DateAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView sunTime;
        TextView time;
        public DateAdapterViewHolder(View itemView) {
            super(itemView);
            sunTime = (TextView) itemView.findViewById(R.id.tv_week);
            time = (TextView) itemView.findViewById(R.id.tv_date);
        }
    }

}
