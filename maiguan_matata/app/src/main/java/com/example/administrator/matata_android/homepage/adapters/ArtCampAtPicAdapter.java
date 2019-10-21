package com.example.administrator.matata_android.homepage.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.administrator.matata_android.R;

import java.util.List;

public class ArtCampAtPicAdapter extends RecyclerView.Adapter<ArtCampAtPicAdapter.ViewHolder> {
    private Context mContext;
    private List mData;
    private final String URL="https://www.maiguanjy.com/";
    public ArtCampAtPicAdapter(Context context){
        mContext=context;
    }
    public ArtCampAtPicAdapter(Context context,List data){
        mContext=context;
        mData=data;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.adapter_artcamp_atpic,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(mContext).load(URL+mData.get(position))
                .into(holder.img);

    }

    @Override
    public int getItemCount() {
        if (mData!=null){
            return mData.size();
        }
       return 0;
    }
    public void getData(List data){
        mData=data;

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        public ViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.adapter_at_pic);
        }
    }
}
