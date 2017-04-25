package com.gmail.leetao94cn.heweather.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by leetao on 2016/10/28.
 */



public class DBOpenHelper extends SQLiteOpenHelper {

    /*
       创建 CityList 表
       字段 id 主键
            city 城市名称
            prov 省份名称
    */
    public static final String CREATE_CITYLIST = "CREATE TABLE CityList ("
            + "id text primary key,"
            + "city text,"
            + "prov text)";

    public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqliteDatabase){
        sqliteDatabase.execSQL(CREATE_CITYLIST);
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        //这里是更新数据库版本时所触发的方法
    }
}
