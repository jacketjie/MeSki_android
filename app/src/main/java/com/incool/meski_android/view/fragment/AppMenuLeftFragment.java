package com.incool.meski_android.view.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.incool.meski_android.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jacketjie.common.libray.adapter.CommonAdapter;
import jacketjie.common.libray.adapter.ViewHolder;
import jacketjie.common.libray.others.DensityUtils;


public class AppMenuLeftFragment extends Fragment implements View.OnClickListener {
    private View view;
    private String menuTitles[] = {"密苑商城", "点餐吃饭", "密苑SPA", "滑雪教练", "随身摄影师", "雪具租赁", "精彩活动", "视频专区", "雪道状态"};

    private int[] iconRes;
    private String[] infos;
    private DisplayImageOptions options;
    private TextView personInfoName;
    private Handler handler;
    private TextView menuOne, menuTwo;

    /**
     * 更新receiver
     */
    private BroadcastReceiver updateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
//			if (MyConstants.UPDATE_USER_RECEIVER_ACTION.equals(intent.getAction())){
//				selectActiveUser();
//			}
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//		IntentFilter filter = new IntentFilter(MyConstants.UPDATE_USER_RECEIVER_ACTION);
//		updateUserReceiver = new UpdateUserReceiver();
//		getActivity().registerReceiver(updateUserReceiver, filter);
        handler = new Handler();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.menu_left_fragment, container,
                    false);
            initView();
            initDatas();
        } else {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initDatas() {

        Drawable d = getResources().getDrawable(R.drawable.test_menu_0);
        Drawable d1 = getResources().getDrawable(R.drawable.test_menu_1);

        int height = DensityUtils.dp2px(getActivity(), 40);
        int imageWidth = d.getMinimumWidth();
        int imageHeight = d.getMinimumHeight();
        float scale = height*1.0f / imageHeight;
        int width = (int) (imageWidth * scale);
        menuOne.setPadding(DensityUtils.dp2px(getActivity(),5),0,0,0);
        d.setBounds(0, 0, width, height);
        menuOne.setCompoundDrawables(d, null, null, null);

        imageWidth = d1.getMinimumWidth();
        imageHeight = d1.getMinimumHeight();
        scale = height*1.0f / imageHeight;
        width = (int) (scale * imageWidth);

        menuTwo.setPadding(DensityUtils.dp2px(getActivity(),5),0,0,0);
        d1.setBounds(0, 0, width, height);
        menuTwo.setCompoundDrawables(d1,null,null,null);

    }

    private void initView() {
        ListView menuListView = (ListView) view.findViewById(R.id.id_menuListView);

        List<ImageBean> beans = new ArrayList<>();
        for (int i=0;i < menuTitles.length;i++){
            ImageBean bean = new ImageBean();
            bean.setTitle(menuTitles[i]);
            bean.setImageRes(R.drawable.test_menu);
            beans.add(bean);
        }
        ListViewAdapter adapter = new ListViewAdapter(getActivity(),beans,R.layout.first_detail_list_item);
        menuListView.setAdapter(adapter);


        menuOne = (TextView) view.findViewById(R.id.id_menuOne);
        menuTwo = (TextView) view.findViewById(R.id.id_menuTwo);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case 0:
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//		getActivity().unregisterReceiver(updateUserReceiver);
    }


    class ListViewAdapter extends CommonAdapter<ImageBean>{

        public ListViewAdapter(Context context, List<ImageBean> mDatas, int itemLayoutId) {
            super(context, mDatas, itemLayoutId);
        }

        @Override
        public void convert(ViewHolder helper, ImageBean item) {
            TextView tv = helper.getView(R.id.id_detailItem);
            tv.setText(item.getTitle());
            Drawable d = getResources().getDrawable(item.getImageRes());
            int height = DensityUtils.dp2px(getActivity(),36);
            int imageWidth = d.getMinimumWidth();
            int imageHeight = d.getMinimumHeight();
            float scale = height*1.0f / imageHeight;
            tv.setPadding(DensityUtils.dp2px(getActivity(),30),0,DensityUtils.dp2px(getActivity(),20),0);
            d.setBounds(0, 0, (int) (imageWidth * scale), height);
            tv.setCompoundDrawables(d, null, null, null);
            tv.setTextColor(Color.BLACK);
        }
    }



    class ImageBean implements Serializable{
        private String title;
        private int imageRes;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getImageRes() {
            return imageRes;
        }

        public void setImageRes(int imageRes) {
            this.imageRes = imageRes;
        }
    }

}
