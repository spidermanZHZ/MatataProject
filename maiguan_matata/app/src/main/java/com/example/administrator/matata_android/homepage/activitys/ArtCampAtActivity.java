package com.example.administrator.matata_android.homepage.activitys;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.matata_android.R;
import com.example.administrator.matata_android.bean.ArtCampInfoBean;
import com.example.administrator.matata_android.homepage.adapters.ArtCampAtDetailPicAdapter;
import com.example.administrator.matata_android.homepage.adapters.ArtCampAtPicAdapter;
import com.example.administrator.matata_android.httputils.BaseObserver;
import com.example.administrator.matata_android.httputils.RetrofitUtil;
import com.example.administrator.matata_android.zhzbase.base.BaseActivity;
import com.example.administrator.matata_android.zhzbase.utils.MatataSPUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import recycler.coverflow.RecyclerCoverFlow;

/**
 * 营地介绍页面
 * detail_pic：营地图片网址数组
 */
public class ArtCampAtActivity extends BaseActivity {

    @BindView(R.id.coverflow_recycler)
    RecyclerCoverFlow coverflowRecycler;
    @BindView(R.id.title)
    LinearLayout title;
    @BindView(R.id.art_camp_tag)
    TextView artCampTag;
    @BindView(R.id.art_camp_title)
    TextView artCampTitle;
    @BindView(R.id.art_camp_adr)
    TextView artCampAdr;
    @BindView(R.id.art_camp_time)
    TextView artCampTime;
    @BindView(R.id.art_camp_people)
    TextView artCampPeople;
    @BindView(R.id.art_camp_number)
    TextView artCampNumber;
    @BindView(R.id.art_at_price)
    TextView artAtPrice;
    @BindView(R.id.art_at_join)
    TextView artAtJoin;
    @BindView(R.id.art_at_web)
    WebView artAtWeb;


    private ArtCampAtDetailPicAdapter artCampAtDetailPicAdapter;
    private ArtCampAtPicAdapter picAdapter;
    private String campsite_id;
    private BaseObserver<ArtCampInfoBean> artCampInfoBeanBaseObserver;
    private final String URL = "https://www.maiguanjy.com/";
    private final String URLweb = "https://www.maiguanjy.com/api/app/view/details";

    private ArrayList<String> data;
    private ArrayList<String> mdate;
    private ArrayList<String> priceData;
    private ArrayList<String> vip_priceData;
    private String is_vip;
    private String cover_pic;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_art_camp_hot_list);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void getExras() {
        Intent intent = getIntent();
        campsite_id = intent.getStringExtra("campsite_id");
    }

    @Override
    protected void initData() {
        getArtCampAtInfo();
        //实现画廊效果
        picAdapter = new ArtCampAtPicAdapter(this, null);
        coverflowRecycler.setAdapter(picAdapter);
        artAtJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ArtCampAtActivity.this, ArtBuyActivity.class);

                intent.putStringArrayListExtra("data",data);
                intent.putStringArrayListExtra("mDate",mdate);
                intent.putStringArrayListExtra("priceData",priceData);
                intent.putStringArrayListExtra("vip_priceData",vip_priceData);
                intent.putExtra("is_vip",is_vip);
                intent.putExtra("cover_pic",cover_pic);
                intent.putExtra("campsite_id",campsite_id);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void setListener() {

    }

    /**
     * 获取活动详情
     */
    public void getArtCampAtInfo() {
        HashMap<String, Object> campAtInfoMap = new HashMap<>();
        campAtInfoMap.put("token", MatataSPUtils.getToken());
        campAtInfoMap.put("campsite_id", campsite_id);

        artCampInfoBeanBaseObserver = new BaseObserver<ArtCampInfoBean>(this, true, false) {
            @Override
            public void onSuccess(ArtCampInfoBean artCampInfoBean) {

                data=(ArrayList<String>) artCampInfoBean.getAttribute();
                mdate=(ArrayList<String>)artCampInfoBean.getDate();
                priceData=(ArrayList<String>)artCampInfoBean.getPrice();
                vip_priceData=(ArrayList<String>)artCampInfoBean.getVipPrice();
                is_vip=(String) artCampInfoBean.getIs_vip();
                cover_pic=artCampInfoBean.getCover_pic();

                picAdapter.getData(artCampInfoBean.getDetail_pic());
                picAdapter.notifyDataSetChanged();
                artCampTag.setText(artCampInfoBean.getTag());
                artCampTitle.setText(artCampInfoBean.getName());
                artCampAdr.setText(artCampInfoBean.getAddr());
                artCampTime.setText(artCampInfoBean.getDate().get(0));
                artCampPeople.setText(artCampInfoBean.getObject());
                artCampNumber.setText(artCampInfoBean.getPeople());
                if (artCampInfoBean.getPrice().size() > 1) {
                    String pricestr = (artCampInfoBean.getPrice().get(0));
                    String pricestr2 = artCampInfoBean.getPrice().get(artCampInfoBean.getPrice().size() - 1);
                    int a = Integer.parseInt(pricestr);
                    int b = a / 100;
                    int c = Integer.parseInt(pricestr2);
                    int d = c / 100;
                    String price1 = String.valueOf(b);
                    String price2 = String.valueOf(d);
                    String prices = "¥" + price1 + "-" + price2;
                    artAtPrice.setText(prices);
                } else if (artCampInfoBean.getPrice().size() == 1) {
                    String price3 = String.valueOf(artCampInfoBean.getPrice().get(0));
                    artAtPrice.setText(price3);
                } else {
                    artAtPrice.setText("价格待定");
                }
                //加载webView
                    artAtWeb.loadUrl(URLweb+"?id="+artCampInfoBean.getId()+"&type=campsite");

            }
        };

        RetrofitUtil.getInstance().getApiService().getcampatinfo(campAtInfoMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(artCampInfoBeanBaseObserver);


    }

    @Override
    protected boolean onKeyBack() {
        return false;
    }

    @Override
    protected boolean onKeyMenu() {
        return false;
    }
}
