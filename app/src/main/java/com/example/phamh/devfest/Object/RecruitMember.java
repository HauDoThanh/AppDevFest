package com.example.phamh.devfest.Object;

/**
 * Created by Au Nguyen on 11/23/2017.
 */

public class RecruitMember {
    private String ID;
    private String IDTeam;
    private String nameOfTeam;
    private String number;
    private String description;
    private String urlAvatar;
    private double X;
    private double Y;

    public RecruitMember() {}

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getIDTeam() {
        return IDTeam;
    }

    public void setIDTeam(String IDTeam) {
        this.IDTeam = IDTeam;
    }

    public String getNameOfTeam() {
        return nameOfTeam;
    }

    public void setNameOfTeam(String nameOfTeam) {
        this.nameOfTeam = nameOfTeam;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrlAvatar() {
        return urlAvatar;
    }

    public void setUrlAvatar(String urlAvatar) {
        this.urlAvatar = urlAvatar;
    }

    public double getX() {
        return X;
    }

    public void setX(double x) {
        X = x;
    }

    public double getY() {
        return Y;
    }

    public void setY(double y) {
        Y = y;
    }
}
