package com.e.dpkartavyaserver.Model;

public class SpouseDetails {
    private String name,dob,wedding;
    public SpouseDetails() {
    }
    public SpouseDetails(String name, String dob, String wedding) {
        this.name = name;
        this.dob = dob;
        this.wedding = wedding;
    }

    public String getName() {
        return name;
    }

    public String getDob() {
        return dob;
    }

    public String getWedding() {
        return wedding;
    }
}
