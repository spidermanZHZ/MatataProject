package com.example.administrator.matata_android.zhzbase.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;

import com.blankj.utilcode.util.BarUtils;
import com.example.administrator.matata_android.R;
import com.example.administrator.matata_android.zhzbase.dialog.ProgressDialog;
import com.example.administrator.matata_android.zhzbase.dialog.TextDialog;
import com.example.administrator.matata_android.zhzbase.utils.LocalManageUtil;


import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 自定义抽象基类BaseActivity
 */
        public abstract class BaseActivity  extends Activity {

            protected Activity mContext;
            Unbinder unbinder;


            private TextDialog textDialog;
          private ProgressDialog progressDialog;

            @Override
            protected void onCreate(@Nullable Bundle savedInstanceState) {
                unbinder= ButterKnife.bind(this);

                super.onCreate(savedInstanceState);

              BarUtils.setStatusBarColor(this,getResources().getColor(R.color.transparent),true);

              //  StatusBarCompat.translucentStatusBar(this,true);


        this.mContext=this;
        getExras();
        initData();
        setListener();
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    //获取Intent传递的数据
    protected abstract void getExras();

    //网络请求等后续操作
    protected abstract void initData();

    //View设置点击事件
    protected abstract void setListener();

    /**
     * @return 返回值为true，则表示用户点击了返回键
     */
    protected abstract boolean onKeyBack();
    /**
     * @return 返回值为true，则表示用户点击了菜单键
     */
    protected abstract boolean onKeyMenu();
    /**
     *  避免按键事件冲突
     * @param keyCode 点击事件状态码
     * @param event 点击事件
     * @return  返回处理结果
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){
            case 4://返回键keyCode
                if (onKeyBack()){
                    return true;
                }
                return super.onKeyDown(keyCode,event);
            case 82:
                if (onKeyMenu()){
                    return true;
                }
                return super.onKeyDown(keyCode,event);
            default:
                return super.onKeyDown(keyCode,event);
        }
    }
    /**
     * 实现activity切换的动画效果
     *
     * @param animIn  R.anim.slide_in_right 新的Activity进入时的动画，这里是指OtherActivity进入时的动画
     * @param animOut R.anim.slide_out_left 旧的Activity出去时的动画，这里是指this进入时的动画
     */
    public void finish(int animIn, int animOut) {
        cancelTextDialog();
        if (progressDialog != null)
            progressDialog.cancelImediately();
        this.finish();
        this.overridePendingTransition(animIn, animOut);
    }

    public void finishActivity(){
        cancelTextDialog();
        if (progressDialog != null)
            progressDialog.cancelImediately();
        this.finish();


    }


    /**
     * 设置Activity跳转时以及动画
     * Android为不同Activity之间的切换，提供了一个overridePendingTransition(A，B)函数
     *
     * @param intent  intent对象
     * @param animIn  R.anim.slide_in_right 新的Activity进入时的动画，这里是指OtherActivity进入时的动画
     * @param animOut R.anim.slide_out_left 旧的Activity出去时的动画，这里是指this进入时的动画
     */
    public void startActivity(Intent intent, int animIn, int animOut) {
        this.startActivity(intent);
        if (this.getParent() != null) {
            this.getParent().overridePendingTransition(animIn, animOut);
        } else {
            this.overridePendingTransition(animIn, animOut);
        }
    }

    /**
     * 使用startActivityForResult(Intent intent, int requestCode)方法打开新的Activity，
     * 我们需要为startActivityForResult()方法传入一个请求码(第二个参数)。
     * 请求码的值是根据业务需要由自已设定，用于标识请求来源
     *
     * @param intent      intent对象
     * @param requestCode 传入请求码，标识请求来源
     * @param animIn      R.anim.slide_in_right 新的Activity进入时的动画，这里是指OtherActivity进入时的动画
     * @param animOut     R.anim.slide_out_left 旧的Activity出去时的动画，这里是指this进入时的动画
     */

    public void startActivityForResult(Intent intent, int requestCode, int animIn, int animOut) {
        this.startActivityForResult(intent, requestCode);
        if (this.getParent() != null) {
            this.getParent().overridePendingTransition(animIn, animOut);
        } else {
            this.overridePendingTransition(animIn, animOut);
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocalManageUtil.setLocal(newBase));
    }

    /**
     * 显示交互弹窗(默认不可以点击弹窗外侧取消)
     *
     * @param text 弹窗提示语
     */
    public void showProgressDialog(String text) {
        if (progressDialog == null)
            progressDialog = new ProgressDialog(this);
        progressDialog.setText(text);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    /**
     * 显示交互弹窗
     *
     * @param text       弹窗提示语id
     * @param cancelable 是否可以点击弹窗外侧取消
     */
    public void showProgressDialog(String text, boolean cancelable) {
        if (progressDialog == null)
            progressDialog = new ProgressDialog(this);
        progressDialog.setText(text);
        progressDialog.setCancelable(cancelable);
        progressDialog.show();
    }

    /**
     * 显示交互弹窗(默认不可以点击弹窗外侧取消)
     *
     * @param text 弹窗提示语
     */
    public void showProgressDialog(int text) {
        if (progressDialog == null)
            progressDialog = new ProgressDialog(this);
        progressDialog.setText(text);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    /**
     * 显示交互弹窗
     *
     * @param text       弹窗提示语
     * @param cancelable 是否可以点击弹窗外侧取消
     */
    public void showProgressDialog(int text, boolean cancelable) {
        if (progressDialog == null)
            progressDialog = new ProgressDialog(this);
        progressDialog.setText(text);
        progressDialog.setCancelable(cancelable);
        progressDialog.show();
    }

    /**
     * 取消交互弹窗(同时setCancelable(false))
     */
    public void cancelProgressDialog() {
        if (progressDialog != null) {
            progressDialog.setCancelable(false);
            progressDialog.cancel();
        }
    }

    /**
     * 显示提示弹窗
     *
     * @param text 弹窗提示语
     */
    public void showTextDialog(String text) {
        if (textDialog == null)
            textDialog = new TextDialog(this);
        textDialog.setText(text);
        textDialog.show();
    }

    /**
     * 显示提示弹窗
     *
     * @param text 弹窗提示语id
     */
    public void showTextDialog(int text) {
        if (textDialog == null)
            textDialog = new TextDialog(this);
        textDialog.setText(text);
        textDialog.show();
    }

    /**
     * 取消提示弹窗
     */
    public void cancelTextDialog() {
        if (textDialog != null)
            textDialog.cancel();
    }


}
