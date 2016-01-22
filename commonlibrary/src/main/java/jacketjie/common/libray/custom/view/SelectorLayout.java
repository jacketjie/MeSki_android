
package jacketjie.common.libray.custom.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import jacketjie.common.libray.R;
import jacketjie.common.libray.custom.view.expandablelayout.Utils;

/**
 * 可以进行收缩展开动画的LinLayout
 * 动画的初始与结束为止取决于父控件的位置
 *
 */
public class SelectorLayout extends LinearLayout {
    /**
     * 默认动画时长
     */
    private static final int Default_druation = 220;
    /**
     * 动画时长
     */
    private int mDuration;
    /**
     * Drawable Style
     */
    private static final int DRAWABLE_STYLE = 0;
    /**
     * Expandable Style
     */
    private static final int EXPANDABLE_STYLE = 1;
    /**
     * 默认动画风格
     */
    private static final int Default_style = DRAWABLE_STYLE;
    /**
     * 当前动画风格
     */
    private Styleable curStyle = Styleable.Drawable;
    /**
     * 是否正在进行动画
     */
    private boolean isAnimation;
    /**
     * 是否处于展开状态
     */
    private boolean isExpandable = true;
    /**
     * 动画插值器
     */
    private TimeInterpolator interpolator;

    /**
     * 记录上一次的动画风格
     */
    private Styleable lastStyle = curStyle;
    /**
     * 动画的方向
     */
    private Direction curDirection = Direction.Top;

    private boolean hadInited;

    private boolean hasDisplayed ;
    private boolean isFromSystem = true;


    public enum Direction {
        Top,
        Left,
        Bottom,
        Right
    }

    public enum Styleable {
        Expanable,
        Drawable
    }

    /**
     * 判断是不是展开的
     * @return
     */
    public boolean isExpand(){
        return isExpandable;
    }

    public SelectorLayout(Context context) {
        this(context, null);
    }

    public SelectorLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SelectorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    public int getInterpolatorStyle() {
        return Utils.getInterpolator(interpolator);
    }


    public void setInterpolator(int interpolatorStyle) {
        this.interpolator = Utils.createInterpolator(interpolatorStyle);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SelectorLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray ta = context.obtainStyledAttributes(
                attrs, R.styleable.SelectorLayout, defStyleAttr, 0);
        mDuration = ta.getInteger(R.styleable.SelectorLayout_duration, Default_druation);
        int style = ta.getInteger(R.styleable.SelectorLayout_style, Default_style);
        switch (style) {
            case DRAWABLE_STYLE:
                curStyle = Styleable.Drawable;
                break;
            case EXPANDABLE_STYLE:
                curStyle = Styleable.Expanable;
                break;
        }
        Integer interpolatorStyle = ta.getInteger(R.styleable.SelectorLayout_sl_interpolator, 8);
        interpolator = Utils.createInterpolator(interpolatorStyle);
        Integer directionStyle = ta.getInteger(R.styleable.SelectorLayout_direction, 0);
        switch (directionStyle) {
            case 0:
                curDirection = Direction.Top;
                break;
            case 1:
                curDirection = Direction.Left;
                break;
            case 2:
                curDirection = Direction.Bottom;
                break;
            case 3:
                curDirection = Direction.Right;
                break;
        }
        ta.recycle();

        setVisibility(GONE);
    }


    public Styleable getAnimationStyle() {
        return curStyle;
    }

    public void setAnimationStyle(Styleable curStyle) {
        this.lastStyle = this.curStyle;
        this.curStyle = curStyle;

//        if (curStyle == Styleable.Drawable) {
//            setTranslationY(0);
//        } else if (curStyle == Styleable.Expanable) {
//            getLayoutParams().height = getRealHeight();
//            requestLayoutByObject(true);
//        }
        resetAnimationStyle();

    }

    /**
     * 重置动画
     */
    private void resetAnimationStyle() {
        if (curStyle == lastStyle)
            return;
        switch (getStyle(lastStyle)) {
            case DRAWABLE_STYLE:
                if (!isExpand()) {
                    if (getDirectionIndex() == 0) {
                        setTranslationY(0);
                        getLayoutParams().height = 0;
                        requestLayoutByObject(true);
                    }
                    if(getDirectionIndex() == 1){
                        setTranslationX(0);
                        getLayoutParams().width = 0;
                        requestLayoutByObject(true);
                    }
                    if (getDirectionIndex() == 2) {
                        setTranslationY(getRealHeight());
                        getLayoutParams().height = 0;
                        requestLayoutByObject(true);
                    }
                    if(getDirectionIndex() == 3){
                        setTranslationX(getRealWidth());
                        getLayoutParams().width = 0;
                        requestLayoutByObject(true);
                    }
                }
                break;
            case EXPANDABLE_STYLE:
                if (!isExpand()) {
                    if (getDirectionIndex() == 0) {
                        getLayoutParams().height = getRealHeight();
                        requestLayoutByObject(true);
                        setTranslationY(-getRealHeight());
                    }
                    if (getDirectionIndex() == 1)  {
                        getLayoutParams().width = getRealWidth();
                        requestLayoutByObject(true);
                        setTranslationX(-getRealWidth());
                    }
                    if (getDirectionIndex() == 2) {
                        getLayoutParams().height = getRealHeight();
                        requestLayoutByObject(true);
                        setTranslationY(getRealHeight());
                    }
                    if (getDirectionIndex() == 3)  {
                        getLayoutParams().width = getRealWidth();
                        requestLayoutByObject(true);
                        setTranslationX(getRealWidth());
                    }
                }
                break;
        }
    }

    public Direction getAnimationDirection() {
        return curDirection;
    }

    public void setAnimationDirection(Direction curDirection) {
        if (getAnimationDirection() != curDirection) {
            Direction lastDirection = this.curDirection;
            this.curDirection = curDirection;
            resetAnimationLayout(lastDirection);
        }
    }

    public int getDuration() {
        return mDuration;
    }

    public void setDuration(int mDuration) {
        this.mDuration = mDuration;
    }

    /**
     * 切换方向，改变状态
     */
    private void resetAnimationLayout(Direction lastDirection) {
        if (Direction.Top == curDirection) {
            //Left-->Top
            if (Direction.Left == lastDirection){
                switch (getStyle(curStyle)) {
                    case DRAWABLE_STYLE:
                        if (!isExpand()) {
                            setTranslationX(0);
                            setTranslationY(-getRealHeight());
                        }
                        break;
                    case EXPANDABLE_STYLE:
                        if (!isExpand()) {
                            getLayoutParams().width = getRealWidth();
                            getLayoutParams().height = 0;
                            requestLayoutByObject(true);
                        }
                        break;
                }
            }
            //Bottom-->Top
            if (Direction.Bottom == lastDirection){
                switch (getStyle(curStyle)) {
                    case DRAWABLE_STYLE:
                        if (!isExpand()) {
                            setTranslationY(-getRealHeight());
                        }
                        break;
                    case EXPANDABLE_STYLE:
                        if (!isExpand()) {
//                            getLayoutParams().width = 0;
                            getLayoutParams().height = 0;
                            setTranslationY(0);
                            requestLayoutByObject(true);
                        }
                        break;
                }
            }
            //Right-->Top
            if (Direction.Right == lastDirection){
                switch (getStyle(curStyle)) {
                    case DRAWABLE_STYLE:
                        if (!isExpand()) {
                            setTranslationX(0);
                            setTranslationY(-getRealHeight());
                        }
                        break;
                    case EXPANDABLE_STYLE:
                        if (!isExpand()) {
                            getLayoutParams().width = getRealWidth();
                            getLayoutParams().height = 0;
                            setTranslationX(0);
                            requestLayoutByObject(true);
                        }
                        break;
                }
            }
        }
        if (Direction.Left == curDirection) {
            //Top-->Left
            if (Direction.Top == lastDirection){
                switch (getStyle(curStyle)) {
                    case DRAWABLE_STYLE:
                        if (!isExpand()) {
                            setTranslationY(0);
                            setTranslationX(-getRealWidth());
                        }
                        break;
                    case EXPANDABLE_STYLE:
                        if (!isExpand()) {
                            getLayoutParams().width = 0;
                            getLayoutParams().height = getRealHeight();
                            requestLayoutByObject(true);
                        }
                        break;
                }
            }
            //Bottom-->Left
            if (Direction.Bottom == lastDirection){
                switch (getStyle(curStyle)) {
                    case DRAWABLE_STYLE:
                        if (!isExpand()) {
                            setTranslationY(0);
                            setTranslationX(-getRealWidth());
                        }
                        break;
                    case EXPANDABLE_STYLE:
                        if (!isExpand()) {
                            getLayoutParams().width = 0;
                            getLayoutParams().height = getRealHeight();
                            setTranslationY(0);
                            requestLayoutByObject(true);
                        }
                        break;
                }
            }
            //Right-->Left
            if (Direction.Right == lastDirection){
                switch (getStyle(curStyle)) {
                    case DRAWABLE_STYLE:
                        if (!isExpand()) {
                            setTranslationX(-getRealWidth());
                        }
                        break;
                    case EXPANDABLE_STYLE:
                        if (!isExpand()) {
                            getLayoutParams().width = 0;
                            getLayoutParams().height = getRealHeight();
                            setTranslationX(0);
                            requestLayoutByObject(true);
                        }
                        break;
                }
            }
        }
        if (Direction.Bottom == curDirection) {
            //Left-->Bottom
            if (Direction.Left == lastDirection){
                switch (getStyle(curStyle)) {
                    case DRAWABLE_STYLE:
                        if (!isExpand()) {
                            setTranslationX(0);
                            setTranslationY(-getRealHeight());
                        }
                        break;
                    case EXPANDABLE_STYLE:
                        if (!isExpand()) {
                            getLayoutParams().width = getRealWidth();
                            getLayoutParams().height = 0;
                            setTranslationY(getRealHeight());
                            requestLayoutByObject(true);
                        }
                        break;
                }
            }
            //Top-->Bottom
            if (Direction.Top == lastDirection){
                switch (getStyle(curStyle)) {
                    case DRAWABLE_STYLE:
                        if (!isExpand()) {
                            setTranslationY(getRealHeight());
                        }
                        break;
                    case EXPANDABLE_STYLE:
                        if (!isExpand()) {
                            getLayoutParams().width = getRealWidth();
                            getLayoutParams().height = 0;
                            setTranslationY(getRealHeight());
                            requestLayoutByObject(true);
                        }
                        break;
                }
            }
            //Right-->Bottom
            if (Direction.Right == lastDirection){
                switch (getStyle(curStyle)) {
                    case DRAWABLE_STYLE:
                        if (!isExpand()) {
                            setTranslationX(0);
                            setTranslationY(getRealHeight());
                        }
                        break;
                    case EXPANDABLE_STYLE:
                        if (!isExpand()) {
                            getLayoutParams().width = getRealWidth();
                            getLayoutParams().height = 0;
                            setTranslationX(0);
                            requestLayoutByObject(true);
                        }
                        break;
                }
            }
        }
        if (Direction.Right == curDirection) {
            //Top-->Right
            if (Direction.Top == lastDirection){
                switch (getStyle(curStyle)) {
                    case DRAWABLE_STYLE:
                        if (!isExpand()) {
                            setTranslationY(0);
                            setTranslationX(getRealWidth());
                        }
                        break;
                    case EXPANDABLE_STYLE:
                        if (!isExpand()) {
                            getLayoutParams().width = 0;
                            getLayoutParams().height = getRealHeight();
                            setTranslationY(0);
                            setTranslationX(getRealWidth());
                            requestLayoutByObject(true);
                        }
                        break;
                }
            }
            //Left-->Right
            if (Direction.Left == lastDirection){
                switch (getStyle(curStyle)) {
                    case DRAWABLE_STYLE:
                        if (!isExpand()) {
                            setTranslationY(0);
                            setTranslationX(getRealWidth());
                        }
                        break;
                    case EXPANDABLE_STYLE:
                        if (!isExpand()) {
                            getLayoutParams().width = 0;
                            getLayoutParams().height = getRealHeight();
                            setTranslationX(getRealWidth());
                            setTranslationY(0);
                            requestLayoutByObject(true);
                        }
                        break;
                }
            }
            //Bottom-->Right
            if (Direction.Bottom == lastDirection){
                switch (getStyle(curStyle)) {
                    case DRAWABLE_STYLE:
                        if (!isExpand()) {
                            setTranslationX(getRealWidth());
                            setTranslationY(0);
                        }
                        break;
                    case EXPANDABLE_STYLE:
                        if (!isExpand()) {
                            getLayoutParams().width = 0;
                            getLayoutParams().height = getRealHeight();
                            setTranslationX(getRealWidth());
                            setTranslationY(0);
                            requestLayoutByObject(true);
                        }
                        break;
                }
            }
        }
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
        if (!isFromSystem){
            setRealHeight(getMeasuredHeight());
            setRealWidth(getMeasuredWidth());
        }
//        moveToHidden();
    }

    public void requestLayoutByObject(boolean isFromSystem){
        this.isFromSystem = isFromSystem;
        this.requestLayout();
    }

    /**
     * 初始是隐藏
     */
    private void moveToHidden() {
        if (!hadInited) {
            if (getRealWidth() == null || getRealHeight() == null) {
                return;
            }
            if (getDirectionIndex() == 0) {
                switch (getStyle(curStyle)) {
                    case DRAWABLE_STYLE:
                        setTranslationY(-getRealHeight());
                        break;
                    case EXPANDABLE_STYLE:
                        getLayoutParams().height = 0;
                        requestLayoutByObject(true);
                        break;
                }
            }
            if (getDirectionIndex() == 1) {
                switch (getStyle(curStyle)) {
                    case DRAWABLE_STYLE:
                        setTranslationX(0);
                        break;
                    case EXPANDABLE_STYLE:
                        getLayoutParams().width = 0;
                        requestLayoutByObject(true);
                        break;
                }
            }
            if (getDirectionIndex() == 2) {
                switch (getStyle(curStyle)) {
                    case DRAWABLE_STYLE:
                        setTranslationY(getRealHeight());
                        break;
                    case EXPANDABLE_STYLE:
                        getLayoutParams().height = 0;
                        setTranslationY(getRealHeight());
                        requestLayoutByObject(true);
                        break;
                }
            }
            if (getDirectionIndex() == 3) {
                switch (getStyle(curStyle)) {
                    case DRAWABLE_STYLE:
                        setTranslationX(getRealHeight());
                        break;
                    case EXPANDABLE_STYLE:
                        getLayoutParams().width = 0;
                        setTranslationX(getRealWidth());
                        requestLayoutByObject(true);
                        break;
                }
            }
            isExpandable = false;
            hadInited = true;
        }
    }

    private void resetLayoutSize(){

    }



    /**
     * 判断动画方向
     *
     * @return true:垂直方向的动画
     */
    public int getDirectionIndex() {
        int index = 0;
        if (curDirection == Direction.Top){
            index = 0;
        }
        if (curDirection == Direction.Left){
            index = 1;
        }
        if (curDirection == Direction.Bottom){
            index = 2;
        }
        if (curDirection == Direction.Right){
            index = 3;
        }
        return index;
    }

    /**
     * 判断动画风格
     *
     * @return
     */
    public int getStyle(Styleable style) {
        int index = 0;
        if (style == Styleable.Drawable)
            index = 0;
        if (style == Styleable.Expanable)
            index = 1;
        return index;
    }


    private Integer realHeight = null;
    private Integer realWidth = null;

    private Integer getRealHeight() {
        return realHeight == null ? getMeasuredHeight():realHeight;
    }

    private void setRealHeight(Integer realHeight) {
        if (realHeight != null && realHeight > 0)
            this.realHeight = realHeight;
    }

    private Integer getRealWidth() {
        return realWidth == null ? getMeasuredWidth() : realWidth;
    }

    private void setRealWidth(Integer realWidth) {
        if (realWidth != null && realWidth > 0)
            this.realWidth = realWidth;
    }

    /**
     * 判断布局方向
     *
     * @return true:vertical布局
     */
    private boolean isVertical() {
        return getOrientation() == VERTICAL;
    }

    /**
     * 设置Expandable动画
     *
     * @param startPos
     * @param endPos
     * @return
     */
    private ValueAnimator setExpandableAnimation(float startPos, float endPos) {
//        Log.d("SelectorLayout", "mDuration=" + mDuration);
        ValueAnimator valueAnimator = null;
        valueAnimator = ValueAnimator.ofFloat(startPos, endPos);
        valueAnimator.setInterpolator(interpolator);
        valueAnimator.setDuration(mDuration);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                if (getDirectionIndex() == 0) {
                    getLayoutParams().height = (int) value;
                }
                if (getDirectionIndex() == 1) {
                    getLayoutParams().width = (int) value;
                }
                requestLayoutByObject(true);
//                Log.d("SelectorLayout", "width=" + getLayoutParams().width + ",height=" + getLayoutParams().height);
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                isAnimation = true;
                setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isAnimation = false;
                if (!isExpandable){
                    setVisibility(GONE);
                }
            }
        });
        return valueAnimator;
    }

    /**
     * 设置Drawable动画
     *
     * @param startPos
     * @param endPos
     * @return
     */
    private ValueAnimator setDrawableAnimation(float startPos, float endPos) {
        ObjectAnimator objectAnimator = null;
        if (getDirectionIndex() == 0 || getDirectionIndex() == 2) {
            objectAnimator = ObjectAnimator.ofFloat(this, View.TRANSLATION_Y, startPos, endPos);
        }
        if (getDirectionIndex() == 1 || getDirectionIndex() == 3) {
            objectAnimator = ObjectAnimator.ofFloat(this, View.TRANSLATION_X, startPos, endPos);
        }
        objectAnimator.setInterpolator(interpolator);
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                isAnimation = true;
                setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isAnimation = false;
                if (!isExpandable){
                    setVisibility(GONE);
                }
            }
        });
        return objectAnimator;
    }


    private ValueAnimator setExpandableBottomOrRightAnimation(float startPos, float endPos){
        ValueAnimator valueAnimator = null;
        valueAnimator =  ValueAnimator.ofFloat(startPos,endPos);
        valueAnimator.setDuration(mDuration);
        valueAnimator.setInterpolator(interpolator);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                if (getDirectionIndex() == 2){
                    getLayoutParams().height = (int) value;
                    setTranslationY(getRealHeight() - value);
                    requestLayoutByObject(true);
                }
                if (getDirectionIndex() == 3){
                    getLayoutParams().width = (int) value;
                    setTranslationX(getRealWidth() - value);
                    requestLayoutByObject(true);
                   
                }

            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                isAnimation = true;
                setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isAnimation = false;
                if (!isExpandable){
                    setVisibility(GONE);
                }
            }
        });

        return valueAnimator;
    }

    /**
     * 展开或者收缩动画
     */
    public void displayOrHidden() {
        if (isAnimation)
            return;
        setVisibility(VISIBLE);
        if (!hasDisplayed){
            moveToHidden();
            hasDisplayed = true;
        }
        if (getDirectionIndex() == 0) {
            switch (getStyle(curStyle)) {
                case DRAWABLE_STYLE:
                    if (isExpand()) {
                        setDrawableAnimation(0, -getRealHeight()).start();
                        isExpandable = false;
                    } else {
                        setDrawableAnimation(-getRealHeight(), 0).start();
                        isExpandable = true;
                    }
                    break;
                case EXPANDABLE_STYLE:
                    if (isExpand()) {
                        setExpandableAnimation(getRealHeight(), 0).start();
                        isExpandable = false;
                    } else {
                        setExpandableAnimation(0, getRealHeight()).start();
                        isExpandable = true;
                    }
                    break;

            }
        }
        if (getDirectionIndex() == 1) {
            switch (getStyle(curStyle)) {
                case DRAWABLE_STYLE:
                    if (isExpand()) {
                        setDrawableAnimation(0, -getRealWidth()).start();
                        isExpandable = false;
                    } else {
                        setDrawableAnimation(-getRealWidth(), 0).start();
                        isExpandable = true;
                    }
                    break;
                case EXPANDABLE_STYLE:
                    if (isExpand()) {
                        setExpandableAnimation(getRealWidth(), 0).start();
                        isExpandable = false;
                    } else {
                        setExpandableAnimation(0, getRealWidth()).start();
                        isExpandable = true;
                    }
                    break;
            }
        }
        if (getDirectionIndex() == 2){
            switch (getStyle(curStyle)){
                case DRAWABLE_STYLE:
                    if (isExpandable){
                        setDrawableAnimation(0,getRealHeight()).start();
                        isExpandable = false;
                    }else{
                        setDrawableAnimation(getRealHeight(),0).start();
                        isExpandable = true;
                    }
                    break;
                case EXPANDABLE_STYLE:
                    if (isExpandable){
                        setExpandableBottomOrRightAnimation(getRealHeight(), 0).start();
                        isExpandable = false;
                    }else {
                        setExpandableBottomOrRightAnimation(0,getRealHeight()).start();
                        isExpandable = true;
                    }
                    break;
            }
        }
        if (getDirectionIndex() == 3){
            switch (getStyle(curStyle)){
                case DRAWABLE_STYLE:
                    if (isExpandable){
                        setDrawableAnimation(0,getRealWidth()).start();
                        isExpandable = false;
                    }else{
                        setDrawableAnimation(getRealWidth(),0).start();
                        isExpandable = true;
                    }
                    break;
                case EXPANDABLE_STYLE:
                    if (isExpandable){
                        setExpandableBottomOrRightAnimation(getRealWidth(),0).start();
                        isExpandable = false;
                    }else {
                        setExpandableBottomOrRightAnimation(0,getRealWidth()).start();
                        isExpandable = true;
                    }
                    break;
            }
        }
    }

}
