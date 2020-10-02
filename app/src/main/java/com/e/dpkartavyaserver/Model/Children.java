package com.e.dpkartavyaserver.Model;

public class Children {
    private String name,mob,address;
    public Children(){}
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMob() {
        return mob;
    }

    public void setMob(String mob) {
        this.mob = mob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Children(String name, String mob, String address) {
        this.name = name;
        this.mob = mob;
        this.address = address;
    }
}
