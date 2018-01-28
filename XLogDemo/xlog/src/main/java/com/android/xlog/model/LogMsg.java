package com.android.xlog.model;

import com.android.xlog.util.TimeUtil;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by handsomezhou on 2018/1/28.
 */

public class LogMsg extends DataSupport implements Serializable{
    /**
     * 消息id 建议用时间戳
     */
    private long id;

    /**
     * 消息key值 建议用int值
     */
    private String key;

    /**
     * 消息value值 建议用JSONObject,根据不同的key值来区分对应的JSONObject
     */
    private String value;

    /**
     * 添加时间
     */
    private String addTime= TimeUtil.getLogTime();

    public LogMsg() {

    }

    public LogMsg(long id, String key, String value) {
        this.id = id;
        this.key = key;
        this.value = value;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
