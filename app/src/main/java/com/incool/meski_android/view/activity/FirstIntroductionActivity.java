package com.incool.meski_android.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.incool.meski_android.MainActivity;
import com.incool.meski_android.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2016/1/26.
 */
public class FirstIntroductionActivity extends AppCompatActivity {
    private static final int TIMER = 3000;
    private static Handler handler = new Handler();
    ;
    private Runnable start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.first_introduction_layout);
        start = new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
                overridePendingTransition(R.anim.activity_slide_right_in, R.anim.activity_no_move);
            }
        };
        handler.postDelayed(start, TIMER);
    }

    @Override
    public void onBackPressed() {
        if (handler != null)
            handler.removeCallbacks(start);
        super.onBackPressed();
    }


}
