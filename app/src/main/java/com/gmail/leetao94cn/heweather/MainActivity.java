package com.gmail.leetao94cn.heweather;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.gmail.leetao94cn.heweather.adapter.SearchAdapter;
import com.gmail.leetao94cn.heweather.presenter.MainPresenter;
import com.gmail.leetao94cn.heweather.presenter.MainPresenterImpl;
import com.gmail.leetao94cn.heweather.view.MainView;
import com.search.material.library.MaterialSearchView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MainView {

    private ProgressBar mProgressBar;
    private MainPresenter mPresenter;
    private MaterialSearchView mSearchView;
    private SearchAdapter mSearchAdapter;
    private double latitude = 0.0;
    private double longitude = 0.0;
    private LocationManager mLocationManager;
    private Location mLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressBar = (ProgressBar) findViewById(R.id.progress);
        mPresenter = new MainPresenterImpl(this, getApplicationContext());
        registerGPS();
        mPresenter.getCityListByHttp();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSearchView = (MaterialSearchView) findViewById(R.id.search_view);
        mSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener(){

            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        mSearchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener(){

            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {

            }
        });

        mSearchView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String[] suggesstion = getResources().getStringArray(R.array.state_array_full);
                ListView listView = (ListView) findViewById(R.id.suggestion_list);
            }
        });

        mSearchAdapter = new SearchAdapter(this);
        mSearchView.setAdapter(mSearchAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        mSearchView.setMenuItem(item);

        return true;
    }

    @Override
    public void onBackPressed() {
        if (mSearchView.isSearchOpen()) {
            mSearchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches != null && matches.size() > 0) {
                String searchWrd = matches.get(0);
                if (!TextUtils.isEmpty(searchWrd)) {
                    mSearchView.setQuery(searchWrd, false);
                }
            }

            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgreess() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void getCityListSuccess() {
        Toast.makeText(getApplicationContext(), "获取城市列表成功",
                Toast.LENGTH_SHORT).show();
    }


    @Override
    public void getCityListFail() {
        Toast.makeText(getApplicationContext(), "获取城市列表失败",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setCityNameSuccess(String cityname) {
        Toast.makeText(getApplicationContext(), cityname, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setCityNameFail() {

    }

    @Override
    public void navigateToDetails() {

    }


    private void registerGPS() {

        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //获取位置信息
        // 需要检查权限,否则编译报错,想抽取成方法都不行,还是会报错
        if ( Build.VERSION.SDK_INT >= 23 &&
                ActivityCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return  ;
        }

        if (mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            mLocation = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }else{
            mLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }

        if(mLocation == null){
            Toast.makeText(getApplicationContext(), "定位失败,尝试手动搜索看看",
                    Toast.LENGTH_SHORT).show();
        }else{
            longitude = mLocation.getLongitude();
            latitude = mLocation.getLatitude();
            mPresenter.getCityNameByGis(Double.toString(longitude),Double.toString(latitude));
        }
    }
}
