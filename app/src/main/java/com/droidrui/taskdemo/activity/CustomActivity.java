package com.droidrui.taskdemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

import com.droidrui.taskdemo.R;
import com.droidrui.taskdemo.component.TaskCallback;
import com.droidrui.taskdemo.component.TaskError;
import com.droidrui.taskdemo.component.TaskManager;
import com.droidrui.taskdemo.task.CustomTask;
import com.droidrui.taskdemo.view.Toaster;

import java.io.File;

public class CustomActivity extends AppCompatActivity {

    private ProgressBar mProgressBar;
    private TaskManager mTaskManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);

        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);

        mTaskManager = new TaskManager();
        mTaskManager.start(CustomTask.download()
                .setCallback(new TaskCallback<File>() {
                    @Override
                    public void onError(TaskError e) {

                    }

                    @Override
                    public void onSuccess(File result) {
                        Toaster.show(result.getAbsolutePath());
                    }

                    @Override
                    public void onMessage(int arg1, int arg2, Object obj) {
                        mProgressBar.setProgress(arg1);
                    }
                }));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTaskManager.onDestroy();
    }
}
