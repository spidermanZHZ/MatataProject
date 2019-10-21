package com.example.administrator.matata_android.login.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
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
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 账号登录页面
 */
public class LoginActivity extends BaseActivity {


    @BindView(R.id.login_btn_login)
    Button loginBtnLogin;
    @BindView(R.id.login_tv_sign)
    TextView loginTvSign;
    @BindView(R.id.login_tv_rtpassword)
    TextView loginTvRtpassword;
    @BindView(R.id.login_et_account)
    EditText loginEtAccount;
    @BindView(R.id.login_et_password)
    EditText loginEtPassword;


    private BaseObserver<LoginBean> loginBeanBaseObserver;
    private String account;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);


    }

    @Override
    protected void getExras() {

    }

    @Override
    protected void initData() {
        //限制EditText输入字符
        loginEtPassword.setFilters(new InputFilter[]{new InputFilter() {
            @Override
            public CharSequence filter(CharSequence charSequence, int start, int end, Spanned dest, int dstart, int dend) {
                String regex = "^[\u4E00-\u9FA5]+$";
                boolean isChinese = Pattern.matches(regex, charSequence.toString());
                if (!Character.isLetterOrDigit(charSequence.charAt(start)) || isChinese) {
                    return "";
                }
                return null;
            }
        }});
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

    @OnClick({R.id.login_btn_login, R.id.login_tv_sign, R.id.login_tv_rtpassword})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_btn_login:
                toLogin();
                break;
            case R.id.login_tv_sign:
                startActivity(new Intent(LoginActivity.this, SignInActivity.class));
                finish();
                break;
            case R.id.login_tv_rtpassword:
                startActivity(new Intent(LoginActivity.this, RetrievePasswordOneActivity.class));

                break;
        }
    }

    /**
     * 调用登录接口
     */
    private void toLogin() {

        account=loginEtAccount.getText().toString();
        password=loginEtPassword.getText().toString();
        HashMap<String,Object> loginMap=new HashMap<>();
        if (StringUtils.isEmpty(account)){
            showTextDialog("请输入手机号");
            return;
        }else {
            if (account.length()==11){
                loginMap.put("phone",account);

            }else {
                showTextDialog("请输入11位手机号码");
                return;
            }
        }
        if (StringUtils.isEmpty(password)){
            showTextDialog("请输入密码");
            return;
        }else {
            if (password.length()< 6||password.length()>20){
                showTextDialog(R.string.sign_setpassword);
                return;
            }else {
                loginMap.put("pwd",password);
            }

        }
            loginBeanBaseObserver=new BaseObserver<LoginBean>(this,false,true) {
                @Override
                public void onSuccess(LoginBean loginBean) {
                    showTextDialog("登录成功");
                    //账号
                    MatataSPUtils.saveAccount(account);
                    //密码
                    MatataSPUtils.savePassword(password);
                    //token
                    if (loginBean.getToken()!=null){
                        MatataSPUtils.saveToken(loginBean.getToken());
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finishActivity();
                        ActivityUtils.finishActivity(LoginActivity.this);
                    }else {
                        Toast.makeText(LoginActivity.this, "--------", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(String message) {
                    super.onFailure(message);
                    Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            };

        RetrofitUtil.getInstance().getApiService().loginapp(loginMap)

                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(loginBeanBaseObserver);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (loginBeanBaseObserver!=null){
            loginBeanBaseObserver.destroy();
        }

    }
}
