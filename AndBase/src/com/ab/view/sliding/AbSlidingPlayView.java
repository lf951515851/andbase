//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ab.view.sliding;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import com.ab.adapter.AbViewPagerAdapter;
import com.ab.util.AbFileUtil;
import com.ab.view.sample.AbInnerViewPager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AbSlidingPlayView extends LinearLayout {
    private Context context;
    private AbInnerViewPager mViewPager;
    private LinearLayout navLinearLayout;
    public LayoutParams navLayoutParams = null;
    private int count;
    private int position;
    private Bitmap displayImage;
    private Bitmap hideImage;
    private AbSlidingPlayView.AbOnItemClickListener mOnItemClickListener;
    private AbSlidingPlayView.AbOnChangeListener mAbChangeListener;
    private AbSlidingPlayView.AbOnScrollListener mAbScrolledListener;
    private AbSlidingPlayView.AbOnTouchListener mAbOnTouchListener;
    private ArrayList<View> mListViews = null;
    private AbViewPagerAdapter mAbViewPagerAdapter = null;
    private LinearLayout mNavLayoutParent = null;
    private int navHorizontalGravity = 5;
    private int playingDirection = 0;
    private boolean play = false;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if(msg.what == 0) {
                int count = AbSlidingPlayView.this.mListViews.size();
                int i = AbSlidingPlayView.this.mViewPager.getCurrentItem();
                if(AbSlidingPlayView.this.playingDirection == 0) {
                    if(i == count - 1) {
                        AbSlidingPlayView.this.playingDirection = -1;
                        --i;
                    } else {
                        ++i;
                    }
                } else if(i == 0) {
                    AbSlidingPlayView.this.playingDirection = 0;
                    ++i;
                } else {
                    --i;
                }

                AbSlidingPlayView.this.mViewPager.setCurrentItem(i, true);
                if(AbSlidingPlayView.this.play) {
                    AbSlidingPlayView.this.handler.postDelayed(AbSlidingPlayView.this.runnable, 5000L);
                }
            }

        }
    };
    private Runnable runnable = new Runnable() {
        public void run() {
            if(AbSlidingPlayView.this.mViewPager != null) {
                AbSlidingPlayView.this.handler.sendEmptyMessage(0);
            }

        }
    };

    public AbSlidingPlayView(Context context) {
        super(context);
        this.initView(context);
    }

    public AbSlidingPlayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initView(context);
    }

    public void initView(Context context) {
        this.context = context;
        this.navLayoutParams = new LayoutParams(-2, -2);
        this.setOrientation(1);
        this.setBackgroundColor(Color.rgb(255, 255, 255));
        RelativeLayout mRelativeLayout = new RelativeLayout(context);
        this.mViewPager = new AbInnerViewPager(context);
        this.mViewPager.setId(1985);
        this.mNavLayoutParent = new LinearLayout(context);
        this.mNavLayoutParent.setPadding(0, 5, 0, 5);
        this.navLinearLayout = new LinearLayout(context);
        this.navLinearLayout.setPadding(15, 1, 15, 1);
        this.navLinearLayout.setVisibility(4);
        this.mNavLayoutParent.addView(this.navLinearLayout, new LayoutParams(-2, -2));
        android.widget.RelativeLayout.LayoutParams lp1 = new android.widget.RelativeLayout.LayoutParams(-2, -2);
        lp1.addRule(12, -1);
        lp1.addRule(14, -1);
        lp1.addRule(15, -1);
        mRelativeLayout.addView(this.mViewPager, lp1);
        android.widget.RelativeLayout.LayoutParams lp2 = new android.widget.RelativeLayout.LayoutParams(-1, -2);
        lp2.addRule(12, -1);
        mRelativeLayout.addView(this.mNavLayoutParent, lp2);
        this.addView(mRelativeLayout, new LayoutParams(-1, -2));
        this.displayImage = AbFileUtil.getBitmapFromSrc("image/play_display.png");
        this.hideImage = AbFileUtil.getBitmapFromSrc("image/play_hide.png");
        this.mListViews = new ArrayList();
        this.mAbViewPagerAdapter = new AbViewPagerAdapter(context, this.mListViews);
        this.mViewPager.setAdapter(this.mAbViewPagerAdapter);
        this.mViewPager.setFadingEdgeLength(0);
        this.mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
            public void onPageSelected(int position) {
                AbSlidingPlayView.this.makesurePosition();
                AbSlidingPlayView.this.onPageSelectedCallBack(position);
            }

            public void onPageScrollStateChanged(int state) {
            }

            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                AbSlidingPlayView.this.onPageScrolledCallBack(position);
            }
        });
    }

    public void creatIndex() {
        this.navLinearLayout.removeAllViews();
        this.mNavLayoutParent.setHorizontalGravity(this.navHorizontalGravity);
        this.navLinearLayout.setGravity(17);
        this.navLinearLayout.setVisibility(0);
        this.count = this.mListViews.size();
        this.navLayoutParams.setMargins(5, 5, 5, 5);

        for(int j = 0; j < this.count; ++j) {
            ImageView imageView = new ImageView(this.context);
            imageView.setLayoutParams(this.navLayoutParams);
            if(j == 0) {
                imageView.setImageBitmap(this.displayImage);
            } else {
                imageView.setImageBitmap(this.hideImage);
            }

            this.navLinearLayout.addView(imageView, j);
        }

    }

    public void makesurePosition() {
        this.position = this.mViewPager.getCurrentItem();

        for(int j = 0; j < this.count; ++j) {
            if(this.position == j) {
                ((ImageView)this.navLinearLayout.getChildAt(this.position)).setImageBitmap(this.displayImage);
            } else {
                ((ImageView)this.navLinearLayout.getChildAt(j)).setImageBitmap(this.hideImage);
            }
        }

    }

    public void addView(View view) {
        this.mListViews.add(view);
        if(!(view instanceof AbsListView)) {
            view.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if(AbSlidingPlayView.this.mOnItemClickListener != null) {
                        AbSlidingPlayView.this.mOnItemClickListener.onClick(AbSlidingPlayView.this.position);
                    }

                }
            });
            view.setOnTouchListener(new OnTouchListener() {
                public boolean onTouch(View view, MotionEvent event) {
                    if(AbSlidingPlayView.this.mAbOnTouchListener != null) {
                        AbSlidingPlayView.this.mAbOnTouchListener.onTouch(event);
                    }

                    return false;
                }
            });
        }

        this.mAbViewPagerAdapter.notifyDataSetChanged();
        this.creatIndex();
    }

    public void addViews(List<View> views) {
        this.mListViews.addAll(views);
        Iterator var3 = views.iterator();

        while(var3.hasNext()) {
            View view = (View)var3.next();
            view.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if(AbSlidingPlayView.this.mOnItemClickListener != null) {
                        AbSlidingPlayView.this.mOnItemClickListener.onClick(AbSlidingPlayView.this.position);
                    }

                }
            });
            view.setOnTouchListener(new OnTouchListener() {
                public boolean onTouch(View view, MotionEvent event) {
                    if(AbSlidingPlayView.this.mAbOnTouchListener != null) {
                        AbSlidingPlayView.this.mAbOnTouchListener.onTouch(event);
                    }

                    return false;
                }
            });
        }

        this.mAbViewPagerAdapter.notifyDataSetChanged();
        this.creatIndex();
    }

    public void removeAllViews() {
        this.mListViews.clear();
        this.mAbViewPagerAdapter.notifyDataSetChanged();
        this.creatIndex();
    }

    public View removeCurrentViews() {
        if(this.getCount() <= 0) {
            return null;
        } else {
            View view = (View)this.mListViews.remove(this.position);
            this.mAbViewPagerAdapter.notifyDataSetChanged();
            this.creatIndex();
            return view;
        }
    }

    private void onPageScrolledCallBack(int position) {
        if(this.mAbScrolledListener != null) {
            this.mAbScrolledListener.onScroll(position);
        }

    }

    private void onPageSelectedCallBack(int position) {
        if(this.mAbChangeListener != null) {
            this.mAbChangeListener.onChange(position);
        }

    }

    public void startPlay() {
        if(this.handler != null) {
            this.play = true;
            this.handler.postDelayed(this.runnable, 5000L);
        }

    }

    public void stopPlay() {
        if(this.handler != null) {
            this.play = false;
            this.handler.removeCallbacks(this.runnable);
        }

    }

    public void setOnItemClickListener(AbSlidingPlayView.AbOnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setOnPageChangeListener(AbSlidingPlayView.AbOnChangeListener abChangeListener) {
        this.mAbChangeListener = abChangeListener;
    }

    public void setOnPageScrolledListener(AbSlidingPlayView.AbOnScrollListener abScrolledListener) {
        this.mAbScrolledListener = abScrolledListener;
    }

    public void setOnTouchListener(AbSlidingPlayView.AbOnTouchListener abOnTouchListener) {
        this.mAbOnTouchListener = abOnTouchListener;
    }

    public void setPageLineImage(Bitmap displayImage, Bitmap hideImage) {
        this.displayImage = displayImage;
        this.hideImage = hideImage;
        this.creatIndex();
    }

    public ViewPager getViewPager() {
        return this.mViewPager;
    }

    public int getCount() {
        return this.mListViews.size();
    }

    public void setNavHorizontalGravity(int horizontalGravity) {
        this.navHorizontalGravity = horizontalGravity;
    }

    public void setParentScrollView(ScrollView parentScrollView) {
        this.mViewPager.setParentScrollView(parentScrollView);
    }

    public void setParentListView(ListView parentListView) {
        this.mViewPager.setParentListView(parentListView);
    }

    public void setNavLayoutBackground(int resid) {
        this.navLinearLayout.setBackgroundResource(resid);
    }

    public interface AbOnChangeListener {
        void onChange(int var1);
    }

    public interface AbOnItemClickListener {
        void onClick(int var1);
    }

    public interface AbOnScrollListener {
        void onScroll(int var1);

        void onScrollStoped();

        void onScrollToLeft();

        void onScrollToRight();
    }

    public interface AbOnTouchListener {
        void onTouch(MotionEvent var1);
    }
}
