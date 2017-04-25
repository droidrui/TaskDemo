package com.droidrui.taskdemo;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by lingrui on 2017/3/19.
 */

public class App extends Application {

    private static App sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = (App) getApplicationContext();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }

    public static App getContext() {
        return sContext;
    }
}
