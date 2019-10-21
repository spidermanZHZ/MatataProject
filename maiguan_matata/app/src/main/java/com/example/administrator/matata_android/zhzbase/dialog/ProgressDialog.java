package com.example.administrator.matata_android.zhzbase.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.matata_android.R;


/**
 * 自定义dialog交互窗口
 */
public class ProgressDialog {
    private Dialog mProgressDialog;
    private TextView mProgressTextView;
    private static final String TAG = "ProgressDialog";
    private Runnable cancelRunnable=new Runnable() {
        @Override
        public void run() {
         if (mProgressDialog.isShowing()){
             mProgressDialog.cancel();
          }
        }
    };

    /**
     * 设置 progressDialog样式
     * @param context
     */
    public ProgressDialog(Context context){
        mProgressDialog=new Dialog(context, R.style.dialog);
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.dialog_progress,null);
        mProgressTextView=(TextView)view.findViewById(R.id.progressDiaLog_TextView);
        setCancelable(false);
        mProgressDialog.setContentView(view);
        mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                mProgressTextView.removeCallbacks(cancelRunnable);
            }
        });
        mProgressDialog.show();
    }

    /**
     * 设置是否可以点击屏幕外取消弹窗
     * @param cancelable
     */
    public void setCancelable(boolean cancelable){
        mProgressDialog.setCancelable(cancelable);
    }

    /**
     * 设置主题内容
     * @param  text  主题
     */
    public void setText(String text){
        mProgressTextView.setText(text);
    }
    /**
     * 设置主题内容
     * @param textID 主题资源Id
     */
    public void setText(int textID){
        mProgressTextView.setText(textID);
    }

    /**
     * 显示 ProgressDialog
     */
    public void show(){
        mProgressTextView.removeCallbacks(cancelRunnable);
        if (!mProgressDialog.isShowing()){
            mProgressDialog.show();
        }
    }

    /**
     * 取消弹窗
     */
    public void cancelImediately(){
        mProgressDialog.cancel();
        Log.i(TAG, "mProgressDialog已退出");
    }

    /**
     * 延迟取消
     */
    public void cancel(){
        mProgressTextView.postDelayed(cancelRunnable,1000);
    }

}
