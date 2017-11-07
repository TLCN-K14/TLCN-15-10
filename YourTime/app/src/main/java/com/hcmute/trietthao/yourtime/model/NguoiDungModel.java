package com.hcmute.trietthao.yourtime.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NguoiDungModel implements Serializable {

    @SerializedName("idNguoiDung")
    @Expose
    private Integer idNguoiDung;

    @SerializedName("tenNguoiDung")
    @Expose
    private String tenNguoiDung;

    @SerializedName("userName")
    @Expose
    private String userName;

    @SerializedName("passWord")
    @Expose
    private String passWord;

    public NguoiDungModel(Integer idNguoiDung, String tenNguoiDung, String userName, String passWord) {
        this.idNguoiDung = idNguoiDung;
        this.tenNguoiDung = tenNguoiDung;
        this.userName = userName;
        this.passWord = passWord;
    }

    public Integer getIdNguoiDung() {
        return idNguoiDung;
    }

    public void setIdNguoiDung(Integer idNguoiDung) {
        this.idNguoiDung = idNguoiDung;
    }

    public String getTenNguoiDung() {
        return tenNguoiDung;
    }

    public void setTenNguoiDung(String tenNguoiDung) {
        this.tenNguoiDung = tenNguoiDung;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
