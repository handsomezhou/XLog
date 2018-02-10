package com.android.xlog.util;

import com.android.xlog.model.LogMsg;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by handsomezhou on 2018/1/28.
 */

public class XLogUtil {
    /**
     * 默认加载数据条数
     */
    private static int DEFAULT_LOAD_DATA_COUNT =10;
    private static String FIND_LOG_MSG_BY_START_TIME_MS_AND_DATA_COUNT_FROM_LOG_MSG = LogMsg.KEY_LOG_ID+" >= ? "+" limit ? ";
    private static String FIND_LOG_MSG_BY_END_TIME_MS_AND_DATA_COUNT_FROM_LOG_MSG = LogMsg.KEY_LOG_ID+" <= ? order by logId desc "+" limit ? ";
    private static String DELETE_LOG_MSG_BY_START_TIME_MS_AND_END_TIME_MS_FROM_LOG_MSG = LogMsg.KEY_LOG_ID+" >= ? and "+LogMsg.KEY_LOG_ID+" <= ? ";

    /**
     *
     * @param logMsgs
     * @return
     */
    public static boolean save(List<LogMsg> logMsgs) {
        boolean saveSuccess = false;

        do {
            if (null == logMsgs || logMsgs.size() <= 0) {
                break;
            }


            DataSupport.saveAll(logMsgs);
            saveSuccess = true;


        } while (false);

        return saveSuccess;
    }


    /**
     *
     * @param startTimeMs 开始时间
     * @param dataCount  数据条数
     * @return
     */
    public static List<LogMsg> load(long startTimeMs, int dataCount){
        List<LogMsg> logMsgs=new ArrayList<>();
        do{
            int loadDataCount=dataCount<=0?(DEFAULT_LOAD_DATA_COUNT):(dataCount);
            logMsgs.addAll(DataSupport.where(FIND_LOG_MSG_BY_START_TIME_MS_AND_DATA_COUNT_FROM_LOG_MSG,String.valueOf(startTimeMs),String.valueOf(loadDataCount)).find(LogMsg.class));
        }while (false);

        return logMsgs;
    }

    /**
     *
     * @param startTimeMs 开始时间
     * @return
     */
    public static List<LogMsg> loadFromStartTime(long startTimeMs){

        return load(startTimeMs,DEFAULT_LOAD_DATA_COUNT);
    }

    /**
     *
     * @param dataCount 数据条数
     * @param endTimeMs 结束时间
     * @return
     */
    public static List<LogMsg> load(int dataCount,long endTimeMs){
        List<LogMsg> logMsgs=new ArrayList<>();
        do{
            int loadDataCount=dataCount<=0?(DEFAULT_LOAD_DATA_COUNT):(dataCount);
            logMsgs.addAll(DataSupport.where(FIND_LOG_MSG_BY_END_TIME_MS_AND_DATA_COUNT_FROM_LOG_MSG,String.valueOf(endTimeMs),String.valueOf(loadDataCount)).find(LogMsg.class));
        }while (false);

        return logMsgs;
    }


    /**
     *
     * @param endTimeMs
     * @return
     */
    public static List<LogMsg> loadToEndTime(long endTimeMs){

        return load(DEFAULT_LOAD_DATA_COUNT,endTimeMs);
    }

    /**
     * 删除日志
     * @param startTimeMs 开始时间
     * @param endTimeMs   结束时间
     * @return
     */
    public static int deleteLogMsg(long startTimeMs,long endTimeMs){
        return DataSupport.deleteAll(LogMsg.class,DELETE_LOG_MSG_BY_START_TIME_MS_AND_END_TIME_MS_FROM_LOG_MSG,String.valueOf(startTimeMs),String.valueOf(endTimeMs));
    }

    /**
     *
     * @return
     */
    public static int deleteAllLogMsg(){
        return DataSupport.deleteAll(LogMsg.class);
    }
}
