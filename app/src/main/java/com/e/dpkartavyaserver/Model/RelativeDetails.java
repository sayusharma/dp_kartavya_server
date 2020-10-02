package com.e.dpkartavyaserver.Model;

public class RelativeDetails {
   private String name,relation,mob,address;

    public RelativeDetails() {
    }

    public RelativeDetails(String name, String relation, String mob, String address) {
        this.name = name;
        this.relation = relation;
        this.mob = mob;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getRelation() {
        return relation;
    }

    public String getMob() {
        return mob;
    }

    public String getAddress() {
        return address;
    }
}
