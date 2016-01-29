package com.incool.meski_android.view.fragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.incool.meski_android.MainActivity;
import com.incool.meski_android.R;
import com.incool.meski_android.view.activity.BannerDetailsActivity;
import com.nostra13.universalimageloader.core.download.ImageDownloader;

import jacketjie.common.libray.adapter.CommonAdapter;
import jacketjie.common.libray.adapter.ViewHolder;
import jacketjie.common.libray.custom.view.viewpager.ScrollBanner;
import jacketjie.common.libray.others.ToastUtils;


public class FirstFragment extends Fragment {
    private View view;
    private GridView detailListView;
    private String[] mDatas = {"我是来度假的", "我是来滑雪、雪橇的", "我是来观光的", "我对房产感兴趣"};
    private List<String> urls;
    private ScrollBanner banner;
    private SwipeRefreshLayout refreshLayout;
    private FrameLayout btn1, btn2, btn3, btn4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.first_fragment_layout, container, false);
            initView(view);
            initDatas();
            setEventListener();
        }
        return view;
    }

    /**
     * 初始化
     */
    private void initView(View view) {
        urls = new ArrayList<String>();
        urls.add(ImageDownloader.Scheme.ASSETS.wrap("4.jpg"));
        urls.add(ImageDownloader.Scheme.ASSETS.wrap("5.jpg"));
        urls.add(ImageDownloader.Scheme.ASSETS.wrap("6.jpg"));

        banner = (ScrollBanner) view.findViewById(R.id.id_banner);
        banner.setBanner(getChildFragmentManager(), urls, urls);

        banner.setOnPageClickListener(new ScrollBanner.OnPageClickListener<String>() {
            @Override
            public void onPageCilck(String data) {
                ToastUtils.showShort(getActivity(), data);
            }
        });

        detailListView = (GridView) view.findViewById(R.id.id_detailListView);

        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.id_firstRefresh);
        refreshLayout.setColorSchemeColors(R.color.colorPrimary);
        //解决ViewPager与SwipeRefreshLayout滑动冲突
        banner.getViewPager().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        refreshLayout.setEnabled(false);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        refreshLayout.setEnabled(true);
                        break;

                }
                return false;
            }
        });
    }

    private void initDatas() {
        Integer[] itemRes = {R.drawable.home_loved_vacation, R.drawable.home_loved_ski, R.drawable.home_loved_travel, R.drawable.home_loved_house};
        DetailAdapter mAdapter = new DetailAdapter(getActivity(), Arrays.asList(itemRes), R.layout.first_grid_item);
        detailListView.setAdapter(mAdapter);
    }

    private void setEventListener() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new AsyncTask<String, Void, String>() {
                    @Override
                    protected String doInBackground(String... params) {
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        super.onPostExecute(s);
                        refreshLayout.setRefreshing(false);
                    }
                }.execute("");
            }
        });

        banner.setOnPageClickListener(new ScrollBanner.OnPageClickListener<String>() {
            @Override
            public void onPageCilck(String data) {
//	                ToastUtils.showShort(getActivity(), data);
                Intent intent = new Intent(getActivity(), BannerDetailsActivity.class);
                intent.putExtra("BANNER_DETAILS", data);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.activity_slide_right_in, R.anim.activity_no_move);
            }
        });
    }

    public void startScrollBanner(){
        if (banner != null){
            banner.startAutoScroll();
        }
    }
    public void stopScrollBanner(){
        if (banner != null){
            banner.stopAutoScroll();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("onPause","onPause");
        banner.stopAutoScroll();
    }

    @Override
    public void onResume() {
        super.onResume();
        banner.startAutoScroll();
    }


    class DetailAdapter extends CommonAdapter<Integer> {
        private Drawable drawableLeft;
        private Drawable drawableRight;

        public DetailAdapter(Context context, List<Integer> mDatas, int itemLayoutId) {
            super(context, mDatas, itemLayoutId);
            drawableLeft = getActivity().getResources().getDrawable(R.drawable.icon_planet);
            drawableRight = getActivity().getResources().getDrawable(R.drawable.icon_right_arrow);
        }

        @Override
        public void convert(ViewHolder helper, Integer item) {
//            TextView detailItem = helper.getView(R.id.id_detailItem);
//            detailItem.setText(item);
//            drawableLeft.setBounds(0, 0, drawableLeft.getMinimumWidth(), drawableLeft.getMinimumHeight());
//            drawableRight.setBounds(0, 0, drawableRight.getMinimumWidth(), drawableRight.getMinimumHeight());
//            detailItem.setCompoundDrawables(drawableLeft, null, drawableRight, null);
            helper.setImageResource(R.id.id_firstItem, item);

        }
    }

}
