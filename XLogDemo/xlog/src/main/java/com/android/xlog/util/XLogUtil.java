package com.android.xlog.util;

import com.android.xlog.model.LogMsg;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by handsomezhou on 2018/1/28.
 */

public class XLogUtil {

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
}
