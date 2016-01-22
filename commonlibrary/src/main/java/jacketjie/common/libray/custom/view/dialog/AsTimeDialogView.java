package jacketjie.common.libray.custom.view.dialog;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import jacketjie.common.libray.R;
import jacketjie.common.libray.others.ScreenUtils;


/**
 * Created by Administrator on 2015/12/9.
 * 自定义加载进度
 */
public class AsTimeDialogView extends View {
    /**
     * 圆形的最大，最小尺寸
     */
    private float maxRadius, minRadius;
    /**
     * 缩放的偏移值
     */
    private int offset = 5;
    /**
     * 两个圆的画笔
     */
    private Paint leftPaint, rightPaint;
    /**
     * 两个圆的画笔颜色
     */
    private int leftPaintColor, rightPaintColor;
    /**
     * 画笔的默认颜色
     */
    private int defaultColor;
    /**
     * 左边圆的半径
     */
    private float leftRadius;
    /**
     * 右边圆的半径
     */
    private float rightRadius;
    /**
     * 圆是在放大还是缩小
     */
    private boolean leftExpand;
    /**
     * 是否重复绘制
     */
    private boolean repeatDraw;
    /**
     * 再次重绘圆的时间
     */
    private int drawDuration;
    /**
     * 默认最小半径
     */
    private int defaultMinRadius;

    /**
     * 默认最大半径
     */
    private int defaultMaxRadius;

    private float initPos;

    public AsTimeDialogView(Context context) {
        this(context, null);
    }

    public AsTimeDialogView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AsTimeDialogView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initProprity(context, attrs);
        init();
    }

    /**
     * 初始化参数
     *
     * @param context
     * @param attrs
     */
    private void initProprity(Context context, AttributeSet attrs) {
        defaultColor = getContext().getResources().getColor(R.color.colorPrimary);
        defaultMaxRadius = context.getResources().getDimensionPixelOffset(R.dimen.x16);
        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.AsTimeDialogView, 0, 0);
        try {
            maxRadius = ta.getDimension(R.styleable.AsTimeDialogView_circle_maxRadius, defaultMaxRadius);
            leftPaintColor = ta.getColor(R.styleable.AsTimeDialogView_circle_left_color, defaultColor);
            rightPaintColor = ta.getColor(R.styleable.AsTimeDialogView_circle_right_color, defaultColor);
            drawDuration = ta.getInteger(R.styleable.AsTimeDialogView_circle_duration, 100);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ta.recycle();
        }
    }

    /**
     * 设置画笔属性
     */
    private void init() {
        minRadius = maxRadius * 2.0f / 3;
        leftPaint = new Paint();
        leftPaint.setAntiAlias(true);
        leftPaint.setColor(leftPaintColor);
        leftPaint.setStyle(Paint.Style.FILL);
        rightPaint = new Paint();
        rightPaint.setAntiAlias(true);
        rightPaint.setColor(rightPaintColor);
        rightPaint.setStyle(Paint.Style.FILL);

        leftRadius = maxRadius;
        rightRadius = minRadius;
        initPos = (ScreenUtils.getScreenWidth(getContext()) - maxRadius * 5) / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRGB(255, 255, 255);
        canvas.drawCircle(initPos+maxRadius, getHeight() / 2, leftRadius, leftPaint);
        canvas.drawCircle(initPos+maxRadius * 4, getHeight() / 2, rightRadius, rightPaint);
        if (leftRadius >= maxRadius) {
            leftExpand = false;
        }
        if (leftRadius <= minRadius) {
            leftExpand = true;
        }
        if (leftExpand) {
            leftRadius += offset;
            rightRadius -= offset;
        } else {
            leftRadius -= offset;
            rightRadius += offset;
        }
        if (repeatDraw) {
            postInvalidateDelayed(drawDuration);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//       ViewGroup.LayoutParams lp =  this.getLayoutParams();
//        if (getMeasuredWidth() < maxRadius * 5) {
//            lp.width = (int) (maxRadius * 5 + offset);
//        }
//        if (getMeasuredHeight() < maxRadius * 2) {
//            lp.height = (int) (maxRadius * 2);
//        }
//        if (lp instanceof LinearLayout.LayoutParams){
//            LinearLayout.LayoutParams ll = (LinearLayout.LayoutParams)lp;
//            ll.leftMargin = (ScreenUtils.getScreenWidth(getContext()) - getMeasuredWidth());
//            setLayoutParams(ll);
//        }
//        if (lp instanceof FrameLayout.LayoutParams){
//            FrameLayout.LayoutParams fl = (FrameLayout.LayoutParams)lp;
//            fl.leftMargin = (ScreenUtils.getScreenWidth(getContext()) - getMeasuredWidth());
//            setLayoutParams(fl);
//        }
//        if (lp instanceof RelativeLayout.LayoutParams){
//            RelativeLayout.LayoutParams rl = (RelativeLayout.LayoutParams)lp;
//            rl.leftMargin = (ScreenUtils.getScreenWidth(getContext()) - getMeasuredWidth());
//            setLayoutParams(rl);
//        }
    }


    /**
     * 停止重绘
     *
     * @param repeatDraw
     */
    public void setRepeatDraw(boolean repeatDraw) {
        this.repeatDraw = repeatDraw;
    }

    /**
     * 重新开始绘制
     */
    public void startRepeatDraw() {
        this.repeatDraw = true;
        this.postInvalidate();
    }

    public void startDraw() {
        repeatDraw = true;
        postInvalidateDelayed(drawDuration);
    }

    public void stopDraw(){
        repeatDraw = false;
    }

    public int getDrawDuration() {
        return drawDuration;
    }

    public void setDrawDuration(int drawDuration) {
        this.drawDuration = drawDuration;
    }

    public float getLeftRadius() {
        return leftRadius;
    }

    public void setLeftRadius(float leftRadius) {
        this.leftRadius = leftRadius;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getRightPaintColor() {
        return rightPaintColor;
    }

    public void setRightPaintColor(int rightPaintColor) {
        this.rightPaintColor = rightPaintColor;
    }

    public int getLeftPaintColor() {
        return leftPaintColor;
    }

    public void setLeftPaintColor(int leftPaintColor) {
        this.leftPaintColor = leftPaintColor;
    }
}
