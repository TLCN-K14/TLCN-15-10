package com.hcmute.trietthao.yourtime.database;

import android.util.Log;

import com.hcmute.trietthao.yourtime.model.CongViecModel;
import com.hcmute.trietthao.yourtime.response.InsertUpdateWorkResponse;
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
    GetWorkListener getWorkListener;
    PostWorkListener postWorkListener;

    public DBWorkServer(GetWorkListener getWorkListener) {
        this.getWorkListener = getWorkListener;
    }
    public DBWorkServer(PostWorkListener postWorkListener ) {
        this.postWorkListener = postWorkListener;
    }
    public DBWorkServer(PostWorkListener postWorkListener,GetWorkListener getWorkListener ) {
            this.postWorkListener = postWorkListener;
            this.getWorkListener = getWorkListener;
        }

    // Hàm lấy list user
    public void getListAllWork(final Integer idnguoidung){
        mService = ApiUtils.getService();
        Call<ArrayList<CongViecModel>> call = mService.getListAllWork(idnguoidung);
        Log.e("Response",call.request().url().toString());
        call.enqueue(new Callback<ArrayList<CongViecModel>>() {
            @Override
            public void onResponse(Call<ArrayList<CongViecModel>> call, Response<ArrayList<CongViecModel>> response) {
                if(response.isSuccessful()){
                    Log.e("Response","Lấy list work thành công"+response.message());
                    getWorkListener.getListAllWork(response.body());
                }else
                    Log.e("Response","Lấy list work thất bại "+response.message());
            }
            @Override
            public void onFailure(Call<ArrayList<CongViecModel>> call, Throwable t) {
                Log.e("Response","Lấy list work thất bại "+t.getMessage());
            }
        });
    }

    public void getListWorkByIdGroup( Integer idnguoidung,final Integer idnhom){
        mService = ApiUtils.getService();
        Call<ArrayList<CongViecModel>> call = mService.getListWorkByIdGroup(idnguoidung,idnhom);
        Log.e("Response",call.request().url().toString());
        call.enqueue(new Callback<ArrayList<CongViecModel>>() {
            @Override
            public void onResponse(Call<ArrayList<CongViecModel>> call, Response<ArrayList<CongViecModel>> response) {
                if(response.isSuccessful()){
                    Log.e("Response","Lấy list work thành công"+response.message());
                    getWorkListener.getListAllWork(response.body());
                }else
                    Log.e("Response","Lấy list work thất bại "+response.message());
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
        call.enqueue(new Callback<ArrayList<CongViecModel>>() {
            @Override
            public void onResponse(Call<ArrayList<CongViecModel>> call, Response<ArrayList<CongViecModel>> response) {
                if(response.isSuccessful()){
                    Log.e("Response","Lấy list work search thành công"+response.message());
                    getWorkListener.getListAllWork(response.body());
                }else
                    Log.e("Response","Lấy list work search thất bại "+response.message());
            }
            @Override
            public void onFailure(Call<ArrayList<CongViecModel>> call, Throwable t) {
                Log.e("Response","Lấy list work search thất bại "+t.getMessage());
            }
        });
    }

    public void updateStatusWorkTimeNotNull(final String trangthai,Integer idcongviec,String thoigianbatdau){
        mService = ApiUtils.getService();
        Call<InsertUpdateWorkResponse> call = mService.updateStatusWorkTimeNotNull(trangthai,idcongviec,thoigianbatdau);
        call.enqueue(new Callback<InsertUpdateWorkResponse>() {
            @Override
            public void onResponse(Call<InsertUpdateWorkResponse> call, Response<InsertUpdateWorkResponse> response) {
                if(response.isSuccessful()){
                    postWorkListener.getResultPostWork(true);
                    Log.e("Response","Update status work time not null thành công"+response.message());
                }else
                {
                    postWorkListener.getResultPostWork(false);
                    Log.e("Response","Update status work time not null thất bại "+response.message());
                }
            }
            @Override
            public void onFailure(Call<InsertUpdateWorkResponse> call, Throwable t) {
                Log.e("Response","Update status work time not null thất bại "+t.getMessage());
            }
        });
    }

    public void updateStatusWork(final String trangthai,Integer idcongviec){
        mService = ApiUtils.getService();
        Call<InsertUpdateWorkResponse> call = mService.updateStatusWork(trangthai,idcongviec);
        call.enqueue(new Callback<InsertUpdateWorkResponse>() {
            @Override
            public void onResponse(Call<InsertUpdateWorkResponse> call, Response<InsertUpdateWorkResponse> response) {
                if(response.isSuccessful()){
                    postWorkListener.getResultPostWork(true);
                    Log.e("Response","Update status work thành công"+response.message());
                }else
                {
                    postWorkListener.getResultPostWork(false);
                    Log.e("Response","Update status work thất bại "+response.message());
                }
            }
            @Override
            public void onFailure(Call<InsertUpdateWorkResponse> call, Throwable t) {
                Log.e("Response","Update status work thất bại "+t.getMessage());
            }
        });
    }

    public void updatePriorityWork(final Integer couutien,Integer idcongviec){
        mService = ApiUtils.getService();
        Call<InsertUpdateWorkResponse> call = mService.updatePriorityWork(couutien,idcongviec);
        call.enqueue(new Callback<InsertUpdateWorkResponse>() {
            @Override
            public void onResponse(Call<InsertUpdateWorkResponse> call, Response<InsertUpdateWorkResponse> response) {
                if(response.isSuccessful()){
                    postWorkListener.getResultPostWork(true);
                    Log.e("Response","Update priority work thành công"+response.message());
                }else
                {
                    postWorkListener.getResultPostWork(false);
                    Log.e("Response","Update priority work thất bại "+response.message());
                }
            }
            @Override
            public void onFailure(Call<InsertUpdateWorkResponse> call, Throwable t) {
                Log.e("Response","Update priority work thất bại "+t.getMessage());
            }
        });
    }

    public void insertWork(CongViecModel congViecModel){
        mService = ApiUtils.getService();
        Call<InsertUpdateWorkResponse> call = mService.insertWork(congViecModel.getIdCongViec(),congViecModel.getTenCongViec(),
                congViecModel.getThoiGianBatDau(),congViecModel.getThoiGianKetThuc(),congViecModel.getGhiChu(),
                congViecModel.getFileDinhKem(),congViecModel.getCoUuTien(),congViecModel.getIdNhom(),
                congViecModel.getIdNhacNho(),congViecModel.getIdNguoiTaoCV(),
                congViecModel.getIdNguoiDuocGiaoCV(),congViecModel.getTrangThai());
        call.enqueue(new Callback<InsertUpdateWorkResponse>() {
            @Override
            public void onResponse(Call<InsertUpdateWorkResponse> call, Response<InsertUpdateWorkResponse> response) {
                if(response.isSuccessful()){
                    postWorkListener.getResultPostWork(true);
                    Log.e("Response","Insert work thành công"+response.message());
                }else
                {
                    postWorkListener.getResultPostWork(false);
                    Log.e("Response","Insert  work thất bại "+response.message());
                }
            }
            @Override
            public void onFailure(Call<InsertUpdateWorkResponse> call, Throwable t) {
                Log.e("Response","Insert  work thất bại "+t.getMessage());
            }
        });
    }

}
