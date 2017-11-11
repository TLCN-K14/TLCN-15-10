package com.hcmute.trietthao.yourtime.mvp.signIn.presenter;



import android.app.Application;
import android.widget.Toast;

import com.hcmute.trietthao.yourtime.base.BasePresenter;
import com.hcmute.trietthao.yourtime.model.NguoiDungModel;
import com.hcmute.trietthao.yourtime.mvp.signIn.view.ISignInView;
import com.hcmute.trietthao.yourtime.database.DBNguoiDungServer;

import java.util.ArrayList;
import java.util.List;

public class SignInPresenter extends BasePresenter implements ISignInPresenter {
    private List<Object> mList;
    DBNguoiDungServer mDBNguoiDungServer;
    NguoiDungModel nguoiDungModel;


    public ISignInView getView() {
        return (ISignInView) getIView();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mList = new ArrayList<>();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public List<Object> getListUser() {
        return mList;
    }


    @Override
    public void login(String email, String passw) {
        nguoiDungModel= new NguoiDungModel();
        if (!isViewAttached()) return;
        if (email == null || email.length() == 0 || passw == null || passw.length() == 0) {
            getView().errorEmptyInput();
            return;
        }
        if(nguoiDungModel.getUserName().equals(email) || nguoiDungModel.getPassW().equals(passw))
        {  // Kiểm tra email và pass có trùng khớp với tài khoản hiện tại
            mDBNguoiDungServer.getNguoiDung(email);
        }

    }

    @Override
    public void getUser() {
        if (!isViewAttached()) return;


    }
}
