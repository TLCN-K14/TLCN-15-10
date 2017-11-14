package com.hcmute.trietthao.yourtime.mvp.tasksFragment.presenter;

import android.os.Handler;
import android.util.Log;

import com.hcmute.trietthao.yourtime.database.DBGroupWorkServer;
import com.hcmute.trietthao.yourtime.database.DBWorkServer;
import com.hcmute.trietthao.yourtime.database.GroupWorkListener;
import com.hcmute.trietthao.yourtime.database.WorkListener;
import com.hcmute.trietthao.yourtime.model.CongViecModel;
import com.hcmute.trietthao.yourtime.model.NhomCVModel;
import com.hcmute.trietthao.yourtime.mvp.tasksFragment.view.ITasksView;
import com.hcmute.trietthao.yourtime.service.Service;

import java.util.ArrayList;

/**
 * Created by lxtri on 11/11/2017.
 */

public class TasksPresenter  implements ITasksPresenter,GroupWorkListener,WorkListener{

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
        dbWorkServer = new DBWorkServer(this);
        dbWorkServer.getListAllWork(idnguoidung);
    }

    @Override
    public void getListGroupWork(final ArrayList<NhomCVModel> listGroupWork) {
        mList = new ArrayList<>();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mList = listGroupWork;
                iTasksView.getAllGroupWorkSuccess();
                iTasksView.hideLoading();
            }
        }, 1000);
    }

    @Override
    public void getResultInsertGroupWork(Boolean isSuccess) {

    }

    @Override
    public void getListAllWork(ArrayList<CongViecModel> congViecModelArrayList) {
        mListWork = congViecModelArrayList;
        iTasksView.getListAllWorkSucess();
    }
}
