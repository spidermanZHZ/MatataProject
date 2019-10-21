package com.example.administrator.matata_android.my;

import android.view.MotionEvent;
import android.view.View;

import com.example.administrator.matata_android.R;
import com.example.administrator.matata_android.zhzbase.base.BaseFragment;

public class MyFragment extends BaseFragment {

    @Override
    protected int initLayoutId() {
        return R.layout.fragment_my;
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
}
