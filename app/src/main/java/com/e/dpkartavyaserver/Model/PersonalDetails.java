package com.e.dpkartavyaserver.Model;

public class PersonalDetails {
    private String photo,name,gender,dob,address,mob,email;
    public PersonalDetails() {
    }
    public PersonalDetails(String photo, String name, String gender, String dob, String address, String mob, String email) {
        this.photo = photo;
        this.name = name;
        this.gender = gender;
        this.dob = dob;
        this.address = address;
        this.mob = mob;
        this.email = email;
    }

    public String getPhoto() {
        return photo;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }
    public String getDob() {
        return dob;
    }

    public String getAddress() {
        return address;
    }

    public String getMob() {
        return mob;
    }

    public String getEmail() {
        return email;
    }
}
