package com.example.phamh.devfest.Class;

/**
 * Created by buimi on 11/22/2017.
 */

public class UserClass {
    private String id;
    private String hoTen;
    private double x;
    private double y;
    private boolean isleader;
    private String sdt;
    private String imgUrlUser;
    private String mota;
    private String diaChi;

    public UserClass(String id, String hoTen, double x, double y, boolean isleader, String sdt, String imgUrlUser, String mota, String diaChi) {
        this.id = id;
        this.hoTen = hoTen;
        this.x = x;
        this.y = y;
        this.isleader = isleader;
        this.sdt = sdt;
        this.imgUrlUser = imgUrlUser;
        this.mota = mota;
        this.diaChi = diaChi;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public boolean isIsleader() {
        return isleader;
    }

    public void setIsleader(boolean isleader) {
        this.isleader = isleader;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getImgUrlUser() {
        return imgUrlUser;
    }

    public void setImgUrlUser(String imgUrlUser) {
        this.imgUrlUser = imgUrlUser;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }
}
