package com.gmail.leetao94cn.heweather.interactor;

/**
 * Created by user on 2016/11/4.
 */

public interface GetCityListInteractor {
    interface OnLoadFinishedListener{
        void OnCityListFail();
        void OnCityListSuccess();
        void OnCityNameSuccess(String cityname);
        void OnCityNameFail();
    }

    void loadCityList(OnLoadFinishedListener listener);
    void loadCityName(String longitude,String latitude,OnLoadFinishedListener listener);
}
