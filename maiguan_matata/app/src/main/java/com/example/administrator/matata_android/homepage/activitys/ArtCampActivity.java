package com.example.administrator.matata_android.homepage.activitys;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.matata_android.R;
import com.example.administrator.matata_android.bean.ArtCampBean;
import com.example.administrator.matata_android.homepage.adapters.ArtCampCampListAdapter;
import com.example.administrator.matata_android.homepage.adapters.ArtCampHotListAdapter;
import com.example.administrator.matata_android.httputils.BaseObserver;
import com.example.administrator.matata_android.httputils.RetrofitUtil;
import com.example.administrator.matata_android.zhzbase.base.BaseActivity;
import com.example.administrator.matata_android.zhzbase.utils.MatataSPUtils;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 首页艺术营地页面
 */
public class ArtCampActivity extends BaseActivity {


    @BindView(R.id.rv_artcamp_activity)
    RecyclerView rvArtcampActivity;

    RecyclerView rvArtcampCamp;

    private ArtCampCampListAdapter adapter;
    private ArtCampHotListAdapter adapterhot;
    private BaseObserver<ArtCampBean> artCampActivityBaseObserver;
    private List<ArtCampBean.campsiteClassify> campListDatas;
    private List<ArtCampBean.hot> hotListDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_art_camp);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);

    }


    @Override
    protected void getExras() {

    }

    @Override
    protected void initData() {

        //热门活动列表
        LinearLayoutManager   managerhot = new LinearLayoutManager(this);
        managerhot.setOrientation(LinearLayoutManager.VERTICAL);
        rvArtcampActivity.setLayoutManager(managerhot);

        adapterhot = new ArtCampHotListAdapter(this, R.layout.adapter_art_camp_hot, null);

      //  adapterhot.setEmptyView(R.layout.adapter_empty,(ViewGroup)rvArtcampActivity.getParent());
        rvArtcampActivity.setAdapter(adapterhot);


        //添加头布局
        View headView = getLayoutInflater().inflate(R.layout.header_camp_list, null);
        adapterhot.addHeaderView(headView);

        rvArtcampCamp=(RecyclerView) headView.findViewById(R.id.rv_artcamp_camp);
        //营地列表
        LinearLayoutManager  manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvArtcampCamp.setLayoutManager(manager);
        adapter = new ArtCampCampListAdapter(this, R.layout.adapter_art_camplist, null);
        rvArtcampCamp.setAdapter(adapter);
        getArtCampData();

    }

    @Override
    protected void setListener() {

    }

    /**
     * 获取营地列表跟热门活动
     */
    public void getArtCampData() {

        HashMap<String, Object> artCampmap = new HashMap<>();
        artCampmap.put("token", MatataSPUtils.getToken());

        artCampActivityBaseObserver = new BaseObserver<ArtCampBean>(this, true, false) {
            @Override
            public void onSuccess(ArtCampBean artCampBean) {

                //营地列表
                campListDatas = artCampBean.getCampsiteClassify();
                adapter.addData(campListDatas);
                adapter.notifyDataSetChanged();
                adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        Intent intent=new Intent(ArtCampActivity.this,ArtCampListActivity.class);
                        intent.putExtra("class_id",campListDatas.get(position).getId());
                        intent.putExtra("atname",campListDatas.get(position).getName());
                        startActivity(intent);
                    }
                });

                //热门活动
                hotListDatas = artCampBean.getHot();
                adapterhot.addData(hotListDatas);
                adapterhot.notifyDataSetChanged();
                adapterhot.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        Intent intent=new Intent(ArtCampActivity.this,ArtCampAtActivity.class);
                        intent.putExtra("campsite_id",hotListDatas.get(position).getId());
                        startActivity(intent);
                    }
                });
            }

        };
        RetrofitUtil.getInstance().getApiService().getartcampList(artCampmap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(artCampActivityBaseObserver);

    }

    @Override
    protected boolean onKeyBack() {
        return false;
    }

    @Override
    protected boolean onKeyMenu() {
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (artCampActivityBaseObserver!=null){
            artCampActivityBaseObserver.destroy();
        }
    }
}
