package com.android.xlog.helper;

import android.content.Context;

import com.android.xlog.model.LogMsg;
import com.android.xlog.service.XLogMsgService;
import com.android.xlog.util.LogUtil;
import com.android.xlog.util.XLogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by handsomezhou on 2018/1/28.
 */

public class XLogMsgHelper {
    private static final String TAG = "XLogMsgHelper";
    private List<LogMsg> mLogMsgs;
    /**
     * 日志消息正在处理中
     */
    private boolean mLogMsgsProcessing = false;

    private XLogMsgHelper() {
        initLogMsgHelper();
        return;
    }

    private static class SingletonHolder {
        public final static XLogMsgHelper sInstance = new XLogMsgHelper();
    }

    public static XLogMsgHelper getInstance() {
        return SingletonHolder.sInstance;
    }

    private void initLogMsgHelper() {
        initLogMsgs();
        return;
    }

    private void initLogMsgs() {
        if (null == mLogMsgs) {
            mLogMsgs = new ArrayList<>();
        } else {
            mLogMsgs.clear();
        }
    }

    public List<LogMsg> getLogMsgs() {
        return mLogMsgs;
    }

    public void setLogMsgs(List<LogMsg> logMsgs) {
        mLogMsgs = logMsgs;
    }

    public boolean isLogMsgsProcessing() {
        return mLogMsgsProcessing;
    }

    public void setLogMsgsProcessing(boolean logMsgsProcessing) {
        mLogMsgsProcessing = logMsgsProcessing;
    }

    /**
     * @param context
     * @param logMsg
     * @return
     */
    public boolean add(Context context, LogMsg logMsg) {
        boolean addSuccess = false;
        do {
            if (null == context) {
                break;
            }

            if (null == logMsg) {
                break;
            }

            addSuccess = XLogMsgHelper.getInstance().add(logMsg);
            if (true == addSuccess) {
                XLogMsgService.startServiceToCheckLogMsg(context, logMsg);
            }
        } while (false);
        return addSuccess;
    }

    /**
     * 处理日志消息
     *
     * @return
     */
    public boolean processLogMsgs() {
        boolean allTaskMessageProcessed = true;
        do {
            if (XLogMsgHelper.getInstance().getLogMsgs().size() <= 0) {
                XLogMsgHelper.getInstance().setLogMsgsProcessing(false);
                break;
            }

            if (true == XLogMsgHelper.getInstance().isLogMsgsProcessing()) {
                break;
            }

            XLogMsgHelper.getInstance().setLogMsgsProcessing(true);

            synchronized (XLogMsgHelper.getInstance().getLogMsgs()) {
                LogUtil.i(TAG, "start:getLogMsgs().size()= [" + XLogMsgHelper.getInstance().getLogMsgs().size() + "]");
                //save all log msgs
                XLogUtil.save(XLogMsgHelper.getInstance().getLogMsgs());

                //clear all log msgs
                XLogMsgHelper.getInstance().getLogMsgs().clear();
                LogUtil.i(TAG, "end:getLogMsgs().size()= [" + XLogMsgHelper.getInstance().getLogMsgs().size() + "]");
            }


        } while (false);

        XLogMsgHelper.getInstance().setLogMsgsProcessing(false);

        return allTaskMessageProcessed;
    }

    /**
     * @param startTimeMs 开始时间
     * @param dataCount   数据条数
     * @return
     */
    public static List<LogMsg> load(long startTimeMs, int dataCount) {
        return XLogUtil.load(startTimeMs, dataCount);
    }

    /**
     *
     * @param dataCount 数据条数
     * @param endTimeMs 结束时间
     * @return
     */
    public static List<LogMsg> load( int dataCount,long endTimeMs) {
        return XLogUtil.load(dataCount,endTimeMs);
    }

    /**
     * 删除日志
     * @param logId
     * @return
     */
    public static int deleteLogMsg(long logId) {
        return XLogUtil.deleteLogMsg(logId);
    }


    /**
     * 删除日志
     *
     * @param startTimeMs 开始时间
     * @param endTimeMs   结束时间
     * @return
     */
    public static int deleteLogMsg(long startTimeMs, long endTimeMs) {
        return XLogUtil.deleteLogMsg(startTimeMs,endTimeMs);
    }

    /**
     * 删除所有日志消息
     *
     * @return
     */
    public static int deleteAllLogMsg() {
        return XLogUtil.deleteAllLogMsg();
    }

    /**
     * @param logMsg
     * @return
     */
    private boolean add(LogMsg logMsg) {
        boolean addSuccess = false;
        do {
            if (null != mLogMsgs) {
                addSuccess = mLogMsgs.add(logMsg);
            }
        } while (false);

        return addSuccess;
    }

}
