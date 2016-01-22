package jacketjie.common.libray.custom.view.expandablelayout;

import android.animation.TimeInterpolator;
import android.support.annotation.IntRange;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

import jacketjie.common.libray.custom.view.animatedlayout.AnimatedLayoutListener;

public class Utils {

    public static final int ACCELERATE_DECELERATE_INTERPOLATOR = 0;
    public static final int ACCELERATE_INTERPOLATOR = 1;
    public static final int ANTICIPATE_INTERPOLATOR = 2;
    public static final int ANTICIPATE_OVERSHOOT_INTERPOLATOR = 3;
    public static final int BOUNCE_INTERPOLATOR = 4;
    public static final int DECELERATE_INTERPOLATOR = 5;
    public static final int FAST_OUT_LINEAR_IN_INTERPOLATOR = 6;
    public static final int FAST_OUT_SLOW_IN_INTERPOLATOR = 7;
    public static final int LINEAR_INTERPOLATOR = 8;
    public static final int LINEAR_OUT_SLOW_IN_INTERPOLATOR = 9;
    public static final int OVERSHOOT_INTERPOLATOR = 10;

    /**
     * Creates interpolator.
     *
     * @param interpolatorType
     * @return
     */
    public static TimeInterpolator createInterpolator(@IntRange(from = 0, to = 10) final int interpolatorType) {
        switch (interpolatorType) {
            case ACCELERATE_DECELERATE_INTERPOLATOR:
                return new AccelerateDecelerateInterpolator();
            case ACCELERATE_INTERPOLATOR:
                return new AccelerateInterpolator();
            case ANTICIPATE_INTERPOLATOR:
                return new AnticipateInterpolator();
            case ANTICIPATE_OVERSHOOT_INTERPOLATOR:
                return new AnticipateOvershootInterpolator();
            case BOUNCE_INTERPOLATOR:
                return new BounceInterpolator();
            case DECELERATE_INTERPOLATOR:
                return new DecelerateInterpolator();
            case FAST_OUT_LINEAR_IN_INTERPOLATOR:
                return new FastOutLinearInInterpolator();
            case FAST_OUT_SLOW_IN_INTERPOLATOR:
                return new FastOutSlowInInterpolator();
            case LINEAR_INTERPOLATOR:
                return new LinearInterpolator();
            case LINEAR_OUT_SLOW_IN_INTERPOLATOR:
                return new LinearOutSlowInInterpolator();
            case OVERSHOOT_INTERPOLATOR:
                return new OvershootInterpolator();
            default:
                return new LinearInterpolator();
        }
    }

    public static int getInterpolator(TimeInterpolator timer){
        int style = 8;
        if (timer instanceof AccelerateDecelerateInterpolator)
            style = 0;
        if (timer instanceof AccelerateInterpolator)
            style = 1;
        if (timer instanceof AnticipateInterpolator)
            style = 2;
        if (timer instanceof AnticipateOvershootInterpolator)
            style = 3;
        if (timer instanceof BounceInterpolator)
            style = 4;
        if (timer instanceof DecelerateInterpolator)
            style = 5;
        if (timer instanceof FastOutLinearInInterpolator)
            style = 6;
        if (timer instanceof FastOutSlowInInterpolator)
            style = 7;
        if (timer instanceof LinearInterpolator)
            style = 8;
        if (timer instanceof LinearOutSlowInInterpolator)
            style = 9;
        if (timer instanceof OvershootInterpolator)
            style = 10;
        return style;
    }

    public static AnimatedLayoutListener.Direction getAnimationDirection(int style) {

        AnimatedLayoutListener.Direction mDirection = null;
        switch (style){
            case 0:
                mDirection = AnimatedLayoutListener.Direction.Top;
                break;
            case 1:
                mDirection = AnimatedLayoutListener.Direction.Left;
                break;
            case 2:
                mDirection = AnimatedLayoutListener.Direction.Right;
                break;
            default:
                mDirection = AnimatedLayoutListener.Direction.Top;
                break;
        }
        return mDirection;
    }
}