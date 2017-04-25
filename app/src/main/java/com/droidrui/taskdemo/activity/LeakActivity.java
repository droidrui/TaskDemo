package com.droidrui.taskdemo.activity;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

import com.droidrui.taskdemo.R;

public class LeakActivity extends AppCompatActivity {

    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leak);

        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);

        new Thread(new Runnable() {

            private volatile int mCount;

            @Override
            public void run() {
                while (mCount <= 100) {
                    mProgressBar.post(new Runnable() {
                        @Override
                        public void run() {
                            mProgressBar.setProgress(mCount);
                        }
                    });
                    SystemClock.sleep(1000);
                    mCount++;
                }
            }
        }).start();
    }
}
