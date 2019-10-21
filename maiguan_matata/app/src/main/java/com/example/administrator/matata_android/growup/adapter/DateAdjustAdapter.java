package com.example.administrator.matata_android.growup.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.matata_android.R;
import com.example.administrator.matata_android.bean.ChildDetailsBean;
import com.example.administrator.matata_android.bean.WeekSelectBean;
import com.example.administrator.matata_android.zhzbase.utils.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 加载日期列表,实现RecyclerView多选功能
 */
public class DateAdjustAdapter extends RecyclerView.Adapter <DateAdjustAdapter.DateAdapterViewHolder>{

    private List<Map<String, Object>> mData;
    private Context mContext;
    private final String URL="https://www.maiguanjy.com/";
    private int select=-1;//选中位置

    private SparseIntArray checkedArrays =new SparseIntArray();

    private String check_position;

    public String getCheck_position() {
        return check_position;
    }

    public void setCheck_position(String check_position) {
        this.check_position = check_position;
    }


    public SparseIntArray getCheckedArrays() {
        return checkedArrays;
    }

    private void setCheckedArrays(SparseIntArray checkedArrays) {
        this.checkedArrays = checkedArrays;
    }


   private boolean isCheckedPosition(int position) {
        if (checkedArrays.size()==0){
            return false;
        }
        return checkedArrays.get(position)!=0;
    }


    public DateAdjustAdapter(Context context, List<Map<String, Object>> data) {
        this.mContext=context;
        this.mData=data;
    }

//    /**
//     * 更新选中位置
//     * @param position
//     */
//    public void setSelect(int position){
//        this.select=position;
//        notifyDataSetChanged();
//    }


    /**
     * 接口回调实现条目点击时间，变色
     */

    public interface  OnItemClickListener{
        void onItemClick(View view, int position);
        void onLongItemClick(View view, int position);
    }

    public OnItemClickListener mOnItemClickListenre;//设置点击事件

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.mOnItemClickListenre=onItemClickListener;
    }

    /**
     * 更新data
     * @param data
     */
    public void addData(List<Map<String, Object>> data){
        mData=data;
    }

    @NonNull
    @Override
    public DateAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.adapter_plan_adjust_date,parent,false);
        return new DateAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DateAdapterViewHolder holder, int position) {

        holder.sunTime.setText(String.valueOf(mData.get(position).get("星期")));
        holder.time.setText(String.valueOf(mData.get(position).get("数字")));

        final int positions =holder.getAdapterPosition();

        if (mOnItemClickListenre!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean contain=isCheckedPosition(positions);
                    if (contain){
                        holder.linearLayout.setBackground(mContext.getResources().getDrawable(R.drawable.linearlayout_bg_date_false_adjust));
                        checkedArrays.delete(positions);
                        setCheck_position(checkedArrays.toString());
                     //   Toast.makeText(mContext, checkedArrays.toString(), Toast.LENGTH_SHORT).show();
                    }else {
                        holder.linearLayout.setBackground(mContext.getResources().getDrawable(R.drawable.linearlayout_bg_date_adjust));
                        checkedArrays.put(positions,8);
                        setCheck_position(checkedArrays.toString());
                     //   Toast.makeText(mContext, checkedArrays.toString(), Toast.LENGTH_SHORT).show();
                    }

                    mOnItemClickListenre.onItemClick(holder.itemView,position);

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
        LinearLayout linearLayout;
        public DateAdapterViewHolder(View itemView) {
            super(itemView);
            sunTime = (TextView) itemView.findViewById(R.id.tv_week);
            time = (TextView) itemView.findViewById(R.id.tv_date);
            linearLayout=(LinearLayout)itemView.findViewById(R.id.adjust_ll);
        }
    }

}
