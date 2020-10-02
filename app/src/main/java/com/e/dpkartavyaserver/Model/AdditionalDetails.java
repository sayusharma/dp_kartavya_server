package com.e.dpkartavyaserver.Model;

public class AdditionalDetails {
    private String retired,from,year,field,residingWith,health,available_hours;
    public AdditionalDetails(){}
    public String getRetired() {
        return retired;
    }

    public void setRetired(String retired) {
        this.retired = retired;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getResidingWith() {
        return residingWith;
    }

    public void setResidingWith(String residingWith) {
        this.residingWith = residingWith;
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public String getAvailable_hours() {
        return available_hours;
    }

    public void setAvailable_hours(String available_hours) {
        this.available_hours = available_hours;
    }

    public AdditionalDetails(String retired, String from, String year, String field, String residingWith, String health, String available_hours) {
        this.retired = retired;
        this.from = from;
        this.year = year;
        this.field = field;
        this.residingWith = residingWith;
        this.health = health;
        this.available_hours = available_hours;
    }
}
