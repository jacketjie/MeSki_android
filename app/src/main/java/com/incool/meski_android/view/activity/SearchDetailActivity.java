package com.incool.meski_android.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.incool.meski_android.MainActivity;
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
                ToastUtils.showShort(getApplicationContext(), "输入搜索内容。。。");
            }

            @Override
            public void onDefaultRightClick() {
                ToastUtils.showShort(getApplicationContext(), "嘿嘿，语音功能不会做。。。");
            }

            @Override
            public void onEnableLeftClick() {
                ToastUtils.showShort(getApplicationContext(), "输入搜索内容，马上进行搜索。。。");
            }

            @Override
            public void onEnableRightClick() {
                searchEdit.clear();
            }
        });
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = searchEdit.getText().toString();
                if (TextUtils.isEmpty(result)) {

                } else {
                    Intent resultSearchIntent = getIntent();
                    resultSearchIntent.putExtra(MainActivity.RESPONSE_SEARCH_RESULT, result);
                    setResult(RESULT_OK, resultSearchIntent);
                    onBackPressed();
                }
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
