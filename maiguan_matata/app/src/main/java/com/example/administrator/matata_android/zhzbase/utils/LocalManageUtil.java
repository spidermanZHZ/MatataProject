package com.example.administrator.matata_android.zhzbase.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.util.DisplayMetrics;
import android.util.Log;


import com.example.administrator.matata_android.R;

import java.util.Locale;

/**
 * 语言工具类
 */
public class LocalManageUtil {
    private static final String TAG="LocalManageUtil";
    public static Locale getSystemLocale(Context context){

        return  MatataSPUtils.getInstance(context).getSystemCurrentLocal();

    }
    public static String getSelectLanguage(Context context) {
        switch (MatataSPUtils.getInstance(context).getSelectLanguage()) {
            case 0:
                return context.getString(R.string.language_auto);
            case 1:
                return context.getString(R.string.language_cn);
            case 2:
            default:
                return context.getString(R.string.language_en);
        }
    }
    /**
     * 获取选择的语言设置
     *
     * @param context
     * @return
     */
    public static Locale getSetLanguageLocale(Context context) {

        switch (MatataSPUtils.getInstance(context).getSelectLanguage()) {
            case 0:
                return getSystemLocale(context);
            case 1:
                return Locale.CHINA;
            case 2:
            default:
                return Locale.ENGLISH;
        }
    }
    public static void saveSelectLanguage(Context context, int select) {
        MatataSPUtils.getInstance(context).saveLanguage(select);
        setApplicationLanguage(context);
    }
    public static Context setLocal(Context context) {
        return updateResources(context, getSetLanguageLocale(context));
    }
    /**
     * 更新系统语言
     * @param context 上下文
     * @param locale 所选语言
     * @return
     */
    private static Context updateResources(Context context, Locale locale) {
        Locale.setDefault(locale);

        Resources res = context.getResources();
        Configuration config = new Configuration(res.getConfiguration());
        if (Build.VERSION.SDK_INT >= 17) {
            config.setLocale(locale);
            context = context.createConfigurationContext(config);
        } else {
            config.locale = locale;
            res.updateConfiguration(config, res.getDisplayMetrics());
        }
        return context;
    }
    /**
     * 设置语言类型
     */
    public static void setApplicationLanguage(Context context) {
        Resources resources = context.getApplicationContext().getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        Locale locale = getSetLanguageLocale(context);
        config.locale = locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            LocaleList localeList = new LocaleList(locale);
            LocaleList.setDefault(localeList);
            config.setLocales(localeList);
            context.getApplicationContext().createConfigurationContext(config);
            Locale.setDefault(locale);
        }
        resources.updateConfiguration(config, dm);
    }

    /**
     * 保存系统语言
     * @param context 上下文
     */
    public static void saveSystemCurrentLanguage(Context context) {
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = LocaleList.getDefault().get(0);
        } else {
            locale = Locale.getDefault();
        }
        Log.d(TAG, locale.getLanguage());
        MatataSPUtils.getInstance(context).setSystemCurrentLocal(locale);
    }
    public static void onConfigurationChanged(Context context){
        saveSystemCurrentLanguage(context);
        setLocal(context);
        setApplicationLanguage(context);
    }
}
