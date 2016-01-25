package com.incool.meski_android.view.fragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.incool.meski_android.R;
import com.nostra13.universalimageloader.core.download.ImageDownloader;

import jacketjie.common.libray.adapter.CommonAdapter;
import jacketjie.common.libray.adapter.ViewHolder;
import jacketjie.common.libray.custom.view.viewpager.ScrollBanner;
import jacketjie.common.libray.others.ToastUtils;


public class FirstFragment extends Fragment{
	private View view;
	private ListView detailListView;
	private String[]mDatas = {"我是来度假的","我是来滑雪、雪橇的","我是来观光的","我对房产感兴趣"};
	private List<String> urls;
	private ScrollBanner banner;
	private SwipeRefreshLayout refreshLayout;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		if (view == null){
			view = inflater.inflate(R.layout.first_fragment_layout, container,false);
			initView(view);
			initDatas();
		}
		return view;
	}

	@Override
	public void onPause() {
		super.onPause();
		banner.stopAutoScroll();
	}

	@Override
	public void onResume() {
		super.onResume();
		banner.startAutoScroll();
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

		detailListView = (ListView) view.findViewById(R.id.id_detailListView);

		refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.id_firstRefresh);
		refreshLayout.setColorSchemeColors(R.color.colorPrimary);
	}
	private void initDatas() {
		DetailAdapter mAdapter = new DetailAdapter(getActivity(), Arrays.asList(mDatas),R.layout.first_detail_list_item);
		detailListView.setAdapter(mAdapter);
	}

	class DetailAdapter extends CommonAdapter<String>{
		private Drawable drawableLeft;
		private Drawable drawableRight;
		public DetailAdapter(Context context, List<String> mDatas, int itemLayoutId) {
			super(context, mDatas, itemLayoutId);
			drawableLeft = getActivity().getResources().getDrawable(R.drawable.icon_planet);
			drawableRight = getActivity().getResources().getDrawable(R.drawable.icon_right_arrow);
		}

		@Override
		public void convert(ViewHolder helper, String item) {
			TextView detailItem = helper.getView(R.id.id_detailItem);
			detailItem.setText(item);
			drawableLeft.setBounds(0, 0, drawableLeft.getMinimumWidth(), drawableLeft.getMinimumHeight());
			drawableRight.setBounds(0,0,drawableRight.getMinimumWidth(),drawableRight.getMinimumHeight());
			detailItem.setCompoundDrawables(drawableLeft,null,drawableRight,null);
		}
	}

}
