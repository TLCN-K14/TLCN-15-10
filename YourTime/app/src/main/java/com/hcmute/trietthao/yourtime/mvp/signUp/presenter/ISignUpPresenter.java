package com.hcmute.trietthao.yourtime.mvp.signUp.presenter;

import android.graphics.Bitmap;

import java.util.HashMap;


public interface ISignUpPresenter {
    void checkSignUp(int id, String name, String avatar, String userName, String passW);
    void getListUserP();
}
