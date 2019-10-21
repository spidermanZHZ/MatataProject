package com.example.administrator.matata_android.zhzbase.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.matata_android.R;


public class MyMessageDialog extends Dialog {
    private Button yes;//确定按钮
    private Button no;//取消按钮
    private TextView titleTv;//消息标题文本
    private TextView messageTv;//消息提示文本

    private boolean b;  //从外界设置的标题显示隐藏
    private String titleStr;//从外界设置的title文本
    private String messageStr;//从外界设置的消息文本

    private onYesOnClickListener yesOnClickListener;//确定按钮被点击了的监听器
    private onNoOnClickLister noOnClickLister;//取消按钮被点击了的监听器
    //确定文本和取消文本的显示内容
    private String yesStr, noStr;


    public MyMessageDialog(@NonNull Context context) {
        super(context, R.style.myDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_message_dialog);
         setCanceledOnTouchOutside(false);//设置点击弹窗外不能取消动画
        initView();
        initData();
        initEvent();

    }
    /**
     * 初始化UI控件
     */
    private void initView(){
        yes = (Button) findViewById(R.id.yes);
        no = (Button) findViewById(R.id.no);
        titleTv = (TextView) findViewById(R.id.title);
        messageTv = (TextView) findViewById(R.id.message);
    }

    /**
     * 初始化UI控件的显示数据
     */
    private void initData(){
//如果用户自定了title和message
        if (titleStr != null) {
            titleTv.setText(titleStr);
        }
        if (messageStr != null) {
            messageTv.setText(messageStr);
        }
        //如果设置按钮的文字
        if (yesStr != null) {
            yes.setText(yesStr);
        }
        if (noStr != null) {
            no.setText(noStr);
        }
        if (b){
            titleTv.setVisibility(View.GONE);
        }else {
            titleTv.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 初始化UI的确定和取消监听器
     */
    private void initEvent(){
        //设置确定按钮被点击后，向外界提供监听
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (yesOnClickListener!=null){
                    yesOnClickListener.onYesClick();
                }
            }
        });
        // 设置取消按钮被点击后，向外界提供监听
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (noOnClickLister!=null){
                    noOnClickLister.onNoClick();
                }
            }
        });
    }
    /**
     * 从外界Activity为Dialog设置标题
     *
     * @param title
     */
    public void setTitle(String title) {
        titleStr = title;
    }
    public void setTitleGone(boolean b){
        this.b = b;

    }

    /**
     * 从外界Activity为Dialog设置dialog的message
     *
     * @param message
     */
    public void setMessage(String message) {
        messageStr = message;
    }

    /**
     * 设置确定按钮的接口
     */
    public interface onYesOnClickListener{
        public void onYesClick();
    }
    /**
     * 设置取消被点击按钮的接口
     */
    public interface  onNoOnClickLister{
        public void onNoClick();
    }
    /**
     * 设置确定按钮内容
     * @param string    确定按钮内容
     * @param onYesOnClickListener  确定按钮点击接口
     */
    public void setOnYesOnClickListener(String string,onYesOnClickListener onYesOnClickListener){
        if (string!=null){
            yesStr=string;
        }
        this.yesOnClickListener=onYesOnClickListener;
    }

    /**
     * 设置取消按钮的显示内容和监听
     * @param s NO按钮的内容
     * @param onNoOnClickLister 监听
     */
    public void setNoOnclickListener(String s,onNoOnClickLister onNoOnClickLister){
        if (s!=null){
            noStr=s;
        }
        this.noOnClickLister=onNoOnClickLister;
    }


}
