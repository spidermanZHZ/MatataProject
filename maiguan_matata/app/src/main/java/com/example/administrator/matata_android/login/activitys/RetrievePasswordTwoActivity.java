package com.example.administrator.matata_android.login.activitys;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 设置新密码页面
 */
public class RetrievePasswordTwoActivity extends BaseActivity {

    @BindView(R.id.login_et_account)
    EditText loginEtAccount;
    @BindView(R.id.login_et_password)
    EditText loginEtPassword;
    @BindView(R.id.retrieve_btn_confirm)
    Button retrieveBtnConfirm;

    private String account;
    private String accountTwo;
    private String password;
    private String passwordConfim;
    private String registerCode;
    private BaseObserver<LoginBean> registerBaseObserver;
    private BaseObserver<Object> findpwdBaseObserver;
    private HashMap<String, Object> findpwdMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_retrieve_password_two);
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
        retrieveBtnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            findpwd();
            }
        });
    }

    /**
     * 修改密码
     */
    private void findpwd(){
        password = loginEtAccount.getText().toString();//第一遍密码
        passwordConfim = loginEtPassword.getText().toString();//第二遍密码

        findpwdMap = new HashMap<>();

        findpwdMap.put("phone", account); //添加注册手机号
        findpwdMap.put("code", registerCode);    //添加code


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
                    findpwdMap.put("password", passwordConfim);   //添加注册密码
                } else {
                    showTextDialog("两次输入的密码不一致");
                }
            }
        }

        findpwdBaseObserver=new BaseObserver<Object>(this,false,false) {
            @Override
            public void onSuccess(Object o) {
                showTextDialog("修改密码成功跳转登录页...");
                startActivity(new Intent(RetrievePasswordTwoActivity.this, LoginActivity.class));
                finishActivity();
                ActivityUtils.finishActivity(RetrievePasswordTwoActivity.this);
            }

        };

        RetrofitUtil.getInstance().getApiService().findpwd(findpwdMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(findpwdBaseObserver);
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
