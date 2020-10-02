package com.e.dpkartavyaserver.Model;

import java.util.ArrayList;
import java.util.Comparator;

public class VerifySnr implements Comparable<VerifySnr>{
    private BasicDetails basicDetails;
    private ArrayList<ServiceProvider> serviceProviders;
    private SecurityChecks securityChecks;
    private MoreDetails moreDetails;
    public VerifySnr() {
    }

    public BasicDetails getBasicDetails() {
        return basicDetails;
    }

    public ArrayList<ServiceProvider> getServiceProviders() {
        return serviceProviders;
    }

    public SecurityChecks getSecurityChecks() {
        return securityChecks;
    }

    public MoreDetails getMoreDetails() {
        return moreDetails;
    }

    public VerifySnr(BasicDetails basicDetails, ArrayList<ServiceProvider> serviceProviders, SecurityChecks securityChecks, MoreDetails moreDetails) {
        this.basicDetails = basicDetails;
        this.serviceProviders = serviceProviders;
        this.securityChecks = securityChecks;
        this.moreDetails = moreDetails;
    }
    public static Comparator<VerifySnr> Name = new Comparator<VerifySnr>() {

        public int compare(VerifySnr s1, VerifySnr s2) {
            String StudentName1 = s1.basicDetails.getPersonalDetails().getName().toUpperCase();
            String StudentName2 = s2.basicDetails.getPersonalDetails().getName().toUpperCase();

            //ascending order
            return StudentName1.compareTo(StudentName2);

            //descending order
        }};
    @Override
    public String toString() {
        return "VerifySnr{" +
                "basicDetails=" + basicDetails +
                ", serviceProviders=" + serviceProviders +
                ", securityChecks=" + securityChecks +
                ", moreDetails=" + moreDetails +
                '}';
    }

    @Override
    public int compareTo(VerifySnr o) {
        return this.getBasicDetails().getPersonalDetails().getName().compareTo(o.getBasicDetails().getPersonalDetails().getName());
    }
}
