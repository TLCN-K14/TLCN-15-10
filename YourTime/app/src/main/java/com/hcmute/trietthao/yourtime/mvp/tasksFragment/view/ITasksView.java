package com.hcmute.trietthao.yourtime.mvp.tasksFragment.view;

/**
 * Created by lxtri on 11/11/2017.
 */

public interface ITasksView {

    void getAllGroupWorkSuccess();

    void getAllGroupWorkFailure(String error);

    void getGroupWorkDetailsSuccess();

    void getGroupWorkDetailsFailure();
}
