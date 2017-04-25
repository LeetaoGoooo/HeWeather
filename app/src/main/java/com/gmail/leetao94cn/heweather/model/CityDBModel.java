package com.gmail.leetao94cn.heweather.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.gmail.leetao94cn.heweather.bean.City;
import com.gmail.leetao94cn.heweather.model.imodel.ICityDBModel;

import java.util.ArrayList;

/**
 * Created by leetao on 2016/10/28.
 *
 * @description:
 *      数据库的插入以及查询
 */

public class CityDBModel implements ICityDBModel {

    private DBOpenHelper dbOpenHelper;
    private SQLiteDatabase sqliteDatabase;

    public CityDBModel(Context context){
        this.dbOpenHelper = new DBOpenHelper(context, "city", null, 0);
        this.sqliteDatabase = dbOpenHelper.getWritableDatabase();
    }

    @Override
    public void insertCity(String id, String city, String prov) {
        String sql = "INSERT INTO CityList(id,city,prov) VALUES(?,?,?)";
        Object bindArgs[] = new Object[] {id,city,prov};
        sqliteDatabase.execSQL(sql,bindArgs);
    }

    @Override
    public ArrayList<City> queryAll() {
        ArrayList<City> cityArrayList = new ArrayList();
        String sql = "SELECT id,city,prov FROM CityList";
        Cursor cursor = sqliteDatabase.rawQuery(sql,null);

        for(cursor.moveToFirst(); !(cursor.isAfterLast()); cursor.moveToNext()){
            City city = new City();
            city.setId(cursor.getString(cursor.getColumnIndex("id")));
            city.setCity(cursor.getString(cursor.getColumnIndex("city")));
            city.setProv(cursor.getString(cursor.getColumnIndex("prov")));
            cityArrayList.add(city);
        }
        return cityArrayList;
    }

    @Override
    public String queryIdByCity(String city) {
        String sql = "SELECT id,city,prov FROM CityList WHERE city = ?";
        String bindArgs[] = new String[] {city};
        Cursor cursor = sqliteDatabase.rawQuery(sql,bindArgs);

        if( cursor.moveToNext() ){
            City cityObj = new City();
            cityObj.setId(cursor.getString(cursor.getColumnIndex("id")));
            cityObj.setCity(cursor.getString(cursor.getColumnIndex("city")));
            cityObj.setProv(cursor.getString(cursor.getColumnIndex("prov")));
            return cityObj.getId();
        }
        return null;
    }

    @Override
    public boolean isCityListExist() {
        String sql = "SELECT * FROM CityList";
        Cursor cursor = sqliteDatabase.rawQuery(sql,null);
        boolean flag = cursor.moveToFirst();
        return flag;
    }
}
