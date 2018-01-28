package com.android.xlogdemo.activity;

import android.support.v4.app.Fragment;

import com.android.xlogdemo.fragment.MainFragment;

/**
 * Created by handsomezhou on 2018/1/28.
 */

public class MainActivity extends BaseSingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new MainFragment();
    }

    @Override
    protected boolean isRealTimeLoadFragment() {
        return false;
    }
}
