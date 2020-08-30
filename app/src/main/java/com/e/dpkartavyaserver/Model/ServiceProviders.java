package com.e.dpkartavyaserver.Model;

public class ServiceProviders {
    private ServiceProvider driver,watchman,servant,tenant,sweeper,carCleaner;
    private OtherServiceProvider others;
    public ServiceProviders() {
    }
    public ServiceProviders(ServiceProvider driver, ServiceProvider watchman, ServiceProvider servant, ServiceProvider tenant, ServiceProvider sweeper, ServiceProvider carCleaner, OtherServiceProvider others) {
        this.driver = driver;
        this.watchman = watchman;
        this.servant = servant;
        this.tenant = tenant;
        this.sweeper = sweeper;
        this.carCleaner = carCleaner;
        this.others = others;
    }

    public ServiceProvider getDriver() {
        return driver;
    }

    public ServiceProvider getWatchman() {
        return watchman;
    }

    public ServiceProvider getServant() {
        return servant;
    }

    public ServiceProvider getTenant() {
        return tenant;
    }

    public ServiceProvider getSweeper() {
        return sweeper;
    }

    public ServiceProvider getCarCleaner() {
        return carCleaner;
    }

    public OtherServiceProvider getOthers() {
        return others;
    }
}
