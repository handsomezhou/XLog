package com.android.xlogdemo.fragment;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.xlog.constant.TimePatternConstant;
import com.android.xlog.helper.XLogMsgHelper;
import com.android.xlog.model.LogMsg;
import com.android.xlog.util.LogUtil;
import com.android.xlog.util.TimeUtil;
import com.android.xlogdemo.R;
import com.android.xlogdemo.adapter.LogMsgAdapter;
import com.android.xlogdemo.constant.LoadingMode;
import com.android.xlogdemo.constant.LogMsgsLoadSorting;
import com.android.xlogdemo.dialog.CommonDialog;
import com.android.xlogdemo.model.LogMsgsParameter;
import com.android.xlogdemo.util.ToastUtil;
import com.android.xlogdemo.util.ViewUtil;
import com.android.xlogdemo.view.RecycleViewDivider;
import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by handsomezhou on 2018/1/31.
 */

public class LogMsgsFragment extends BaseFragment implements OnRefreshListener, OnLoadMoreListener,LogMsgAdapter.OnLogMsgAdapter,CommonDialog.OnCommonDialog{
    private static final String TAG = "LogMsgsFragment";
    public static final String EXTRA_LOG_MSGS_PARAMETER = "LogMsgsFragment.EXTRA_LOG_MSGS_PARAMETER";

    private SwipeToLoadLayout mSwipeToLoadLayout;
    private RecyclerView mLogMsgRv;
    private LogMsgAdapter mLogMsgAdapter;
    private List<LogMsg> mLogMsgs;
    private boolean mLoading = false;
    private LogMsgsParameter mLogMsgsParameter;
    private CommonDialog mCommonDialog;

    private enum DialogType {
        DELETE_LOG_MSG,
    }

    public static LogMsgsFragment newInstance( LogMsgsParameter logMsgsParameter) {
        Bundle bundle = new Bundle();
        if (null != logMsgsParameter) {
            bundle.putSerializable(EXTRA_LOG_MSGS_PARAMETER,
                    logMsgsParameter);
        } else {
            bundle.putSerializable(EXTRA_LOG_MSGS_PARAMETER,
                    new LogMsgsParameter());
        }

        LogMsgsFragment fragment = new LogMsgsFragment();
        fragment.setArguments(bundle);

        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (getArguments().containsKey(EXTRA_LOG_MSGS_PARAMETER)) {
            mLogMsgsParameter = (LogMsgsParameter) getArguments()
                    .getSerializable(EXTRA_LOG_MSGS_PARAMETER);
        } else {
            mLogMsgsParameter = new LogMsgsParameter();
        }
        super.onCreate(savedInstanceState);
    }
    
    @Override
    protected void initData() {
        setContext(getActivity());
        initLogMsgs();
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_log_msgs, container, false);
        mSwipeToLoadLayout = (SwipeToLoadLayout) view.findViewById(R.id.swipe_to_load_layout);
        mSwipeToLoadLayout.setOnRefreshListener(this);
        mSwipeToLoadLayout.setOnLoadMoreListener(this);

        mLogMsgRv = (RecyclerView) view.findViewById(R.id.swipe_target);


        mLogMsgRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        mLogMsgRv.setItemAnimator(new DefaultItemAnimator());
        //mLogMsgRv.addItemDecoration(new RecycleViewDivider(getContext(), LinearLayoutManager.VERTICAL));
        mLogMsgRv.addItemDecoration(new RecycleViewDivider(getContext(), LinearLayoutManager.HORIZONTAL, getContext().getResources().getInteger(R.integer.horizontal_divider_height), getContext().getResources().getColor(R.color.color_horizontal_divider)));
        mLogMsgAdapter = new LogMsgAdapter(getContext(), R.layout.list_item_log_msg, mLogMsgs);
        mLogMsgAdapter.setOnLogMsgAdapter(this);

        mLogMsgRv.setAdapter(mLogMsgAdapter);
        return view;
    }

    @Override
    protected void initListener() {
        switch (mLogMsgsParameter.getLogMsgsLoadSorting()){
            case LogMsgsLoadSorting.DES:
                loadLogMsgsByDes(LoadingMode.PULL_DOWN_REFRESH);
                break;
            case LogMsgsLoadSorting.ASC:
                loadLogMsgsByAsc(LoadingMode.PULL_DOWN_REFRESH);
                break;
            default:
                loadLogMsgsByDes(LoadingMode.PULL_DOWN_REFRESH);
                break;
        }

    }

    /*start: OnRefreshListener*/
    @Override
    public void onRefresh() {
        switch (mLogMsgsParameter.getLogMsgsLoadSorting()){
            case LogMsgsLoadSorting.DES:
                loadLogMsgsByDes(LoadingMode.PULL_DOWN_REFRESH);
                break;
            case LogMsgsLoadSorting.ASC:
                loadLogMsgsByAsc(LoadingMode.PULL_DOWN_REFRESH);
                break;
            default:
                loadLogMsgsByDes(LoadingMode.PULL_DOWN_REFRESH);
                break;
        }
    }
    /*end: OnRefreshListener*/

    /*start: OnLoadMoreListener*/
    @Override
    public void onLoadMore() {
        switch (mLogMsgsParameter.getLogMsgsLoadSorting()){
            case LogMsgsLoadSorting.DES:
                loadLogMsgsByDes(LoadingMode.PULL_UP_LOAD);
                break;
            case LogMsgsLoadSorting.ASC:
                loadLogMsgsByAsc(LoadingMode.PULL_UP_LOAD);
                break;
            default:
                loadLogMsgsByDes(LoadingMode.PULL_UP_LOAD);
                break;
        }
    }
    /*start: OnLoadMoreListener*/

    /*start: LogMsgAdapter.OnLogMsgAdapter*/
    @Override
    public void onClickItem(LogMsg logMsg) {

    }

    @Override
    public void onLongClickItem(LogMsg logMsg) {
        getCommonDialog(DialogType.DELETE_LOG_MSG, logMsg).show();
    }
    /*end: LogMsgAdapter.OnLogMsgAdapter*/

    /*start: CommonDialog.OnCommonDialog*/
    @Override
    public void onCommonDialogOk(Object dialogType, Object dialogData) {
        switch ((DialogType) dialogType) {
            case DELETE_LOG_MSG:
                if(dialogData instanceof LogMsg) {
                    delete((LogMsg)dialogData);
                    refreshView();
                }
                break;

            default:
                break;
        }
    }



    @Override
    public void onCommonDialogCancel(Object dialogType, Object dialogData) {

    }
    /*end: CommonDialog.OnCommonDialog*/



    public boolean isLoading() {
        return mLoading;
    }

    public void setLoading(boolean loading) {
        mLoading = loading;
    }

    public CommonDialog getCommonDialog(DialogType dialogType, Object object) {
        if (null == dialogType || null == object) {
            return null;
        }

        if (null == mCommonDialog) {
            mCommonDialog = new CommonDialog(getContext());
            mCommonDialog.setCancelable(true);
            mCommonDialog.setCanceledOnTouchOutside(true);
            mCommonDialog.setOnCommonDialog(this);
        }

        mCommonDialog.setDialogType(dialogType);
        switch (dialogType) {
            case DELETE_LOG_MSG:
                mCommonDialog.setDialogData(object);
                mCommonDialog.getTitleTv().setText(R.string.delete_log_msg);

                mCommonDialog.getMessageTv().setText(
                        R.string.sure_delete_log_msg);
                break;
            default:
                break;
        }

        return mCommonDialog;
    }

    public void setCommonDialog(CommonDialog commonDialog) {
        mCommonDialog = commonDialog;
    }

    private void initLogMsgs() {
        if (null == mLogMsgs) {
            mLogMsgs = new ArrayList<>();
        } else {
            mLogMsgs.clear();
        }
        return;
    }

    private void loadLogMsgsByAsc(LoadingMode loadingMode) {
        if (true == isLoading()) {
            return;
        }
        setLoading(true);
        switch (loadingMode) {
            case PULL_DOWN_REFRESH:
                initLogMsgs();

                loadLogMsgsByAsc(LoadingMode.PULL_DOWN_REFRESH,-1,mLogMsgsParameter.getLoadDataCountPerTime());

                break;
            case PULL_UP_LOAD:
                if (mLogMsgs.size() > 0) {
                    loadLogMsgsByAsc(LoadingMode.PULL_UP_LOAD,mLogMsgs.get(mLogMsgs.size() - 1).getLogId() + 1,mLogMsgsParameter.getLoadDataCountPerTime());
                } else {
                    loadLogMsgsByAsc(LoadingMode.PULL_DOWN_REFRESH,-1,mLogMsgsParameter.getLoadDataCountPerTime());
                }

                break;
            default:
                break;
        }

        loadingModeComplete(loadingMode);
        setLoading(false);
    }

    private void loadLogMsgsByDes(LoadingMode loadingMode) {
        if (true == isLoading()) {
            return;
        }
        setLoading(true);
        switch (loadingMode) {
            case PULL_DOWN_REFRESH:
                initLogMsgs();
                loadLogMsgsByDes(LoadingMode.PULL_DOWN_REFRESH,mLogMsgsParameter.getLoadDataCountPerTime(),System.currentTimeMillis());

                break;
            case PULL_UP_LOAD:
                if (mLogMsgs.size() > 0) {
                    loadLogMsgsByDes(LoadingMode.PULL_UP_LOAD,mLogMsgsParameter.getLoadDataCountPerTime(),mLogMsgs.get(mLogMsgs.size() - 1).getLogId() - 1);
                } else {
                    loadLogMsgsByDes(LoadingMode.PULL_DOWN_REFRESH,mLogMsgsParameter.getLoadDataCountPerTime(),System.currentTimeMillis());
                }

                break;
            default:
                break;
        }

        loadingModeComplete(loadingMode);
        setLoading(false);
    }


    private void loadingModeComplete(LoadingMode loadingMode) {
        switch (loadingMode) {
            case PULL_DOWN_REFRESH:
                ViewUtil.stopRefresh(mSwipeToLoadLayout);
                break;
            case PULL_UP_LOAD:
                ViewUtil.stopLoadMore(mSwipeToLoadLayout);
                break;
            default:
                break;
        }
    }

    private void loadLogMsgsByAsc(LoadingMode loadingMode, long startTimeMs, int dataCount) {
        LogUtil.i(TAG,"loadLogMsgsByAsc loadingMode "+loadingMode+"; startTimeMs ["+startTimeMs+"]dataCount["+dataCount+"]"+"["+ TimeUtil.getFormatTime(startTimeMs, TimePatternConstant.LOG_TIME_PATTERN)+"]");
        List<LogMsg> logMsgs = XLogMsgHelper.load(startTimeMs, dataCount);
        if (logMsgs.size() > 0) {
            mLogMsgs.addAll(logMsgs);
        } else {
            switch (loadingMode) {
                case PULL_DOWN_REFRESH:
                    ToastUtil.toastLengthLong(getContext(), R.string.no_log_msgs);
                    break;
                case PULL_UP_LOAD:
                    ToastUtil.toastLengthLong(getContext(), R.string.no_more_log_msgs);
                    break;
                default:
                    break;
            }

        }
    }

    private void loadLogMsgsByDes(LoadingMode loadingMode, int dataCount,long endTimeMs) {
        LogUtil.i(TAG,"loadLogMsgsByDes loadingMode "+loadingMode+";dataCount["+dataCount+"];endTimeMs["+endTimeMs+"] ["+ TimeUtil.getFormatTime(endTimeMs, TimePatternConstant.LOG_TIME_PATTERN)+"]");
        List<LogMsg> logMsgs = XLogMsgHelper.load(dataCount,endTimeMs);
        if (logMsgs.size() > 0) {
            mLogMsgs.addAll(logMsgs);
        } else {
            switch (loadingMode) {
                case PULL_DOWN_REFRESH:
                    ToastUtil.toastLengthLong(getContext(), R.string.no_log_msgs);
                    break;
                case PULL_UP_LOAD:
                    ToastUtil.toastLengthLong(getContext(), R.string.no_more_log_msgs);
                    break;
                default:
                    break;
            }

        }
    }

    private void delete(LogMsg logMsg){
        do{
            if(null==logMsg){
                break;
            }
            int rowsAffected=XLogMsgHelper.deleteLogMsg(logMsg.getLogId());
            if(rowsAffected>0){
                removeFromLogMsgList(logMsg);
                ToastUtil.toastLengthshort(getContext(),R.string.delete_log_msg_success);
            }
        }while (false);

        return;
    }

    private void removeFromLogMsgList(LogMsg logMsg){
        do{
            if(null==logMsg){
                break;
            }

            if(null==mLogMsgs||mLogMsgs.size()<=0){
                break;
            }

            int logMsgsSize=mLogMsgs.size();
            for(int i=0; i<logMsgsSize; i++){
                if(mLogMsgs.get(i).getLogId()==logMsg.getLogId()){
                    mLogMsgs.remove(i);
                    break;
                }
            }
        }while (false);

        return;
    }

    private void refreshView(){
        refreshLogMsgRv();
    }

    private void refreshLogMsgRv(){
        if (null == mLogMsgRv) {
            return;
        }

        if (null != mLogMsgAdapter) {
            mLogMsgAdapter.notifyDataSetChanged();

            if (mLogMsgAdapter.getItemCount() > 0) {
                ViewUtil.showView(mLogMsgRv);
            } else {
                ViewUtil.hideView(mLogMsgRv);
            }
        }

        return;
    }
}
