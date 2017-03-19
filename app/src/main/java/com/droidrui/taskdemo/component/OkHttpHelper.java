package com.droidrui.taskdemo.component;

import com.alibaba.fastjson.JSONObject;
import com.droidrui.taskdemo.R;
import com.droidrui.taskdemo.constant.API;
import com.droidrui.taskdemo.util.NetUtils;
import com.droidrui.taskdemo.util.ResUtils;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpHelper {

    private static final String TAG = "OkHttpHelper";

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final MediaType IMAGE = MediaType.parse("image/jpeg");
    public static final MediaType STREAM = MediaType.parse("application/octet-stream");

    private static volatile OkHttpHelper sInstance;

    public static OkHttpHelper getInstance() {
        if (sInstance == null) {
            synchronized (OkHttpHelper.class) {
                if (sInstance == null) {
                    sInstance = new OkHttpHelper();
                }
            }
        }
        return sInstance;
    }

    private OkHttpClient mClient;

    private OkHttpHelper() {
        mClient = new OkHttpClient();
    }

    public HttpResponse get(String method) throws IOException {
        return get(API.HOST, method);
    }

    public HttpResponse get(String host, String method) throws IOException {
        if (!NetUtils.isNetworkAvailable()) {
            return new HttpResponse(new TaskError(ResUtils.getString(R.string.network_invalid)), "");
        }
        String url = host + method;
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = mClient.newCall(request).execute();
        String res = response.body().string();
        Logger.e("url = " + url + ", response = " + res);
        JSONObject obj = null;
        try {
            obj = JSONObject.parseObject(res);
        } catch (Exception e) {
            e.printStackTrace();
            Logger.e("url = " + url + ", response = " + res);
            return new HttpResponse(new TaskError(ResUtils.getString(R.string.data_error)), "");
        }

        if (obj == null) {
            return new HttpResponse(new TaskError(ResUtils.getString(R.string.data_error)), "");
        }
        TaskError error = null;
        if (obj.getBooleanValue("error")) {
            error = new TaskError(ResUtils.getString(R.string.server_error));
        }
        String result = "";
        if (error == null) {
            if (obj.containsKey("results")) {
                result = obj.getString("results");
            }
        }
        return new HttpResponse(error, result);
    }

    public Response getResponse(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        return mClient.newCall(request).execute();
    }

    public HttpResponse post(String method, JSONObject o) throws IOException {
        if (!NetUtils.isNetworkAvailable()) {
            return new HttpResponse(new TaskError(ResUtils.getString(R.string.network_invalid)), "");
        }
        String url = API.HOST + method;
        String json = o.toString();
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = mClient.newCall(request).execute();
        String res = response.body().string();
        Logger.e("url = " + url + ", response = " + res);

        JSONObject obj = null;
        try {
            obj = JSONObject.parseObject(res);
        } catch (Exception e) {
            e.printStackTrace();
            Logger.e("url = " + url + ", response = " + res);
            return new HttpResponse(new TaskError(ResUtils.getString(R.string.data_error)), "");
        }

        if (obj == null) {
            return new HttpResponse(new TaskError(ResUtils.getString(R.string.data_error)), "");
        }

        TaskError error = null;
        if (obj.getBooleanValue("error")) {
            error = new TaskError(ResUtils.getString(R.string.server_error));
        }
        String result = "";
        if (error == null) {
            if (obj.containsKey("results")) {
                result = obj.getString("result");
            }
        }
        return new HttpResponse(error, result);
    }

}
