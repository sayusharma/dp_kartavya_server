package com.e.dpkartavyaserver.Model;

public class Loc {
    private String latitude,longitude;
    public Loc(){}

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Loc(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
