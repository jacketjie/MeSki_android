package jacketjie.common.libray.custom.view.dialog;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by Administrator on 2015/12/30.
 */
public class AsTimeDialogLayout extends FrameLayout {
    private AsTimeDialogView dialogView;

    public AsTimeDialogLayout(Context context) {
        this(context, null);
    }

    public AsTimeDialogLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AsTimeDialogLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        dialogView = new AsTimeDialogView(context);
        dialogView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addView(dialogView);
    }

    public void stop() {
       setVisibility(View.GONE);
        if (dialogView != null){
            dialogView.stopDraw();
        }
    }

    public void start(){
        setVisibility(View.VISIBLE);
        if (dialogView != null){
            dialogView.startDraw();
        }
    }

    public AsTimeDialogView getDialog(){
        return dialogView;
    }
}
