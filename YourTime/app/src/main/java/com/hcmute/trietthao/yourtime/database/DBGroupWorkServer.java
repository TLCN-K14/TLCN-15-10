package com.hcmute.trietthao.yourtime.database;

import android.util.Log;

import com.hcmute.trietthao.yourtime.model.NhomCVModel;
import com.hcmute.trietthao.yourtime.response.InsertGroupWorkResponse;
import com.hcmute.trietthao.yourtime.service.Service;
import com.hcmute.trietthao.yourtime.service.utils.ApiUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lxtri on 11/11/2017.
 */

public class DBGroupWorkServer {

    Service mService;
    GroupWorkListener groupWorkListener;

    public DBGroupWorkServer(GroupWorkListener groupWorkListener ) {
        this.groupWorkListener = groupWorkListener;
    }

    public void insertGroupWork(Integer idnhom,String tennhom, boolean lanhomcanhan){
        mService = ApiUtils.getService();
        Call<InsertGroupWorkResponse> call = mService.insertGroupWork(idnhom,tennhom,lanhomcanhan);
        call.enqueue(new Callback<InsertGroupWorkResponse>() {
            @Override
            public void onResponse(Call<InsertGroupWorkResponse> call, Response<InsertGroupWorkResponse> response) {
                if(response.isSuccessful()) {
                    Log.e("Response",""+response.message());
                    groupWorkListener.getResultInsertGroupWork(true);
                }
                else
                {
                    Log.e("Response. Lỗi: ",response.message());
                    groupWorkListener.getResultInsertGroupWork(false);
                }
            }
            @Override
            public void onFailure(Call<InsertGroupWorkResponse> call, Throwable t) {
                Log.e("Response",t.getMessage());
            }
        });
    }
    // Hàm lấy list user
    public void getListGroupWork(final Integer idnguoidung){
        mService = ApiUtils.getService();
        Call<ArrayList<NhomCVModel>> call = mService.getListGroupWork(idnguoidung);
        Log.e("Response",call.request().url().toString());
        Log.e("idNguoiDung :::::::",idnguoidung.toString());
        call.enqueue(new Callback<ArrayList<NhomCVModel>>() {
            @Override
            public void onResponse(Call<ArrayList<NhomCVModel>> call, Response<ArrayList<NhomCVModel>> response) {
                if(response.isSuccessful()){
                    Log.e("Response","Lấy list groupwork thành công"+response.message());
                    groupWorkListener.getListGroupWork(response.body());
                }else
                    Log.e("Response","Lấy list groupwork thất bại ");
            }
            @Override
            public void onFailure(Call<ArrayList<NhomCVModel>> call, Throwable t) {
                Log.e("Response","Lấy list groupwork thất bại "+t.getMessage());
            }
        });
    }

}
