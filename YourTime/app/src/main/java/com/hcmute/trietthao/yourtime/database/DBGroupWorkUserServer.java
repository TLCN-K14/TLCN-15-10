package com.hcmute.trietthao.yourtime.database;

import android.util.Log;

import com.hcmute.trietthao.yourtime.response.InsertGroupWorkUserReponse;
import com.hcmute.trietthao.yourtime.service.Service;
import com.hcmute.trietthao.yourtime.service.utils.ApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lttha on 11/28/2017.
 */

public class DBGroupWorkUserServer {
    Service mService;
    PostGroupWorkUserListener postGroupWork_userListener;
    public DBGroupWorkUserServer(PostGroupWorkUserListener postGroupWork_userListener) {
        this.postGroupWork_userListener = postGroupWork_userListener;
    }

    public void insertGroupWorkUser(Integer idnhom, Integer idnguoidung,String vaitro){
        mService = ApiUtils.getService();
        Call<InsertGroupWorkUserReponse> call = mService.insertWorkGroupUser(idnhom,idnguoidung,vaitro);
        call.enqueue(new Callback<InsertGroupWorkUserReponse>() {
            @Override
            public void onResponse(Call<InsertGroupWorkUserReponse> call, Response<InsertGroupWorkUserReponse> response) {
                if(response.isSuccessful()) {
                    postGroupWork_userListener.getResultPostGroupWorkUser(true);
                    Log.e("Response",""+response.message());
                }
                else
                {
                    postGroupWork_userListener.getResultPostGroupWorkUser(false);
                    Log.e("Response. Lỗi: ",response.message());
                }
            }
            @Override
            public void onFailure(Call<InsertGroupWorkUserReponse> call, Throwable t) {
                Log.e("Response",t.getMessage());
            }
        });
    }
//    // Hàm lấy list group work
//    public void getListGroupWork(final Integer idnguoidung){
//        mService = ApiUtils.getService();
//        Call<ArrayList<NhomCVModel>> call = mService.getListGroupWork(idnguoidung);
//        Log.e("Response",call.request().url().toString());
//        Log.e("idNguoiDung :::::::",idnguoidung.toString());
//        call.enqueue(new Callback<ArrayList<NhomCVModel>>() {
//            @Override
//            public void onResponse(Call<ArrayList<NhomCVModel>> call, Response<ArrayList<NhomCVModel>> response) {
//                if(response.isSuccessful()){
//                    Log.e("Response","Lấy list groupwork thành công"+response.message());
//                    getGroupWorkListener.getListGroupWork(response.body());
//                }else
//                    Log.e("Response","Lấy list groupwork thất bại ");
//            }
//            @Override
//            public void onFailure(Call<ArrayList<NhomCVModel>> call, Throwable t) {
//                Log.e("Response","Lấy list groupwork thất bại "+t.getMessage());
//            }
//        });
//    }
//    // Hàm lấy list user
//    public void getGroupWorkById(final Integer idnhom){
//        mService = ApiUtils.getService();
//        Call<ArrayList<NhomCVModel>> call = mService.getGroupWorkById(idnhom);
//        Log.e("Response",call.request().url().toString());
//        Log.e("idnhom :::::::",idnhom.toString());
//        call.enqueue(new Callback<ArrayList<NhomCVModel>>() {
//            @Override
//            public void onResponse(Call<ArrayList<NhomCVModel>> call, Response<ArrayList<NhomCVModel>> response) {
//                if(response.isSuccessful()){
//                    Log.e("Response","Lấy groupwork thành công"+response.message());
//                    getGroupWorkByIDListener.getGroupWorkById(response.body().get(0));
//                }else
//                    Log.e("Response","Lấy groupwork thất bại ");
//            }
//            @Override
//            public void onFailure(Call<ArrayList<NhomCVModel>> call, Throwable t) {
//                Log.e("Response","Lấy list groupwork thất bại "+t.getMessage());
//            }
//        });
//    }
}
