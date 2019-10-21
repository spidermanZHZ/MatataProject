package com.example.administrator.matata_android.homepage.activitys;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.administrator.matata_android.R;
import com.example.administrator.matata_android.bean.ArtCampATListBean;
import com.example.administrator.matata_android.homepage.adapters.ArtCampAtListAdapter;
import com.example.administrator.matata_android.httputils.BaseObserver;
import com.example.administrator.matata_android.httputils.RetrofitUtil;
import com.example.administrator.matata_android.zhzbase.base.BaseActivity;
import com.example.administrator.matata_android.zhzbase.utils.MatataSPUtils;
import com.hjq.bar.TitleBar;
import com.liaoinstan.springview.widget.SpringView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 不同营地的所有活动页面
 */
public class ArtCampListActivity extends BaseActivity {

    @BindView(R.id.rv_artcamphot_activity)
    RecyclerView rvArtcamphotActivity;
    @BindView(R.id.first_springView)
    SpringView firstSpringView;
    @BindView(R.id.atcamp_titlebar)
    TitleBar atcampTitlebar;
    private BaseObserver<ArtCampATListBean> artCampATListBeanBaseObserver;
    private String id;
    private String atName;
    private ArtCampAtListAdapter adapterhot;

    private int mPageNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_art_camp_list);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void getExras() {
        Intent intent = getIntent();
        id = intent.getStringExtra("class_id");
        atName = intent.getStringExtra("atname");
    }

    @Override
    protected void initData() {
        atcampTitlebar.setTitle(atName+"营地");
        getCampAtList(1);
        //活动列表
        LinearLayoutManager managerhot = new LinearLayoutManager(this);
        managerhot.setOrientation(LinearLayoutManager.VERTICAL);
        rvArtcamphotActivity.setLayoutManager(managerhot);

        adapterhot = new ArtCampAtListAdapter(this, R.layout.adapter_art_camp_hot, null);
        //  设置空布局
        //  adapterhot.setEmptyView(R.layout.adapter_empty,(ViewGroup)rvArtcampActivity.getParent());
        rvArtcamphotActivity.setAdapter(adapterhot);

        firstSpringView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                mPageNumber = 1;
                getCampAtList(mPageNumber);
                if (firstSpringView != null) {
                    firstSpringView.onFinishFreshAndLoad();
                }

            }

            @Override
            public void onLoadmore() {
                getCampAtList(mPageNumber);
                if (firstSpringView != null) {
                    firstSpringView.onFinishFreshAndLoad();
                }
            }
        });

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
     * 获取营地下全部活动
     * last_page:一共有多少页
     */
    public void getCampAtList(final int page) {
        HashMap<String, Object> campAtMap = new HashMap<>();
        campAtMap.put("token", MatataSPUtils.getToken());
        campAtMap.put("page", page);
        campAtMap.put("class_id", id);
        artCampATListBeanBaseObserver = new BaseObserver<ArtCampATListBean>(this, true, false) {
            @Override
            public void onSuccess(ArtCampATListBean artCampATListBean) {
                Toast.makeText(ArtCampListActivity.this, "列表请求成功", Toast.LENGTH_SHORT).show();
                if (firstSpringView != null) {
                    firstSpringView.onFinishFreshAndLoadDelay();
                }
                if (artCampATListBean.getData().size() > 0 && artCampATListBean.getData() != null) {
                    mPageNumber++;
                    if (page == 1) {
                        adapterhot.setNewData(artCampATListBean.getData());
                        adapterhot.notifyDataSetChanged();
                    } else {
                        adapterhot.addData(artCampATListBean.getData());
                    }
                } else {
                    if (page == 1) {
                        if (artCampATListBean.getData().size() == 0) {
                            adapterhot.addData(artCampATListBean.getData());
                        } else {
                            showTextDialog("没有更多数据了");
                        }
                    }
                }
            }
        };
        RetrofitUtil.getInstance().getApiService().getArtCampAllList(campAtMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(artCampATListBeanBaseObserver);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (artCampATListBeanBaseObserver!=null){
            artCampATListBeanBaseObserver.destroy();
        }
    }
}
