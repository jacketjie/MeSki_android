package jacketjie.common.libray.custom.view.viewpager;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.List;

import jacketjie.common.libray.R;
import jacketjie.common.libray.custom.view.viewpager.indicator.CirclePageIndicator;
import jacketjie.common.libray.custom.view.viewpager.transformer.DepthPageTransformer;
import jacketjie.common.libray.custom.view.viewpager.transformer.ZoomOutPageTransformer;
import jacketjie.common.libray.custom.view.viewpager.utils.ViewPagerAdapter;
import jacketjie.common.libray.others.DensityUtils;
import jacketjie.common.libray.others.ScreenUtils;

/**
 * 带页面指示器的轮播页面
 * Created by Administrator on 2016/test_1/18.
 */
public class ScrollBanner extends FrameLayout {
    /**
     * 能否自动轮播
     */
    private boolean canAutoScroll = true;
    /**
     * 轮播ViewPager
     */
    private AutoScrollViewPager mViewPager;
    /*
    * 页面指示器
    * */
    private CirclePageIndicator pageIndicator;
    /*
    * 页面长宽比
    * */
    private float aspectRatio = 0.5f;
    /**
     * 屏幕宽度
     */
    private int screenWidth;
    /**
     * ViewPager适配器
     */
    private ViewPagerAdapter viewPagerAdapter;
    /**
     * ViewPager切换样式
     */
    private ViewPager.PageTransformer pageTransformer;
    /**
     * 是否需要页面指示器
     */
    private boolean needIndicator = true;
    /**
     * ViewPager切换时长，时长为200ms*scrollFactor
     */
    private int scrollFactor = 5;
    /**
     * 图片绑定数据,必须实现序列化接口,Serializable,Parcelable
     */
    private List binderData;
    /**
     * 默认页面切换周期
     */
    public static final int DEFAULT_INTERVAL = 3000;
    /**
     * 页面切换周期
     */
    private int scrollInterval;


    public ScrollBanner(Context context) {
        this(context, null);
    }

    public ScrollBanner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        createBanner(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ScrollBanner(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        createBanner(context, attrs, defStyleAttr);
    }

    /**
     * 创建广告牌
     *
     * @param context
     */
    private void createBanner(Context context, AttributeSet attrs, int defStyleAttr) {

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ScrollBanner, defStyleAttr, 0);
        needIndicator = ta.getBoolean(R.styleable.ScrollBanner_banner_need_indicator, true);
        aspectRatio = ta.getFloat(R.styleable.ScrollBanner_banner_aspect_ratio, 0.5f);
        scrollFactor = ta.getInteger(R.styleable.ScrollBanner_banner_scroll_factor, 5);
        canAutoScroll = ta.getBoolean(R.styleable.ScrollBanner_banner_can_cycle, true);
        scrollInterval = ta.getInteger(R.styleable.ScrollBanner_banner_scroll_interval, DEFAULT_INTERVAL);

        scrollInterval = Math.max(scrollInterval, scrollFactor * 200);

        int transformerStyle = ta.getInteger(R.styleable.ScrollBanner_banner_trans_former, 0);
        switch (transformerStyle) {
            case 0:
                break;
            case 1:
                pageTransformer = new DepthPageTransformer();
                break;
            case 2:
                pageTransformer = new ZoomOutPageTransformer();
                break;
        }


        createViewPager();

        createIndicator();

        screenWidth = ScreenUtils.getScreenWidth(context);
//        mViewPager.setPageTransformer(true, new DepthPageTransformer());
    }

    /**
     * 创建ViewPager
     */
    private void createViewPager() {
        mViewPager = new AutoScrollViewPager(getContext());
        mViewPager.setId(R.id.id_auto_carouse_viewpager);
        mViewPager.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        if (pageTransformer != null) {
            mViewPager.setPageTransformer(true, pageTransformer);
        }
        setScrollFactor(scrollFactor);
        mViewPager.setInterval(scrollInterval);
        addView(mViewPager, 0);
    }

    /**
     * 创建Indicator
     */
    private void createIndicator() {
        if (needIndicator) {
            pageIndicator = new CirclePageIndicator(getContext());
            FrameLayout.LayoutParams flp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.RIGHT | Gravity.BOTTOM);
            flp.bottomMargin = DensityUtils.dp2px(getContext(), 20);
            flp.rightMargin = DensityUtils.dp2px(getContext(), 20);
            pageIndicator.setLayoutParams(flp);
            pageIndicator.setPadding(DensityUtils.dp2px(getContext(), 2), DensityUtils.dp2px(getContext(), 1), DensityUtils.dp2px(getContext(), 2), DensityUtils.dp2px(getContext(), 1));
            addView(pageIndicator, 1);
        }
    }

    /**
     * 设置广告栏数据
     *
     * @param fm
     * @param urls       图片地址
     * @param binderData 对应图片绑定数据
     */
    public void setBanner(FragmentManager fm, List<String> urls, List binderData) {
        if (urls == null || urls.size() == 0)
            return;
        this.binderData = binderData;
        viewPagerAdapter = new ViewPagerAdapter(fm, binderData, urls, canAutoScroll);
        mViewPager.setAdapter(viewPagerAdapter);
        viewPagerAdapter.notifyDataSetChanged();
        viewPagerAdapter.setTarget(mViewPager);

        ViewGroup.LayoutParams lp = mViewPager.getLayoutParams();
        lp.width = screenWidth;
        lp.height = (int) (screenWidth * aspectRatio);
        mViewPager.setLayoutParams(lp);
        if (needIndicator) {
            pageIndicator.setViewPagerCount(urls.size());
            pageIndicator.setViewPager(mViewPager);
        }
        mViewPager.requestLayout();

        if (canAutoScroll) {
            mViewPager.startAutoScroll();
        } else {
            mViewPager.stopAutoScroll();
        }
    }

    /**
     * mViewPager停止自动滑动
     */
    public void stopAutoScroll() {
        if (mViewPager != null)
            mViewPager.stopAutoScroll();
    }

    /**
     * mViewPager开始自动滑动
     */
    public void startAutoScroll() {
        if (mViewPager != null) {
            mViewPager.startAutoScroll();
        }
    }

    /**
     * 设置切换动画
     *
     * @param reverseDrawingOrder
     * @param transformer
     */
    public void setViewPagerPageTransformer(boolean reverseDrawingOrder, ViewPager.PageTransformer transformer) {
        this.pageTransformer = transformer;
        mViewPager.setPageTransformer(reverseDrawingOrder, transformer);
    }

    /**
     * 设置切换时间
     *
     * @param scrollFactor
     */
    public void setScrollFactor(int scrollFactor) {
        this.scrollFactor = scrollFactor;
        if (scrollFactor < 0)
            return;
        mViewPager.setAutoScrollDurationFactor(scrollFactor);
        mViewPager.setSwipeScrollDurationFactor(scrollFactor);
    }


    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (!hasWindowFocus) {
            mViewPager.stopAutoScroll();
        } else if (canAutoScroll)
            mViewPager.startAutoScroll();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = 0, height = 0;
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int childCount = this.getChildCount();

        int childMeasuredWidth = 0;
        int childMeasuredHeight = 0;
//        int childWidthMeasureSpec = 0;
//        int childHeightMeasureSpec = 0;
        // 修改了系统自动测量的子View的大小
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);

            // 系统自动测量子View:
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);

            // 如果不希望系统自动测量子View,我们用以下的方式:
            // childWidthMeasureSpec =
            // MeasureSpec.makeMeasureSpec(100,MeasureSpec.EXACTLY);
            // childHeightMeasureSpec =
            // MeasureSpec.makeMeasureSpec(100,MeasureSpec.EXACTLY);
            // childView.measure(childWidthMeasureSpec, childHeightMeasureSpec);
        }
        // 获取每个子View测量所得的宽和高
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            MarginLayoutParams mlp = (MarginLayoutParams) childView.getLayoutParams();
            int childWidth = childView.getMeasuredWidth() + mlp.leftMargin + mlp.rightMargin;
            int childHeight = childView.getMeasuredHeight() + mlp.topMargin + mlp.bottomMargin;
            childMeasuredWidth = Math.max(childMeasuredWidth, childWidth);
            childMeasuredHeight = Math.max(childMeasuredHeight, childHeight);

//            Log.e("SelectorLayout", "i=" + i + ",获取系统自动测量的该子View的大小:" +
//                    "childMeasuredWidth=" + childWidth + "," +
//                    "childMeasuredHeight=" + childHeight);

        }
        width += getPaddingRight() + getPaddingLeft() + childMeasuredWidth;
        height += getPaddingTop() + getPaddingBottom() + childMeasuredHeight;
//        Log.e("SelectorLayout", "width=" + width + ",height=" + height);
        setMeasuredDimension(widthMode == MeasureSpec.EXACTLY ? widthSize : width, heightMode == MeasureSpec.EXACTLY ? heightSize : height);
    }

    public boolean isCanAutoScroll() {
        return canAutoScroll;
    }

    public void setCanAutoScroll(boolean canAutoScroll) {
        this.canAutoScroll = canAutoScroll;
        if (mViewPager != null) {
            if (canAutoScroll) {
                mViewPager.startAutoScroll();
            } else {
                mViewPager.stopAutoScroll();
            }
            viewPagerAdapter.setCanCycle(canAutoScroll);
        }
    }

    public boolean isNeedIndicator() {
        return needIndicator;
    }

    public void setNeedIndicator(boolean needIndicator) {
        this.needIndicator = needIndicator;
        if (!needIndicator) {
            if (pageIndicator != null) {
                removeView(pageIndicator);
            }
        }
    }

    private OnPageClickListener onPageClickListener;

    public void setOnPageClickListener(OnPageClickListener onPageClickListener) {
        this.onPageClickListener = onPageClickListener;
        viewPagerAdapter.setOnPageClickListener(onPageClickListener);
        viewPagerAdapter.notifyDataSetChanged();
    }

    public interface OnPageClickListener<T> {
        void onPageCilck(T data);
    }
//    float distanceY = 0;
//    float lastDownY = 0;
//    private static final float DEFAULT_MAX_Y = 120;
//
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                lastDownY = ev.getY();
//                Log.d("ACTION_DOWN", "lastDownY=" + lastDownY);
//                break;
//            case MotionEvent.ACTION_MOVE:
//                distanceY = ev.getY() - lastDownY;
//                Log.d("ACTION_MOVE", "ev.getY()=" + ev.getY()+",distanceY="+distanceY);
//                break;
//            case MotionEvent.ACTION_UP:
//                if (distanceY >= DEFAULT_MAX_Y) {
//                    return true;
//                }
//                Log.d("ACTION_UP", "distanceY" + distanceY);
//                break;
//        }
//        return super.onInterceptTouchEvent(ev);
//    }
}
