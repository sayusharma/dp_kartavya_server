package com.e.dpkartavyaserver.Model;

public class AdditionalDetails {
    private String retired,from,year,field,children,residingWith,health,lpVisit,freeTime;
    public AdditionalDetails() {

    }
    public AdditionalDetails(String retired, String from, String year, String field, String children, String residingWith, String health, String lpVisit, String freeTime) {
        this.retired = retired;
        this.from = from;
        this.year = year;
        this.field = field;
        this.children = children;
        this.residingWith = residingWith;
        this.health = health;
        this.lpVisit = lpVisit;
        this.freeTime = freeTime;
    }

    public String getRetired() {
        return retired;
    }

    public String getFrom() {
        return from;
    }

    public String getYear() {
        return year;
    }

    public String getField() {
        return field;
    }

    public String getChildren() {
        return children;
    }

    public String getResidingWith() {
        return residingWith;
    }

    public String getHealth() {
        return health;
    }

    public String getLpVisit() {
        return lpVisit;
    }

    public String getFreeTime() {
        return freeTime;
    }
}
