package com.example.administrator.matata_android.activitys;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.StringUtils;
import com.example.administrator.matata_android.MainActivity;
import com.example.administrator.matata_android.R;
import com.example.administrator.matata_android.login.activitys.LoginActivity;
import com.example.administrator.matata_android.zhzbase.base.BaseActivity;
import com.example.administrator.matata_android.zhzbase.utils.MatataSPUtils;
import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WelcomeActivity extends BaseActivity {

    @BindView(R.id.welcome_pass_btn)
    Button welcomePassBtn;
    @BindView(R.id.welcome_start_img)
    ImageView welcomeStartImg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void getExras() {

    }

    @Override
    protected void initData() {
        /**
         * 为广告页添加过场渐变动画
         */
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.logo);
        animation.setAnimationListener(new StartAnimationListener());
        welcomeStartImg.startAnimation(animation);
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

    @OnClick({R.id.welcome_pass_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.welcome_pass_btn:
                toNext();
                break;


        }
    }
    /**
     * 判断状态后跳转
     */
    public void toNext() {
        //判断是否拥有权限
        PermissionsUtil.requestPermission(WelcomeActivity.this, new PermissionListener() {
            @Override
            public void permissionGranted(@NonNull String[] permission) {
                //权限全部通过之后判断是否登录过，登陆过直接登录跳转到主页 否侧跳转到登陆页面
                String account = MatataSPUtils.getAccount();
                String password = MatataSPUtils.getPassword();
                if (StringUtils.isEmpty(account) || StringUtils.isEmpty(password)) {
                    Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finishActivity();
                    ActivityUtils.finishActivity(WelcomeActivity.class);
                } else {
                    Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                    startActivity(intent);
                    finishActivity();
                    ActivityUtils.finishActivity(WelcomeActivity.class);
                }
            }

            @Override
            public void permissionDenied(@NonNull String[] permission) {
                finishActivity();
            }
        }, new String[]{

               Manifest.permission.WRITE_EXTERNAL_STORAGE,     // 写入权限
               Manifest.permission.READ_EXTERNAL_STORAGE,      //读取权限
//                Manifest.permission.CAMERA,                     //开启相机
//                Manifest.permission.READ_PHONE_STATE,       // 读取手机

        });
    }
    /**
     * 动画接口
     */
    public class StartAnimationListener implements Animation.AnimationListener{

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
                    toNext();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }



}
