package com.hcmute.trietthao.yourtime.mvp.signIn.presenter;



import com.hcmute.trietthao.yourtime.base.BasePresenter;
import com.hcmute.trietthao.yourtime.model.NguoiDungModel;
import com.hcmute.trietthao.yourtime.mvp.signIn.view.ISignInView;
import com.hcmute.trietthao.yourtime.database.DBNguoiDungServer;

import java.util.ArrayList;


public class SignInPresenter extends BasePresenter implements ISignInPresenter, DBNguoiDungServer.userListener {

    DBNguoiDungServer dbNguoiDungServer;
    NguoiDungModel nguoiDungModel=null;
    ISignInView isignInView;
    String email="", passw="";



    public SignInPresenter(ISignInView iSignInView) {
        this.isignInView=iSignInView;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }



    @Override
    public void checkLogin(String email, String passw) {
        dbNguoiDungServer=new DBNguoiDungServer(this);
        this.email=email;
        this.passw=passw;
        dbNguoiDungServer.getListUser(); // ok.override của retrofit mún chạy đx phải   có hàm sử dụng nó ra chứ. chạy lại đi

    }


    @Override
    public void getListUser(ArrayList<NguoiDungModel> listUser) {
        nguoiDungModel=listUser.get(0);
        if(nguoiDungModel.getUserName().equals(email) || nguoiDungModel.getPassW().equals(passw))
        {
            isignInView.loginSuccess();
        }else {
            isignInView.loginFail();
        }

    }

    @Override
    public void getResultInsert(Boolean isSuccess) {

    }

    @Override
    public void getUser(NguoiDungModel user) {

    }
}
