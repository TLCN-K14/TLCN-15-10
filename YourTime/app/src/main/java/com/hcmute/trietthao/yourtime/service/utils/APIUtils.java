package com.hcmute.trietthao.yourtime.service.utils;

import com.hcmute.trietthao.yourtime.service.RetrofitClient;
import com.hcmute.trietthao.yourtime.service.Service;

/**
 * Created by xuant on 13/05/2017.
 */

public class ApiUtils {
    public static final String BASE_URL = "https://tlcn-yourtime.herokuapp.com/";
    //        public static final String BASE_URL = "http://192.168.216.2:8000/";
    // đường dẫn tới webservice
    public static Service getService() {
        return RetrofitClient.getClient(BASE_URL).create(Service.class);
    }
}
