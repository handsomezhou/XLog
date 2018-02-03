package com.android.xlog.model;

import com.android.xlog.util.TimeUtil;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by handsomezhou on 2018/1/28.
 */

public class LogMsg extends DataSupport implements Serializable{
    public static final String KEY_LOG_ID="logId";
    public static final String KEY_LOG_KEY="logKey";
    public static final String KEY_LOG_VALUE="logValue";
    public static final String KEY_ADD_TIME="addTime";
    /**
     * logId 建议用时间戳
     */
    private long logId;

    /**
     * 消息logKey值 建议用int值
     */
    private String logKey;

    /**
     * 消息logValue值 建议用JSONObject,根据不同的key值来区分对应的JSONObject
     */
    private String logValue;

    /**
     * 添加时间
     */
    private String addTime= TimeUtil.getLogTime();

    public LogMsg() {

    }

    public LogMsg(long logId, String logKey, String logValue) {
        this.logId = logId;
        this.logKey = logKey;
        this.logValue = logValue;
    }

    public long getLogId() {
        return logId;
    }

    public void setLogId(long logId) {
        this.logId = logId;
    }

    public String getLogValue() {
        return logValue;
    }

    public void setLogValue(String logValue) {
        this.logValue = logValue;
    }

    public String getLogKey() {
        return logKey;
    }

    public void setLogKey(String logKey) {
        this.logKey = logKey;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }
}
