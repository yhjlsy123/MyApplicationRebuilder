package com.isgala.xishuashua.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.isgala.xishuashua.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CuiXC
 * @date on 2018/3/21.
 */

public class RecyclerViewBanner extends FrameLayout {

    private RecyclerView recyclerView;
    private LinearLayout linearLayout;
    private GradientDrawable defaultDrawable, selectedDrawable;

    private ReyclerAdapter adapter;
    private OnRvBannerClickListener onRvBannerClickListener;
    private OnSwitchRvBannerListener onSwitchRvBannerListener;
    private List datas = new ArrayList<>();

    private int size, startX, startY, currentIndex;
    private boolean isPlaying;
    private int millisecond = 3000;
    private boolean isShowPoint = true;
    private int pointFocusBg, pointUnFocusBg;
    private Handler handler = new Handler();

    private Runnable playTask = new Runnable(){

        @Override
        public void run(){
            recyclerView.smoothScrollToPosition(++currentIndex);
            if (isShowPoint) {
                switchIndicatorPoint();
            }
            handler.postDelayed(this, millisecond);
        }
    };

    public RecyclerViewBanner(Context context){
        this(context, null);
    }

    public RecyclerViewBanner(Context context, AttributeSet attrs){
        this(context, attrs, 0);
    }

    public RecyclerViewBanner(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
        size = (int)(5 * context.getResources().getDisplayMetrics().density + 0.5f);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RecyclerViewBanner);
        isShowPoint = typedArray.getBoolean(R.styleable.RecyclerViewBanner_isShowPoint, true);
        pointFocusBg = typedArray.getColor(R.styleable.RecyclerViewBanner_pointFocusBg, 0xff0099ff);
        pointUnFocusBg = typedArray.getColor(R.styleable.RecyclerViewBanner_pointUnfocusBg, 0xffffffff);
        millisecond = typedArray.getInt(R.styleable.RecyclerViewBanner_interval, 3000);
        typedArray.recycle();
        recyclerView = new RecyclerView(context);
        LayoutParams vpLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        LayoutParams linearLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayout.setGravity(Gravity.CENTER);
        linearLayout.setPadding(size * 2, size * 2, size * 2, size * 2);
        linearLayoutParams.gravity = Gravity.BOTTOM;
        addView(recyclerView, vpLayoutParams);
        addView(linearLayout, linearLayoutParams);

        defaultDrawable = new GradientDrawable();
        defaultDrawable.setSize(size, size);
        defaultDrawable.setCornerRadius(size);
        defaultDrawable.setColor(pointUnFocusBg);
        selectedDrawable = new GradientDrawable();
        selectedDrawable.setSize(size, size);
        selectedDrawable.setCornerRadius(size);
        selectedDrawable.setColor(pointFocusBg);

        new PagerSnapHelper().attachToRecyclerView(recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        adapter = new ReyclerAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState){
                super.onScrollStateChanged(recyclerView, newState);
                int first = ((LinearLayoutManager)recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                int last = ((LinearLayoutManager)recyclerView.getLayoutManager()).findLastVisibleItemPosition();
                if (currentIndex != (first + last) / 2) {
                    currentIndex = (first + last) / 2;
                    if (isShowPoint) {
                        switchIndicatorPoint();
                    }
                }
            }
        });
    }

    /**
     * 设置是否显示指示器导航点
     *
     * @param flag
     */
    public void isShowIndicatorPoint(boolean flag){
        this.isShowPoint = flag;
    }

    /**
     * 设置轮播间隔时间
     *
     * @param millisecond
     */
    public void setScrollIntervalTime(int millisecond){
        this.millisecond = millisecond;
    }

    /**
     * 设置 是否自动播放（上锁）
     *
     * @param playing
     */
    public synchronized void setPlaying(boolean playing){
        if (!isPlaying && playing && adapter != null && adapter.getItemCount() > 2) {
            handler.postDelayed(playTask, millisecond);
            isPlaying = true;
        } else if (isPlaying && !playing) {
            handler.removeCallbacksAndMessages(null);
            isPlaying = false;
        }
    }

    /**
     * 获取轮播图是否滚动
     */
    public boolean getIsPlaying(){
        return isPlaying;
    }

    /**
     * 设置轮播数据集
     *
     * @param datas
     */
    public void setRvBannerDatas(List datas){
        setPlaying(false);
        this.datas.clear();
        linearLayout.removeAllViews();
        if (datas != null) {
            this.datas.addAll(datas);
        }
        if (this.datas.size() > 1) {
            currentIndex = this.datas.size();
            adapter.notifyDataSetChanged();
            recyclerView.scrollToPosition(currentIndex);
            if (isShowPoint) {
                for (int i = 0; i < this.datas.size(); i++) {
                    ImageView img = new ImageView(getContext());
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    lp.leftMargin = size / 2;
                    lp.rightMargin = size / 2;
                    img.setImageDrawable(i == 0 ? selectedDrawable : defaultDrawable);
                    linearLayout.addView(img, lp);
                }
            }
            setPlaying(true);
        } else {
            currentIndex = 0;
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev){
        //手动触摸的时候，停止自动播放，根据手势变换
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = (int)ev.getX();
                startY = (int)ev.getY();
                getParent().requestDisallowInterceptTouchEvent(true);
                setPlaying(false);
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int)ev.getX();
                int moveY = (int)ev.getY();
                int disX = moveX - startX;
                int disY = moveY - startY;
                getParent().requestDisallowInterceptTouchEvent(2 * Math.abs(disX) > Math.abs(disY));
                setPlaying(false);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                setPlaying(true);
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onAttachedToWindow(){
        super.onAttachedToWindow();
        setPlaying(true);
    }

    @Override
    protected void onDetachedFromWindow(){
        super.onDetachedFromWindow();
        setPlaying(false);
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility){
        if (visibility == GONE) {
            // 停止轮播
            setPlaying(false);
        } else if (visibility == VISIBLE) {
            // 开始轮播
            setPlaying(true);
        }
        super.onWindowVisibilityChanged(visibility);
    }

    /**
     * recyclerview的adapter，适配器
     */
    public class ReyclerAdapter extends RecyclerView.Adapter<ReyclerAdapter.MyViewHolder>{

        @Override
        public ReyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
            return new MyViewHolder(
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.view_recycler_banner, parent, false));
        }

        @Override
        public void onBindViewHolder(ReyclerAdapter.MyViewHolder holder, int position){
            onSwitchRvBannerListener.switchBanner(position, holder.imgICON, holder.title);
        }

        @Override
        public int getItemCount(){
            return datas == null ? 0 : datas.size() < 2 ? datas.size() : Integer.MAX_VALUE;
        }

        /**
         * View Holder
         */
        class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

            private TextView title;
            private ImageView imgICON;

            MyViewHolder(View view){
                super(view);
                title = (TextView)view.findViewById(R.id.title);
                imgICON = (ImageView)view.findViewById(R.id.img_Carousel);
                view.setOnClickListener(this);
            }

            @Override
            public void onClick(View v){
                if (onRvBannerClickListener != null) {
                    onRvBannerClickListener.onClick(currentIndex % datas.size());
                }
            }
        }

    }

    private class PagerSnapHelper extends LinearSnapHelper{

        @Override
        public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY){
            int targetPos = super.findTargetSnapPosition(layoutManager, velocityX, velocityY);
            final View currentView = findSnapView(layoutManager);
            if (targetPos != RecyclerView.NO_POSITION && currentView != null) {
                int currentPostion = layoutManager.getPosition(currentView);
                int first = ((LinearLayoutManager)layoutManager).findFirstVisibleItemPosition();
                int last = ((LinearLayoutManager)layoutManager).findLastVisibleItemPosition();
                currentPostion = targetPos < currentPostion ? last : (targetPos > currentPostion ? first : currentPostion);
                targetPos = targetPos < currentPostion ? currentPostion - 1 : (targetPos > currentPostion ? currentPostion + 1 : currentPostion);
            }
            return targetPos;
        }
    }

    /**
     * 改变导航的指示点
     */
    private void switchIndicatorPoint(){
        if (linearLayout != null && linearLayout.getChildCount() > 0) {
            for (int i = 0; i < linearLayout.getChildCount(); i++) {
                ((ImageView)linearLayout.getChildAt(i)).setImageDrawable(
                        i == currentIndex % datas.size() ? selectedDrawable : defaultDrawable);
            }
        }
    }

    public interface OnSwitchRvBannerListener{
        void switchBanner(int position, ImageView bannerView, TextView title);
    }

    public void setOnSwitchRvBannerListener(OnSwitchRvBannerListener listener){
        this.onSwitchRvBannerListener = listener;
    }

    public interface OnRvBannerClickListener{
        void onClick(int position);
    }

    public void setOnRvBannerClickListener(OnRvBannerClickListener onRvBannerClickListener){
        this.onRvBannerClickListener = onRvBannerClickListener;
    }

}