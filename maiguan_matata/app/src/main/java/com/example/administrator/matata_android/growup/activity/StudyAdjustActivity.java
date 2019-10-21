package com.example.administrator.matata_android.growup.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.Utils;
import com.example.administrator.matata_android.R;
import com.example.administrator.matata_android.bean.WeekSelectBean;
import com.example.administrator.matata_android.growup.adapter.DateAdjustAdapter;
import com.example.administrator.matata_android.httputils.BaseObserver;
import com.example.administrator.matata_android.httputils.RetrofitUtil;
import com.example.administrator.matata_android.zhzbase.base.BaseActivity;
import com.example.administrator.matata_android.zhzbase.dialog.TextDialog;
import com.example.administrator.matata_android.zhzbase.utils.MatataSPUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 调整学习计划页面
 */
public class StudyAdjustActivity extends BaseActivity {

    @BindView(R.id.tv_adjust_week_day)
    TextView tvAdjustWeekDay;
    @BindView(R.id.adjust_rv)
    RecyclerView adjustRv;
    @BindView(R.id.tv_adjust_day_time)
    TextView tvAdjustDayTime;
    @BindView(R.id.seekBar1)
    SeekBar seekBar1;
    @BindView(R.id.adjust_btn_confim)
    Button adjustBtnConfim;

    private BaseObserver<Object> changeTimeBaseOberver;

    private String child_id;
    private double width, fDensity;
    private int numbers = 0;
    private DisplayMetrics displayMetrics;

    private List<Map<String, Object>> data;

    private WeekSelectBean weekSelectBean;

    private DateAdjustAdapter adjustAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_study_adjust);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void getExras() {

        Intent intent = getIntent();
        child_id=intent.getStringExtra("child_id");
    }

    @Override
    protected void initData() {

        setWeekSelectBeanList();

        seekBar1.getThumb().setColorFilter(getResources().getColor(R.color.background_title), PorterDuff.Mode.SRC_ATOP);
        seekBar1.getProgressDrawable().setColorFilter(getResources().getColor(R.color.background_title), PorterDuff.Mode.SRC_ATOP);
        tvAdjustWeekDay.setText(String.valueOf("0天"));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        adjustRv.setLayoutManager(linearLayoutManager);

        adjustAdapter = new DateAdjustAdapter(this, data);
        adjustRv.setAdapter(adjustAdapter);


        adjustAdapter.setOnItemClickListener(new DateAdjustAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                // adjustAdapter.setSelect(position);
                //更新选择了几天
                tvAdjustWeekDay.setText(String.valueOf(adjustAdapter.getCheckedArrays().size() + "天"));


               Toast.makeText(mContext, adjustAdapter.getCheck_position().replace("=", "").replace("8", "").replace("{","").replace("}",""), Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        });

        displayMetrics = getResources().getDisplayMetrics();
        width = displayMetrics.widthPixels;
        fDensity = (width - dip2px(this, 51)) / 100;

        seekBar1.setProgress(numbers);
        seekBar1.setOnSeekBarChangeListener(mSeekChange);
//        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
//        params.leftMargin=(int) (progress*fDensity);
//        tvAdjustDayTime.setLayoutParams(params);
        tvAdjustDayTime.setText(String.valueOf(numbers));


    }

    /**
     * 自定义Bean添加数据
     */
    public void setWeekSelectBeanList() {
        data = new ArrayList<Map<String, Object>>();
        Map<String, Object> item;
        item = new HashMap<String, Object>();
        item.put("星期", "周一");
        item.put("数字", "1");
        data.add(item);

        item = new HashMap<String, Object>();
        item.put("星期", "周二");
        item.put("数字", "2");
        data.add(item);

        item = new HashMap<String, Object>();
        item.put("星期", "周三");
        item.put("数字", "3");
        data.add(item);

        item = new HashMap<String, Object>();
        item.put("星期", "周四");
        item.put("数字", "4");
        data.add(item);

        item = new HashMap<String, Object>();
        item.put("星期", "周五");
        item.put("数字", "5");
        data.add(item);

        item = new HashMap<String, Object>();
        item.put("星期", "周六");
        item.put("数字", "6");
        data.add(item);

        item = new HashMap<String, Object>();
        item.put("星期", "周日");
        item.put("数字", "7");
        data.add(item);

    }

    private SeekBar.OnSeekBarChangeListener mSeekChange = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            numbers = progress;

//        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
//        params.leftMargin=(int) (progress*fDensity);
//        tvAdjustDayTime.setLayoutParams(params);

            tvAdjustDayTime.setText(String.valueOf(numbers));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };


    /**
     * 根据手机分辨率从 px(像素) 单位 转成 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 根据手机分辨率从 dp 单位 转成 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
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

    @OnClick(R.id.adjust_btn_confim)
    public void onClick() {

        changeTime();

    }

    /**
     * 编辑学员的周计划
     */
    public void changeTime(){
        Map<String,Object> map =new HashMap<String,Object>();
        String ss= adjustAdapter.getCheck_position().replace("=", "").replace("8", "").replace("{","").replace("}","").replace(" ","");

        if (adjustAdapter.getCheckedArrays().size()>1){
            String[] arr=ss.replace(" ","").split(",");

            int [] arr2=new int[arr.length];
            for (int n=0;n<arr.length;n++){
                arr2[n]=Integer.parseInt(arr[n]);
            }
            int[] arr3=new int[arr2.length];
            for (int i =0;i<arr2.length;i++){
                arr3[i]=arr2[i]+1;
            }

            String[] arr4 = new String[arr3.length];
            for (int j=0;j<arr3.length;j++){
                arr4[j]=String.valueOf(arr3[j]);
            }
            String week =Arrays.toString(arr4).replace("[","").replace("]","");
            Toast.makeText(mContext, week, Toast.LENGTH_SHORT).show();
            map.put("day",week);
        }else if (adjustAdapter.getCheckedArrays().size()==1){
            int c= Integer.parseInt(ss);
            int d =c+1;
            map.put("day",String.valueOf(d));

        }else {
            map.put("day","");
        }



        map.put("token", MatataSPUtils.getToken());

        map.put("time",String.valueOf(tvAdjustDayTime.getText().toString()));
        map.put("child_id",child_id);

        changeTimeBaseOberver=new BaseObserver<Object>(this,true,false) {
            @Override
            public void onSuccess(Object o) {

                StudyAdjustActivity.this.showProgressDialog("调整成功",false);

                ActivityUtils.finishActivity(StudyAdjustActivity.this);

            }

        };
        RetrofitUtil.getInstance().getApiService().changeTime(map)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(changeTimeBaseOberver);
    }


}
