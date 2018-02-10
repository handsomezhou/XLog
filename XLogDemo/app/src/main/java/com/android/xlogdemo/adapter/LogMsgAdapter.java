package com.android.xlogdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.xlog.constant.Constant;
import com.android.xlog.model.LogMsg;
import com.android.xlogdemo.R;

import java.util.List;

/**
 * Created by handsomezhou on 2017/3/11.
 */

public class LogMsgAdapter extends RecyclerView.Adapter<LogMsgAdapter.LogMsgViewHolder> {
    private Context mContext;
    private int mLayoutResId;
    private List<LogMsg> mLogMsgs;
    private OnLogMsgAdapter mOnLogMsgAdapter;

    public interface OnLogMsgAdapter {
        void onClickItem(LogMsg logMsg);
        void onLongClickItem(LogMsg logMsg);
    }

    public LogMsgAdapter(Context context, int layoutResId, List<LogMsg> logMsgs) {
        mContext = context;
        mLayoutResId = layoutResId;
        mLogMsgs = logMsgs;
    }

    @Override
    public LogMsgViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(mLayoutResId, parent, false);
        LogMsgViewHolder logMsgViewHolder = new LogMsgViewHolder(view);
        return logMsgViewHolder;
    }

    @Override
    public void onBindViewHolder(LogMsgViewHolder holder, int position) {
        LogMsg logMsg = mLogMsgs.get(position);
        if (null != logMsg) {
            holder.mIndexTv.setText(Constant.NULL_STRING + (position + 1));
            holder.mIdTv.setText(Constant.NULL_STRING+logMsg.getLogId());
            holder.mKeyTv.setText(logMsg.getLogKey());
            holder.mAddTimeTv.setText(logMsg.getAddTime());
            holder.mValueTv.setText(logMsg.getLogValue());
        }


    }

    @Override
    public int getItemCount() {
        return (null != mLogMsgs) ? mLogMsgs.size() : 0;
    }

    public class LogMsgViewHolder extends RecyclerView.ViewHolder {
        public TextView mIndexTv;
        public TextView mIdTv;
        public TextView mKeyTv;
        public TextView mValueTv;
        public TextView mAddTimeTv;


        public LogMsgViewHolder(View view) {
            super(view);
            mIndexTv = (TextView) view.findViewById(R.id.index_text_view);
            mIdTv = (TextView) view.findViewById(R.id.id_text_view);
            mKeyTv = (TextView) view.findViewById(R.id.key_text_view);
            mAddTimeTv = (TextView) view.findViewById(R.id.add_time_text_view);
            mValueTv = (TextView) view.findViewById(R.id.value_text_view);

            view.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    LogMsg logMsg = mLogMsgs.get(getAdapterPosition());
                    if (null != mOnLogMsgAdapter) {
                        mOnLogMsgAdapter.onClickItem(logMsg);
                    }
                }
            });

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    LogMsg logMsg = mLogMsgs.get(getAdapterPosition());
                    if (null != mOnLogMsgAdapter) {
                        mOnLogMsgAdapter.onLongClickItem(logMsg);
                    }
                    return true;
                }
            });
        }

    }

    public OnLogMsgAdapter getOnLogMsgAdapter() {
        return mOnLogMsgAdapter;
    }

    public void setOnLogMsgAdapter(OnLogMsgAdapter onLogMsgAdapter) {
        mOnLogMsgAdapter = onLogMsgAdapter;
    }
}
