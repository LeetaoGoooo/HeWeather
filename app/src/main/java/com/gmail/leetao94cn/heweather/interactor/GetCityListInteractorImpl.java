package com.gmail.leetao94cn.heweather.interactor;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.gmail.leetao94cn.heweather.model.CityNetModel;
import com.gmail.leetao94cn.heweather.model.GisModel;
import com.gmail.leetao94cn.heweather.model.imodel.BaseDBModelCallBack;
import com.gmail.leetao94cn.heweather.model.imodel.BaseGisModelCallBack;
import com.gmail.leetao94cn.heweather.model.imodel.ICityNetModel;
import com.gmail.leetao94cn.heweather.model.imodel.IGisModel;

/**
 * Created by leetao on 2016/11/4.
 */

public class GetCityListInteractorImpl implements GetCityListInteractor{

    private ICityNetModel mICityNetModel;
    private IGisModel mGisModel;

    public GetCityListInteractorImpl(Context context){
        this.mICityNetModel = new CityNetModel(context);
        this.mGisModel = new GisModel(context);
    }

    @Override
    public void loadCityList(final OnLoadFinishedListener listener) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mICityNetModel.getAllCity(new BaseDBModelCallBack() {
                    @Override
                    public void onResponse(Boolean response) {
                        if (response){
                            listener.OnCityListSuccess();
                        }else{
                            listener.OnCityListFail();
                        }
                    }
                });
            }
        },2000);

    }

    @Override
    public void loadCityName(final String longitude,final String latitude,final OnLoadFinishedListener listener) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mGisModel.getCityNameByGis(longitude, latitude,new BaseGisModelCallBack() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("GISCITYNAME","reponse");
                        if (response != null){
                            listener.OnCityNameSuccess(response);
                        }else {
                            listener.OnCityNameFail();
                        }
                    }
                });
            }
        },2000);
    }
}
