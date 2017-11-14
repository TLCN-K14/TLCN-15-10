package com.hcmute.trietthao.yourtime.mvp.searchWork.presenter;

import com.hcmute.trietthao.yourtime.database.DBGroupWorkServer;
import com.hcmute.trietthao.yourtime.database.DBWorkServer;
import com.hcmute.trietthao.yourtime.database.GroupWorkListener;
import com.hcmute.trietthao.yourtime.database.WorkListener;
import com.hcmute.trietthao.yourtime.model.CongViecModel;
import com.hcmute.trietthao.yourtime.model.NhomCVModel;
import com.hcmute.trietthao.yourtime.mvp.searchWork.view.ISearchWork;

import java.util.ArrayList;

/**
 * Created by lxtri on 11/14/2017.
 */

public class SearchWorkPresenter implements ISearchWorkPresenter,WorkListener,GroupWorkListener{

    ISearchWork iSearchWork;
    ArrayList<NhomCVModel> mListNhomCV;
    ArrayList<CongViecModel> mListCV;
    DBWorkServer dbWorkServer;
    DBGroupWorkServer dbGroupWorkServer;
    int idnguoidung; String keysearch;
    boolean flag= true;

    public SearchWorkPresenter(ISearchWork iSearchWork){ this.iSearchWork=iSearchWork;}

    public ArrayList<NhomCVModel> getListSearchOnline(){ return mListNhomCV;}

    @Override
    public void getAllWorkSearchOnline(int idnguoidung, String keysearch) {

        mListNhomCV = new ArrayList<NhomCVModel>();
        mListCV = new ArrayList<CongViecModel>();

        iSearchWork.showLoading();

        dbGroupWorkServer = new DBGroupWorkServer(this);
        dbGroupWorkServer.getListGroupWork(idnguoidung);

        this.idnguoidung = idnguoidung;
        this.keysearch = keysearch;
    }

    @Override
    public void getListAllWork(ArrayList<CongViecModel> congViecModelArrayList) {
        mListCV = congViecModelArrayList;
        setupListNhomCV();
    }

    @Override
    public void getListGroupWork(ArrayList<NhomCVModel> listGroupWork) {
        if(listGroupWork==null){
            iSearchWork.getListGroupWorkFail();
        }else{
            NhomCVModel inbox = new NhomCVModel();
            inbox.setTenNhom("Inbox");
            inbox.setIdNhom(0);
            mListNhomCV.add(inbox);
            mListNhomCV.addAll(listGroupWork);

            dbWorkServer = new DBWorkServer(this);
            dbWorkServer.getListAllWorkSearch(idnguoidung,keysearch);
        }
    }

    public void setupListNhomCV(){
        for(int i=0;i<mListNhomCV.size();i++){
            ArrayList<CongViecModel> listCVTemp = new ArrayList<>();
            for(int j=0;j<mListCV.size();j++){
                if(mListNhomCV.get(i).getIdNhom()==mListCV.get(j).getIdNhom()){
                    listCVTemp.add(mListCV.get(j));
                    flag = false;
                }
            }
            mListNhomCV.get(i).setCongViecModels(listCVTemp);
        }
        iSearchWork.hideLoading();
        if(flag && !keysearch.equals(""))
            iSearchWork.getListGroupWorkEmpty();
        else
        {
            flag = true;
            iSearchWork.getListGroupWorkSucess();
        }

    }

    @Override
    public void getResultInsertGroupWork(Boolean isSuccess) {

    }


}
