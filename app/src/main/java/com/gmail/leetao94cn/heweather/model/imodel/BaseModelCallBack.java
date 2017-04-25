package com.gmail.leetao94cn.heweather.model.imodel;

import com.android.volley.Response;

import org.json.JSONObject;

/**
 * Created by leetao on 2016/10/28.
 */

public interface BaseModelCallBack {
    void onResponse(Response<JSONObject> response);
}
