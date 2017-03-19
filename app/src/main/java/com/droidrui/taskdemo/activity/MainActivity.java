package com.droidrui.taskdemo.activity;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.droidrui.taskdemo.R;
import com.droidrui.taskdemo.adapter.BlogListAdapter;
import com.droidrui.taskdemo.component.TaskCallback;
import com.droidrui.taskdemo.component.TaskError;
import com.droidrui.taskdemo.constant.Common;
import com.droidrui.taskdemo.model.Blog;
import com.droidrui.taskdemo.task.BlogTask;
import com.droidrui.taskdemo.view.Toaster;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private BlogListAdapter mAdapter;
    private ArrayList<Blog> mList;
    private LinearLayoutManager mLayoutManager;

    private int mPage = 1;

    private boolean mLoadingMore;
    private boolean mNoMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        getDataList();
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(mActivity);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {

            @Override
            public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
                if (itemPosition == 0) {
                    outRect.set(0, 0, 0, 0);
                } else {
                    outRect.set(0, 16, 0, 0);
                }
            }
        });
        mList = new ArrayList<>();
        mAdapter = new BlogListAdapter(mActivity, mList);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int position = mLayoutManager.findLastVisibleItemPosition();
                if (!mNoMore && mList.size() >= Common.COUNT && position == mList.size() && !mLoadingMore) {
                    mLoadingMore = true;
                    getDataList();
                }
            }
        });
    }

    private void getDataList() {
        mTaskManager.start(BlogTask.getDataList("Android", Common.COUNT, mPage)
                .setCallback(new TaskCallback<ArrayList<Blog>>() {

                    @Override
                    public void onFinish() {
                        mLoadingMore = false;
                    }

                    @Override
                    public void onError(TaskError e) {
                        Toaster.show(e.msg);
                    }

                    @Override
                    public void onSuccess(ArrayList<Blog> result) {
                        if (mPage == 1) {
                            mList.clear();
                        }
                        mList.addAll(result);
                        mAdapter.notifyDataSetChanged();
                        if (result.size() >= Common.COUNT) {
                            mPage++;
                        } else {
                            mNoMore = true;
                            mAdapter.setNoMore(true);
                        }
                    }
                }));
    }
}
