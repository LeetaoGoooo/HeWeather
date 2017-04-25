package com.gmail.leetao94cn.heweather.model;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.gmail.leetao94cn.heweather.model.imodel.BaseGisModelCallBack;
import com.gmail.leetao94cn.heweather.model.imodel.IGisModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by leetao on 2016/11/7.
 *
 * @description 通过 GPS 或者 NetWork 来获取当前设备的经纬度
 *  然后根据经纬度调取 google 地图 api 获取当前城市名
 */

public class GisModel implements IGisModel {

    private double latitude = 0.0;
    private double longitude = 0.0;
    private RequestQueue requestQueue;


    //谷歌地图服务API调用地址：http://maps.google.com/maps/api/geocode/json?latlng= 纬度,经度 &language=zh-CN&sensor=true
    private static final String GOOGLE_MAP_URL = "http://maps.google.com/maps/api/geocode/json?latlng=";

    public GisModel(Context context) {
        this.requestQueue = Volley.newRequestQueue(context);
    }

    @Override
    public void getCityNameByGis(String longitude, String  latitude, final BaseGisModelCallBack baseGisModelCallBack) {
            String url = GOOGLE_MAP_URL + longitude + "," + latitude + "&language=zh-CN&sensor=true";
            Log.d("GOOGLE_URL", url);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    String cityname = null;
                    cityname = handleGoogleMapInfo(response);
                    baseGisModelCallBack.onResponse(cityname);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("JSONObjectRequestError", error.getMessage(), error);
                    baseGisModelCallBack.onResponse(null);
                }
            });
            requestQueue.add(jsonObjectRequest);
    }


    /**
     * 处理调用 Google 地图 api 返回的信息
     *
     * @param response JSONObject
     *      数据格式参考:http://maps.google.com/maps/api/geocode/json?latlng=22.552549,113.951320&language=zh-CN&sensor=true
     *
     * @return String  返回城市名
     */
    private String handleGoogleMapInfo(JSONObject response) {
        String cityname = null;
        try {
            String status = response.getString("status");
            if (!status.equals("ok")) {
                return cityname;
            }
            JSONObject jsonObject = response.getJSONObject("results");
            JSONArray jsonArray = jsonObject.getJSONArray("address_components");
            int len = jsonArray.length();
            for (int i = 0; i < len; i++) {
                JSONObject itemObject = jsonArray.getJSONObject(i);
                JSONArray typesArray = itemObject.getJSONArray("types");
                int itemLen = typesArray.length();
                for (int j = 0; j < itemLen; j++) {
                    if (typesArray.getString(j).equals("locality")) {

                        cityname = itemObject.getString("long_name");
                        Log.i("cityname", cityname);
                        return cityname;
                    }
                }
            }
        } catch (JSONException e) {
            Log.e("handleGoogelMapError", e.toString());
        }
        return cityname;
    }
}
