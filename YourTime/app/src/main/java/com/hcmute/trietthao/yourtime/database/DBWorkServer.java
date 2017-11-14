package com.hcmute.trietthao.yourtime.database;

import android.util.Log;

import com.hcmute.trietthao.yourtime.model.CongViecModel;
import com.hcmute.trietthao.yourtime.service.Service;
import com.hcmute.trietthao.yourtime.service.utils.ApiUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lxtri on 11/11/2017.
 */

public class DBWorkServer {

    Service mService;
    WorkListener workListener;

    public DBWorkServer(WorkListener workListener ) {
        this.workListener = workListener;
    }

    // Hàm lấy list user
    public void getListAllWork(final Integer idnguoidung){
        mService = ApiUtils.getService();
        Call<ArrayList<CongViecModel>> call = mService.getListAllWork(idnguoidung);
        Log.e("Response",call.request().url().toString());
        Log.e("idNguoiDung :::::::",idnguoidung.toString());
        call.enqueue(new Callback<ArrayList<CongViecModel>>() {
            @Override
            public void onResponse(Call<ArrayList<CongViecModel>> call, Response<ArrayList<CongViecModel>> response) {
                if(response.isSuccessful()){
                    Log.e("Response","Lấy list work thành công"+response.message());
                    workListener.getListAllWork(response.body());
                }else
                    Log.e("Response","Lấy list work thất bại ");
            }
            @Override
            public void onFailure(Call<ArrayList<CongViecModel>> call, Throwable t) {
                Log.e("Response","Lấy list work thất bại "+t.getMessage());
            }
        });
    }

    public void getListAllWorkSearch(final Integer idnguoidung,String keysearch){
        mService = ApiUtils.getService();
        Call<ArrayList<CongViecModel>> call = mService.getListAllWorkSearch(idnguoidung,keysearch);
        Log.e("Response",call.request().url().toString());
        Log.e("idNguoiDung :::::::",idnguoidung.toString());
        call.enqueue(new Callback<ArrayList<CongViecModel>>() {
            @Override
            public void onResponse(Call<ArrayList<CongViecModel>> call, Response<ArrayList<CongViecModel>> response) {
                if(response.isSuccessful()){
                    Log.e("Response","Lấy list work search thành công"+response.message());
                    workListener.getListAllWork(response.body());
                }else
                    Log.e("Response","Lấy list work search thất bại ");
            }
            @Override
            public void onFailure(Call<ArrayList<CongViecModel>> call, Throwable t) {
                Log.e("Response","Lấy list work search thất bại "+t.getMessage());
            }
        });
    }

}
