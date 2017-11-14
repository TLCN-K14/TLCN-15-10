package com.hcmute.trietthao.yourtime.database;

import com.hcmute.trietthao.yourtime.model.NhomCVModel;

import java.util.ArrayList;

/**
 * Created by lxtri on 11/13/2017.
 */

public interface GroupWorkListener {
    void  getListGroupWork(final ArrayList<NhomCVModel> listGroupWork);

    void  getResultInsertGroupWork(Boolean isSuccess);
}
