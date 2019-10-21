package com.example.administrator.matata_android.httputils;

import android.content.Context;
import android.content.SharedPreferences;

import com.blankj.utilcode.util.StringUtils;
import com.example.administrator.matata_android.zhzbase.base.BaseApplication;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 自定义拦截器，判断如果该请求存在Cookie,则为其添加到Header的Cookie中
 */
public class AddCookiesInterceptor implements Interceptor {
    private static  final  String COOKIE_PREF="cookies_prefs";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request=chain.request();
        Request.Builder builder=request.newBuilder();
        String cookie=getCookie(request.url().toString(),request.url().host());
        if (!StringUtils.isEmpty(cookie)){
            builder.addHeader("Cookie",cookie);
        }
        return chain.proceed(builder.build());
    }
    private String getCookie(String url ,String domain){
        SharedPreferences sp = BaseApplication.getInstance().getSharedPreferences(COOKIE_PREF, Context.MODE_PRIVATE);
        if (!StringUtils.isEmpty(domain)&&sp.contains(domain)&&!StringUtils.isEmpty(sp.getString(domain,""))){

            return sp.getString(domain,"");
        }

        return null;

    }


}
