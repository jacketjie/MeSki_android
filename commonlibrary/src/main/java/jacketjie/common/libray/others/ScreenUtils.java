package jacketjie.common.libray.others;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

/**
 * 获得屏幕相关的辅助类
 *
 * @author zhy
 */
public class ScreenUtils {
    private ScreenUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 获得屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    public static int getStatusHeight(Context context) {

        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * 获取当前屏幕截图，包含状态栏
     *
     * @param activity
     * @return
     */
    public static Bitmap snapShotWithStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
        view.destroyDrawingCache();
        return bp;

    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     *
     * @param activity
     * @return
     */
    public static Bitmap snapShotWithoutStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;

        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height
                - statusBarHeight);
        view.destroyDrawingCache();
        return bp;

    }

    /**
     * 测量
     *
     * @param view
     * @return
     */
    public static int[] measure(View view) {
        int realWidth = 0;
        int realHeight = 0;
        if (view instanceof ViewGroup) {
            return measureViewGroup((ViewGroup) view);
        } else {
            return measureView(view);
        }
    }

    /**
     * 测量ViewGroup
     *
     * @param group
     * @return
     */
    public static int[] measureViewGroup(ViewGroup group) {
        int sumWidth = 0, sumHeight = 0;
        for (int i = 0; i < group.getChildCount(); i++) {
            View child = group.getChildAt(i);
            if (child instanceof ViewGroup) {
                sumWidth += measureViewGroup((ViewGroup) child)[0];
                sumHeight += measureViewGroup((ViewGroup) child)[1];
            } else {
                sumWidth += measureView(child)[0];
                sumHeight += measureView(child)[1];
            }
        }
        return new int[]{sumWidth, sumHeight};
    }

    /**
     * 策略View
     *
     * @param view
     * @return
     */
    public static int[] measureView(View view) {
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        int width = view.getMeasuredWidth();
        int height = view.getMeasuredHeight();
        return new int[]{width, height};
    }


    public static ViewSize measureSize(View view) {
        int[] size = measure(view);
        ViewSize viewSize = new ViewSize();
        viewSize.setWidth(size[0]);
        viewSize.setHeight(size[1]);
        Log.d("BadgeView", viewSize.toString());
        return viewSize;
    }

    /**
     * 保存View尺寸
     */
    public static class ViewSize implements Parcelable {
        private int width;
        private int height;

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.width);
            dest.writeInt(this.height);
        }

        public ViewSize() {
        }

        protected ViewSize(Parcel in) {
            this.width = in.readInt();
            this.height = in.readInt();
        }

        public static final Creator<ViewSize> CREATOR = new Creator<ViewSize>() {
            public ViewSize createFromParcel(Parcel source) {
                return new ViewSize(source);
            }

            public ViewSize[] newArray(int size) {
                return new ViewSize[size];
            }
        };

        @Override
        public String toString() {
            return "ViewSize{" +
                    "height=" + height +
                    ", width=" + width +
                    '}';
        }
    }
}
