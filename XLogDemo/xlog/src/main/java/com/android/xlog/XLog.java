package com.android.xlog;

import android.content.Context;

import com.android.xlog.application.XLogApplication;

import org.litepal.LitePal;

/**
 * Created by handsomezhou on 2018/2/4.
 */

public class XLog {
    /**
     * 初始化Xlog
     * @param context
     */
    public static void initialize(Context context) {
        XLogApplication.sContext = context;

        //初始化数据库
        LitePal.initialize(context);
    }
}
