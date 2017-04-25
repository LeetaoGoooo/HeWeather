package com.gmail.leetao94cn.heweather.presenter;

/**
 * Created by leetao on 2016/11/4.
 */

public interface MainPresenter {
    void getCityListByHttp();
    void getCityNameByGis(String longitude, String latitude);
}
