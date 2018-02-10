package com.android.xlogdemo.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.xlog.constant.Constant;
import com.android.xlog.helper.XLogMsgHelper;
import com.android.xlog.model.LogMsg;
import com.android.xlog.util.TimeUtil;
import com.android.xlog.util.XLogUtil;
import com.android.xlogdemo.R;
import com.android.xlogdemo.activity.LogMsgsActivity;
import com.android.xlogdemo.constant.LogMsgsLoadSorting;
import com.android.xlogdemo.model.LogMsgsParameter;
import com.android.xlogdemo.util.ToastUtil;

/**
 * Created by handsomezhou on 2018/1/28.
 */

public class MainFragment extends BaseFragment {
    private Button mAddLogMsgBtn;
    private Button mViewLogMsgByDesBtn;
    private Button mViewLogMsgByAscBtn;
    private Button mClearLogMsgBtn;

    @Override
    protected void initData() {
        setContext(getActivity());
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mAddLogMsgBtn = (Button) view.findViewById(R.id.add_log_msg_btn);
        mViewLogMsgByDesBtn = (Button) view.findViewById(R.id.view_log_msg_by_des_btn);
        mViewLogMsgByAscBtn = (Button) view.findViewById(R.id.view_log_msg_by_asc_btn);
        mClearLogMsgBtn = (Button) view.findViewById(R.id.clear_log_msg_btn);
        return view;
    }

    @Override
    protected void initListener() {
        mAddLogMsgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addLogMsg();
            }
        });

        mViewLogMsgByDesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewLogMsgByDes();
            }
        });

        mViewLogMsgByAscBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewLogMsgByAsc();
            }
        });
        mClearLogMsgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearLogMsg();
            }
        });
    }

    private void addLogMsg() {


        long time = System.currentTimeMillis();
        XLogMsgHelper.getInstance().add(getContext(), new LogMsg(time, String.valueOf(time % Constant.THOUSAND_OF_INTEGER), "value " + time));

        ToastUtil.toastLengthLong(getContext(), R.string.add_log_msg_success);
    }

    private void viewLogMsgByDes() {
        LogMsgsParameter logMsgsParameter=new LogMsgsParameter();
        logMsgsParameter.setLogMsgsLoadSorting(LogMsgsLoadSorting.DES);
        LogMsgsActivity.launch(getActivity(),logMsgsParameter);
    }

    private void viewLogMsgByAsc() {
        LogMsgsParameter logMsgsParameter=new LogMsgsParameter();
        logMsgsParameter.setLoadDataCountPerTime(logMsgsParameter.getLoadDataCountPerTime()*2);
        logMsgsParameter.setLogMsgsLoadSorting(LogMsgsLoadSorting.ASC);
        LogMsgsActivity.launch(getActivity(),logMsgsParameter);
    }


    private void clearLogMsg(){
        XLogMsgHelper.deleteAllLogMsg();
   /*    long currentTimeMillis= System.currentTimeMillis();
        XLogMsgHelper.deleteLogMsg(currentTimeMillis- TimeUtil.sec2ms(30),currentTimeMillis);*/
        ToastUtil.toastLengthLong(getContext(), R.string.clear_log_msg_success);

    }
}
