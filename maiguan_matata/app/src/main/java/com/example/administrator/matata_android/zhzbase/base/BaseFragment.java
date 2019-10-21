package com.example.administrator.matata_android.zhzbase.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 *
 * startActivityForRe自定义BaseFragmentsult()、onActivityResult（）方法传递数据也可以用EventBus代替
 * BaseApplication.getInstance()
 */
public abstract class BaseFragment extends Fragment implements View.OnTouchListener{

    protected View rootView;

    private Activity activity;


    private static Fragment fragment;
    /**
     * 判断activity是否为空；
     *  getActivity():获得Fragment依附的Activity对象。
     *  Fragment里边的getActivity()不推荐使用原因如下：
     *  这个方法会返回当前Fragment所附加的Activity，
     *  当Fragment生命周期结束并销毁时，getActivity()返回的是null，
     *  所以在使用时要注意判断null或者捕获空指针异常。
     * @return activity
     */
    @Nullable
    @Override
    public Context getContext() {
        activity=getActivity();
        if (activity==null){
            return BaseApplication.getInstance();
        }
        return activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (this.rootView==null){
            rootView=inflater.inflate(initLayoutId(),container,false);
        }
        rootView.setOnTouchListener(this);
        getExras();
        initData();
        setListener();
        return rootView;
    }

    /**
     * fragment跳转activity界面,带请求值确定是哪个Fragment
     * @param intent    intent
     * @param requestCode   请求值
     */
    public void startActivityForResult(Intent intent, int requestCode) {
        if (fragment==null){
            fragment=this;
        }
        if (this.getParentFragment()!=null){
            this.getParentFragment().startActivityForResult(intent,requestCode);
        }else {
            super.startActivityForResult(intent, requestCode);
        }
    }

    /**
     * 首先判断fragment不为空，是当前fragment调用onActivityResult
     * @param requestCode   请求码
     * @param resultCode    返回码
     * @param data  数据
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (null!=fragment&&!fragment.equals(this)){
            fragment.onActivityResult(requestCode,resultCode,data);
        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }
        fragment=null;
    }

    /**
     * 获取布局文件ID
     * @return layoutId
     */
    protected  abstract  int initLayoutId();
    /**
     *   获取Bundle传递的数据
     */
    protected abstract void getExras();
    /**
     *   网络请求等后续操作
     */
    protected abstract void initData();
    /**
     * View设置点击事件
     */
    protected abstract void setListener();



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

        FragmentManager manager=getChildFragmentManager();
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