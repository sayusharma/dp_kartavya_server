package com.e.dpkartavyaserver.Model;

public class Admin {
    private String name,photo,mob,pass;
    public Admin(){}
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getMob() {
        return mob;
    }

    public void setMob(String mob) {
        this.mob = mob;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Admin(String name, String photo, String mob, String pass) {
        this.name = name;
        this.photo = photo;
        this.mob = mob;
        this.pass = pass;
    }
}
