package com.example.administrator.matata_android.httputils;

import com.example.administrator.matata_android.zhzbase.base.BaseApplication;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;


import java.io.IOException;
import java.net.URLDecoder;
import java.util.concurrent.TimeUnit;
import okhttp3.ConnectionPool;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 封装Retrofit,Okhttp3进行网络请求
 */
public class RetrofitUtil {

    private static RetrofitUtil mInstance;//单实例模式
    private static APIService apiService;//请求接口对象
    private static Retrofit retrofit; //retrofit 对象

    private OkHttpClient okHttpClient;

    //服务器地址
    public static String baseUrl="https://www.maiguanjy.com/api/app/";

    /**
     * 初始化必要对象和参数
     */
    private RetrofitUtil(){
        try {
            InputStream inputStream=BaseApplication.getInstance().getAssets().open("a.pem");

            OkhttpManager.getInstance().setTrustrCertificates(inputStream);

            okHttpClient= OkhttpManager.getInstance().build();

        } catch (IOException e) {
            e.printStackTrace();
        }

        retrofit=new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(CustomizeGsonConverterFactory.create())//自定义解析工厂
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();


//
//        OkHttpClient client =new OkHttpClient().newBuilder()
//                .connectTimeout(20, TimeUnit.SECONDS)
//                .readTimeout(20,TimeUnit.SECONDS)
//                .writeTimeout(20,TimeUnit.SECONDS)
//                //这里可以根据自己的机型设置同时连接的个数和时间,我这里8个,和每个保持时间15s
//                .connectionPool(new ConnectionPool(8,15,TimeUnit.SECONDS))
//                //对所有请求添加请求头!!!
////                .addInterceptor(new Interceptor() {
////                    @Override
////                    public BaseResponse intercept(Chain chain) throws IOException {
////                        Request request = chain.request();
////                    String timestamp =String.valueOf(System.currentTimeMillis());
////                        String token = ShareUtils.getToken();
////                        request = request.newBuilder()
////                                .addHeader("UserId", ShareUtils.getUserId())
////                                .addHeader("Timestamp", timestamp)
////                                .addHeader("Token", token)
////                                .addHeader("Sign", MD5Util.Md5(MD5Util.Md5((token + timestamp)) + sign ))
////                                .build();
////                        return chain.proceed(request);
////                    }
////                })
//                .retryOnConnectionFailure(true)
//                .build();
//
//        retrofit=new Retrofit.Builder()
//                .baseUrl(baseUrl)
//                .client(client)
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .build();

    }

    /**
     * 单实例模式获取RetrofitUtil对象
     * @return retrofitUtil
     */
    public static RetrofitUtil getInstance(){
        if (mInstance==null){
            synchronized (RetrofitUtil.class){
                if (mInstance==null){
                    mInstance=new RetrofitUtil();
                }
            }
        }
        return mInstance;
    }

    /**
     * 单实例模式获取APIService
     * @return apiService
     */
    public  APIService getApiService(){
        if (apiService==null){
            synchronized (APIService.class){
                apiService=retrofit.create(APIService.class);
            }
        }
        return apiService;
    }

}
