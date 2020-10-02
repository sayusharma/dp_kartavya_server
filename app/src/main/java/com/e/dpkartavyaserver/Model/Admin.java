package com.e.dpkartavyaserver.Model;

public class Admin {
    private String name,rank,photo,mob,police;
    public Admin(){}
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getRank() {
        return rank;
    }
    public void setRank(String rank) {
        this.rank = rank;
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
    public String getPolice() {
        return police;
    }
    public void setPolice(String police) {
        this.police = police;
    }
    public Admin(String name, String rank, String photo, String mob, String police) {
        this.name = name;
        this.rank = rank;
        this.photo = photo;
        this.mob = mob;
        this.police = police;
    }
}
