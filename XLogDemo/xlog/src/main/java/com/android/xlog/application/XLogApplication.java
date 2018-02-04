package com.android.xlog.application;

import android.app.Application;
import android.content.Context;

import org.litepal.LitePal;
import org.litepal.exceptions.GlobalException;

/**
 * Created by handsomezhou on 2018/1/27.
 */

public class XLogApplication extends Application{
    /**
     * Global application context.
     */
    public static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static Context getContext() {
        return sContext;
    }

    public static void setContext(Context context) {
        sContext = context;
    }
}
