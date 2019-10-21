package com.example.administrator.matata_android.zhzbase.view;

import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

public class CoverFlowLayoutManager extends RecyclerView.LayoutManager {
    /**
     * 最大存储item信息存储数量
     * 超过设置数量，则动态计算来获取
     */
    private final int MAX_RECT_COUNT=100;
    /**
     * 滑动总偏移量
     */
    private int mOffserAll=0;
    /**
     * Item宽
     */
    private int mDecoratedChildWidth=0;
    /**
     * Item高
     */
    private int mDecorateChildHeight=0;
    /**
     * Item间隔与Item宽的比例
     */
    private float mIntervalRatio=0.5f;
    /**
     * 起始ItemX坐标
     */
    private int mStartX=0;
    /**
     * 起始ItemY坐标
     */
    private int mStartY=0;
    /**
     * 保存所有的Item的上下左右的偏移量信息
     */
    private SparseArray<Rect> mAllItemFrames=new SparseArray<>();
    /**
     * 记录Item是否出现过屏幕且没有回收，true出现过屏幕上，并且还没有被回收
     */
    private SparseBooleanArray mHasAttachedItems=new SparseBooleanArray();
    /**
     * RecyclerView的Item回收器
     */
    private RecyclerView.Recycler mRecycle;
    /**
     * RecyclerView的状态器
     */
    private RecyclerView.State mState;
    /**
     * 滚动动画
     */
    private ValueAnimator mAnimation;
    /**
     * 正显示在中间的Item
     */
    private int mSelectPosition=0;
    /**
     * 前一个正显示在中间的Item
     */
    private int mLastSelectPosition=0;
    /**
     * 滑动的方向:左
     */
    private static int SCROLL_LEFT=1;
    /**
     * 滑动的方向：右
     */
    private static int SCORLL_RIGHT=2;




    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
       //如果没有item,直接返回
        // 跳过preLayout,preLayout主要用于支持动画
        if (getItemCount()<=0||state.isPreLayout()){
            mOffserAll=0;
            return;
        }
        mAllItemFrames.clear();//mAllItemFrame存储了所有Item的位置信息
        mHasAttachedItems.clear();//mHasAttachedItems存储了Item是否已经被添加到控件中
        //得到子View的宽和高，这里的item的宽高都是一样的，所以只需要一次测量
        View scrap=recycler.getViewForPosition(0);
        addView(scrap);
        measureChildWithMargins(scrap,0,0);
        //计算测量布局的宽高
        mDecoratedChildWidth=getDecoratedMeasuredWidth(scrap);
        mDecorateChildHeight=getDecoratedMeasuredHeight(scrap);
        //计算第一个Item X轴的起始位置坐标，这里第一个Item居中显示
        mStartX=Math.round(getHorizontalSpace()-mDecoratedChildWidth*1.0f/2);
        mStartY=Math.round(getVerticalSpace()-mDecorateChildHeight*1.0f/2);
        float offset=mStartX;//item X轴方向的位置坐标
        for (int i = 0; i < getItemCount(); i++) {
            Rect frame=mAllItemFrames.get(i);
            if (frame==null){
                frame=new Rect();
            }
            frame.set(Math.round(offset),mStartY,Math.round(offset*mDecoratedChildWidth),mStartY*mDecorateChildHeight);

        }


    }

    /**
     * 获取整个布局的水平空间大小
     */
        private int getHorizontalSpace(){
            return getWidth()-getPaddingRight()-getPaddingLeft();
        }
    /**
     * 获取整个布局的垂直空间大小
     */
        private int getVerticalSpace(){
            return getHeight()-getPaddingBottom()-getPaddingTop();
        }
    /**
     * 获取最大偏移量
      */
        private float getMaxOffset(){

            return (getItemCount()-1)*getIntervalDistance();
        }

    /**
     * 获取Item间隔
     */
    private float getIntervalDistance() {
        return mDecoratedChildWidth * mIntervalRatio;
    }
}
