package com.example.administrator.matata_android;


import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.administrator.matata_android.growup.GrowUpFragment;
import com.example.administrator.matata_android.homepage.HomePageFragment;
import com.example.administrator.matata_android.my.MyFragment;
import com.example.administrator.matata_android.zhzbase.base.BaseFragmentActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * app主页面
 */
public class MainActivity extends BaseFragmentActivity {

    @BindView(R.id.rb_frist)
    RadioButton rbFrist;
    @BindView(R.id.rb_study)
    RadioButton rbStudy;
    @BindView(R.id.rb_my)
    RadioButton rbMy;
    @BindView(R.id.rg_tab)
    RadioGroup rgTab;
    private HomePageFragment homePageFragment;
    private GrowUpFragment growUpFragment;
    private MyFragment myFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void getExras() {

    }

    @Override
    protected void initData() {
        onFragmentRbClick(0);
    }

    @Override
    protected void setListener() {

    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.getIntExtra("HomePageFragment",-1)==0){
            rbFrist.setChecked(true);
            onFragmentRbClick(0);
        }

    }

    /**
     * 设置RadioButton属性
     *
     */
    public void init(){
        RadioButton[] rbs =new RadioButton[3];
        rbs[0]=rbFrist;
        rbs[1]=rbStudy;
        rbs[2]=rbMy;
        for (RadioButton rb: rbs){
            //挨着给每个RadioButton加入drawable限制边距以控制显示大小
            Drawable[] drawables = rb.getCompoundDrawables();
            //获取drawables
            Rect r = new Rect(0, 0, drawables[1].getMinimumWidth()*2/3, drawables[1].getMinimumHeight()*2/3);
            //定义一个Rect边界
            drawables[1].setBounds(r);
            //给指定的radiobutton设置drawable边界
//            if (rb.getId() == R.id.rb_more) {
//                r = new Rect(0, 0, drawa bles[1].getMinimumWidth(), drawables[1].getMinimumHeight());
//                drawables[1].setBounds(r);
//            }
            //添加限制给控件
            rb.setCompoundDrawables(null,drawables[1],null,null);

        }
    }

    /**
     * 防止误触返回按键
     * @return toast提示
     */
    @Override
    protected boolean onKeyBack() {

        return true;
    } 

    @Override
    protected boolean onKeyMenu() {
        return false;
    }


    @OnClick({R.id.rb_frist, R.id.rb_study, R.id.rb_my})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rb_frist:
                onFragmentRbClick(0);
                break;
            case R.id.rb_study:
                onFragmentRbClick(1);
                break;
            case R.id.rb_my:
                onFragmentRbClick(2);
                break;
        }
    }

    /**
     * 切换fragment监听
     * @param type fragment对应角标
     */
    private void onFragmentRbClick(int type){
        //开启事务
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        //显示之前将所有的fragment都隐藏起来,在去显示我们想要显示的fragment
        hideFragment(fragmentTransaction);
        switch (type){
            case 0:
                if (homePageFragment==null){
                    homePageFragment=new HomePageFragment();
                    fragmentTransaction.add(R.id.content_frame,homePageFragment);
                }else {
                    fragmentTransaction.show(homePageFragment);
                }
                break;
            case 1:
                if (growUpFragment==null){
                    growUpFragment=new GrowUpFragment();
                    fragmentTransaction.add(R.id.content_frame,growUpFragment);
                }else {
                    fragmentTransaction.show(growUpFragment);
                }
                break;
            case 2:
                if (myFragment==null){
                    myFragment=new MyFragment();
                    fragmentTransaction.add(R.id.content_frame,myFragment);
                }else {
                    fragmentTransaction.show(myFragment);
                }
                break;
        }
        fragmentTransaction.commit();
    }

    /**
     *  隐藏Fragment的方法
     * @param fragmentTransaction
     */
    private void hideFragment(FragmentTransaction fragmentTransaction){
        //不为空隐藏
        if (homePageFragment!=null){
            fragmentTransaction.hide(homePageFragment);
        }
        if (growUpFragment!=null){
            fragmentTransaction.hide(growUpFragment);
        }
        if (myFragment!=null){
            fragmentTransaction.hide(myFragment);
        }

    }

}
