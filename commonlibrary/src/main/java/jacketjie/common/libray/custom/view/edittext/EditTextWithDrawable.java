package jacketjie.common.libray.custom.view.edittext;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

import jacketjie.common.libray.R;
import jacketjie.common.libray.others.DensityUtils;
import jacketjie.common.libray.others.ToastUtils;


/**
 * Created by Administrator on 2015/12/16.
 * 自定义edittext
 */
public class EditTextWithDrawable extends EditText implements TextWatcher {
    private Drawable defaultDrawableLeft;
    private Drawable defaultDrawableRight;
    private Drawable drawableLeftEnable;
    private Drawable drawableRightEnable;
    private OnEditTextDrawableClickListener onEditTextDrawableClickListener;
    private boolean isDrawableLeftEnable;
    private boolean isDrawableRightEnable;
    private int offset;

    public void setOnEditTextDrawableClickListener(OnEditTextDrawableClickListener onEditTextDrawableClickListener) {
        this.onEditTextDrawableClickListener = onEditTextDrawableClickListener;
        setFocusable(true);
    }


    public EditTextWithDrawable(Context context) {
        super(context);
    }

    public EditTextWithDrawable(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public EditTextWithDrawable(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        offset = DensityUtils.dp2px(context,2);

        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.EditTextWithDrawable, 0, 0);
        try {
            defaultDrawableLeft = ta.getDrawable(R.styleable.EditTextWithDrawable_defaultDrawableLeft);
            defaultDrawableRight = ta.getDrawable(R.styleable.EditTextWithDrawable_defaultDrawableRight);
            drawableLeftEnable = ta.getDrawable(R.styleable.EditTextWithDrawable_drawableLeftEnable);
            drawableRightEnable = ta.getDrawable(R.styleable.EditTextWithDrawable_drawableRightEnable);
            drawBitmap(defaultDrawableLeft, defaultDrawableRight);
//            drawRight(defaultDrawableRight);
            setTextChangeDrawable();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ta.recycle();
        }
    }

    private void setTextChangeDrawable() {
        this.addTextChangedListener(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        return super.dispatchTouchEvent(event);
    }

    public void clear() {
        setText("");
//        setFocusable(false);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int eventX, eventY;
        Rect rect = new Rect();
        if (MotionEvent.ACTION_DOWN == event.getAction()) {
            getGlobalVisibleRect(rect);
            eventX = (int) event.getRawX();
            eventY = (int) event.getRawY();
            if (onEditTextDrawableClickListener != null) {
                Rect leftRect = new Rect(rect);
                leftRect.left = leftRect.left + getPaddingLeft() - offset;
                if (isDrawableLeftEnable) {
                    leftRect.right = leftRect.left + drawableLeftEnable.getMinimumWidth() + offset;
                    if (leftRect.contains(eventX, eventY)) {
                        onEditTextDrawableClickListener.onEnableLeftClick();
                        return true;
                    }
                } else {
                    leftRect.right = leftRect.left + defaultDrawableLeft.getMinimumWidth()+ offset;
                    if (leftRect.contains(eventX, eventY)) {
                        onEditTextDrawableClickListener.onDefaultLeftClick();
                        return true;
                    }
                }
                Rect rightRect = new Rect(rect);
                if (isDrawableRightEnable) {
                    rightRect.right = rightRect.right - getPaddingRight() + offset;
                    if (isDrawableRightEnable) {
                        rightRect.left = rightRect.right - drawableRightEnable.getMinimumWidth() -  offset;
                        if (rightRect.contains(eventX, eventY)) {
                            onEditTextDrawableClickListener.onEnableRightClick();
                            return true;
                        }
                    }
                } else {
                    rightRect.left = rightRect.right - defaultDrawableRight.getMinimumWidth() -  offset;
                    if (rightRect.contains(eventX, eventY)) {
                        onEditTextDrawableClickListener.onDefaultRightClick();
                        return true;
                    }
                }
            }
        }


//        if (MotionEvent.ACTION_DOWN == event.getAction()) {
//
//            eventX = (int) event.getRawX();
//            eventY = (int) event.getRawY();
//            int[] position = new int[2];
//            getLocationOnScreen(position);
//            rect.left = position[0] + getPaddingLeft();
//            rect.top = position[1] + getPaddingTop();
//            rect.bottom = rect.top + getMeasuredHeight() + getPaddingBottom();
//            rect.right = rect.left + getMeasuredWidth() + getPaddingRight();
//            if (onEditTextDrawableClickListener != null) {
//                Rect leftRect = new Rect();
//                leftRect.top = position[1];
//                leftRect.bottom = leftRect.top + getPaddingTop() + getPaddingBottom() + getMeasuredHeight();
//                leftRect.left = position[0] + getPaddingLeft();
//                leftRect.right = leftRect.left  + (defaultDrawableRight == null ? drawableRightEnable.getMinimumWidth() : defaultDrawableRight.getMinimumWidth());
//                if (leftRect.contains(eventX, eventY)) {
//                    if (isDrawableLeftEnable) {
//                        onEditTextDrawableClickListener.onEnableLeftClick();
//                    } else {
//                        onEditTextDrawableClickListener.onDefaultLeftClick();
//                    }
//                }
//                Rect rightRect = new Rect();
//                rightRect.top = position[1];
//                rightRect.bottom = rightRect.top + getMeasuredHeight() + getPaddingTop() + getPaddingBottom();
//                rightRect.right = position[0] + getMeasuredWidth() + getPaddingRight();
//                rightRect.left = rightRect.right - defaultDrawableRight.getMinimumWidth();
//                if (rightRect.contains(eventX, eventY)) {
//                    if (isDrawableLeftEnable) {
//                        onEditTextDrawableClickListener.onEnableRightClick();
//                    } else {
//                        onEditTextDrawableClickListener.onDefaultRightClick();
//                    }
//                }
//            }
//
//        }
//        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//            eventX = (int) event.getRawX();
//            eventY = (int) event.getRawY();
//            rect = new Rect();
//            getGlobalVisibleRect(rect);
//            int[]position = new int[2];
//            getLocationOnScreen(position);
//
//
//            if (defaultDrawableLeft != null) {
//                int dw = defaultDrawableLeft.getMinimumWidth();
//                rect.right = rect.left + dw + getPaddingLeft();
//                if (rect.contains(eventX, eventY) && onEditTextDrawableClickListener != null) {
//                    if(isDrawableLeftEnable){
//                        onEditTextDrawableClickListener.onEnableLeftClick();
//                    }else{
//                        onEditTextDrawableClickListener.onDefaultLeftClick();
//                    }
//                }
//            }
//            if (defaultDrawableRight != null) {
//                int dw = defaultDrawableRight.getMinimumWidth();
//                rect.left = rect.right - dw - getPaddingRight();
//                if (rect.contains(eventX, eventY) && onEditTextDrawableClickListener != null) {
//                    if (isDrawableRightEnable){
//                        onEditTextDrawableClickListener.onEnableRightClick();
//                    }else{
//                        onEditTextDrawableClickListener.onDefaultRightClick();
//                    }
//                }
//            }
//        }
        return super.onTouchEvent(event);
    }

    private boolean handlerEvent(MotionEvent event) {
        int eventX, eventY;
        Rect rect = new Rect();
        if (MotionEvent.ACTION_DOWN == event.getAction()) {
            getGlobalVisibleRect(rect);
            eventX = (int) event.getRawX();
            eventY = (int) event.getRawY();
            if (onEditTextDrawableClickListener != null) {
                Rect leftRect = new Rect(rect);
                leftRect.left = leftRect.left + getPaddingLeft();
                if (isDrawableLeftEnable) {
                    leftRect.right = leftRect.left + drawableLeftEnable.getMinimumWidth();
                    if (leftRect.contains(eventX, eventY)) {
                        onEditTextDrawableClickListener.onEnableLeftClick();
                    }
                } else {
                    leftRect.right = leftRect.left + defaultDrawableLeft.getMinimumWidth();
                    if (leftRect.contains(eventX, eventY)) {
                        onEditTextDrawableClickListener.onDefaultLeftClick();
                    }
                }
                if (isDrawableRightEnable) {
                    Rect rightRect = new Rect(rect);
                    rightRect.right = rightRect.right - getPaddingRight();
                    if (isDrawableRightEnable) {
                        rightRect.left = rightRect.right - drawableRightEnable.getMinimumWidth();
                        if (rightRect.contains(eventX, eventY)) {
                            onEditTextDrawableClickListener.onEnableRightClick();
                        }
                    } else {
                        rightRect.left = rightRect.right - defaultDrawableRight.getMinimumWidth();
                        if (rightRect.contains(eventX, eventY)) {
                            onEditTextDrawableClickListener.onDefaultRightClick();
                        }
                    }
                }
            }


//            int[] position = new int[2];
//            getLocationOnScreen(position);
//            rect.left = position[0] + getPaddingLeft();
//            rect.top = position[1] + getPaddingTop();
//            rect.bottom = rect.top + getMeasuredHeight() + getPaddingBottom();
//            rect.right = rect.left + getMeasuredWidth() + getPaddingRight();
//            if (onEditTextDrawableClickListener != null) {
//                Rect leftRect = new Rect();
//                leftRect.top = position[1];
//                leftRect.bottom = leftRect.top + getPaddingTop() + getPaddingBottom() + getMeasuredHeight();
//                leftRect.left = position[0] + getPaddingLeft();
//                leftRect.right = leftRect.left + getPaddingLeft() + (defaultDrawableRight == null ? drawableRightEnable.getMinimumWidth() : defaultDrawableRight.getMinimumWidth());
//                if (leftRect.contains(eventX, eventY)) {
//                    if (isDrawableLeftEnable) {
//                        onEditTextDrawableClickListener.onEnableLeftClick();
//                    } else {
//                        onEditTextDrawableClickListener.onDefaultLeftClick();
//                    }
//                }
//                Rect rightRect = new Rect();
//                rightRect.top = position[1];
//                rightRect.bottom = rightRect.top + getMeasuredHeight() + getPaddingTop() + getPaddingBottom();
//                rightRect.right = position[0] + getMeasuredWidth();
//                rightRect.left = rightRect.right - defaultDrawableRight.getMinimumWidth();
//                if (rightRect.contains(eventX, eventY)) {
//                    if (isDrawableLeftEnable) {
//                        onEditTextDrawableClickListener.onEnableRightClick();
//                    } else {
//                        onEditTextDrawableClickListener.onDefaultRightClick();
//                    }
//                }
//            }

        }
        return false;
    }

    public void drawBitmap(Drawable drawableLeft, Drawable drawableRight) {
        if (drawableLeft != null) {
            drawableLeft.setBounds(0, 0, drawableLeft.getMinimumWidth(), drawableLeft.getMinimumHeight());
        }
        if (drawableRight != null) {
            drawableRight.setBounds(0, 0, drawableRight.getMinimumWidth(), drawableRight.getMinimumHeight());
        }
        this.setCompoundDrawables(drawableLeft, null, drawableRight, null);
    }

//
//    public void drawRight(Drawable drawableRight) {
//        if (drawableRight != null) {
//            drawableRight.setBounds(0, 0, drawableRight.getMinimumWidth(), drawableRight.getMinimumHeight());
//            this.setCompoundDrawables(null, null, drawableRight, null);
//        }
//    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (TextUtils.isEmpty(s.toString())) {
            drawBitmap(defaultDrawableLeft, defaultDrawableRight);
//            drawRight(defaultDrawableRight);
            if (defaultDrawableLeft != null) {
                isDrawableLeftEnable = false;
            }
            if (defaultDrawableRight != null) {
                isDrawableRightEnable = false;
            }

        } else {
            drawBitmap(drawableLeftEnable, drawableRightEnable);
//            drawRight(drawableRightEnable);
            if (defaultDrawableLeft != null) {
                isDrawableLeftEnable = true;
            }
            if (defaultDrawableRight != null) {
                isDrawableRightEnable = true;
            }
        }
    }
}
