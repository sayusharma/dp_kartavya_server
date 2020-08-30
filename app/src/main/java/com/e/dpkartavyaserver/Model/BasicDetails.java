package com.e.dpkartavyaserver.Model;

public class BasicDetails {
    private PersonalDetails personalDetails;
    private SpouseDetails spouseDetails;
    private AdditionalDetails additionalDetails;
    private RelativeDetails relativeDetails;
    public BasicDetails() {
    }
    public PersonalDetails getPersonalDetails() {
        return personalDetails;
    }

    public SpouseDetails getSpouseDetails() {
        return spouseDetails;
    }

    public AdditionalDetails getAdditionalDetails() {
        return additionalDetails;
    }

    public RelativeDetails getRelativeDetails() {
        return relativeDetails;
    }

    public BasicDetails(PersonalDetails personalDetails, SpouseDetails spouseDetails, AdditionalDetails additionalDetails, RelativeDetails relativeDetails) {
        this.personalDetails = personalDetails;
        this.spouseDetails = spouseDetails;
        this.additionalDetails = additionalDetails;
        this.relativeDetails = relativeDetails;
    }
}
