package com.android.xlogdemo.activity;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.android.xlogdemo.fragment.LogMsgsFragment;
import com.android.xlogdemo.util.ActivityUtil;

/**
 * Created by handsomezhou on 2018/1/31.
 */

public class LogMsgsActivity extends BaseSingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new LogMsgsFragment();
    }

    @Override
    protected boolean isRealTimeLoadFragment() {
        return false;
    }

    public static void launch(Context context){
        ActivityUtil.launch(context, LogMsgsActivity.class);
        return;
    }
}
