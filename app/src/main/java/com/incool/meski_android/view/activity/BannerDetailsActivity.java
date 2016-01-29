package com.incool.meski_android.view.activity;

import android.os.Bundle;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.incool.meski_android.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import jacketjie.common.libray.custom.view.swipeback.SwipeBackActivity;

public class BannerDetailsActivity extends SwipeBackActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.banner_details_layout);
		initView();
		
	}
	private void initView() {
		String url = getIntent().getStringExtra("BANNER_DETAILS");
//		appTab = new AppTab(this,false,false);
		getSupportActionBar().setTitle("广告");
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		ImageView iv = (ImageView) findViewById(R.id.id_banner);
		ImageLoader.getInstance().displayImage(url, iv);
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
			case android.R.id.home:
				onBackPressed();
				break;
		}
		return true;
	}

}
