package com.e.dpkartavyaserver.Model;

public class LastVisit {
    private String name,mob,date,police,photo;
    public LastVisit(){}

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPolice() {
        return police;
    }

    public void setPolice(String police) {
        this.police = police;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public LastVisit(String name, String mob, String date, String police, String photo) {
        this.name = name;
        this.mob = mob;
        this.date = date;
        this.police = police;
        this.photo = photo;
    }
}
