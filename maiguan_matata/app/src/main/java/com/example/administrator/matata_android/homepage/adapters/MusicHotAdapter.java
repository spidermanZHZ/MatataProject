package com.example.administrator.matata_android.homepage.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.matata_android.R;
import com.example.administrator.matata_android.bean.MusicHotBean;

import java.util.List;

/**
 * 音乐学院最新上架适配器
 */
public class MusicHotAdapter extends BaseQuickAdapter<MusicHotBean ,BaseViewHolder> {
    private Context mContext;
    private final String URL="https://www.maiguanjy.com/";

    public MusicHotAdapter(Context context,int layoutResId, @Nullable List<MusicHotBean> data) {
        super(layoutResId, data);
        this.mContext=context;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MusicHotBean item) {
        //加载图片
        Glide.with(mContext)
                .load(URL+item.getCover_pic())
                .into((ImageView)helper.getView(R.id.music_hot_iv));

        helper.setText(R.id.music_hot_tv_title,item.getName());//名称
        if (item.getType().equals("online")){
            helper.setText(R.id.music_hot_tv_type,"(线上课程)");

        }else {
            helper.setText(R.id.music_hot_tv_type,"(线下课程)");
        }
        helper.setText(R.id.music_hot_tv_price,"活动价：¥"+item.getPrice());
    }

}
