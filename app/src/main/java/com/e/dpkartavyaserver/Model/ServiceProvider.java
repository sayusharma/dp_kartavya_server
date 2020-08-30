package com.e.dpkartavyaserver.Model;

public class ServiceProvider {
    private String name,address,verStatus,verNo;
    public ServiceProvider() {

    }
    public ServiceProvider(String name, String address, String verStatus, String verNo) {
        this.name = name;
        this.address = address;
        this.verStatus = verStatus;
        this.verNo = verNo;
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
