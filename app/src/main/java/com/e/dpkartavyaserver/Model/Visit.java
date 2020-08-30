package com.e.dpkartavyaserver.Model;

public class Visit {
    private String name,mob,addr,photo,off_mob,off_name,date,time,notes;
    public Visit(){}

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

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getOff_mob() {
        return off_mob;
    }

    public void setOff_mob(String off_mob) {
        this.off_mob = off_mob;
    }

    public String getOff_name() {
        return off_name;
    }

    public void setOff_name(String off_name) {
        this.off_name = off_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Visit(String name, String mob, String addr, String photo, String off_mob, String off_name, String date, String time, String notes) {
        this.name = name;
        this.mob = mob;
        this.addr = addr;
        this.photo = photo;
        this.off_mob = off_mob;
        this.off_name = off_name;
        this.date = date;
        this.time = time;
        this.notes = notes;
    }
}
