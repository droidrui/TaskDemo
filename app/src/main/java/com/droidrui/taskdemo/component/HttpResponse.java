package com.droidrui.taskdemo.component;

public class HttpResponse {

    public TaskError error;
    public String result;

    public HttpResponse(TaskError error, String result) {
        this.error = error;
        this.result = result;
    }

}
