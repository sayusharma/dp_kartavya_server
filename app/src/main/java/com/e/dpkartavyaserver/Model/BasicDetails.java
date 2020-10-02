package com.e.dpkartavyaserver.Model;

import java.util.ArrayList;

public class BasicDetails {
    private PersonalDetails personalDetails;
    private SpouseDetails spouseDetails;
    private AdditionalDetails additionalDetails;
    private RelativeDetails relativeDetails;
    private ArrayList<Children> childrenDetails;
    public BasicDetails(){}

    public PersonalDetails getPersonalDetails() {
        return personalDetails;
    }

    public void setPersonalDetails(PersonalDetails personalDetails) {
        this.personalDetails = personalDetails;
    }

    public SpouseDetails getSpouseDetails() {
        return spouseDetails;
    }

    public void setSpouseDetails(SpouseDetails spouseDetails) {
        this.spouseDetails = spouseDetails;
    }

    public AdditionalDetails getAdditionalDetails() {
        return additionalDetails;
    }

    public void setAdditionalDetails(AdditionalDetails additionalDetails) {
        this.additionalDetails = additionalDetails;
    }

    public RelativeDetails getRelativeDetails() {
        return relativeDetails;
    }

    public void setRelativeDetails(RelativeDetails relativeDetails) {
        this.relativeDetails = relativeDetails;
    }

    public ArrayList<Children> getChildrenDetails() {
        return childrenDetails;
    }

    public void setChildrenDetails(ArrayList<Children> childrenDetails) {
        this.childrenDetails = childrenDetails;
    }

    public BasicDetails(PersonalDetails personalDetails, SpouseDetails spouseDetails, AdditionalDetails additionalDetails, RelativeDetails relativeDetails, ArrayList<Children> childrenDetails) {
        this.personalDetails = personalDetails;
        this.spouseDetails = spouseDetails;
        this.additionalDetails = additionalDetails;
        this.relativeDetails = relativeDetails;
        this.childrenDetails = childrenDetails;
    }
}
