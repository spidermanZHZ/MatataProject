package com.example.administrator.matata_android.growup;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.matata_android.R;
import com.example.administrator.matata_android.bean.ChildDetailsBean;
import com.example.administrator.matata_android.growup.activity.ChangChildInfoActivity;
import com.example.administrator.matata_android.growup.activity.StudyAdjustActivity;
import com.example.administrator.matata_android.growup.adapter.DateAdapter;
import com.example.administrator.matata_android.httputils.BaseObserver;
import com.example.administrator.matata_android.httputils.RetrofitUtil;
import com.example.administrator.matata_android.zhzbase.base.BaseFragment;
import com.example.administrator.matata_android.zhzbase.utils.DateUtils;
import com.example.administrator.matata_android.zhzbase.utils.MatataSPUtils;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 成长陪伴页面
 */
public class GrowUpFragment extends BaseFragment {

    @BindView(R.id.stu_name)
    TextView stuName;
    @BindView(R.id.stu_sex)
    TextView stuSex;
    @BindView(R.id.stu_sign)
    TextView stuSign;
    @BindView(R.id.stu_edit)
    TextView stuEdit;
    @BindView(R.id.stu_more)
    TextView stuMore;
    @BindView(R.id.stu_all_time)
    TextView stuAllTime;
    @BindView(R.id.stu_test_time)
    TextView stuTestTime;
    @BindView(R.id.stu_all_day)
    TextView stuAllDay;
    @BindView(R.id.stu_day)
    TextView stuDay;
    @BindView(R.id.stu_adjust)
    TextView stuAdjust;
    @BindView(R.id.stu_course_more)
    TextView stuCourseMore;
    @BindView(R.id.up_stu_rv_course)
    RecyclerView upStuRvCourse;
    @BindView(R.id.up_stu_start_test)
    Button upStuStartTest;
    Unbinder unbinder;
    @BindView(R.id.stu_rv_plan_time)
    RecyclerView stuRvPlanTime;
    @BindView(R.id.up_tv_plan_study)
    TextView upTvPlanStudy;
    @BindView(R.id.up_tv_study_already)
    TextView upTvStudyAlready;
    @BindView(R.id.up_tv_study_state)
    TextView upTvStudyState;
    @BindView(R.id.up_ll_plan)
    LinearLayout upLlPlan;

    private String child_id;
    private BaseObserver<ChildDetailsBean> childDetailsBeanBaseObserver;
    private DateAdapter dateAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(initLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    protected int initLayoutId() {
        return R.layout.fragment_growup;
    }

    @Override
    public void onStart() {
        super.onStart();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        stuRvPlanTime.setLayoutManager(linearLayoutManager);
        dateAdapter = new DateAdapter(getContext(), null);
        stuRvPlanTime.setAdapter(dateAdapter);
        getChlidDetails();

    }


    @Override
    protected void getExras() {

    }


    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }

    public void getChlidDetails() {
        HashMap<String, Object> childMap = new HashMap<>();
        childMap.put("token", MatataSPUtils.getToken());
        childMap.put("child_id", "20");

        childDetailsBeanBaseObserver = new BaseObserver<ChildDetailsBean>(getContext(), false, false) {
            @Override
            public void onSuccess(ChildDetailsBean childDetailsBean) {
                child_id = childDetailsBean.getId();
                stuName.setText(childDetailsBean.getName());
                stuSex.setText(String.valueOf(childDetailsBean.getSex() + "\t" + "\t" + childDetailsBean.getAge()));
                stuSign.setText(childDetailsBean.getSignature());
                stuAllTime.setText(childDetailsBean.getTime().getSumTime());
                stuTestTime.setText(childDetailsBean.getTime().getExerciseTime());
                stuAllDay.setText(childDetailsBean.getTime().getOnlineTime());
                stuDay.setText(childDetailsBean.getTime().getOfflineTime());

                //日期加载
                dateAdapter.addData(childDetailsBean.getPlans());
                dateAdapter.notifyDataSetChanged();
                dateAdapter.setOnItemClickListener(new DateAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        dateAdapter.setSelect(position);

                        upTvPlanStudy.setText(String.valueOf("计划学习" + DateUtils.getInstance().stringTime(childDetailsBean.getPlans().get(position).getSum_time()) + "分钟"));
                        upTvStudyAlready.setText(String.valueOf("已按计划学习" + DateUtils.getInstance().stringTime(childDetailsBean.getPlans().get(position).getTime()) + "分钟"));

                        int sun_time = Integer.parseInt(childDetailsBean.getPlans().get(position).getSum_time());
                        int time = Integer.parseInt(childDetailsBean.getPlans().get(position).getTime());
                        if (sun_time > 0) {

                            if (time >= sun_time) {
                                upTvStudyState.setText("已完成计划");
                            } else {
                                upTvStudyState.setText("未完成计划");
                            }
                        } else {
                            upTvStudyState.setText("未制定计划");
                        }
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                });

            }
        };
        RetrofitUtil.getInstance().getApiService().getChildDetails(childMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(childDetailsBeanBaseObserver);

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * 点击事件
     *
     * @param view
     */
    @OnClick({R.id.stu_edit,R.id.stu_more, R.id.stu_adjust})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.stu_edit:
                Intent intent2 = new Intent(getActivity(), ChangChildInfoActivity.class);
                intent2.putExtra("child_id", child_id);
                startActivity(intent2);
                break;

            case R.id.stu_adjust:
                Intent intent = new Intent(getActivity(), StudyAdjustActivity.class);
                intent.putExtra("child_id", child_id);
                startActivity(intent);
                break;

            case R.id.stu_more:

                break;


        }
    }

}
