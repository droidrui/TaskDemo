package com.droidrui.taskdemo.util;

import com.droidrui.taskdemo.App;

/**
 * Created by lingrui on 2017/3/19.
 */

public class ResUtils {

    public static String getString(int resId) {
        return App.getContext().getString(resId);
    }

}
