package com.boss.routedirectionsapp;

public class Loc {
    public String latitude;
    public String longtitude;

    public String getLatitude() {
        return latitude;
    }

    public Loc(String latitude, String longtitude) {
        this.latitude = latitude;
        this.longtitude = longtitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(String longtitude) {
        this.longtitude = longtitude;
    }
}
