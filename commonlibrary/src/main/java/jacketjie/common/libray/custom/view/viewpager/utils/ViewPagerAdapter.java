package jacketjie.common.libray.custom.view.viewpager.utils;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

import java.io.Serializable;
import java.util.List;

import jacketjie.common.libray.custom.view.viewpager.ScrollBanner;

/**
 * Created by Administrator on 2016/test_1/18.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {
    private List<String> imageRes;
    private boolean canCycle = true;
    private final int FAKE_BANNER_SIZE = 100;
    private ViewPager mViewPager;
    private List binderData;

    private ScrollBanner.OnPageClickListener onPageClickListener;

    public<T> void setOnPageClickListener(ScrollBanner.OnPageClickListener<T> onPageClickListener) {
        this.onPageClickListener = onPageClickListener;
    }

    public ViewPagerAdapter(FragmentManager fm, List<String> imageRes) {
        super(fm);
        this.imageRes = imageRes;
        this.binderData = imageRes;
    }

    public ViewPagerAdapter(FragmentManager fm, List<String> imageRes, boolean canCycle) {
        super(fm);
        this.canCycle = canCycle;
        this.imageRes = imageRes;
        this.binderData = imageRes;
    }

    public ViewPagerAdapter(FragmentManager fm, List binderData, List<String> imageRes, boolean canCycle) {
        super(fm);
        this.binderData = binderData;
        this.imageRes = imageRes;
        this.canCycle = canCycle;
        this.binderData = binderData;
    }

    @Override
    public int getCount() {
        return canCycle ? FAKE_BANNER_SIZE : (imageRes == null ? 0 : imageRes.size());
    }

    @Override
    public Fragment getItem(final int position) {
        ImageFragment fragment = null;
        if (imageRes != null && imageRes.size() > 0) {
            fragment = new ImageFragment();
            Bundle bundle = new Bundle();
            bundle.putString("ImageRes", imageRes.get(position % imageRes.size()));
            Object binder = binderData.get(position % imageRes.size());
            if (binder instanceof Serializable) {
                Serializable data = (Serializable) binder;
                bundle.putSerializable("BinderData", data);
            }
            if (binder instanceof Parcelable) {
                Parcelable data = (Parcelable) binder;
                bundle.putParcelable("BinderData", data);
            }
            fragment.setArguments(bundle);
            fragment.setOnPageClickListener(onPageClickListener);
        }
        return fragment;
    }

    public void setTarget(ViewPager mViewPager) {
        this.mViewPager = mViewPager;
    }

    public boolean isCanCycle() {
        return canCycle;
    }

    public void setCanCycle(boolean canCycle) {
        this.canCycle = canCycle;
    }

    @Override
    public void finishUpdate(ViewGroup container) {
        super.finishUpdate(container);
        if (mViewPager != null && canCycle) {
            int pos = mViewPager.getCurrentItem();
            if (pos == 0) {
                mViewPager.setCurrentItem(imageRes.size(), false);
            } else if (pos == FAKE_BANNER_SIZE - 1) {
                mViewPager.setCurrentItem(imageRes.size() - 1, false);
            }
        }
    }
}
