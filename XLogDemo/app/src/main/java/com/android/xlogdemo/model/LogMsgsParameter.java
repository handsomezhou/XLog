package com.android.xlogdemo.model;

import com.android.xlogdemo.constant.LogMsgsLoadSorting;

import java.io.Serializable;

/**
 * Created by handsomezhou on 2018/2/10.
 */

public class LogMsgsParameter implements Serializable {
    public static final  int LOAD_DATA_COUNT_PER_TIME=3;
    int mLogMsgsLoadSorting= LogMsgsLoadSorting.DES;
    int mLoadDataCountPerTime=LOAD_DATA_COUNT_PER_TIME;
    public LogMsgsParameter() {
    }

    public LogMsgsParameter(int logMsgsLoadSorting, int loadDataCountPerTime) {
        mLogMsgsLoadSorting = logMsgsLoadSorting;
        mLoadDataCountPerTime = loadDataCountPerTime;
    }

    public int getLogMsgsLoadSorting() {
        return mLogMsgsLoadSorting;
    }

    public void setLogMsgsLoadSorting(int logMsgsLoadSorting) {
        mLogMsgsLoadSorting = logMsgsLoadSorting;
    }

    public int getLoadDataCountPerTime() {
        return mLoadDataCountPerTime;
    }

    public void setLoadDataCountPerTime(int loadDataCountPerTime) {
        mLoadDataCountPerTime = loadDataCountPerTime;
    }
}
