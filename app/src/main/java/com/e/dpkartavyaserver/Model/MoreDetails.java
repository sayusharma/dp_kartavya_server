package com.e.dpkartavyaserver.Model;

public class MoreDetails {
    private Loc loc;
    private String off_mob,off_name,date,time;
    public MoreDetails(){}

    public Loc getLoc() {
        return loc;
    }

    public void setLoc(Loc loc) {
        this.loc = loc;
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

    public MoreDetails(Loc loc, String off_mob, String off_name, String date, String time) {
        this.loc = loc;
        this.off_mob = off_mob;
        this.off_name = off_name;
        this.date = date;
        this.time = time;
    }
}
