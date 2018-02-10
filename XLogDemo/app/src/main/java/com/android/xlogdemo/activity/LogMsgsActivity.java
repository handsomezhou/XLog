package com.android.xlogdemo.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.android.xlogdemo.fragment.LogMsgsFragment;
import com.android.xlogdemo.model.LogMsgsParameter;
import com.android.xlogdemo.util.ActivityUtil;

/**
 * Created by handsomezhou on 2018/1/31.
 */

public class LogMsgsActivity extends BaseSingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        LogMsgsParameter logMsgsParameter=(LogMsgsParameter) getIntent().getSerializableExtra(LogMsgsFragment.EXTRA_LOG_MSGS_PARAMETER);
        return LogMsgsFragment.newInstance(logMsgsParameter);
    }

    @Override
    protected boolean isRealTimeLoadFragment() {
        return false;
    }

    public static void launch(Context context){
        ActivityUtil.launch(context, LogMsgsActivity.class);
        return;
    }

    public static void launch(Activity activity, LogMsgsParameter logMsgsParameter){
        if(null==activity||null==logMsgsParameter){
            return;
        }

        Intent intent=new Intent(activity, LogMsgsActivity.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable(LogMsgsFragment.EXTRA_LOG_MSGS_PARAMETER, logMsgsParameter);
        intent.putExtras(bundle);

        activity.startActivity(intent);

        return;
    }
}
