package com.example.phamh.devfest;

import java.util.List;

/**
 * Created by quocb14005xx on 11/22/2017.
 */

public class Team {
    private String ID_TEAM;
    private String teamName;
    private String leaderName;
    private String sdt;
    private String mota;
    private String imageUrl;
    List<String> listIdMember;
    private double doTuongTac;


    public Team() {
    }

    public Team(String ID_TEAM, String teamName, String leaderName, String sdt,
                String mota, String imageUrl, List<String> listIdMember, double doTuongTac) {
        this.ID_TEAM = ID_TEAM;
        this.teamName = teamName;
        this.leaderName = leaderName;
        this.sdt = sdt;
        this.mota = mota;
        this.imageUrl = imageUrl;
        this.listIdMember = listIdMember;
        this.doTuongTac = doTuongTac;
    }

    public String getID_TEAM() {
        return ID_TEAM;
    }

    public void setID_TEAM(String ID_TEAM) {
        this.ID_TEAM = ID_TEAM;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getLeaderName() {
        return leaderName;
    }

    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<String> getListIdMember() {
        return listIdMember;
    }

    public void setListIdMember(List<String> listIdMember) {
        this.listIdMember = listIdMember;
    }

    public double getDoTuongTac() {
        return doTuongTac;
    }

    public void setDoTuongTac(double doTuongTac) {
        this.doTuongTac = doTuongTac;
    }
}
