package com.example.phamh.devfest.Object;

import java.io.Serializable;

/**
 * Created by Au Nguyen on 11/23/2017.
 */

public class SearchMatch implements Serializable{
    private String ID;
    private String IDTeam;
    private String nameOfTeam;
    private String date;
    private String time;
    private String nameOfYard;
    private String type;

    private String urlAvatar;
    private double X;
    private double Y;

    public SearchMatch() {
    }

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNameOfYard() {
        return nameOfYard;
    }

    public void setNameOfYard(String nameOfYard) {
        this.nameOfYard = nameOfYard;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
