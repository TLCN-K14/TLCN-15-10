package com.hcmute.trietthao.yourtime.mvp.tasksFragment.presenter;

/**
 * Created by lxtri on 11/11/2017.
 */

public interface ITasksPresenter {
    void getAllGroupWorkOnline(int idnguoidung);

    void getAllWorkOnline(int idnguoidung);
    void deleteGroupWork(int idNhom, int idNguoiDung);

}
