package jacketjie.common.libray.custom.view.edittext;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import jacketjie.common.libray.R;

/**
 * Created by Administrator on 2016/1/25.
 */
public class SearchEditTextWithImage extends RelativeLayout implements View.OnClickListener, TextWatcher {
    private int leftDefaultImage;
    private int leftEnableImage;
    private int rightDefaultImage;
    private int rightEnableImage;
    private EditText et;
    private ImageView leftImageView;
    private ImageView rightImageView;
    private static final int NO_IMAGE_RESOURCE = -1;

    public SearchEditTextWithImage(Context context) {
        this(context, null);
    }

    public SearchEditTextWithImage(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SearchEditTextWithImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SearchEditTextWithImage(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SearchEditTextWithImage, defStyleAttr, 0);
        leftDefaultImage = ta.getResourceId(R.styleable.SearchEditTextWithImage_leftDefaultImage, NO_IMAGE_RESOURCE);
        leftEnableImage = ta.getResourceId(R.styleable.SearchEditTextWithImage_leftEnableImage, NO_IMAGE_RESOURCE);
        rightDefaultImage = ta.getResourceId(R.styleable.SearchEditTextWithImage_rightDefaultImage, NO_IMAGE_RESOURCE);
        rightEnableImage = ta.getResourceId(R.styleable.SearchEditTextWithImage_rightEnableImage, NO_IMAGE_RESOURCE);
        ta.recycle();
        addImageView();
        addEditView();
    }

    private void addImageView() {
        leftImageView = new ImageView(getContext());
        RelativeLayout.LayoutParams llp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        llp.addRule(RelativeLayout.CENTER_VERTICAL);
        leftImageView.setLayoutParams(llp);

        rightImageView = new ImageView(getContext());
        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rlp.addRule(RelativeLayout.CENTER_VERTICAL);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        rightImageView.setLayoutParams(rlp);

        leftImageView.setId(R.id.id_edit_with_left_image);
        rightImageView.setId(R.id.id_edit_with_right_image);

        addView(leftImageView);
        addView(rightImageView);

        if (leftDefaultImage != NO_IMAGE_RESOURCE) {
            leftImageView.setImageResource(leftDefaultImage);
        }
        if (rightDefaultImage != NO_IMAGE_RESOURCE) {
            rightImageView.setImageResource(rightDefaultImage);
        }
        leftImageView.setOnClickListener(this);
        rightImageView.setOnClickListener(this);
    }

    private void addEditView() {
        et = new EditText(getContext());
        RelativeLayout.LayoutParams elp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        elp.addRule(RelativeLayout.LEFT_OF, R.id.id_edit_with_left_image);
        elp.addRule(RelativeLayout.RIGHT_OF, R.id.id_edit_with_right_image);
        elp.addRule(RelativeLayout.CENTER_VERTICAL);

        et.setLayoutParams(elp);
        et.setId(R.id.id_edit_with_right_image);
        addView(et);
        et.addTextChangedListener(this);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.id_edit_with_left_image) {

        }
        if (id == R.id.id_edit_with_right_image) {

        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (TextUtils.isEmpty(s.toString())) {
            if (leftDefaultImage != NO_IMAGE_RESOURCE) {
                leftImageView.setImageResource(leftDefaultImage);
            }
            if (rightDefaultImage != NO_IMAGE_RESOURCE) {
                rightImageView.setImageResource(rightDefaultImage);
            }
        } else {
            if (leftEnableImage != NO_IMAGE_RESOURCE) {
                leftImageView.setImageResource(leftEnableImage);
            }
            if (rightEnableImage != NO_IMAGE_RESOURCE) {
                rightImageView.setImageResource(rightEnableImage);
            }
        }
    }

    private void clear() {
        et.setText("");
    }
}
