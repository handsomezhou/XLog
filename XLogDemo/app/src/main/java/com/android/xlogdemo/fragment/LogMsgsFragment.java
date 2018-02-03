package com.android.xlogdemo.fragment;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.xlog.helper.XLogMsgHelper;
import com.android.xlog.model.LogMsg;
import com.android.xlogdemo.R;
import com.android.xlogdemo.adapter.LogMsgAdapter;
import com.android.xlogdemo.constant.LoadingMode;
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

public class LogMsgsFragment extends BaseFragment implements OnRefreshListener, OnLoadMoreListener {
    private SwipeToLoadLayout mSwipeToLoadLayout;
    private RecyclerView mLogMsgRv;
    private LogMsgAdapter mLogMsgAdapter;
    private List<LogMsg> mLogMsgs;
    private boolean mLoading = false;

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

        mLogMsgRv.setAdapter(mLogMsgAdapter);
        return view;
    }

    @Override
    protected void initListener() {
        loadLogMsgs(LoadingMode.PULL_DOWN_REFRESH);
    }

    /*start: OnRefreshListener*/
    @Override
    public void onRefresh() {
        loadLogMsgs(LoadingMode.PULL_DOWN_REFRESH);
    }
    /*end: OnRefreshListener*/

    /*start: OnLoadMoreListener*/
    @Override
    public void onLoadMore() {
        loadLogMsgs(LoadingMode.PULL_UP_LOAD);
    }
    /*start: OnLoadMoreListener*/

    public boolean isLoading() {
        return mLoading;
    }

    public void setLoading(boolean loading) {
        mLoading = loading;
    }


    private void initLogMsgs() {
        if (null == mLogMsgs) {
            mLogMsgs = new ArrayList<>();
        } else {
            mLogMsgs.clear();
        }
        return;
    }

    private void loadLogMsgs(LoadingMode loadingMode) {
        if (true == isLoading()) {
            return;
        }
        setLoading(true);
        List<LogMsg> logMsgs = null;
        switch (loadingMode) {
            case PULL_DOWN_REFRESH:
                initLogMsgs();
                loadLogMsgs(LoadingMode.PULL_DOWN_REFRESH,-1,3);

                break;
            case PULL_UP_LOAD:
                if (mLogMsgs.size() > 0) {
                    loadLogMsgs(LoadingMode.PULL_DOWN_REFRESH,mLogMsgs.get(mLogMsgs.size() - 1).getLogId() + 1,3);
                } else {
                    loadLogMsgs(LoadingMode.PULL_DOWN_REFRESH,-1,3);
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

    private void loadLogMsgs(LoadingMode loadingMode, long startTimeMs, int dataCount) {
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
}
