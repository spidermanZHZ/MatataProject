package com.example.administrator.matata_android.zhzbase.base;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import com.blankj.utilcode.util.Utils;
import com.example.administrator.matata_android.zhzbase.utils.LocalManageUtil;
import com.zhy.autolayout.config.AutoLayoutConifg;

/**
 * 自定义BaseApplication
 * Manifest中application添加name属性
 */
public class BaseApplication extends Application{

    private static BaseApplication baseApplication=null;

    public BaseApplication(){}

    public static BaseApplication getInstance(){return baseApplication;}

    /**
     * 第三方配置
     */
    @Override
    public void onCreate() {
        baseApplication=this;
        super.onCreate();
        Utils.init(this);//初始化工具类
        LocalManageUtil.setApplicationLanguage(this);//语言设置
        AutoLayoutConifg.getInstance().useDeviceSize();//自动适配框架设置
    }

    @Override
    protected void attachBaseContext(Context base) {
        //保存系统语言
        LocalManageUtil.saveSystemCurrentLanguage(base);
        super.attachBaseContext(base);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //保存系统选择语言
        LocalManageUtil.onConfigurationChanged(getApplicationContext());
    }




}
