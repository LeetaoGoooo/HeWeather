package com.gmail.leetao94cn.heweather.model;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.gmail.leetao94cn.heweather.model.imodel.BaseDBModelCallBack;
import com.gmail.leetao94cn.heweather.model.imodel.BaseModelCallBack;
import com.gmail.leetao94cn.heweather.model.imodel.ICityNetModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by leetao on 2016/10/28.
 */

public class CityNetModel implements ICityNetModel {

    private static final String WEATHER_KEY = "88fdd878e62c416e93ca0ef510b71677";
    private static final String CITYLIST_URL = "https://api.heweather.com/x3/citylist?search=allchina&key=";
    private static final String CITYWEATHER_URL = "https://api.heweather.com/x3/weather?cityid=";

    private CityDBModel mCityDBModel;
    private JsonObjectRequest mObjectRequest;
    private RequestQueue mRequestQueue;

    public  CityNetModel(Context context){
        this.mCityDBModel = new CityDBModel(context);
        this.mRequestQueue = Volley.newRequestQueue(context);
    }

    @Override
    public void getAllCity(final BaseDBModelCallBack baseDBModelCallBack) {
        if (mCityDBModel.isCityListExist()){
                Log.i("DATABASE","DATABASE is Exist");
                baseDBModelCallBack.onResponse(true);
        }else {
            String url = CITYLIST_URL + WEATHER_KEY;
            mObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        String status = response.getString("status");
                        if (status.equals("ok")){
                            if (saveCityIntoDB(response)){
                                baseDBModelCallBack.onResponse(true);
                            }else{
                                baseDBModelCallBack.onResponse(false);
                            }
                        }
                    } catch (JSONException e) {
                        Log.e("GetStatusError",e.toString());
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("JSONObjectRequestError",error.getMessage(),error);
                }
            });
            mRequestQueue.add(mObjectRequest);
        }
    }



    @Override
    public void getCityWeatherByCity(String city, BaseModelCallBack baseModelCallBack) {
        String id = getCityIdByCity(city);
        String url = CITYWEATHER_URL + id + "&key=" + WEATHER_KEY;
        mObjectRequest = new JsonObjectRequest(url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String status = response.getString("status");
                    if (status.equals("ok")){

                    }
                } catch (JSONException e) {
                    Log.e("JSONException",e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("getWeatherByCityError",error.getMessage(),error);
            }
        });
    }


    /**
     * 将城市信息保存到数据库中
     *
     * @param jsonObject json格式对象
     *
     * @return boolean 成功返回true,否则返回false
     */
    private boolean saveCityIntoDB(JSONObject jsonObject){
        try {
            JSONArray jsonArray =  jsonObject.getJSONArray("city_info");
            int len = jsonArray.length();
            for (int i = 0; i < len; i++){
                JSONObject object = (JSONObject) jsonArray.get(i);
                String id =  object.getString("id");
                String city = object.getString("city");
                String prov = object.getString("prov");
                mCityDBModel.insertCity(id,city,prov);
            }
            return true;
        } catch (JSONException e) {
            Log.e("SaveCityIntoDB",e.toString());
            return false;
        }
    }



    /**
     * 根据城市名称获取城市 id
     *
     * @param city 城市名称
     *
     * @return string 城市id
     */
    private String getCityIdByCity(String city){
        String id = mCityDBModel.queryIdByCity(city);
        return id;
    }


}
