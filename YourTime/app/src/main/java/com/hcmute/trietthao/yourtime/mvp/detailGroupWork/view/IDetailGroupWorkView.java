package com.hcmute.trietthao.yourtime.mvp.detailGroupWork.view;

/**
 * Created by lxtri on 11/15/2017.
 */

public interface IDetailGroupWorkView {
    void getWorkByIDGroupSuccess();

    void getWorkByIDGroupFail();

    void showLoading();

    void hideLoading();

    void insertWorkSuccess();

    void insertWorkFail();

}