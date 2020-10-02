package com.e.dpkartavyaserver.Model;

public class ServiceProvider {
    private String serType,name,address,verStatus,verNotes;
    public ServiceProvider() {

    }

    public String getSerType() {
        return serType;
    }

    public void setSerType(String serType) {
        this.serType = serType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getVerStatus() {
        return verStatus;
    }

    public void setVerStatus(String verStatus) {
        this.verStatus = verStatus;
    }

    public String getVerNotes() {
        return verNotes;
    }

    public void setVerNotes(String verNotes) {
        this.verNotes = verNotes;
    }

    public ServiceProvider(String serType, String name, String address, String verStatus, String verNotes) {
        this.serType = serType;
        this.name = name;
        this.address = address;
        this.verStatus = verStatus;
        this.verNotes = verNotes;
    }
}
