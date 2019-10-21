package com.example.administrator.matata_android.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.administrator.matata_android.R;
import com.example.administrator.matata_android.homepage.activitys.ArtCampActivity;
import com.example.administrator.matata_android.homepage.activitys.MusicCollageActivity;
import com.example.administrator.matata_android.zhzbase.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * 首页fragment页面
 */
public class HomePageFragment extends BaseFragment {
    @BindView(R.id.banner_guide_content)
    BGABanner bannerGuideContent;
    @BindView(R.id.ll_homepage_art_camp)
    LinearLayout llHomepageArtCamp;
    @BindView(R.id.rv_homepage_teacher)
    RecyclerView rvHomepageTeacher;
    @BindView(R.id.rv_homepage_course)
    RecyclerView rvHomepageCourse;
    Unbinder unbinder;
    @BindView(R.id.ll_homepage_music)
    LinearLayout llHomepageMusic;
    @BindView(R.id.ll_homepage_theatre)
    LinearLayout llHomepageTheatre;
    @BindView(R.id.ll_homepage_grade)
    LinearLayout llHomepageGrade;

    @Override
    protected int initLayoutId() {
        return R.layout.fragment_homepage;
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

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.ll_homepage_music, R.id.ll_homepage_theatre, R.id.ll_homepage_art_camp, R.id.ll_homepage_grade})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_homepage_music:
                startActivity(new Intent(getActivity(), MusicCollageActivity.class));
                break;
            case R.id.ll_homepage_theatre:
                break;
            case R.id.ll_homepage_art_camp:
                startActivity(new Intent(getActivity(), ArtCampActivity.class));
                break;
            case R.id.ll_homepage_grade:
                break;
        }
    }
}
