package com.hcmute.trietthao.yourtime.mvp.searchWork.view;

/**
 * Created by lxtri on 11/14/2017.
 */

public interface ISearchWork {
    void getListGroupWorkSucess();

    void getListGroupWorkEmpty();

    void getListGroupWorkFail();

    void showLoading();

    void hideLoading();
}
