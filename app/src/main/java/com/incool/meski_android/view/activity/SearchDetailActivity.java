package com.incool.meski_android.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.incool.meski_android.R;

import jacketjie.common.libray.custom.view.edittext.EditTextWithDrawable;
import jacketjie.common.libray.custom.view.edittext.OnEditTextDrawableClickListener;
import jacketjie.common.libray.custom.view.swipeback.SwipeBackActivity;
import jacketjie.common.libray.others.ToastUtils;

/**
 * Created by Administrator on 2016/1/22.
 */
public class SearchDetailActivity extends AppCompatActivity {
    private ImageView backBtn;
    private EditTextWithDrawable searchEdit;
    private TextView searchBtn;
    private int currentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_details_activity);
        getSupportActionBar().hide();
        currentId = getIntent().getIntExtra("currentId", 0);
        initViews();
        setEventListener();
    }

    private void initViews() {
        backBtn = (ImageView) findViewById(R.id.action_back);
        searchEdit = (EditTextWithDrawable) findViewById(R.id.id_searchEdit);
        searchBtn = (TextView) findViewById(R.id.action_search);


    }

    private void setEventListener() {
        searchEdit.setOnEditTextDrawableClickListener(new OnEditTextDrawableClickListener() {
            @Override
            public void onDefaultLeftClick() {

            }

            @Override
            public void onDefaultRightClick() {
                ToastUtils.showShort(getApplicationContext(),"嘿嘿，语音功能不会做。。。");
            }

            @Override
            public void onEnableLeftClick() {

            }

            @Override
            public void onEnableRightClick() {
                searchEdit.clear();
            }
        });
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort(getApplicationContext(), searchEdit.getText().toString());
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
