package com.example.administrator.matata_android.homepage.activitys;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.matata_android.R;
import com.example.administrator.matata_android.bean.MusicHotBean;
import com.example.administrator.matata_android.homepage.adapters.MusicHotAdapter;
import com.example.administrator.matata_android.httputils.BaseObserver;
import com.example.administrator.matata_android.httputils.RetrofitUtil;
import com.example.administrator.matata_android.zhzbase.base.BaseActivity;
import com.example.administrator.matata_android.zhzbase.utils.MatataSPUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 首页二级页面，音乐学院页面
 */
public class MusicCollageActivity extends BaseActivity {

    @BindView(R.id.music_tv_refresh_hot)
    TextView musicTvRefreshHot;
    @BindView(R.id.music_rv_hot)
    RecyclerView musicRvHot;

    private BaseObserver<List<MusicHotBean>> baseMusicHotBeanObserver;

    private MusicHotAdapter hotAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_music_collage);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onResume() {
        super.onResume();
        getMusicHot();
    }

    @Override
    protected void getExras() {

    }

    @Override
    protected void initData() {
        getMusicHot();

        StaggeredGridLayoutManager manager =new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);


        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);


        musicRvHot.setLayoutManager(manager);
        hotAdapter=new MusicHotAdapter(this,R.layout.adapter_music_hot,null);
        musicRvHot.setAdapter(hotAdapter);

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected boolean onKeyBack() {
        return false;
    }

    @Override
    protected boolean onKeyMenu() {
        return false;
    }

    /**
     * 获取最新上架的列表
     */
    private void getMusicHot(){
        Map<String ,Object> map=new HashMap<String,Object>();
            map.put("token", MatataSPUtils.getToken());
        baseMusicHotBeanObserver=new BaseObserver<List<MusicHotBean>> (this,false,false) {
            @Override
            public void onSuccess(List<MusicHotBean> musicHotBean) {

                if (musicHotBean.size()>4){
                    List<MusicHotBean> lists=musicHotBean.subList(0,3);
                    hotAdapter.addData(lists);
                    hotAdapter.notifyDataSetChanged();
                }else {
                    hotAdapter.addData(musicHotBean);
                    hotAdapter.notifyDataSetChanged();
                }

                hotAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        Toast.makeText(MusicCollageActivity.this, "请求成功", Toast.LENGTH_SHORT).show();
                        Toast.makeText(MusicCollageActivity.this, "点击了第"+position+"位置", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } ;
        RetrofitUtil.getInstance().getApiService().getMusicHot(map)
            .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseMusicHotBeanObserver);
    }
}
