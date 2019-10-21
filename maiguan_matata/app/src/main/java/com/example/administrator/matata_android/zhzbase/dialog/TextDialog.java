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
 * 弹窗提示语样式
 */
public class TextDialog {
    private Dialog mTextDialog;
    private TextView mTextDialogTextView;
    private static final String TAG = "TextDialog";
    private Runnable cancelRunnable=new Runnable() {
        @Override
        public void run() {
            cancel();
        }
    };

    /**
     * 设置TextDialog样式
     * @param context 上下文
     */
    public TextDialog(Context context){
        mTextDialog=new Dialog(context, R.style.dialog);
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.dialog_text,null);
        mTextDialogTextView=(TextView)view.findViewById(R.id.textDialogTextView);
        mTextDialog.setCancelable(true);
        mTextDialog.setContentView(view);
        mTextDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                mTextDialogTextView.removeCallbacks(cancelRunnable);
            }
        });
        mTextDialog.show();
    }

    /**
     * 设置主题内容
     * @param text 主题内容
     */
    public void setText(String text){
        mTextDialogTextView.setText(text);

    }

    /**
     * 主题内容
     * @param textID 主题内容资源ID
     */
    public void setText(int textID){
        mTextDialogTextView.setText(textID);
    }

    /**
     * 延迟时间
     */
    public void show(){
        mTextDialog.show();
        mTextDialogTextView.postDelayed(cancelRunnable,1000);

    }
    /**
     * 取消弹窗
     */
    public void cancel(){
        try{
            mTextDialog.cancel();
            Log.i(TAG, "mTextDialog已退出 ");
        }catch (Exception e){
            Log.i(TAG, "mTextDialog没退出 ");
        }
    }
}
