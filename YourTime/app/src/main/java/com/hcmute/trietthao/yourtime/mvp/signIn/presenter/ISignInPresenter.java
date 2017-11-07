package com.hcmute.trietthao.yourtime.mvp.signIn.presenter;

import java.util.HashMap;

public interface ISignInPresenter {
    void createUserLoginSession(String email, String passw);
    boolean checkLogin();
    HashMap<String,String> getUserDetails();
    void logoutUser();
    boolean isUserLoggedIn();
}
