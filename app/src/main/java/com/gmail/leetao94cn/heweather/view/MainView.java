package com.gmail.leetao94cn.heweather.view;

/**
 * Created by leetao on 2016/11/4.
 */

public interface MainView {
    void showProgress();
    void hideProgreess();
    void getCityListSuccess();
    void getCityListFail();
    void setCityNameSuccess(String cityname);
    void setCityNameFail();
    void navigateToDetails();
}
