package com.gmail.leetao94cn.heweather.model.imodel;

/**
 * Created by leetao on 2016/11/7.
 *
 *  @description gis 接口,根据 gis 获取当前城市名
 */

public interface IGisModel {
    void getCityNameByGis(String longitude,String latitude,final BaseGisModelCallBack baseGisModelCallBack);
}
