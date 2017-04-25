package com.gmail.leetao94cn.heweather.presenter;

import android.content.Context;

import com.gmail.leetao94cn.heweather.interactor.GetCityListInteractor;
import com.gmail.leetao94cn.heweather.interactor.GetCityListInteractorImpl;
import com.gmail.leetao94cn.heweather.view.MainView;

/**
 * Created by leetao on 2016/11/4.
 */

public class MainPresenterImpl implements MainPresenter,GetCityListInteractor.OnLoadFinishedListener {

    private MainView mMainView;
    private GetCityListInteractor mGetCityListInteractor;

    public MainPresenterImpl(MainView mainView, Context context){
        this.mMainView = mainView;
        this.mGetCityListInteractor = new GetCityListInteractorImpl(context);
    }


    @Override
    public void getCityListByHttp() {
        if (mMainView != null){
            mMainView.showProgress();
        }
        mGetCityListInteractor.loadCityList(this);
    }

    @Override
    public void getCityNameByGis(String longitude, String latitude) {
        if (mMainView != null){
            mMainView.showProgress();
        }
        mGetCityListInteractor.loadCityName(longitude,latitude,this);
    }

    @Override
    public void OnCityListFail() {
        if (mMainView != null){
            mMainView.hideProgreess();
            mMainView.getCityListFail();
        }
    }

    @Override
    public void OnCityListSuccess() {
        if (mMainView != null){
            mMainView.hideProgreess();
        }
        mMainView.getCityListSuccess();
    }

    @Override
    public void OnCityNameFail() {
        if (mMainView != null){
            mMainView.setCityNameFail();
        }
    }

    @Override
    public void OnCityNameSuccess(String cityname) {
        if (mMainView != null){
            mMainView.setCityNameSuccess(cityname);
        }
    }
}
