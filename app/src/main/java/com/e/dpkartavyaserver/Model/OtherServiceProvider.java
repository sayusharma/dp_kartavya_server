package com.e.dpkartavyaserver.Model;

public class OtherServiceProvider {
    private String serType,name,address,verStatus,verNo;
    public OtherServiceProvider() {
    }
    public OtherServiceProvider(String serType, String name, String address, String verStatus, String verNo) {
        this.serType = serType;
        this.name = name;
        this.address = address;
        this.verStatus = verStatus;
        this.verNo = verNo;
    }

    public String getSerType() {
        return serType;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getVerStatus() {
        return verStatus;
    }

    public String getVerNo() {
        return verNo;
    }
}
