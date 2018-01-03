package com.hcmute.trietthao.yourtime.mvp.detailWork.presenter;

import com.hcmute.trietthao.yourtime.database.DBRepeatServer;
import com.hcmute.trietthao.yourtime.database.DBWorkServer;
import com.hcmute.trietthao.yourtime.database.GetRepeatListener;
import com.hcmute.trietthao.yourtime.database.GetWorkListener;
import com.hcmute.trietthao.yourtime.database.PostWorkListener;
import com.hcmute.trietthao.yourtime.model.CongViecModel;
import com.hcmute.trietthao.yourtime.model.LoaiNhacNhoModel;
import com.hcmute.trietthao.yourtime.mvp.detailWork.view.IDetailWorkView;

import java.util.ArrayList;

/**
 * Created by lxtri on 12/4/2017.
 */

public class DetailWorkPresenter implements IDetaiWorkPresenter,GetWorkListener,PostWorkListener,
        GetRepeatListener{

    CongViecModel currentWork;
    ArrayList<LoaiNhacNhoModel> loaiNhacNhoModelArrayList;
    IDetailWorkView iDetailWorkView;
    DBWorkServer dbWorkServer;
    DBRepeatServer dbRepeatServer;

    public DetailWorkPresenter(IDetailWorkView iDetailWorkView){
        this.iDetailWorkView = iDetailWorkView;
    }

    public  CongViecModel getDetailWork(){ return currentWork; }

    public  ArrayList<LoaiNhacNhoModel> getListRepeat(){ return loaiNhacNhoModelArrayList; }

    @Override
    public void getDetailWork(int idWork) {
        dbWorkServer = new DBWorkServer(this,this);
        dbWorkServer.getWorkById(idWork);
        dbRepeatServer = new DBRepeatServer(this);
        dbRepeatServer.getListRepeat();
    }

    @Override
    public void updateDetailWork(CongViecModel congViecModel) {
        dbWorkServer = new DBWorkServer(this,this);

    }

    @Override
    public void getResultPostWork(Boolean isSucess) {

    }

    @Override
    public void getListAllWork(ArrayList<CongViecModel> congViecModelArrayList) {
        if(congViecModelArrayList!=null){
            currentWork = congViecModelArrayList.get(0);
            iDetailWorkView.getDetailWorkSuccess();
        }else
            iDetailWorkView.getDetailWorkFail();

    }

    @Override
    public void getListRepeat(ArrayList<LoaiNhacNhoModel> loaiNhacNhoModelArrayList) {
        loaiNhacNhoModelArrayList = loaiNhacNhoModelArrayList;
    }
}
