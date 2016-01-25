package com.incool.meski_android;

import android.app.Application;
import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

/**
 * Created by Administrator on 2016/1/21.
 */
public class MeSkiApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
//        DisplayImageOptions options = new DisplayImageOptions.Builder()
//                .showImageOnLoading(R.drawable.ic_stub)
//                .showImageForEmptyUri(R.drawable.plugin_camera_no_pictures)
//                .showImageOnFail(R.drawable.plugin_camera_no_pictures)
//                .imageScaleType(ImageScaleType.EXACTLY)
//                .cacheInMemory(false)// 设置下载的图片是否缓存在内存中
//                .resetViewBeforeLoading(true)//设置图片在下载前是否重置，复位
//                .displayer(new FadeInBitmapDisplayer(100))//设置图片渐显的时间
//                .cacheOnDisk(true)
//                .considerExifParams(true)
//                .bitmapConfig(Bitmap.Config.RGB_565)
//                .build();
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(getApplicationContext()));
    }

}
