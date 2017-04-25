package com.gmail.leetao94cn.heweather.bean;

/**
 * Created by leetao on 2016/10/28.
 */

public class City{
    private String id;
    private String city;
    private String prov;

    public String getId(){
        return id;
    }

    public String getCity(){
        return city;
    }

    public String getProv(){
        return prov;
    }

    public void setId(String id){
        this.id = id;
    }

    public void setCity(String city){
        this.city = city;
    }

    public void setProv(String prov){
        this.prov = prov;
    }
}