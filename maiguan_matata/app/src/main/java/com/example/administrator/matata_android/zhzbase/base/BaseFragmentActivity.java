package com.example.administrator.matata_android.zhzbase.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;

import com.blankj.utilcode.util.BarUtils;
import com.example.administrator.matata_android.R;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 自定义抽象类BaseFragmentActivity
 */
public abstract class BaseFragmentActivity extends FragmentActivity {

    private Activity mContext;
    protected boolean isStop;
    Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
      unbinder= ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        BarUtils.setStatusBarColor(this,getResources().getColor(R.color.background_title),true);
        //添加当前Activity
        this.mContext = this;
        getExras();
        initData();
        setListener();
    }
    //获取Intent传递的数据
    protected abstract void getExras();

    //网络请求等后续操作
    protected abstract void initData();

    //View设置点击事件
    protected abstract void setListener();

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }
    protected abstract boolean onKeyBack();//点击返回键

    protected abstract boolean onKeyMenu();//点击菜单键

    /**
     *  避免按键事件冲突
     * @param keyCode 点击事件状态码
     * @param event 点击事件
     * @return  返回处理结果
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){
            case 4://返回键keyCode
                if (onKeyBack()){
                    return true;
                }
                return super.onKeyDown(keyCode,event);
            case 82:
                if (onKeyMenu()){
                    return true;
                }
                return super.onKeyDown(keyCode,event);
            default:
                return super.onKeyDown(keyCode,event);
        }
    }

    /**
     * @param animIn  R.anim.slide_in_right 新的Activity进入时的动画，这里是指OtherActivity进入时的动画
     * @param animOut R.anim.slide_out_left 旧的Activity出去时的动画，这里是指this进入时的动画
     */
    public void finish(int animIn, int animOut) {
        this.finish();
        this.overridePendingTransition(animIn, animOut);
    }

    /**
     * 设置Activity跳转时以及动画
     * Android为不同Activity之间的切换，提供了一个overridePendingTransition(A，B)函数
     *
     * @param intent  intent对象
     * @param animIn  R.anim.slide_in_right 新的Activity进入时的动画，这里是指OtherActivity进入时的动画
     * @param animOut R.anim.slide_out_left 旧的Activity出去时的动画，这里是指this进入时的动画
     */
    public void startActivity(Intent intent, int animIn, int animOut) {
        this.startActivity(intent);
        if (this.getParent() != null) {
            this.getParent().overridePendingTransition(animIn, animOut);
        } else {
            this.overridePendingTransition(animIn, animOut);
        }
    }

    /**
     * 使用startActivityForResult(Intent intent, int requestCode)方法打开新的Activity，
     * 我们需要为startActivityForResult()方法传入一个请求码(第二个参数)。
     * 请求码的值是根据业务需要由自已设定，用于标识请求来源
     *
     * @param intent      intent对象
     * @param requestCode 传入请求码，标识请求来源
     * @param animIn      R.anim.slide_in_right 新的Activity进入时的动画，这里是指OtherActivity进入时的动画
     * @param animOut     R.anim.slide_out_left 旧的Activity出去时的动画，这里是指this进入时的动画
     */

    public void startActivityForResult(Intent intent, int requestCode, int animIn, int animOut) {
        this.startActivityForResult(intent, requestCode);
        if (this.getParent() != null) {
            this.getParent().overridePendingTransition(animIn, animOut);
        } else {
            this.overridePendingTransition(animIn, animOut);
        }
    }

    @Override
    protected void onStop() {
        this.isStop=true;
        super.onStop();
    }

    /**
     *
     * @param fg 要显示的fragment
     * @param frameLayoutId frameId
     * @param isDestroy 是否选择replace方法：但是当使用replace()来添加Fragment的时候，
     *                  第二次添加的Fragment会导致第一个Fragment被销毁（在不使用回退栈的情况下），
     *                  即执行第二个Fragment的onAttach()方法之前会先执行第一个Fragment的onPause()-onDetach()方法，
     *                  与此同时containerView会detach掉第一个Fragment的View。
     */
    public void toogleFragment(Class<?extends Fragment> fg, int frameLayoutId, boolean isDestroy){
        if (!this.isStop){
            FragmentManager manager=getSupportFragmentManager();
            String tag=fg.getName();
            FragmentTransaction transaction =manager.beginTransaction();
            Fragment fragment=manager.findFragmentByTag(tag);
            if (fragment==null){
                try{
                    fragment=(Fragment)fg.newInstance();

                    if (isDestroy){
                        transaction.replace(frameLayoutId,fragment,tag);
                    }else {
                        //替换时保留Fragment.以便复用
                        transaction.add(frameLayoutId,fragment,tag);
                    }
                }catch (Exception e){
                    //ignore
                }
            }else {
                transaction.show(fragment);
            }



            //遍历存在的Fragment,隐藏其余Fragment
            // 维护当前遍历的位置，然后操作指针移动就可以了
//            List list=manager.getFragments();
//            if (list!=null){
//                Iterator iterator=list.iterator();
//                while (iterator.hasNext()){
//                    Fragment fragment10=(Fragment)iterator.next();
//                    if (fragment10!=null&&fragment10.equals(fragment)){
//                        transaction.hide(fragment10);
//                    }
//                }
//            }


            //遍历存在的Fragment,隐藏其余Fragment
            // 查询位置在链表前半部分，从链表头开始查找
            //查询位置在链表后半部分，从链表尾开始查找
            List<Fragment> fragments =manager.getFragments();
            if (fragment!=null){
                for (Fragment fm:fragments){
                    if (!fm.equals(fragment)){
                        transaction.hide(fm);
                    }
                }
            }
            if (fragment!=null){
                transaction.show(fragment);
            }
            transaction.commit();
        }
    }

}
