package com.example.administrator.matata_android.homepage.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.matata_android.R;
import com.example.administrator.matata_android.bean.ArtCampBean;

import java.util.List;

/**
 * 艺术营地列表的适配器
 */
public class ArtCampCampListAdapter extends BaseQuickAdapter<ArtCampBean.campsiteClassify,BaseViewHolder>{
    private Context mContext;
    private final String URL="https://www.maiguanjy.com/";
    public ArtCampCampListAdapter(Context context,int layoutResId, @Nullable List<ArtCampBean.campsiteClassify> data) {
        super(layoutResId, data);
        this.mContext=context;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ArtCampBean.campsiteClassify item) {

        //设置图片圆角角度
        RoundedCorners roundedCorners=new RoundedCorners(10);
        //通过RequestOptions扩展功能
        RequestOptions options=RequestOptions.bitmapTransform(roundedCorners).override(300,300);

        //加载图片
        Glide.with(mContext)
                .load(URL+item.getIcon())
                .apply(options)
                .into((ImageView)helper.getView(R.id.iv_art_camp_list));


    }
}
