package com.droidrui.taskdemo.task;

import com.alibaba.fastjson.JSON;
import com.droidrui.taskdemo.R;
import com.droidrui.taskdemo.component.HttpResponse;
import com.droidrui.taskdemo.component.OkHttpHelper;
import com.droidrui.taskdemo.component.Task;
import com.droidrui.taskdemo.constant.API;
import com.droidrui.taskdemo.model.Blog;
import com.droidrui.taskdemo.util.ResUtils;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by lr on 2016/6/30.
 */
public class BlogTask {

    public static Task<ArrayList<Blog>> getDataList(final String title, final int count, final int page) {
        return new Task<ArrayList<Blog>>() {
            @Override
            protected void call() {
                try {
                    String method = API.BLOG + title + "/" + count + "/" + page;
                    HttpResponse response = OkHttpHelper.getInstance().get(method);
                    if (response.error != null) {
                        onError(response.error);
                        return;
                    }
                    ArrayList<Blog> dataList = (ArrayList<Blog>) JSON.parseArray(response.result, Blog.class);
                    if (dataList != null) {
                        onSuccess(dataList);
                    } else {
                        onError(ResUtils.getString(R.string.data_error));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    onError(e);
                }
            }
        };
    }


}
