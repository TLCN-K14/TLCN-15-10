package com.hcmute.trietthao.yourtime.mvp.signUp.view;


import com.hcmute.trietthao.yourtime.model.NguoiDungModel;

import java.util.ArrayList;

public interface ISignUpView  {
    void signUpSuccess();
    void signUpFail();
    void showToast(String message);
    void getIntentData();
}
