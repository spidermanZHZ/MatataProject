package com.example.administrator.matata_android.httputils;

import android.content.Context;
import android.content.SharedPreferences;

import com.blankj.utilcode.util.StringUtils;
import com.example.administrator.matata_android.zhzbase.base.BaseApplication;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 自定义Interceptor保存cookie,实现持久化
 */
public class SaveCookiesInterceptor implements Interceptor{

    private static final String COOKIE_PREF="cookies_prefs";
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request=chain.request();
        Response response=chain.proceed(request);

        if (!response.header("Set-Cookie").isEmpty()){
            List<String> cookies=response.headers("Set-Cookie");
            String cookie =encodeCookie(cookies);
            saveCookie(request.url().toString(),request.url().host(),cookie);
        }

        return response;
    }

    /**
     * 整合cookie为唯一字符串
     * @param cookies
     * @return
     */
    private String encodeCookie(List<String> cookies){
        StringBuilder sb=new StringBuilder();
        Set<String> set =new HashSet<>();
        for (String cookie: cookies){
            String[] arr=cookie.split(";");
            for (String s :arr){
                if (set.contains(s)){
                    continue;
                }
                set.add(s);
            }
        }
        Iterator<String > ite=set.iterator();
        while (ite.hasNext()){
            String cookie=ite.next();//这一步
            if (cookie.contains("laravel_session")){
                sb.append(cookie).append(";");
            }
        }
        int last=sb.lastIndexOf(";");
        if (sb.length() - 1 ==last ){
            sb.deleteCharAt(last);
        }
        return sb.toString();
    }
    /**
     * 持久化cookie
     */
    private void saveCookie(String url,String domain,String cookies){

        SharedPreferences sp= BaseApplication.getInstance().getSharedPreferences(COOKIE_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        if (StringUtils.isEmpty(url)){
            throw  new NullPointerException("url is null");

        }else {
            editor.putString(url,cookies);
        }

        if (!StringUtils.isEmpty(domain)){
            editor.putString(domain,cookies);
        }
        editor.apply();

    }

}
