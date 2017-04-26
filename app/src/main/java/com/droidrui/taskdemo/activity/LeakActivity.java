package com.droidrui.taskdemo.activity;

import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.droidrui.taskdemo.R;
import com.droidrui.taskdemo.component.ThreadPool;
import com.droidrui.taskdemo.constant.API;
import com.droidrui.taskdemo.view.Toaster;

import java.io.File;

public class LeakActivity extends AppCompatActivity {

    private ProgressBar mProgressBar;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leak);

        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mImageView = (ImageView) findViewById(R.id.iv);
        Glide.with(this).load(API.IMAGE).into(mImageView);

        ThreadPool.getInstance().execute(new Runnable() {

            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    sendProgress(i);
                    SystemClock.sleep(1000);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toaster.show(new File(Environment.getExternalStorageDirectory(), "out.png").getAbsolutePath());
                    }
                });
            }
        });
    }

    private void sendProgress(final int progress) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProgressBar.setProgress(progress);
            }
        });
    }
}
