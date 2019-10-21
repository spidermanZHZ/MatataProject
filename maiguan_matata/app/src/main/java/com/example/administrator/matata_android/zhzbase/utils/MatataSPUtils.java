package com.example.administrator.matata_android.zhzbase.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.blankj.utilcode.util.SPUtils;

import java.util.Locale;

/**
 * 自定义SP工具类
 */
public class MatataSPUtils {


    private final String SP_NAME = "language_setting";
    private final String TAG_LANGUAGE = "language_select";
    private final String TAG_SYSTEM_LANGUAGE = "system_language";
    private static volatile MatataSPUtils instance;

    private final SharedPreferences mSharedPreferences;

    private Locale systemCurrentLocal = Locale.ENGLISH;


    public MatataSPUtils(Context context) {
        mSharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }


    public void saveLanguage(int select) {
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.putInt(TAG_LANGUAGE, select);
        edit.commit();
    }

    public int getSelectLanguage() {
        return mSharedPreferences.getInt(TAG_LANGUAGE, 0);
    }


    public Locale getSystemCurrentLocal() {
        return systemCurrentLocal;
    }

    public void setSystemCurrentLocal(Locale local) {
        systemCurrentLocal = local;
    }

    public static MatataSPUtils getInstance(Context context) {
        if (instance == null) {
            synchronized (MatataSPUtils.class) {
                if (instance == null) {
                    instance = new MatataSPUtils(context);
                }
            }
        }
        return instance;
    }
    /**
     * 保存app内语言格式
     * @param languageConfig app语言
     */
    public static void saveLanguageConfig(String languageConfig){
        SPUtils.getInstance("AppLanguage").put("languageConfig",languageConfig);


    }

    public static String getLanguageConfig(){
        return SPUtils.getInstance("AppLanguage").getString("languageConfig");
    }

    /**
     * 保存用户token
     * @param token
     */
    public static void saveToken(String token){
        SPUtils.getInstance("UserParts").put("token",token);

    }
    public static String getToken(){
        return SPUtils.getInstance("UserParts").getString("token");
    }

    /**
     * 保存用户账号
     * @param phone
     */

    public static void saveAccount(String phone){
        SPUtils.getInstance("UserParts").put("phone",phone);
    }
    public static String getAccount(){
        return SPUtils.getInstance("UserParts").getString("phone");

    }

    /**
     * 保存用户密码
     * @param pwd
     */
    public static void savePassword(String pwd){
        SPUtils.getInstance("UserParts").put("pwd",pwd);
    }
    public static  String getPassword(){
        return SPUtils.getInstance("UserParts").getString("pwd");
    }

/**
 * 保存用户选择价格position
 */
    public static void savePricePosition(int position){
        SPUtils.getInstance("UserParts").put("position",position);
    }
    public static int getPricePosition(){
        return SPUtils.getInstance("UserParts").getInt("position");
    }
}
