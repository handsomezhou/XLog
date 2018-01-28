package com.android.xlog.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;

import com.android.xlog.helper.XLogMsgHelper;
import com.android.xlog.model.LogMsg;
import com.android.xlog.util.LogUtil;
import com.android.xlog.util.TimeUtil;

/**
 * Created by handsomezhou on 2018/1/28.
 */

public class XLogMsgService extends Service {
    private static final String TAG="XLogMsgService";
    public static final int CHECK_LOG_MSG_INTERVAL_TIME_SEC=4;
    public static final long CHECK_LOG_MSG_INTERVAL_TIME_MS= TimeUtil.sec2ms(CHECK_LOG_MSG_INTERVAL_TIME_SEC);


    private static final int MSG_LOG_MSG_CHECK =1;//即时消息检查

    public static final String ACTION_CHECK_LOG_MSG="com.android.xlog.service.ACTION_CHECK_LOG_MSG";
    public static final String EXTRA_CHECK_LOG_MSG_PARAMETER ="com.android.xlog.service.EXTRA_CHECK_LOG_MSG_PARAMETER";


    private Handler mHandler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case MSG_LOG_MSG_CHECK:
                    LogUtil.i(TAG,"MSG_LOG_MSG_CHECK "+System.currentTimeMillis());
                    boolean allLogMsgsProcessed= XLogMsgHelper.getInstance().processLogMsgs();
                    if(false==allLogMsgsProcessed){
                        tryToCheckLogMsgs();
                    }
                    LogUtil.i(TAG,"allLogMsgsProcessed="+allLogMsgsProcessed);
                    break;

                default:
                    break;
            }
            return false;
        }
    });

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        do{
            if(null==intent){
                break;
            }

            String action=intent.getAction();
            switch (action){
                case ACTION_CHECK_LOG_MSG:
                    checkLogMsgs();
                    break;

                default:
                    break;
            }
        }while (false);

        return super.onStartCommand(intent, flags, startId);
    }


    public static void startServiceToCheckLogMsg(Context context, LogMsg logMsg){
        do{
            if(null==context){
                break;
            }

            if(null==logMsg){
                break;
            }

            Intent intent=new Intent(context,XLogMsgService.class);
            intent.setAction(XLogMsgService.ACTION_CHECK_LOG_MSG);
            Bundle bundle=new Bundle();
            bundle.putSerializable(XLogMsgService.EXTRA_CHECK_LOG_MSG_PARAMETER,logMsg);
            intent.putExtras(bundle);
            context.startService(intent);

        }while (false);

        return;
    }


    private void checkLogMsgs(){
        if(null!=mHandler){
            mHandler.removeMessages(MSG_LOG_MSG_CHECK);
            mHandler.sendEmptyMessage(MSG_LOG_MSG_CHECK);
        }
        return;
    }

    private void tryToCheckLogMsgs(){
        if(null!=mHandler){
            mHandler.removeMessages(MSG_LOG_MSG_CHECK);
            mHandler.sendEmptyMessageDelayed(MSG_LOG_MSG_CHECK,CHECK_LOG_MSG_INTERVAL_TIME_MS);
        }

        return;
    }

}
