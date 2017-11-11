package com.hcmute.trietthao.yourtime.mvp.tasksFragment.presenter;

import com.hcmute.trietthao.yourtime.base.BasePresenter;
import com.hcmute.trietthao.yourtime.model.NhomCVModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lxtri on 11/11/2017.
 */

public class TasksPresenter  extends BasePresenter implements ITasksPresenter{

    private List<NhomCVModel> mList;

    @Override
    public void onCreate() {
        super.onCreate();
        mList = new ArrayList<>();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public List<NhomCVModel> getListGroupWork() {
        return mList;
    }

    @Override
    public void getAllGroupWork() {
        if (!isViewAttached()) return;


    }

    @Override
    public void getGroupWorkDetails(String id) {

    }
}
