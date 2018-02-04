package com.android.xlogdemo;

import android.app.Application;

import com.android.xlog.XLog;

/**
 * Created by handsomezhou on 2018/2/4.
 */

public class XLogDemoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        /**
         * 初始化Xlog
         */
        XLog.initialize(this);
    }
}
