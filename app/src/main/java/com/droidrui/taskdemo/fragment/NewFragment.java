package com.droidrui.taskdemo.fragment;


import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.support.v4.app.Fragment;

import com.droidrui.taskdemo.component.Logger;
import com.droidrui.taskdemo.component.ThreadPool;

import java.io.File;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewFragment extends Fragment {

    private volatile FragmentCallback mCallback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallback = (FragmentCallback) context;
        Logger.e("onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);

        ThreadPool.getInstance().execute(new Runnable() {

            private volatile boolean mQuit;

            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    if (mCallback == null && !mQuit) {
                        Logger.e(NewFragment.this.toString());
                        Logger.e(this.toString());
                        Thread t = Thread.currentThread();
                        Logger.e(t.toString());
                        Logger.e("mCallback == null");
                        mQuit = true;
                    }
                    if (mCallback != null) {
                        mCallback.onProgress(i);
                    }
//                    if (mCallback != null) {
//                        mCallback.onProgress(i);
//                    }
                    SystemClock.sleep(1000);
                }
                if (mCallback != null) {
                    mCallback.onSuccess(new File(Environment.getExternalStorageDirectory(), "out.png"));
                }
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
        Logger.e("onDetach");
    }

    public interface FragmentCallback {
        @WorkerThread
        void onProgress(int progress);

        @WorkerThread
        void onSuccess(File file);
    }
}
