package com.gmail.leetao94cn.heweather.model.imodel;


/**
 * Created by leetao on 2016/10/28.
 *
 *  @description:
 *         网络读取数据接口
 */

public interface ICityNetModel {
    void getAllCity(final BaseDBModelCallBack baseDBModelCallBack);
    void getCityWeatherByCity(String city, final BaseModelCallBack baseModelCallBack);
}
