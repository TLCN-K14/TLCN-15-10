package com.hcmute.trietthao.yourtime.mvp.signIn.presenter;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.hcmute.trietthao.yourtime.base.BasePresenter;
import com.hcmute.trietthao.yourtime.base.IView;
import com.hcmute.trietthao.yourtime.mvp.login.view.LoginActivity;
import com.hcmute.trietthao.yourtime.mvp.signIn.view.ISignInView;
import com.hcmute.trietthao.yourtime.mvp.signIn.view.SignInActivity;

import java.util.HashMap;

public class SignInPresenter extends BasePresenter implements ISignInPresenter {

    public ISignInView getView() {
        return (ISignInView) getIView();
    }

    @Override
    public void login(String email, String passw) {
        if (email == null || email.length() == 0 || passw == null || passw.length() == 0) {
            getView().errorEmptyInput();
            return;
        }
    }
}
