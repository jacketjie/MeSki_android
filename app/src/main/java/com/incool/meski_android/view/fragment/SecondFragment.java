package com.incool.meski_android.view.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.incool.meski_android.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jacketjie.common.libray.adapter.CommonAdapter;
import jacketjie.common.libray.adapter.ViewHolder;
import jacketjie.common.libray.custom.view.animatedlayout.DrawableLinearLayout;
import jacketjie.common.libray.custom.view.pulltorefresh.PullRefreshLayout;


public class SecondFragment extends Fragment {
    private View view;
    private ListView firstListView;
    private DrawableLinearLayout drawableLinearLayout;
    private GridView mSelections;
    private ImageView bg;
    private Button mSelectedOne, mSelectedTwo, mSelectedThree, mSelectedFour;

    private String[] titles = {"优美散文", "短篇小说", "美文日赏", "青春碎碎念", "左岸阅读", "慢文艺", "诗歌精选", "经典语录"};
    private int lastClickPos = -1;
    private List<String> mSelectionList;
    private String[] mDatas1 = {"全部区域", "长宁区", "宝山区", "杨浦区", "全部区域", "虹口区", "浦东新区"};
    private String[] mDatas2 = {"不限价格", "500以下", "500—2000", "2000—5000", "5000以上"};
    private String[] mDatas3 = {"所有类型", "官方推荐", "类型一", "类型二", "类型三", "类型四", "类型五"};
    private String[] mDatas4 = {"推荐排序", "智能排序", "按距离", "按价格", "按习惯"};
    private GridViewAdapter gridViewAdapter;
    private View top1;
    private PullRefreshLayout pullToRefresh;

    public interface OnSecondFragmentTouchEventListener{
        void setFragmentInstance(SecondFragment fragment);
    }
    protected  OnSecondFragmentTouchEventListener onSecondFragmentTouchEventListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if((getActivity() instanceof  OnSecondFragmentTouchEventListener)){
            onSecondFragmentTouchEventListener = (OnSecondFragmentTouchEventListener) getActivity();
            onSecondFragmentTouchEventListener.setFragmentInstance(this);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.second_fragment_layout, container, false);
            initView();
            initDatas();
            setEventListener();
            setZForLOLLIPOP();
        }
        return view;
    }

    /**
     * 初始化
     */
    private void initView() {
        firstListView = (ListView) view.findViewById(R.id.id_firstListView);
        drawableLinearLayout = (DrawableLinearLayout) view.findViewById(R.id.id_drawer_layout);
        mSelections = (GridView) view.findViewById(R.id.id_mSelections);
        bg = (ImageView) view.findViewById(R.id.id_bg);

        top1 = view.findViewById(R.id.id_top1);
        mSelectedOne = (Button) view.findViewById(R.id.id_mSelectedOne);
        mSelectedTwo = (Button) view.findViewById(R.id.id_mSelectedTwo);
        mSelectedThree = (Button) view.findViewById(R.id.id_mSelectedThree);
        mSelectedFour = (Button) view.findViewById(R.id.id_mSelectedFour);
        pullToRefresh = (PullRefreshLayout) view.findViewById(R.id.id_pullToRefresh);

    }

    private void initDatas() {
        // TODO Auto-generated method stub
        mSelectionList = new ArrayList<>();
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_expandable_list_item_1, Arrays.asList(titles));
//        firstListView.setAdapter(adapter);
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < 20 ;i++){
            list.add("s");
        }
        ListViewAdapter listViewAdapter = new ListViewAdapter(getActivity(),list,R.layout.second_list_item);
        firstListView.setAdapter(listViewAdapter);

        gridViewAdapter = new GridViewAdapter(getActivity(), mSelectionList, R.layout.grid_item);
        mSelections.setAdapter(gridViewAdapter);


    }

    private void setEventListener() {
        mSelectedOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAnimByPos(0);
            }
        });
        mSelectedTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAnimByPos(1);
            }
        });
        mSelectedThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAnimByPos(2);
            }
        });
        mSelectedFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAnimByPos(3);
            }
        });

        pullToRefresh.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new AsyncTask<String,Void,String>(){
                    @Override
                    protected String doInBackground(String... params) {
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        super.onPostExecute(s);
                        pullToRefresh.setRefreshing(false);
                    }
                }.execute("");
            }
        });
    }

    private void setZForLOLLIPOP() {
        top1 = view.findViewById(R.id.id_top1);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            top1.setTranslationZ(10);
        }
    }

    /**\
     * 处理点击事件
     * @param ev
     * @return
     */
    public boolean onTouchEvent(MotionEvent ev){
        int eX = (int) ev.getX();
        int eY = (int) ev.getY();
        Rect rect = new Rect();
        drawableLinearLayout.getLocalVisibleRect(rect);
        int[] position = new int[2];
        drawableLinearLayout.getLocationOnScreen(position);
        rect.left = rect.left + position[0];
        rect.right = rect.right + position[0];
        rect.top = rect.top + position[1];
        rect.bottom = rect.bottom + position[1];

        Rect rect1 = new Rect();
        top1.getLocalVisibleRect(rect1);
        int[] position1 = new int[2];
        top1.getLocationOnScreen(position1);
        rect1.left = rect1.left + position1[0];
        rect1.right = rect1.right + position1[0];
        rect1.top = rect1.top + position1[1];
        rect1.bottom = rect1.bottom + position1[1];

//
//        if (drawableLinearLayout.isExpandableStatus() && eY > rect.bottom) {
//            displayOrHidden();
//            return true;
//        }
        if (!rect.contains(eX, eY) &&!rect1.contains(eX, eY) && drawableLinearLayout.isExpandableStatus() ) {
            displayOrHidden();
            return true;
        }

        return false;
    }

    /**
     * 拦截返回事件
     * @return
     */
    public boolean onBackPressed() {
        if (drawableLinearLayout.isExpandableStatus()){
            displayOrHidden();
            return true;
        }
        return false;
    }

    private void setAnimByPos(int pos) {
        if (pullToRefresh.isRefreshing()){
            return;
        }
        if (pos == lastClickPos) {
            displayOrHidden();
        } else {
            if (drawableLinearLayout.isExpandableStatus()) {
                drawableLinearLayout.toggle();
            }
            mSelectionList.clear();
            switch (pos) {
                case 0:
                    mSelectionList.addAll(Arrays.asList(mDatas1));
                    break;
                case 1:
                    mSelectionList.addAll(Arrays.asList(mDatas2));
                    break;
                case 2:
                    mSelectionList.addAll(Arrays.asList(mDatas3));
                    break;
                case 3:
                    mSelectionList.addAll(Arrays.asList(mDatas4));
                    break;
            }
            gridViewAdapter.notifyDataSetChanged();
            drawableLinearLayout.requestLayoutByAnim();

            displayOrHidden();
            lastClickPos = pos;
        }
    }

    private void setBgAnimation(int duration, final int startAlpha, final int endAlpaha) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(startAlpha, endAlpaha);
        valueAnimator.setDuration(duration);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (Integer) animation.getAnimatedValue();
                String alpha = Integer.toHexString(value);
                String color = String.format("#%s000000", alpha.length() < 2 ? String.format("0%s", alpha) : alpha);
//                Log.d("SelectorLayout", "color:" + color);
                bg.setBackgroundColor(Color.parseColor(color));
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                if (startAlpha == 0) {
                    bg.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (endAlpaha == 0) {
                    bg.setVisibility(View.GONE);
                }
            }
        });
        valueAnimator.start();
    }

    /**
     * 设置动画
     */
    private void displayOrHidden() {
        if (!drawableLinearLayout.isExpandableStatus()) {
            setBgAnimation(drawableLinearLayout.getDuration(), 0, 104);
        } else {
            setBgAnimation(drawableLinearLayout.getDuration(), 104, 0);
        }
        drawableLinearLayout.displayOrHidden();
    }


    class GridViewAdapter extends CommonAdapter<String> {

        public GridViewAdapter(Context context, List<String> mDatas, int itemLayoutId) {
            super(context, mDatas, itemLayoutId);
        }

        @Override
        public void convert(ViewHolder helper, String item) {
            helper.setText(R.id.grid_item, item);
        }
    }

    class ListViewAdapter extends CommonAdapter<String>{

        public ListViewAdapter(Context context, List<String> mDatas, int itemLayoutId) {
            super(context, mDatas, itemLayoutId);
        }

        @Override
        public void convert(ViewHolder helper, String item) {

        }
    }
//
//
//	@Override
//	public boolean dispatchTouchEvent(MotionEvent ev) {
//		switch (ev.getAction()) {
//			case MotionEvent.ACTION_DOWN:
//				int eX = (int) ev.getX();
//				int eY = (int) ev.getY();
//				Rect rect = new Rect();
////                selectorLayout.getLocalVisibleRect(rect);
//				drawableLinearLayout.getLocalVisibleRect(rect);
//				int[] position = new int[2];
////                selectorLayout.getLocationOnScreen(position);
//				drawableLinearLayout.getLocationOnScreen(position);
//				rect.left = rect.left + position[0];
//				rect.right = rect.right + position[0];
//				rect.top = rect.top + position[1];
//				rect.bottom = rect.bottom + position[1];
////                if (selectorLayout.isExpand() && eY > rect.bottom) {
////                    displayOrHidden();
////                    return true;
////                }
//				if (drawableLinearLayout.isExpandableStatus() && eY > rect.bottom) {
//					displayOrHidden();
//					return true;
//				}
////                if (!rect.contains(eX, eY) && selectorLayout.isExpand()) {
////                    displayOrHidden();
////                    return true;
////                }
//				break;
//		}
//		return super.dispatchTouchEvent(ev);
//	}
//
//
//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		if (keyCode == KeyEvent.KEYCODE_BACK){
//			if (drawableLinearLayout.isExpandableStatus()){
//				displayOrHidden();
//				return true;
//			}
//		}
//		return super.onKeyDown(keyCode, event);
//	}


}
