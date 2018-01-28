package com.android.xlog;

import android.app.Application;

import org.litepal.LitePal;

/**
 * Created by handsomezhou on 2018/1/27.
 */

public class XLogApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();

        LitePal.initialize(this);

    }
}
