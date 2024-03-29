package com.example.administrator.matata_android.httputils;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import io.reactivex.annotations.Nullable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * 自定义GsonConverterFactory实现相同接口返回不同类型数据的处理
 */
public class CustomizeGsonConverterFactory extends Converter.Factory {

    public static CustomizeGsonConverterFactory create() {
        return create(new Gson());
    }
    public static CustomizeGsonConverterFactory create(Gson gson){
        return new CustomizeGsonConverterFactory(gson);
    }
    private final Gson gson;

    private CustomizeGsonConverterFactory(Gson gson){
        if (gson==null){
            throw  new NullPointerException("gson==null");
        }
        this.gson=gson;
    }
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
                                                            Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new CustomizeGsonResponseBodyConverter<>(gson, adapter);
    }
    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new CustomizeGsonRequestBodyConverter<>(gson, adapter);
    }
}
