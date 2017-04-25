package com.droidrui.taskdemo.task;

import android.os.Environment;
import android.os.SystemClock;

import com.droidrui.taskdemo.component.Task;

import java.io.File;

/**
 * Created by Administrator on 2017/4/25.
 */

public class CustomTask {

    public static Task<File> download(){
        return new Task<File>() {
            @Override
            protected void call() {
                for (int i = 0; i < 100; i++){
                    onMessage(i, 0, null);
                    SystemClock.sleep(1000);
                }
                onSuccess(new File(Environment.getExternalStorageDirectory(), "out.png"));
            }
        };
    }

}
