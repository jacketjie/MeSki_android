package jacketjie.common.libray.custom.view.animatedlayout;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import java.util.LinkedList;
import java.util.List;

import jacketjie.common.libray.R;
import jacketjie.common.libray.custom.view.expandablelayout.Utils;


/**
 * Created by wujie on 2016/test_1/15.
 */
public class DrawableLinearLayout extends LinearLayout implements AnimatedLayout {
    /**
     * 动画时长
     */
    private int mDuration;
    /**
     * 动画方向
     */
    private AnimatedLayoutListener.Direction mDirection;
    /**
     * 动画插值器
     */
    private TimeInterpolator mInterpolator;
    /**
     * 动画进程监听器
     */
    private AnimatedLayoutListener animatedLayoutListener;
    /**
     * view的展开收缩状态
     */
    private boolean isExpandableStatus = DEFAULT_EXPANDABLE;
    /**
     * 是否正在进行动画
     */
    private boolean isAnimation;
    /**
     * view的宽度
     */
    private Integer layoutWidth;
    /**
     * view的高度
     */
    private Integer layoutHeight;
    /**
     * 是否需要重新计算view的尺寸
     */
    private boolean needToRequest = true;
    /**
     * 多次点击是处理的handler
     */
    private Handler handler = null;

    public static final int ANIMATION_UPDATE_CODE = 0x456;
    /**
     * 动画进行的时长
     */
    private long animationPlayTime = 0;

    private List<Message> mTask = new LinkedList<>();

    public DrawableLinearLayout(Context context) {
        this(context, null);
    }

    public DrawableLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawableLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DrawableLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleAttr);
        init(context, attrs, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray ta = context.obtainStyledAttributes(
                attrs, R.styleable.DrawableLinearLayout, defStyleAttr, 0);
        mDuration = ta.getInteger(R.styleable.DrawableLinearLayout_dll_duration, DEFAULT_DURATION);
        int style = ta.getInteger(R.styleable.DrawableLinearLayout_dll_direction, DEFAULT_DIRECTION);
        mDirection = Utils.getAnimationDirection(style);
        int interpolator = ta.getInteger(R.styleable.DrawableLinearLayout_dll_interpolator, DEFAULT_INTERPOLATOR);
        mInterpolator = Utils.createInterpolator(interpolator);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == ANIMATION_UPDATE_CODE) {
                    toggle();
                }
            }
        };

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
            if (isVertical()) {
                childMeasuredWidth = Math.max(childMeasuredWidth, childWidth);
                childMeasuredHeight += childHeight;
            } else {
                childMeasuredHeight = Math.max(childMeasuredHeight, childHeight);
                childMeasuredWidth += childWidth;
            }
//            Log.e("SelectorLayout", "i=" + i + ",获取系统自动测量的该子View的大小:" +
//                    "childMeasuredWidth=" + childWidth + "," +
//                    "childMeasuredHeight=" + childHeight);

        }
        width += getPaddingRight() + getPaddingLeft() + childMeasuredWidth;
        height += getPaddingTop() + getPaddingBottom() + childMeasuredHeight;
//        Log.e("SelectorLayout", "width=" + width + ",height=" + height);
        setMeasuredDimension(widthMode == MeasureSpec.EXACTLY ? widthSize : width, heightMode == MeasureSpec.EXACTLY ? heightSize : height);
        if (needToRequest) {
            setLayoutWidth(width);
            setLayoutHeight(height);
            moveToHiddenOrNot();
            needToRequest = false;
        }
    }


    /**
     * 移动隐藏，根据最初的状态决定是否隐藏
     */
    private void moveToHiddenOrNot() {
        if (!isExpandableStatus()) {
            switch (getDirection()) {
                case DIRECTION_TOP:
                    setTranslationY(-getLayoutHeight());
                    break;
                case DIRECTION_LEFT:
                    setTranslationX(-getLayoutWidth());
                    break;
                case DIRECTION_RIGHT:
                    setTranslationX(getLayoutWidth());
                    break;
            }
            setVisibility(GONE);
        } else {
            setVisibility(VISIBLE);
        }
    }

    /**
     * 创建Drawable动画
     *
     * @param startPos
     * @param endPos
     * @return
     */
    private ValueAnimator createDrawableAnimation(float startPos, float endPos) {
        ObjectAnimator objectAnimator = null;
        if (getDirection() == DIRECTION_TOP) {
            objectAnimator = ObjectAnimator.ofFloat(this, View.TRANSLATION_Y, startPos, endPos);
        }
        if (getDirection() == DIRECTION_LEFT || getDirection() == DIRECTION_RIGHT) {
            objectAnimator = ObjectAnimator.ofFloat(this, View.TRANSLATION_X, startPos, endPos);
        }
        objectAnimator.setInterpolator(mInterpolator);
        objectAnimator.setDuration(mDuration);
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                isAnimation = true;
                setVisibility(VISIBLE);
                if (animatedLayoutListener != null) {
                    animatedLayoutListener.animationCreated();
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isAnimation = false;
                if (!isExpandableStatus()) {
                    setVisibility(GONE);
                }
                if (animatedLayoutListener != null) {
                    animatedLayoutListener.animationEnded();
                }
            }
        });
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animationPlayTime = animation.getCurrentPlayTime();
//                Log.e("DrawableLinearLayout", String.valueOf(animationPlayTime));
            }
        });
        return objectAnimator;
    }

    /**
     * 创建动画
     *
     * @param startPos
     * @param endPos
     * @param duration
     * @return
     */
    private ValueAnimator createDrawableAnimation(float startPos, float endPos, Integer duration) {
        ValueAnimator objectAnimator = null;
        objectAnimator = createDrawableAnimation(startPos, endPos);
        objectAnimator.setDuration(duration == null ? mDuration : duration);

        return objectAnimator;
    }

    public void displayOrHidden() {
//        Message message = Message.obtain();
//        message.what = ANIMATION_UPDATE_CODE;
//        mTask.add(message);
        if (isAnimation) {
            handler.sendEmptyMessageDelayed(ANIMATION_UPDATE_CODE, getAnimationResidualTime());
        } else {
            handler.sendEmptyMessage(ANIMATION_UPDATE_CODE);
        }
    }



    public void requestLayoutByAnim(){
        needToRequest = true;
        createDrawableAnimation(0,-1,1).start();
        requestLayout();
    }
//    class LooperThread extends Thread{
//        @Override
//        public void run() {
//            Looper.prepare();
//            if (mTask != null && mTask.size() > 0){
//                Message message = mTask.get(mTask.size() - test_1);
//                handler.sendMessageDelayed(message,getAnimationResidualTime());
//            }
//            Looper.loop();
//        }
//    }

    /**
     * 当前动画剩余时间
     *
     * @return 剩余时间
     */
    public long getAnimationResidualTime() {
        long residualTime = mDuration - animationPlayTime;
        if (residualTime < 0) {
            residualTime = 0;
            isAnimation = false;
        }
        return residualTime;
    }

    /**
     * 展开或者收缩
     */
    public void toggle() {
        if (isExpandableStatus()) {
            expand();
        } else {
            collapse();
        }
    }

    /**
     * 展开
     */
    public void expand() {
            if (isAnimation) {
                return;
            }
            switch (getDirection()) {
                case DIRECTION_TOP:
                    createDrawableAnimation(0, -getLayoutHeight()).start();
                    break;
                case DIRECTION_LEFT:
                    createDrawableAnimation(0, -getLayoutWidth()).start();
                    break;
                case DIRECTION_RIGHT:
                    createDrawableAnimation(0, getLayoutWidth()).start();
                    break;
            }
            setExpandableStatus(false);
    }

    /**
     * 收缩
     */
    public void collapse() {
            if (isAnimation) {
                return;
            }
            switch (getDirection()) {
                case DIRECTION_TOP:
                    createDrawableAnimation(-getLayoutHeight(), 0).start();
                    break;
                case DIRECTION_LEFT:
                    createDrawableAnimation(-getLayoutWidth(), 0).start();
                    break;
                case DIRECTION_RIGHT:
                    createDrawableAnimation(getLayoutWidth(), 0).start();
                    break;
            }
            setExpandableStatus(true);
    }

    /**
     * 动画重置
     *
     * @param lastDirection
     */
    private void resetAnimationDirection(AnimatedLayoutListener.Direction lastDirection) {
        if (isExpandableStatus()) {
            return;
        }
        if (lastDirection == AnimatedLayoutListener.Direction.Top) {
            switch (getDirection()) {
                case DIRECTION_LEFT:
                    setTranslationX(-getLayoutWidth());
                    setTranslationY(0);
                    break;
                case DIRECTION_RIGHT:
                    setTranslationX(getLayoutWidth());
                    setTranslationY(0);
                    break;
            }
        }
        if (lastDirection == AnimatedLayoutListener.Direction.Left) {
            switch (getDirection()) {
                case DIRECTION_TOP:
                    setTranslationY(-getLayoutHeight());
                    setTranslationX(0);
                    break;
                case DIRECTION_RIGHT:
                    setTranslationX(getLayoutWidth());
                    setTranslationY(0);
                    break;
            }
        }
        if (lastDirection == AnimatedLayoutListener.Direction.Right) {
            switch (getDirection()) {
                case DIRECTION_TOP:
                    setTranslationY(-getLayoutHeight());
                    setTranslationX(0);
                    break;
                case DIRECTION_LEFT:
                    setTranslationX(-getLayoutWidth());
                    break;
            }
        }
    }


    public boolean isAnimation() {
        return isAnimation;
    }

    public boolean isVertical() {
        return getOrientation() == VERTICAL;
    }

    public int getDuration() {
        return mDuration;
    }

    public void setDuration(int mDuration) {
        this.mDuration = mDuration;
    }

    public int getDirection() {
        if (mDirection == AnimatedLayoutListener.Direction.Top)
            return DIRECTION_TOP;
        if (mDirection == AnimatedLayoutListener.Direction.Left)
            return DIRECTION_LEFT;
        if (mDirection == AnimatedLayoutListener.Direction.Right)
            return DIRECTION_RIGHT;
        return DIRECTION_TOP;
    }

    public void setDirection(AnimatedLayoutListener.Direction mDirection) {
        if (this.mDirection == mDirection)
            return;
        AnimatedLayoutListener.Direction lastDirection = this.mDirection;
        this.mDirection = mDirection;
        resetAnimationDirection(lastDirection);
    }

    public AnimatedLayoutListener getAnimatedLayoutListener() {
        return animatedLayoutListener;
    }

    public void setAnimatedLayoutListener(AnimatedLayoutListener animatedLayoutListener) {
        this.animatedLayoutListener = animatedLayoutListener;
    }

    public boolean isExpandableStatus() {
        return isExpandableStatus;
    }

    public void setExpandableStatus(boolean expandableStatus) {
        isExpandableStatus = expandableStatus;
    }

    public Integer getLayoutWidth() {
        return layoutWidth;
    }

    public void setLayoutWidth(Integer layoutWidth) {
        this.layoutWidth = layoutWidth;
    }

    public Integer getLayoutHeight() {
        return layoutHeight;
    }

    public void setLayoutHeight(Integer layoutHeight) {
        this.layoutHeight = layoutHeight;
    }

    public TimeInterpolator getInterpolator() {
        return mInterpolator;
    }

    public void setInterpolator(int interpolator) {
        this.mInterpolator = Utils.createInterpolator(interpolator);
    }
}
