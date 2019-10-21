package com.example.administrator.matata_android.homepage.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.matata_android.R;
import com.example.administrator.matata_android.bean.ArtBuyCompleteBean;
import com.example.administrator.matata_android.homepage.SerializableMap;
import com.example.administrator.matata_android.httputils.BaseObserver;
import com.example.administrator.matata_android.httputils.RetrofitUtil;
import com.example.administrator.matata_android.zhzbase.base.BaseActivity;
import com.example.administrator.matata_android.zhzbase.utils.MatataSPUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ArtBuyCompleteActivity extends BaseActivity {

    @BindView(R.id.tv_art_buy_no)
    TextView tvArtBuyNo;
    @BindView(R.id.tv_art_buy_price)
    TextView tvArtBuyPrice;
    @BindView(R.id.tv_art_buy_name)
    TextView tvArtBuyName;
    @BindView(R.id.tv_art_buy_type)
    TextView tvArtBuyType;
    @BindView(R.id.tv_art_buy_date)
    TextView tvArtBuyDate;
    @BindView(R.id.tv_art_buy_people)
    TextView tvArtBuyPeople;
    @BindView(R.id.tv_art_buy_num)
    TextView tvArtBuyNum;
    @BindView(R.id.tv_art_buy_contact)
    TextView tvArtBuyContact;
    @BindView(R.id.tv_art_buy_contact_phone)
    TextView tvArtBuyContactPhone;
    @BindView(R.id.art_buy_price_all)
    TextView artBuyPriceAll;
    @BindView(R.id.art_confirm_buy)
    TextView artConfirmBuy;

    SerializableMap dataMap;

    private String campsite_id;
    private String attribute;
    private String date;
    private String num;
    private String contact;
    private String contact_phone;

    private BaseObserver<ArtBuyCompleteBean> buyCompleteBeanBaseObserver;
    private HashMap<String, Object> campBuyInfoMap ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_art_buy_complete);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void getExras() {

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("mBundle");
        dataMap = (SerializableMap)bundle.getSerializable("myMap");

        if (dataMap != null) {
            campsite_id=dataMap.get("campsite_id").toString().trim();
            attribute=dataMap.get("attribute").toString().trim();
            date=dataMap.get("date").toString().trim();
            num=dataMap.get("num").toString().trim();
            contact=dataMap.get("contact").toString().trim();
            contact_phone=dataMap.get("contact_phone").toString().trim();

        }else {
            return ;
        }

    }

    @Override
    protected void initData() {
        getCampOrder();
    }

    @Override
    protected void setListener() {

    }

    /**
     * 获得订单信息
     */
    public void getCampOrder(){
        campBuyInfoMap=new HashMap<>();
        campBuyInfoMap.put("token", MatataSPUtils.getToken());
        campBuyInfoMap.put("campsite_id",campsite_id);
        campBuyInfoMap.put("attribute",attribute);
        campBuyInfoMap.put("date",date);
        campBuyInfoMap.put("num",num);
        campBuyInfoMap.put("contact",contact);
        campBuyInfoMap.put("contact_phone",contact_phone);

        buyCompleteBeanBaseObserver=new BaseObserver<ArtBuyCompleteBean>(this,false,false) {
            @Override
            public void onSuccess(ArtBuyCompleteBean artBuyCompleteBean) {
                tvArtBuyNo.setText(artBuyCompleteBean.getNo());
                tvArtBuyPrice.setText(getPrice(artBuyCompleteBean.getPrice()));
                tvArtBuyName.setText(artBuyCompleteBean.getCampsite_name());

                tvArtBuyType.setText(artBuyCompleteBean.getAttribute());//套餐类型
                tvArtBuyDate.setText(artBuyCompleteBean.getDate());
                tvArtBuyPeople.setText(artBuyCompleteBean.getAttribute());
                tvArtBuyNum.setText(artBuyCompleteBean.getNum());
                tvArtBuyContact.setText(artBuyCompleteBean.getContact());
                tvArtBuyContactPhone.setText(artBuyCompleteBean.getContact_phone());

                artBuyPriceAll.setText(getPrice(artBuyCompleteBean.getPrice()));

                Toast.makeText(ArtBuyCompleteActivity.this, "获取成功", Toast.LENGTH_SHORT).show();
            }
        };

        RetrofitUtil.getInstance().getApiService().getCampOrder(campBuyInfoMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(buyCompleteBeanBaseObserver);
        
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
     * 处理接口返回String类型价格
     */
    public String getPrice(String price) {
        int a = Integer.parseInt(price);
        int b = a / 100;
        return  String.valueOf(b);
    }
}
