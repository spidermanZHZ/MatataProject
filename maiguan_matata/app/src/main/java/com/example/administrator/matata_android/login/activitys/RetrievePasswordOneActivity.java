package com.example.administrator.matata_android.login.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.blankj.utilcode.util.StringUtils;
import com.example.administrator.matata_android.R;
import com.example.administrator.matata_android.httputils.BaseObserver;
import com.example.administrator.matata_android.httputils.RetrofitUtil;
import com.example.administrator.matata_android.zhzbase.base.BaseActivity;
import com.example.administrator.matata_android.zhzbase.view.CountDownTextView;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 找回，修改密码页面
 */
public class RetrievePasswordOneActivity extends BaseActivity {
    @BindView(R.id.sign_et_number)
    EditText signEtNumber;
    @BindView(R.id.sign_et_code)
    EditText signEtCode;
    @BindView(R.id.retrieve_tv_countdown)
    CountDownTextView retrieveTvCountdown;
    @BindView(R.id.retrieve_btn_next)
    Button retrieveBtnNext;

    private BaseObserver<Object> sendCodeBaseObeserver;
    private BaseObserver<Object> confimCodeBaseObeserver;
    private String account;
    private String accountTwo;
    private String registerCode;
    private HashMap<String, Object> sendCodeMap;
    private HashMap<String, Object> confimCodeMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_retrieve_password_one);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void getExras() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {
        retrieveTvCountdown.setNormalText(getResources().getString(R.string.sign_sendcode))
                .setCountDownText("(", "s)")
                .setCountDownClickable(false)
                .setCloseKeepCountDown(false)
                .setShowFormatTime(false)
                .setIntervalUnit(TimeUnit.SECONDS)
                .setOnCountDownStartListener(new CountDownTextView.OnCountDownStartListener() {
                    @Override
                    public void onStart() {
                        sendCode();
                    }
                })
                .setOnCountDownTickListener(new CountDownTextView.OnCountDownTickListener() {
                    @Override
                    public void onTick(long untilFinished) {
                        Log.e("------", "onTick: " + untilFinished);
                    }
                })
                .setOnCountDownFinishListener(new CountDownTextView.OnCountDownFinishListener() {
                    @Override
                    public void onFinish() {
                        Toast.makeText(RetrievePasswordOneActivity.this, "倒计时结束", Toast.LENGTH_SHORT).show();
                    }
                })
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(RetrievePasswordOneActivity.this, "短信已发送", Toast.LENGTH_SHORT).show();
                        retrieveTvCountdown.startCountDown(60);
                    }
                });


        retrieveBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confimCode();
            }
        });

    }
    /**
     * 发送验证码
     */
    public void sendCode() {
        account = signEtNumber.getText().toString();
        sendCodeMap = new HashMap<>();
        if (StringUtils.isEmpty(account)) {
            showTextDialog("请输入手机号");
            return;
        } else {
            if (account.length() == 11) {
                sendCodeMap.put("phone", account);
            } else {
                showTextDialog("请输入11位手机号码");
                return;
            }
        }
        sendCodeBaseObeserver = new BaseObserver<Object>(this, false,true) {
            @Override
            public void onSuccess(Object o) {
                showTextDialog("验证码发送成功");
            }

        };
        RetrofitUtil.getInstance().getApiService().sendcode(sendCodeMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(sendCodeBaseObeserver);

    }
    /**
     * 提交验证码
     */
    public void confimCode(){
        accountTwo = signEtNumber.getText().toString();
        registerCode=signEtCode.getText().toString();
        confimCodeMap=new HashMap<>();
        if (StringUtils.isEmpty(accountTwo)) {
            showTextDialog("请输入手机号");
            return;
        } else {
            if (accountTwo.length() == 11) {
                confimCodeMap.put("phone", accountTwo);
            } else {
                showTextDialog("请输入11位手机号码");
                return;
            }
        }


        if (StringUtils.isEmpty(registerCode)) {
            showTextDialog("请输入验证码");
            return;
        } else {
            if (registerCode.length()==4){
                confimCodeMap.put("code", registerCode);    //添加code
            }else {
                showTextDialog("请输入4位验证码");
                return;
            }

        }

        confimCodeBaseObeserver=new BaseObserver<Object>(this,false,false) {
            @Override
            public void onSuccess(Object o) {
                RetrievePasswordOneActivity();
            }


        };
        RetrofitUtil.getInstance().getApiService().confimcode(confimCodeMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(confimCodeBaseObeserver);

    }

    public void RetrievePasswordOneActivity(){
        Intent intent = new Intent(RetrievePasswordOneActivity.this, RetrievePasswordTwoActivity.class);
        intent.putExtra("phone",account);
        intent.putExtra("code",registerCode);
        startActivity(intent);
        finishActivity();
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