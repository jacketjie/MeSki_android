package jacketjie.common.libray.custom.view.viewpager.utils;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

import jacketjie.common.libray.R;
import jacketjie.common.libray.custom.view.viewpager.ScrollBanner;

/**
 * Created by Administrator on 2016/1/18.
 */
public class ImageFragment extends Fragment {
    private String resId;
    private Object binderData;
    private ScrollBanner.OnPageClickListener onPageClickListener;

    public <T> void setOnPageClickListener(ScrollBanner.OnPageClickListener<T> onPageClickListener) {
        this.onPageClickListener = onPageClickListener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_pager_item, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.id_image);
        try {
            resId = getArguments().getString("ImageRes");
            binderData = getArguments().getSerializable("BinderData");
            if (binderData == null) {
                binderData = getArguments().getParcelable("BinderData");
            }
            ImageLoader.getInstance().displayImage(resId, imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                ToastUtils.showShort(getContext(), resId);
//                Log.e("ImageFragment", "binderData:" + binderData.toString());
                    if (onPageClickListener != null) {
                        onPageClickListener.onPageCilck(binderData);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

}
