package com.hcmute.trietthao.yourtime.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class LoaiNhacNhoModel implements Serializable {
    @SerializedName("idNhacNho")
    @Expose
    private Integer idNhacNho;

    @SerializedName("noiDung")
    @Expose
    private String noiDung;

    @SerializedName("thoiGianLapLai")
    @Expose
    private Date thoiGianLapLai;

    public LoaiNhacNhoModel(Integer idNhacNho, String noiDung, Date thoiGianLapLai) {
        this.idNhacNho = idNhacNho;
        this.noiDung = noiDung;
        this.thoiGianLapLai = thoiGianLapLai;
    }

    public LoaiNhacNhoModel() {

    }

    public Integer getIdNhacNho() {
        return idNhacNho;
    }

    public void setIdNhacNho(Integer idNhacNho) {
        this.idNhacNho = idNhacNho;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public Date getThoiGianLapLai() {
        return thoiGianLapLai;
    }

    public void setThoiGianLapLai(Date thoiGianLapLai) {
        this.thoiGianLapLai = thoiGianLapLai;
    }
}
