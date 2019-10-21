package com.example.administrator.matata_android.httputils;

import android.content.Context;
import android.os.Looper;
import android.support.annotation.CallSuper;
import android.util.Log;
import android.widget.Toast;

import com.blankj.utilcode.util.StringUtils;
import com.example.administrator.matata_android.zhzbase.dialog.ProgressDialog;
import com.example.administrator.matata_android.zhzbase.dialog.TextDialog;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * 自定义观察者
 * @param <T>
 */
public abstract class BaseObserver<T> implements Observer<BaseBeanResponse<T>> {

    private ProgressDialog progressDialog;//网络请求提示progress
    private TextDialog textDialog;
    private Context mContext;
    private boolean isShowTextDialog;// 是否显示提示弹窗
    private Disposable disposable;//中断网络请求
    private final int SUCCESS_CODE = 200;  //请求成功code码
    private final int FALL_CODE = 500;    //请求失败code码
    private final int TOKEN_FAILURE_CODE = 401;//token失效code码


    public BaseObserver(Context context,boolean isShowProgress,boolean isShowTextDialog){
        this.mContext=context;
        this.isShowTextDialog=isShowTextDialog;
        if (isShowProgress){
            showProgressDialog("数据加载中");
        }
    }
    public abstract void onSuccess(T t);

    @Override
    public void onSubscribe(Disposable d) {
        this.disposable=d;
    }

    @CallSuper
    public void onFailure(String message){
        if (!StringUtils.isEmpty(message)){
            showTextDialog(message);
        }
    }

    @Override
    public void onNext(BaseBeanResponse<T> tBaseBeanResponse) {
      try{
          cancelProgressDialog();
          cancelTextDialog();
          switch (tBaseBeanResponse.getCode()){
              case SUCCESS_CODE:
                  onSuccess(tBaseBeanResponse.getMessage());
                  break;
              case TOKEN_FAILURE_CODE:
                  //退出登录，重新登录
                  break;
              case FALL_CODE:
                  onFailure(tBaseBeanResponse.getStatus());
                  break;
          }

      }
          catch (Exception e){

          }


    }

    /**
     * 服务错误类型
     * @param t 错误
     */
    @Override
    public void onError(Throwable t) {
        cancelProgressDialog();
        cancelTextDialog();
        String errorMessage = "未知错误";
        if (t instanceof HttpException) {
            HttpException httpException = (HttpException) t;
            errorMessage = httpException.getMessage();
        } else if (t instanceof SocketTimeoutException) {  //VPN open
            errorMessage = "服务器响应超时";
        } else if (t instanceof ConnectException) {
            errorMessage = "网络连接异常，请检查网络";
        }

//        else if (t instanceof RuntimeException) {
//
//            Toast.makeText(mContext,t.getMessage(), Toast.LENGTH_SHORT).show();
//            Log.e("-------",t.getMessage());
//
//          //  errorMessage = "运行时错误";
//        }

        else if (t instanceof UnknownHostException) {
            errorMessage = "无法解析主机，请检查网络连接";
        }
        else if (t instanceof UnknownServiceException) {
            errorMessage = "未知的服务器错误";
        } else {
            if (t instanceof ResultException){
                errorMessage=((ResultException)t).getErrMsg();
            }else {
                errorMessage="网络连接超时，请稍后再试...";
            }
        }

//
//        else if (t instanceof IOException) {  //飞行模式等
//            errorMessage = "没有网络，请检查网络连接";
//        }
//
        if (isShowTextDialog){
            showTextDialog(errorMessage);
        }
    }

    @Override
    public void onComplete() {
        cancelProgressDialog();
        cancelTextDialog();

    }

    /**
     * 取消网络请求
     */
    public void destroy(){
        disposable.dispose();
        cancelProgressDialog();
        cancelTextDialog();
    }

    /**
     * 显示交互弹窗(默认不可以点击弹窗外侧取消)
     * @param text 弹窗提示语
     */

    public void showProgressDialog(String text) {
        if (progressDialog == null)
            progressDialog = new ProgressDialog(mContext);
        progressDialog.setText(text);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
    /**
     * 取消交互弹窗(同时setCancelable(false))
     */
    public void cancelProgressDialog() {
        if (progressDialog != null) {
            progressDialog.setCancelable(false);
            progressDialog.cancelImediately();
        }
    }
    /**
     * 显示提示弹窗
     *
     * @param text 弹窗提示语
     */
    public void showTextDialog(final String text) {
        new Thread(){
            @Override
            public void run() {
                Looper.prepare();
                if (textDialog == null)
                    textDialog = new TextDialog(mContext);
                textDialog.setText(text);
                textDialog.show();

                Looper.loop();// 进入loop中的循环，查看消息队列
            }
        }.start();

    }

    public void cancelTextDialog(){
        if (textDialog!=null){
            textDialog.cancel();
        }
    }
}
