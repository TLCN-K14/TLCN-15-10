package com.hcmute.trietthao.yourtime.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NhomCVModel implements Serializable {
    @SerializedName("idNhom")
    @Expose
    private Integer idNhom;

    @SerializedName("tenNhom")
    @Expose
    private String tenNhom;

    @SerializedName("laNhomCaNhan")
    @Expose
    private boolean laNhomCaNhan;

    public NhomCVModel(Integer idNhom, String tenNhom, boolean laNhomCaNhan) {
        this.idNhom = idNhom;
        this.tenNhom = tenNhom;
        this.laNhomCaNhan = laNhomCaNhan;
    }

    public Integer getIdNhom() {
        return idNhom;
    }

    public void setIdNhom(Integer idNhom) {
        this.idNhom = idNhom;
    }

    public String getTenNhom() {
        return tenNhom;
    }

    public void setTenNhom(String tenNhom) {
        this.tenNhom = tenNhom;
    }

    public boolean isLaNhomCaNhan() {
        return laNhomCaNhan;
    }

    public void setLaNhomCaNhan(boolean laNhomCaNhan) {
        this.laNhomCaNhan = laNhomCaNhan;
    }
}
