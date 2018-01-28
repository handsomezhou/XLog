package com.android.xlogdemo.fragment;

import android.support.v4.util.TimeUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.xlog.constant.Constant;
import com.android.xlog.helper.XLogMsgHelper;
import com.android.xlog.model.LogMsg;
import com.android.xlogdemo.R;
import com.android.xlogdemo.util.ToastUtil;

/**
 * Created by handsomezhou on 2018/1/28.
 */

public class MainFragment extends BaseFragment {
    private Button mAddLogMsgBtn;
    @Override
    protected void initData() {
        setContext(getActivity());
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mAddLogMsgBtn=(Button) view.findViewById(R.id.add_log_msg_btn);
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
    }

    private void addLogMsg(){
        ToastUtil.toastLengthLong(getContext(),"addLogMsg ");

        long time=System.currentTimeMillis();
        XLogMsgHelper.getInstance().add(getContext(),new LogMsg(time,String.valueOf(time% Constant.THOUSAND_OF_INTEGER),"value "+time));
    }
}
