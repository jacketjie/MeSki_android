package com.incool.meski_android.view.activity;

import android.os.Bundle;
import android.view.MenuItem;

import com.incool.meski_android.R;

import jacketjie.common.libray.custom.view.swipeback.SwipeBackActivity;

/**
 * Created by Administrator on 2016/1/26.
 */
public class EstateDetailActivity extends SwipeBackActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.estate_detail_layout);
        initView();
    }

    private void initView() {
        getSupportActionBar().setTitle(R.string.str_estate);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
