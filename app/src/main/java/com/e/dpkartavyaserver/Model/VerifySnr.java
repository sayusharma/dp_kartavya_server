package com.e.dpkartavyaserver.Model;

public class VerifySnr {
    private BasicDetails basicDetails;
    private ServiceProviders serviceProviders;
    private SecurityChecks securityChecks;
    private MoreDetails moreDetails;
    public VerifySnr() {
    }

    public BasicDetails getBasicDetails() {
        return basicDetails;
    }

    public ServiceProviders getServiceProviders() {
        return serviceProviders;
    }

    public SecurityChecks getSecurityChecks() {
        return securityChecks;
    }

    public MoreDetails getMoreDetails() {
        return moreDetails;
    }

    public VerifySnr(BasicDetails basicDetails, ServiceProviders serviceProviders, SecurityChecks securityChecks, MoreDetails moreDetails) {
        this.basicDetails = basicDetails;
        this.serviceProviders = serviceProviders;
        this.securityChecks = securityChecks;
        this.moreDetails = moreDetails;
    }
}
