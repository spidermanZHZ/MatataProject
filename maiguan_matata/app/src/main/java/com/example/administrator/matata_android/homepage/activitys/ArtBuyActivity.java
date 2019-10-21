package com.example.administrator.matata_android.homepage.activitys;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.example.administrator.matata_android.R;
import com.example.administrator.matata_android.homepage.ChangeBuyPrice;
import com.example.administrator.matata_android.homepage.MyPriceTask;
import com.example.administrator.matata_android.homepage.SerializableMap;
import com.example.administrator.matata_android.homepage.adapters.ArtBuyJoinDateAdapter;
import com.example.administrator.matata_android.homepage.adapters.ArtBuyJoinPeopleAdapter;
import com.example.administrator.matata_android.zhzbase.base.BaseActivity;
import com.example.administrator.matata_android.zhzbase.utils.MatataSPUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArtBuyActivity extends BaseActivity {

    @BindView(R.id.title)
    LinearLayout title;
    @BindView(R.id.tv_join_bug_price)
    TextView tvJoinBugPrice;
    @BindView(R.id.tv_join_bug_vip_price)
    TextView tvJoinBugVipPrice;
    @BindView(R.id.tv_join_to_bug_vip)
    TextView tvJoinToBugVip;
    @BindView(R.id.tv_join_bug_name)
    TextView tvJoinBugName;
    @BindView(R.id.rv_join_people)
    RecyclerView rvJoinPeople;
    @BindView(R.id.rv_join_date)
    RecyclerView rvJoinDate;
    @BindView(R.id.btn_buy_subtract)
    Button btnBuySubtract;
    @BindView(R.id.tv_buy_number)
    TextView tvBuyNumber;
    @BindView(R.id.btn_buy_add)
    Button btnBuyAdd;
    @BindView(R.id.et_art_bug_people)
    EditText etArtBugPeople;
    @BindView(R.id.et_art_bug_phone_number)
    EditText etArtBugPhoneNumber;
    @BindView(R.id.art_btn_buy_confirm)
    Button artBtnBuyConfirm;
    @BindView(R.id.art_buy_iv)
    ImageView artBuyIv;

    final String URL="https://www.maiguanjy.com/";
    private ArrayList<String> mData;
    private ArrayList<String> mDate;
    private ArrayList<String> priceData;
    private ArrayList<String> vip_priceData;
    private String is_vip;
    private String cover_pic;
    private String campsite_id;
    private  String contact;
    private String contact_phone;
    private int buyNumber;
    SerializableMap myMap;

    private Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            Bundle bundle=msg.getData();
            tvJoinBugPrice.setText(String.valueOf("¥"+bundle.getString("normalPrice")));
            tvJoinBugVipPrice.setText(String.valueOf("会员价："+bundle.getString("vipPrice")));
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_art_buy);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void getExras() {
        Intent intent = getIntent();
        mData = intent.getStringArrayListExtra("data");
        mDate = intent.getStringArrayListExtra("mDate");
        priceData = intent.getStringArrayListExtra("priceData");
        vip_priceData = intent.getStringArrayListExtra("vip_priceData");
        is_vip = intent.getStringExtra("is_vip");
        cover_pic = intent.getStringExtra("cover_pic");
        campsite_id=intent.getStringExtra("campsite_id");
    }

    @Override
    protected void initData() {
      HashMap<String, Object> dataMap =new HashMap<String ,Object>();

        dataMap.put("campsite_id",campsite_id);

        Glide.with(mContext).load(URL+cover_pic)
                .into(artBuyIv);
        int a = Integer.parseInt(is_vip);
        //若是会员，则隐藏按钮
        if (a == 1) {
            tvJoinToBugVip.setVisibility(View.GONE);
            tvJoinBugPrice.setVisibility(View.GONE);

        }
        tvJoinToBugVip.setVisibility(View.VISIBLE);
        tvJoinBugPrice.setVisibility(View.VISIBLE);

        tvJoinBugPrice.setText(getPrice(priceData.get(0)));

        tvJoinBugVipPrice.setText(String.valueOf("会员价：" + getPrice(vip_priceData.get(0))));
        tvJoinToBugVip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "点击了开通会员", Toast.LENGTH_SHORT).show();
            }
        });

        //加载参与人数
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvJoinPeople.setLayoutManager(linearLayoutManager);
        ArtBuyJoinPeopleAdapter artBuyJoinPeopleAdapter = new ArtBuyJoinPeopleAdapter(this, mData);
        rvJoinPeople.setAdapter(artBuyJoinPeopleAdapter);
        artBuyJoinPeopleAdapter.setSelection(0);
        dataMap.put("attribute",mData.get(0));
        artBuyJoinPeopleAdapter.setOnItemClickLitener(new ArtBuyJoinPeopleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                artBuyJoinPeopleAdapter.setSelection(position);
                //保存价格position
                MatataSPUtils.savePricePosition(position);

                //更新String价格显示
                tvJoinBugPrice.setText(getPrice(priceData.get(position)));
                tvJoinBugVipPrice.setText(String.valueOf("会员价："+getPrice(vip_priceData.get(position))));

                dataMap.put("attribute",mData.get(position));
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }

        });

        //加载参与日期 rvJoinDate
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvJoinDate.setLayoutManager(manager);
        ArtBuyJoinDateAdapter artBuyJoinDateAdapter = new ArtBuyJoinDateAdapter(this, mDate);
        rvJoinDate.setAdapter(artBuyJoinDateAdapter);
        artBuyJoinDateAdapter.setSelect(0);
        dataMap.put("date",mDate.get(0));
        artBuyJoinDateAdapter.setOnItemClickListener(new ArtBuyJoinDateAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                artBuyJoinDateAdapter.setSelect(position);

                dataMap.put("date",mDate.get(position));

            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        });
        //处理购买数量选择
        buyNumber=1;
        tvBuyNumber.setText(getNumber(buyNumber));
        btnBuySubtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (buyNumber>1){
                        buyNumber--;
                        tvBuyNumber.setText(getNumber(buyNumber));

                        new Thread(){
                            @Override
                            public void run() {
                                Message msg=Message.obtain();
                                Bundle bundle=new Bundle();
                                bundle.putString("normalPrice",getPrice(getNumber(Integer.parseInt(priceData.get(MatataSPUtils.getPricePosition()))*buyNumber)));
                                bundle.putString("vipPrice",getPrice(getNumber(Integer.parseInt(vip_priceData.get(MatataSPUtils.getPricePosition()))*buyNumber)));
                                msg.setData(bundle);
                                handler.sendMessage(msg);
                            }
                        }.start();
                    }else {
                        Toast.makeText(mContext, "最少选择一件产品", Toast.LENGTH_SHORT).show();
                    }
            }
        });
        btnBuyAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyNumber++;
                tvBuyNumber.setText(getNumber(buyNumber));
                  new Thread(){
                    @Override
                    public void run() {
                        Message msg=Message.obtain();
                        Bundle bundle=new Bundle();
                        bundle.putString("normalPrice",getPrice(getNumber(Integer.parseInt(priceData.get(MatataSPUtils.getPricePosition()))*buyNumber)));
                        bundle.putString("vipPrice",getPrice(getNumber(Integer.parseInt(vip_priceData.get(MatataSPUtils.getPricePosition()))*buyNumber)));
                        msg.setData(bundle);
                        handler.sendMessage(msg);
                    }
                }.start();
            }
        });
        artBtnBuyConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contact=etArtBugPeople.getText().toString().trim();
                contact_phone=etArtBugPhoneNumber.getText().toString().trim();
                if (StringUtils.isEmpty(contact)){
                    showTextDialog("请输入联系人姓名");
                    return;
                }else {
                    dataMap.put("contact",contact);
                }

                if (StringUtils.isEmpty(contact_phone)){
                    showTextDialog("请输入联系人手机号");
                    return;
                }else {
                    if (contact_phone.length()==11){
                        dataMap.put("contact_phone",contact_phone);
                    }else {
                        showTextDialog("请输入11位手机号码");
                        return;
                    }
                }

                String num = tvBuyNumber.getText().toString().trim();
                dataMap.put("num",num);

                Intent intent = new Intent();
                intent.setClass(ArtBuyActivity.this,ArtBuyCompleteActivity.class);

                myMap=new SerializableMap();
                myMap.setMap(dataMap);

                Bundle bundle=new Bundle();
                bundle.putSerializable("myMap",myMap);
                intent.putExtra("mBundle",bundle);
                startActivity(intent);
            }
        });
    }
    /**
     * 处理接口返回String类型价格
     */
    public String getPrice(String price) {
        int a = Integer.parseInt(price);
        int b = a / 100;
        return  String.valueOf(b);
    }

    /**
     * 处理商品数量
     */
    public String getNumber(int number){
        return  String.valueOf(number);
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
}
