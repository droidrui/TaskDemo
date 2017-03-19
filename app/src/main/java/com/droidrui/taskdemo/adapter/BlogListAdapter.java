package com.droidrui.taskdemo.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.droidrui.taskdemo.R;
import com.droidrui.taskdemo.model.Blog;

import java.util.ArrayList;

/**
 * Created by tnitf on 2016/7/1.
 */
public class BlogListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Blog> mList;
    private LayoutInflater mInflater;

    private boolean mNoMore;

    public BlogListAdapter(Activity activity, ArrayList<Blog> list) {
        mList = list;
        mInflater = LayoutInflater.from(activity);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View v = mInflater.inflate(R.layout.item_blog, parent, false);
            return new ItemViewHolder(v);
        } else {
            View v = mInflater.inflate(R.layout.item_load_more, parent, false);
            return new LoadMoreViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == 0) {
            ItemViewHolder ho = (ItemViewHolder) holder;
            Blog item = mList.get(position);
            ho.mDescTv.setText(item.desc);
            ho.mAuthorTv.setText(item.who);
            ho.mTimeTv.setText(item.createdAt);
        } else {
            LoadMoreViewHolder ho = (LoadMoreViewHolder) holder;
            if (mNoMore) {
                ho.mProgressBar.setVisibility(View.GONE);
                ho.mTv.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mList.size()) {
            return 1;
        }
        return 0;
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView mDescTv;
        TextView mAuthorTv;
        TextView mTimeTv;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mDescTv = (TextView) itemView.findViewById(R.id.tv_desc);
            mAuthorTv = (TextView) itemView.findViewById(R.id.tv_author);
            mTimeTv = (TextView) itemView.findViewById(R.id.tv_time);
        }
    }

    private class LoadMoreViewHolder extends RecyclerView.ViewHolder {

        ProgressBar mProgressBar;
        TextView mTv;

        public LoadMoreViewHolder(View itemView) {
            super(itemView);
            mProgressBar = (ProgressBar) itemView.findViewById(R.id.progress_bar);
            mTv = (TextView) itemView.findViewById(R.id.tv);
        }
    }

    public void setNoMore(boolean noMore) {
        mNoMore = noMore;
        notifyItemChanged(mList.size());
    }

}
