package com.gmail.leetao94cn.heweather.model.imodel;


import com.gmail.leetao94cn.heweather.bean.City;

import java.util.ArrayList;

/**
 * Created by leetao on 2016/10/28.
 *
 * @description:
 *      数据库操作的接口
 */

public interface ICityDBModel {
    void insertCity(String id, String city, String prov);
    ArrayList<City> queryAll();
    String queryIdByCity(String city);
    boolean isCityListExist();
}
