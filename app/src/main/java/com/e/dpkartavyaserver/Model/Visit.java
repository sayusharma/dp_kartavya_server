package com.e.dpkartavyaserver.Model;

public class Visit {
    private String police,name,photo,notes,complaint,off_mob,off_name,date,time,mob;
    private Loc loc;
    public Visit(){}

    public String getPolice() {
        return police;
    }

    public void setPolice(String police) {
        this.police = police;
    }

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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
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

    public String getMob() {
        return mob;
    }

    public void setMob(String mob) {
        this.mob = mob;
    }

    public Loc getLoc() {
        return loc;
    }

    public void setLoc(Loc loc) {
        this.loc = loc;
    }

    public Visit(String police, String name, String photo, String notes, String complaint, String off_mob, String off_name, String date, String time, String mob, Loc loc) {
        this.police = police;
        this.name = name;
        this.photo = photo;
        this.notes = notes;
        this.complaint = complaint;
        this.off_mob = off_mob;
        this.off_name = off_name;
        this.date = date;
        this.time = time;
        this.mob = mob;
        this.loc = loc;
    }
}
