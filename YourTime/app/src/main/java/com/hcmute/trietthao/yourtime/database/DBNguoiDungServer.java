package com.hcmute.trietthao.yourtime.database;

import android.util.Log;

import com.hcmute.trietthao.yourtime.model.NguoiDungModel;
import com.hcmute.trietthao.yourtime.prefer.PreferManager;
import com.hcmute.trietthao.yourtime.response.InsertUserResponse;
import com.hcmute.trietthao.yourtime.service.Service;
import com.hcmute.trietthao.yourtime.service.utils.APIUtils;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DBNguoiDungServer {

    private Service mService;
    public  interface userListener{
        void  getListUser(ArrayList<NguoiDungModel> listUser);// Model j vayaj ItemWhere
        void  getResultInsert(Boolean isSuccess);
        void  getUser(NguoiDungModel user);
    }

    userListener userListener;

    public DBNguoiDungServer(userListener userListener ) {
        this.userListener = userListener;
    }


    // Hàm insert user chỗ đăng ký
    public void insertNguoiDung(String name,String anhdaidien, String username, String pass){
        mService = APIUtils.getService();
        Call<InsertUserResponse> call = mService.insertNguoiDung(name,anhdaidien,username,pass);
        Log.e("Response",call.request().url().toString());
        Log.e("Response name:::::",name);
        Log.e("Response anh:::::",anhdaidien);
        Log.e("Response username:::::",username);
        Log.e("Response pass:::::",pass);
        call.enqueue(new Callback<InsertUserResponse>() {
            @Override
            public void onResponse(Call<InsertUserResponse> call, Response<InsertUserResponse> response) {
                if(response.isSuccessful()) {
                    Log.e("Response",""+response.message());
                    userListener.getResultInsert(true);
                }
                else
                {
                    Log.e("Response. Lỗi: ",response.message());
                    userListener.getResultInsert(false);
                }
            }
            @Override
            public void onFailure(Call<InsertUserResponse> call, Throwable t) {
                Log.e("Response",t.getMessage());
            }
        });
    }
    // Hàm lấy list user
    public void getListNguoiDung(){
        mService = APIUtils.getService();
        Call<ArrayList<NguoiDungModel>> call = mService.getListNguoiDung();
        Log.e("Response",call.request().url().toString());
        call.enqueue(new Callback<ArrayList<NguoiDungModel>>() {
            @Override
            public void onResponse(Call<ArrayList<NguoiDungModel>> call, Response<ArrayList<NguoiDungModel>> response) {
                userListener.getListUser(response.body());
                Log.e("","Lấy list user thành công");
            }

            @Override
            public void onFailure(Call<ArrayList<NguoiDungModel>> call, Throwable t) {
                Log.e("","Lấy list user thất bại "+t.getMessage());
            }
        });
    }

    // ==== Login ==== //
    public void getNguoiDung(String mail){
        mService = APIUtils.getService();
        Call<ArrayList<NguoiDungModel>> call = mService.getNguoiDung(mail);
        Log.e("Response",call.request().url().toString());
        call.enqueue(new Callback<ArrayList<NguoiDungModel>>() {
            @Override
            public void onResponse(Call<ArrayList<NguoiDungModel>> call, Response<ArrayList<NguoiDungModel>> response) {
                if(response.isSuccessful()) {
                    userListener.getUser(response.body().get(0));
                    Log.e("Response","lay duoc "+response.body().size());
                }
                else
                    Log.e("Response","khong lay duoc");
            }
            @Override
            public void onFailure(Call<ArrayList<NguoiDungModel>> call, Throwable t) {
                Log.e("Response",t.getMessage());
            }
        });
    }


}