package com.example.administrator.matata_android.httputils;

import android.annotation.SuppressLint;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.matata_android.BuildConfig;
import com.example.administrator.matata_android.zhzbase.base.BaseApplication;
import com.example.administrator.matata_android.zhzbase.base.BaseBean;
import com.example.administrator.matata_android.zhzbase.dialog.TextDialog;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

public class CustomizeGsonResponseBodyConverter<T> implements Converter<ResponseBody,T> {

    private final Gson gson;
    private TextDialog textDialog;

    private final TypeAdapter<T> adapter;
    CustomizeGsonResponseBodyConverter(Gson gson,TypeAdapter<T> adapter){
        this.gson=gson;
        this.adapter=adapter;
    }

    @SuppressLint("LongLogTag")
    @Override
    public T convert(ResponseBody value) throws IOException {
        //把responsebody转为string
        String response=value.string();
        if (BuildConfig.DEBUG){
            //打印后台数据
            Log.e(BuildConfig.APPLICATION_ID,response);
        }
        try{
            //这里的type实际类型
            BaseBeanResponse  baseBean=gson.fromJson(response,BaseBeanResponse.class);
               int code=baseBean.getCode();
               if (code==200){

//                   if ((baseBean.getMessage()) instanceof String) {
//
//                      throw new ResultException(baseBean.getMessage().toString(), code);
//
//                   }
                   return adapter.fromJson(response);
               }else {
                   Log.d("httpManager","err==:"+response);
                   BaseErrorBean errorResponse=gson.fromJson(response,BaseErrorBean.class);
                   if (code==500){
                        throw new ResultException(errorResponse.getMessage(),code);
                   }else {
                       throw new ResultException(errorResponse.getMessage(),code);
                   }
               }

        }finally {
                value.close();
        }

    }
    /**
     * 显示提示弹窗
     *
     * @param text 弹窗提示语
     */
    public void showTextDialog(String text) {
        if (textDialog == null)
            textDialog = new TextDialog(BaseApplication.getInstance());
        textDialog.setText(text);
        textDialog.show();
    }
}
