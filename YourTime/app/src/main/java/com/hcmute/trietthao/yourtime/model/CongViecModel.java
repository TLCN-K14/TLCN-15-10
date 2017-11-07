package com.hcmute.trietthao.yourtime.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class CongViecModel {
    @SerializedName("idCongViec")
    @Expose
    private Integer idCongViec;

    @SerializedName("tenCongViec")
    @Expose
    private String tenCongViec;

    @SerializedName("thoiGianBatDau")
    @Expose
    private Date thoiGianBatDau;

    @SerializedName("thoiGianKetThuc")
    @Expose
    private Date thoiGianKetThuc;

    @SerializedName("ghiChu")
    @Expose
    private String ghiChu;

    @SerializedName("fileDinhKem")
    @Expose
    private String fileDinhKem;

    @SerializedName("coUuTien")
    @Expose
    private boolean coUuTien;

    @SerializedName("idNhom")
    @Expose
    private Integer idNhom;

    @SerializedName("idNhacNho")
    @Expose
    private Integer idNhacNho;

    @SerializedName("idNguoiGiaoCV")
    @Expose
    private Integer idNguoiGiaoCV;

    @SerializedName("idNguoiduocGiaoCV")
    @Expose
    private Integer idNguoiduocGiaoCV;

    public CongViecModel(Integer idCongViec, String tenCongViec, Date thoiGianBatDau, Date thoiGianKetThuc, String ghiChu, String fileDinhKem, boolean coUuTien, Integer idNhom, Integer idNhacNho, Integer idNguoiGiaoCV, Integer idNguoiduocGiaoCV) {
        this.idCongViec = idCongViec;
        this.tenCongViec = tenCongViec;
        this.thoiGianBatDau = thoiGianBatDau;
        this.thoiGianKetThuc = thoiGianKetThuc;
        this.ghiChu = ghiChu;
        this.fileDinhKem = fileDinhKem;
        this.coUuTien = coUuTien;
        this.idNhom = idNhom;
        this.idNhacNho = idNhacNho;
        this.idNguoiGiaoCV = idNguoiGiaoCV;
        this.idNguoiduocGiaoCV = idNguoiduocGiaoCV;
    }

    public Integer getIdCongViec() {
        return idCongViec;
    }

    public void setIdCongViec(Integer idCongViec) {
        this.idCongViec = idCongViec;
    }

    public String getTenCongViec() {
        return tenCongViec;
    }

    public void setTenCongViec(String tenCongViec) {
        this.tenCongViec = tenCongViec;
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

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getFileDinhKem() {
        return fileDinhKem;
    }

    public void setFileDinhKem(String fileDinhKem) {
        this.fileDinhKem = fileDinhKem;
    }

    public boolean isCoUuTien() {
        return coUuTien;
    }

    public void setCoUuTien(boolean coUuTien) {
        this.coUuTien = coUuTien;
    }

    public Integer getIdNhom() {
        return idNhom;
    }

    public void setIdNhom(Integer idNhom) {
        this.idNhom = idNhom;
    }

    public Integer getIdNhacNho() {
        return idNhacNho;
    }

    public void setIdNhacNho(Integer idNhacNho) {
        this.idNhacNho = idNhacNho;
    }

    public Integer getIdNguoiGiaoCV() {
        return idNguoiGiaoCV;
    }

    public void setIdNguoiGiaoCV(Integer idNguoiGiaoCV) {
        this.idNguoiGiaoCV = idNguoiGiaoCV;
    }

    public Integer getIdNguoiduocGiaoCV() {
        return idNguoiduocGiaoCV;
    }

    public void setIdNguoiduocGiaoCV(Integer idNguoiduocGiaoCV) {
        this.idNguoiduocGiaoCV = idNguoiduocGiaoCV;
    }
}
