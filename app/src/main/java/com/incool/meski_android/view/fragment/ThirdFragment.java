package com.incool.meski_android.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.incool.meski_android.R;


public class ThirdFragment extends Fragment{
	private View view;
	private ListView firstListView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		if (view == null){
			view = inflater.inflate(R.layout.third_fragment_layout, container,false);
			initView(view);
			initDatas();
		}
		return view;
	}




	/**
	 * 初始化
	 */
	private void initView(View view) {
		firstListView = (ListView) view.findViewById(R.id.id_firstListView);
	}
	private void initDatas() {
		// TODO Auto-generated method stub
		
	}

}
