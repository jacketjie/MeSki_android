package jacketjie.common.libray.custom.view.edittext;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.InputFilter;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.AttributeSet;
import android.widget.EditText;

import java.io.UnsupportedEncodingException;

/**
 * 自定义限制文字长度
 * Created by Administrator on 2016/test_1/11.
 */
public class EditTestWithFilter extends EditText {
    /**
     * 最大长度
     */
    private int maxByteLength = 20;
    /**
     * 编码格式
     */
    private String encoding = "GBK";

    public EditTestWithFilter(Context context) {
        super(context);
        init();
    }

    public EditTestWithFilter(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EditTestWithFilter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public EditTestWithFilter(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        setFilters(new InputFilter[]{inputFilter});
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public int getMaxByteLength() {
        return maxByteLength;
    }

    public void setMaxByteLength(int maxByteLength) {
        this.maxByteLength = maxByteLength;
    }


    /**
     * input输入过滤
     */
    private InputFilter inputFilter = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end,
                                   Spanned dest, int dstart, int dend) {
            try {
                int len = 0;
                boolean more = false;
                do {
                    SpannableStringBuilder builder =
                            new SpannableStringBuilder(dest).replace(dstart, dend, source.subSequence(start, end));
                    len = builder.toString().getBytes(encoding).length;
                    more = len > maxByteLength;
                    if (more) {
                        end--;
                        source = source.subSequence(start, end);
                    }
                } while (more);
                return source;
            } catch (UnsupportedEncodingException e) {
                return "Exception";
            }
        }
    };
}
