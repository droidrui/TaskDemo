package com.droidrui.taskdemo.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

import com.droidrui.taskdemo.R;
import com.droidrui.taskdemo.fragment.NewFragment;
import com.droidrui.taskdemo.view.Toaster;

import java.io.File;

public class NewActivity extends AppCompatActivity implements NewFragment.FragmentCallback {

    private static final String TAG = "fragment_task";

    private ProgressBar mProgressBar;

    private NewFragment mNewFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);

        FragmentManager fm = getSupportFragmentManager();
        mNewFragment = (NewFragment) fm.findFragmentByTag(TAG);
        if (mNewFragment == null) {
            mNewFragment = new NewFragment();
            fm.beginTransaction().add(mNewFragment, TAG).commit();
        }
    }

    @Override
    public void onProgress(final int progress) {
        mProgressBar.post(new Runnable() {
            @Override
            public void run() {
                mProgressBar.setProgress(progress);
            }
        });
    }

    @Override
    public void onSuccess(File file) {
        Toaster.show(file.getAbsolutePath());
    }
}
