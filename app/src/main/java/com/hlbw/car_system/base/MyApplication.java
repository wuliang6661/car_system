package com.hlbw.car_system.base;

import android.app.Application;
import android.content.Context;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.Utils;

import androidx.multidex.MultiDex;
import cat.ereza.customactivityoncrash.CustomActivityOnCrash;

/**
 * 作者 by wuliang 时间 16/10/26.
 * <p>
 * 程序的application
 */

public class MyApplication extends Application {

    private static final String TAG = "MyApplication";

    public static SPUtils spUtils;

    public static boolean AppInBack = false;  //App 是否在后台

    public static String token;


    @Override
    public void onCreate() {
        super.onCreate();
        CustomActivityOnCrash.install(this);
        /***初始化工具类*/
        Utils.init(this);
        spUtils = SPUtils.getInstance(TAG);

        registerActivityLifecycleCallbacks(new AppLifecycleHandler());
    }



    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
