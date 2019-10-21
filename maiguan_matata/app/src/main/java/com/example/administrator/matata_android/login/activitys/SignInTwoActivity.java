package com.example.administrator.matata_android.login.activitys;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.StringUtils;
import com.example.administrator.matata_android.MainActivity;
import com.example.administrator.matata_android.R;
import com.example.administrator.matata_android.bean.LoginBean;
import com.example.administrator.matata_android.httputils.BaseObserver;
import com.example.administrator.matata_android.httputils.RetrofitUtil;
import com.example.administrator.matata_android.zhzbase.base.BaseActivity;
import com.example.administrator.matata_android.zhzbase.utils.MatataSPUtils;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SignInTwoActivity extends BaseActivity {

    @BindView(R.id.sing_et_setpassword)
    EditText singEtSetpassword;
    @BindView(R.id.sign_et_confirmpassword)
    EditText signEtConfirmpassword;
    @BindView(R.id.sign_cb_agree)
    CheckBox signCbAgree;
    @BindView(R.id.sign_tv_agree)
    TextView signTvAgree;
    @BindView(R.id.sign_tv_agreeservice)
    TextView signTvAgreeservice;
    @BindView(R.id.sign_btn_sign)
    Button signBtnSign;
    @BindView(R.id.sign_tv_hasaccount)
    TextView signTvHasaccount;
    private String account;
    private String accountTwo;
    private String password;
    private String passwordConfim;
    private String registerCode;
    private BaseObserver<LoginBean> registerBaseObserver;
    private HashMap<String, Object> registerAccountMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_sign_in_two);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void getExras() {
        Intent intent = getIntent();
        account = intent.getStringExtra("phone");
        registerCode = intent.getStringExtra("code");

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {
        signBtnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                registerAccount();
            }
        });

    }

    /**
     * 注册账号
     */
    public void registerAccount() {

        password = singEtSetpassword.getText().toString();//第一遍密码
        passwordConfim = signEtConfirmpassword.getText().toString();//第二遍密码

        registerAccountMap = new HashMap<>();

        registerAccountMap.put("phone", account); //添加注册手机号
        registerAccountMap.put("code", registerCode);    //添加code


        if (StringUtils.isEmpty(password)) {
            showTextDialog("请输入密码");
            return;
        } else if (password.length() < 6 || password.length() > 20) {
            showTextDialog(R.string.sign_setpassword);
            return;
        }
        if (StringUtils.isEmpty(passwordConfim)) {
            showTextDialog("请确认密码");
            return;
        } else {
            if (passwordConfim.length() < 6 || passwordConfim.length() > 20) {
                showTextDialog(R.string.sign_setpassword);
                return;
            } else {
                if (password.equals(passwordConfim)) {
                    registerAccountMap.put("password", passwordConfim);   //添加注册密码
                } else {
                    showTextDialog("两次输入的密码不一致");
                }
            }
        }

        registerBaseObserver = new BaseObserver<LoginBean>(this, false, true) {
            @Override
            public void onSuccess(LoginBean loginBean) {
                showTextDialog("注册成功正在跳转首页...");
                //账号
                MatataSPUtils.saveAccount(account);
                //密码
                MatataSPUtils.savePassword(password);
                //token
                if (loginBean.getToken() != null) {
                    MatataSPUtils.saveToken(loginBean.getToken());
                    startActivity(new Intent(SignInTwoActivity.this, MainActivity.class));
                    finishActivity();
                    ActivityUtils.finishActivity(SignInTwoActivity.this);
                } else {
                    Toast.makeText(SignInTwoActivity.this, "--------", Toast.LENGTH_SHORT).show();
                }
            }
        };
        RetrofitUtil.getInstance().getApiService().registerAccount(registerAccountMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(registerBaseObserver);

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
