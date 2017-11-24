package com.hcmute.trietthao.yourtime.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class CVThongBaoModel implements Serializable {

    @SerializedName("idCongViec")
    @Expose
    private Integer idCongViec;

    @SerializedName("thoiGianBatDau")
    @Expose
    private Date thoiGianBatDau;

    @SerializedName("thoiGianKetThuc")
    @Expose
    private Date thoiGianKetThuc;

    @SerializedName("idNguoiThucHien")
    @Expose
    private Date idNguoiThucHien;

    @SerializedName("trangThai")
    @Expose
    private String trangThai;

    public CVThongBaoModel(Integer idCongViec, Date thoiGianBatDau,
                           Date thoiGianKetThuc, Date idNguoiThucHien, String trangThai) {
        this.idCongViec = idCongViec;
        this.thoiGianBatDau = thoiGianBatDau;
        this.thoiGianKetThuc = thoiGianKetThuc;
        this.idNguoiThucHien = idNguoiThucHien;
        this.trangThai = trangThai;
    }

    public CVThongBaoModel() {

    }

    public Integer getIdCongViec() {
        return idCongViec;
    }

    public void setIdCongViec(Integer idCongViec) {
        this.idCongViec = idCongViec;
    }

    public Date getThoiGianBatDau() {
        return thoiGianBatDau;
    }

    public void setThoiGianBatDau(Date thoiGianBatDau) {
        this.thoiGianBatDau = thoiGianBatDau;
    }

    public Date getThoiGianKetThuc() {
        return thoiGianKetThuc;
    }

    public void setThoiGianKetThuc(Date thoiGianKetThuc) {
        this.thoiGianKetThuc = thoiGianKetThuc;
    }

    public Date getIdNguoiThucHien() {
        return idNguoiThucHien;
    }

    public void setIdNguoiThucHien(Date idNguoiThucHien) {
        this.idNguoiThucHien = idNguoiThucHien;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}
