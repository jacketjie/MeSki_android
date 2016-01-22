package jacketjie.common.libray.custom.view.bradge;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import jacketjie.common.libray.others.DensityUtils;

/**
 * View角标
 * Created by Administrator on 2016/test_1/13.
 */
public class BadgeTipView extends TextView {
    private int bgRes = Color.RED;
    private int myTextValue = TypedValue.COMPLEX_UNIT_SP;
    private int myTextSize = 8;
    private int myTextColor = Color.WHITE;
    private int myRadius = 8;
    private boolean hiddenOnNull = true;


    public BadgeTipView(Context context) {
        this(context, null);
    }

    public BadgeTipView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BadgeTipView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BadgeTipView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    /**
     * 初始化
     *
     * @param context
     * @param attrs
     */
    private void init(Context context, AttributeSet attrs) {
        if (!(getLayoutParams() instanceof FrameLayout.LayoutParams)) {
            FrameLayout.LayoutParams layoutParams =
                    new FrameLayout.LayoutParams(
                            android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
                            android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
            setLayoutParams(layoutParams);
        }
        setBackgroundStyle(bgRes, myRadius);
        setTextColor(myTextColor);
        setTextSize(myTextSize);
        setGravity(Gravity.CENTER);
        setPadding(DensityUtils.dp2px(context, 3), 0, DensityUtils.dp2px(context, 3), 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setTranslationZ(10);
        }
    }

    /**
     * 设置相对于父布局的位置
     *
     * @param gravity
     */
    public void setDisplayGravity(int gravity) {
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) getLayoutParams();
        lp.gravity = gravity;
        requestLayout();
    }

    /**
     * 设置背景
     *
     * @param bgColor
     * @param radius
     */
    private void setBackgroundStyle(int bgColor, int radius) {
        int r = DensityUtils.dp2px(getContext(), radius);
        float[] array = new float[]{r, r, r, r, r, r, r, r};
        RoundRectShape roundRect = new RoundRectShape(array, null, null);
        ShapeDrawable bgDrawable = new ShapeDrawable(roundRect);
        bgDrawable.getPaint().setColor(bgColor);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(bgDrawable);
        } else {
            setBackgroundDrawable(bgDrawable);
        }
    }

//    public void setBadgeWeight(Float widthWeight, Float heightWeight) {
//        this.widthWeight = widthWeight;
//        this.heightWeight = heightWeight;
////        setTarget(targetView);
//        requestLayout();
//    }

    /**
     * 给View添加角标
     *
     * @param targetView
     */
    public void setTarget(View targetView) {
        if (targetView == null) {
            Log.d("BadgeView", "target view is null");
            return;
        }
        if (getParent() != null) {
            ((ViewGroup) getParent()).removeView(this);
        }
        ViewGroup parent = (ViewGroup) targetView.getParent();
        ViewGroup.LayoutParams targetLP = targetView.getLayoutParams();
        FrameLayout container = null;

        if (parent != null) {
            if (parent instanceof FrameLayout) {
                parent.addView(this);
                parent.invalidate();
            } else {
                int index = parent.indexOfChild(targetView);
                parent.removeView(targetView);
                container = new FrameLayout(getContext());
                parent.addView(container, index, targetLP);
                container.addView(targetView);
                container.addView(this);
                parent.invalidate();
            }
        }
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        if (isHiddenOnNull() && text != null && text.toString().equals("0")){
            setVisibility(GONE);
        }else{
            setVisibility(VISIBLE);
        }
        super.setText(text, type);
    }

    /**
     * 设置角标上的数字
     *
     * @param count
     */
    public void setBadgeCount(int count) {
        setVisibility(VISIBLE);
        setText(String.valueOf(count));
    }

    /**
     * 设置角标上的数字
     *
     * @param s
     */
    public void setBadgeCount(String s) {
        if (TextUtils.isEmpty(s)) {
            setVisibility(GONE);
            return;
        }
        setVisibility(VISIBLE);
        setText(s);
    }

    public boolean isHiddenOnNull() {
        return hiddenOnNull;
    }

    public void setHiddenOnNull(boolean hiddenOnNull) {
        this.hiddenOnNull = hiddenOnNull;
        setText(getText());
    }

    public int getBgRes() {
        return bgRes;
    }

    public void setBgRes(int bgRes) {
        this.bgRes = bgRes;
    }

    public int getMyTextSize() {
        return myTextSize;
    }

    public void setMyTextSize(int myTextSize) {
        this.myTextSize = myTextSize;
        setTextSize(myTextValue, myTextSize);
    }

    public int getMyRadius() {
        return myRadius;
    }

    public void setMyRadius(int myRadius) {
        this.myRadius = myRadius;
    }

    public int getMyTextColor() {
        return myTextColor;
    }

    public void setMyTextColor(int myTextColor) {
        this.myTextColor = myTextColor;
    }


}
