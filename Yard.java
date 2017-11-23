package com.example.phamh.devfest.Object;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Au Nguyen on 11/23/2017.
 */

public class Yard implements Serializable {
    private String tenSan;
    private String tenChuSan;
    private String diaChiSan;
    private String sdt;
    private int soLuongSan;
    private int soLanThue;
    private List<String> listHinhAnh = new ArrayList<>();

    public String getTenSan() {
        return tenSan;
    }

    public void setTenSan(String tenSan) {
        this.tenSan = tenSan;
    }

    public String getTenChuSan() {
        return tenChuSan;
    }

    public void setTenChuSan(String tenChuSan) {
        this.tenChuSan = tenChuSan;
    }

    public String getDiaChiSan() {
        return diaChiSan;
    }

    public void setDiaChiSan(String diaChiSan) {
        this.diaChiSan = diaChiSan;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public int getSoLuongSan() {
        return soLuongSan;
    }

    public void setSoLuongSan(int soLuongSan) {
        this.soLuongSan = soLuongSan;
    }

    public int getSoLanThue() {
        return soLanThue;
    }

    public void setSoLanThue(int soLanThue) {
        this.soLanThue = soLanThue;
    }

    public List<String> getListHinhAnh() {
        return listHinhAnh;
    }

    public void setListHinhAnh(List<String> listHinhAnh) {
        this.listHinhAnh = listHinhAnh;
    }

    public Yard(){};

}

