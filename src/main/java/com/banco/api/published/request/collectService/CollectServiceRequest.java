package com.banco.api.published.request.collectService;

import java.util.List;

public class CollectServiceRequest {

    private List<CollectService> services;
    private String serviceProviderCBU;

    public List<CollectService> getCollectServices() {
        return services;
    }

    public String getServiceProviderCBU() {
        return serviceProviderCBU;
    }

    @Override
    public String toString() {
        return "CollectServiceRequest{" +
                "services=" + services +
                ", serviceProviderCBU='" + serviceProviderCBU + '\'' +
                '}';
    }
}
