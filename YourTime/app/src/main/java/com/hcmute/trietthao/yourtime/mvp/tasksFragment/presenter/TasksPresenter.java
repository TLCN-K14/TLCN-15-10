package com.hcmute.trietthao.yourtime.mvp.tasksFragment.presenter;

import android.os.Handler;
import android.util.Log;

import com.hcmute.trietthao.yourtime.database.DBGroupWorkServer;
import com.hcmute.trietthao.yourtime.database.DBWorkServer;
import com.hcmute.trietthao.yourtime.database.GetGroupWorkListener;
import com.hcmute.trietthao.yourtime.database.GetWorkListener;
import com.hcmute.trietthao.yourtime.database.GetWorkNotificationListener;
import com.hcmute.trietthao.yourtime.database.PostWorkListener;
import com.hcmute.trietthao.yourtime.model.CVThongBaoModel;
import com.hcmute.trietthao.yourtime.model.CongViecModel;
import com.hcmute.trietthao.yourtime.model.NhomCVModel;
import com.hcmute.trietthao.yourtime.mvp.tasksFragment.view.ITasksView;
import com.hcmute.trietthao.yourtime.service.Service;

import java.text.ParseException;
import java.util.ArrayList;

import static com.hcmute.trietthao.yourtime.service.utils.DateUtils.getDateTimeToInsertUpdate;
import static com.hcmute.trietthao.yourtime.service.utils.DateUtils.isOverDueDate;


public class TasksPresenter  implements ITasksPresenter,GetGroupWorkListener,GetWorkListener,
        PostWorkListener,GetWorkNotificationListener {

    ITasksView iTasksView;
    private Service mService;
    private static ArrayList<NhomCVModel> mList;
    private static ArrayList<CongViecModel> mListWork;
    DBGroupWorkServer dbGroupWorkServer;
    DBWorkServer dbWorkServer;

    public TasksPresenter(ITasksView iTasksView){
        this.iTasksView = iTasksView;
    }

    public ArrayList<NhomCVModel> getListGroupWorkOnline(){ return mList;}
    public ArrayList<CongViecModel> getListAllWorkOnline(){ return mListWork;}

    @Override
    public void getAllGroupWorkOnline(int idnguoidung) {
        iTasksView.showLoading();
        dbGroupWorkServer = new DBGroupWorkServer(this);
        dbGroupWorkServer.getListGroupWork(idnguoidung);
    }

    @Override
    public void getAllWorkOnline(int idnguoidung) {
        Log.e("TaskPresenter","Vaoo get all workonline");
        dbWorkServer = new DBWorkServer(this,this,this);
        dbWorkServer.getListAllWork(idnguoidung);
        dbWorkServer.getListAllWorkNotification(idnguoidung);
    }

    @Override
    public void getListGroupWork(final ArrayList<NhomCVModel> listGroupWork) {
        mList = new ArrayList<>();
        final Handler handler = new Handler();
        if(listGroupWork!=null){
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mList = listGroupWork;
                    iTasksView.hideLoading();
                    iTasksView.getAllGroupWorkSuccess();
                }
            }, 1000);
        }
    }

    @Override
    public void getListAllWork(ArrayList<CongViecModel> congViecModelArrayList) {
        for(int i=0;i<congViecModelArrayList.size();i++){
            if(congViecModelArrayList.get(i).getThoiGianBatDau()!=null){
                try {
                    if(congViecModelArrayList.get(i).getTrangThai().equals("waiting")){
                        if(isOverDueDate(congViecModelArrayList.get(i).getThoiGianBatDau())){
                            congViecModelArrayList.get(i).setTrangThai("overdue");
                            dbWorkServer.updateStatusWork("overdue",congViecModelArrayList.get(i).getIdCongViec());
                        }
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        mListWork = congViecModelArrayList;
        iTasksView.getListAllWorkSucess();
    }

    @Override
    public void getResultPostWork(Boolean isSucess) {

    }

    @Override
    public void getAllWorkNotification(ArrayList<CVThongBaoModel> congViecModelArrayList) {
        for(int i=0;i<congViecModelArrayList.size();i++){
            if(congViecModelArrayList.get(i).getThoiGianBatDau()!=null){
                try {
                    if(congViecModelArrayList.get(i).getTrangThai().equals("waiting")){
                        if(isOverDueDate(congViecModelArrayList.get(i).getThoiGianBatDau())){
                            dbWorkServer.updateStatusWorkTimeNotNull("overdue",
                                    congViecModelArrayList.get(i).getIdCongViec(),
                                    getDateTimeToInsertUpdate(congViecModelArrayList.get(i).getThoiGianBatDau()));
                        }
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
