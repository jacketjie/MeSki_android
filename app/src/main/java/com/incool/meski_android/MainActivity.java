package com.incool.meski_android;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.incool.meski_android.view.activity.SearchDetailActivity;
import com.incool.meski_android.view.fragment.AppMenuLeftFragment;
import com.incool.meski_android.view.fragment.FirstFragment;
import com.incool.meski_android.view.fragment.ForthFragment;
import com.incool.meski_android.view.fragment.SecondFragment;
import com.incool.meski_android.view.fragment.ThirdFragment;

import jacketjie.common.libray.custom.view.tabhost.AnimFragmentTabHost;

public class MainActivity extends AppCompatActivity implements SecondFragment.OnSecondFragmentTouchEventListener{
    private static final int DEFAULT_TAB_COLOR = Color.parseColor("#666666");

    private static int SELECTED_TAB_COLOR = Color.RED;
    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawerLayout;
    private RecyclerView recyclerView;
    private NavigationView navigationView;
    private AnimFragmentTabHost tabHost;
    private String[] TAB_TILES;
    private Class[] fragments = {FirstFragment.class, SecondFragment.class,
            ThirdFragment.class, ForthFragment.class};
    private int[] tabImageIds = {R.drawable.ic_account_box_black_24dp, R.drawable.ic_dashboard, R.drawable.ic_headset, R.drawable.ic_settings_black_24dp};
    private int[] tabImageSelectedIds = {R.drawable.ic_account_box_black_24dp, R.drawable.ic_dashboard, R.drawable.ic_headset, R.drawable.ic_settings_black_24dp};
    ;
    protected int currentId;

    private SecondFragment secondFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initTabHost();
        setTabSelect(currentId);
    }

    /**
     * 初始化
     */
    private void initView() {
        TAB_TILES = getResources().getStringArray(R.array.main_tab_titles);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(TAB_TILES[0]);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.id_menu, new AppMenuLeftFragment());
        transaction.commit();

        drawerLayout = (DrawerLayout) findViewById(R.id.id_drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.action_open, R.string.action_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                Log.d("onDrawerSlide", "slideOffset=" + slideOffset);

            }
        };
        toggle.syncState();
        drawerLayout.setDrawerListener(toggle);
    }

    /**
     * 初始化TabHost
     */
    private void initTabHost() {

        tabHost = (AnimFragmentTabHost) findViewById(R.id.id_tabhost);
        tabHost.setup(this, getSupportFragmentManager(),
                R.id.realtabcontent);
        for (int i = 0; i < TAB_TILES.length; i++) {
            TabHost.TabSpec tab = tabHost.newTabSpec(TAB_TILES[i]).setIndicator(
                    getTabView(i));
            tabHost.addTab(tab, fragments[i], null);
        }

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {

            @Override
            public void onTabChanged(String tabId) {
                currentId = tabHost.getCurrentTab();
                setTabSelect(currentId);
            }
        });
        tabHost.getTabWidget().setDividerDrawable(null);
        tabHost.getTabWidget().setBackgroundResource(R.drawable.main_tab_bg);
    }

    /**
     * 设置Tabhost
     *
     * @param index
     */
    protected void setTabSelect(int index) {
        for (int i = 0; i < TAB_TILES.length; i++) {
            View tabView = tabHost.getTabWidget().getChildAt(i);
            if (index == i) {
                getSupportActionBar().setTitle(TAB_TILES[i]);
                ImageView tab = (ImageView) tabView
                        .findViewById(R.id.id_main_tab_icon);
                TextView tabText = (TextView) tabView.findViewById(R.id.id_main_tab_text);
                tab.setImageResource(tabImageSelectedIds[i]);
                tabText.setTextColor(SELECTED_TAB_COLOR);
            } else {
                ImageView tab = (ImageView) tabView
                        .findViewById(R.id.id_main_tab_icon);
                TextView tabText = (TextView) tabView.findViewById(R.id.id_main_tab_text);
                tab.setImageResource(tabImageIds[i]);
                tabText.setTextColor(DEFAULT_TAB_COLOR);
            }

        }
    }

    @Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
			case MotionEvent.ACTION_DOWN:
                if (secondFragment!=null){
                    if (secondFragment.onTouchEvent(ev)){
                        return true;
                    }
                }
                break;
		}
		return super.dispatchTouchEvent(ev);
	}


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(Gravity.LEFT)){
            drawerLayout.closeDrawer(Gravity.LEFT);
            return;
        }
        if (secondFragment!=null){
            if(secondFragment.onBackPressed()){
                return;
            }
        }
        super.onBackPressed();
    }

    /**
     * 设置Tab样式
     *
     * @param i
     * @return
     */
    protected View getTabView(int i) {
        View tabView = LayoutInflater.from(this).inflate(R.layout.main_tab_layout, null);
        ImageView tab = (ImageView) tabView
                .findViewById(R.id.id_main_tab_icon);
        TextView tabText = (TextView) tabView.findViewById(R.id.id_main_tab_text);
        tab.setImageResource(tabImageIds[i]);
        tabText.setText(TAB_TILES[i]);
        tabText.setGravity(Gravity.CENTER);
        return tabView;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_pager_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            Intent intent = new Intent(this, SearchDetailActivity.class);
            intent.putExtra("currentId",currentId);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setFragmentInstance(SecondFragment fragment) {
        this.secondFragment = fragment;
    }


}
