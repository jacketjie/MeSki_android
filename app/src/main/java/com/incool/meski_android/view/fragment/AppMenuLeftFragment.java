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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.incool.meski_android.MainActivity;
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
    private String menuTitles[] = {"主页", "密苑商城", "点餐吃饭", "密苑SPA", "滑雪教练", "随身摄影师", "雪具租赁", "精彩活动", "视频专区", "雪道状态"};
    private int[] menuLeftRes = {R.drawable.leftbar_icon_home,
            R.drawable.leftbar_icon_mall, R.drawable.leftbar_icon_eat,
            R.drawable.leftbar_icon_spa, R.drawable.leftbar_icon_coach,
            R.drawable.leftbar_icon_camera, R.drawable.leftbar_icon_rent,
            R.drawable.leftbar_icon_events, R.drawable.leftbar_icon_video,
            R.drawable.leftbar_icon_skitrack,};
    private int[] iconRes;
    private String[] infos;
    private DisplayImageOptions options;
    private TextView personInfoName;
    private Handler handler;
    private TextView menuOne, menuTwo;
    private ListView menuListView;
    private ListViewAdapter adapter;
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

    private void initView() {
        menuListView = (ListView) view.findViewById(R.id.id_menuListView);

        List<Menus> beans = new ArrayList<Menus>();
        for (int i = 0; i < menuTitles.length; i++) {
            Menus bean = new Menus(menuLeftRes[i],
                    R.drawable.leftbar_icon_more, menuTitles[i]);
            beans.add(bean);
        }
        adapter = new ListViewAdapter(getActivity(), beans, R.layout.first_detail_list_item);
        menuListView.setAdapter(adapter);


        menuOne = (TextView) view.findViewById(R.id.id_menuOne);
        menuTwo = (TextView) view.findViewById(R.id.id_menuTwo);
    }


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
            setEventListener();
        } else {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
        }
        return view;
    }

    private void initDatas() {


    }

    private void setEventListener() {
        menuOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        menuTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        menuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                handleClick(position);
            }
        });
    }

    /**
     * 处理点击事件
     * @param position
     */
    private void handleClick(int position) {
        if (getActivity() instanceof MainActivity) {
            MainActivity parent = (MainActivity) getActivity();
            parent.handleMenuSelection(position);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
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


    /**
     * 列表的适配器
     *
     * @author Administrator
     */
    class ListViewAdapter extends CommonAdapter<Menus> {
        private List<Menus> menus;

        public ListViewAdapter(Context context, List<Menus> mDatas,
                               int itemLayoutId) {
            super(context, mDatas, itemLayoutId);
        }

        @Override
        public void convert(ViewHolder helper, Menus item) {
            TextView tv = helper.getView(R.id.id_detailItem);
            Drawable left = getResources().getDrawable(item.getLeftRes());
            Drawable right = getResources().getDrawable(item.getRightRes());
            left.setBounds(0, 0, left.getMinimumWidth(),
                    left.getMinimumHeight());
            right.setBounds(0, 0, right.getMinimumWidth(),
                    right.getMinimumHeight());
            tv.setCompoundDrawables(left, null, right, null);
            tv.setText(item.getTitle());
        }
    }


    static class Menus {
        private int leftRes;
        private int rightRes;
        private String title;

        public Menus(int leftRes, int rightRes, String title) {
            super();
            this.leftRes = leftRes;
            this.rightRes = rightRes;
            this.title = title;
        }

        public int getLeftRes() {
            return leftRes;
        }

        public void setLeftRes(int leftRes) {
            this.leftRes = leftRes;
        }

        public int getRightRes() {
            return rightRes;
        }

        public void setRightRes(int rightRes) {
            this.rightRes = rightRes;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

    }

}
