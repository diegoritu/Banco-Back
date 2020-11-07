package com.banco.api.published.request.collectService;

import java.util.List;

public class CollectServiceRequest {

    private List<CollectService> collectServices;
    private String serviceProviderCBU;

    public List<CollectService> getCollectServices() {
        return collectServices;
    }

    public String getServiceProviderCBU() {
        return serviceProviderCBU;
    }

    @Override
    public String toString() {
        return "CollectServiceRequest{" +
                "collectServices=" + collectServices +
                ", serviceProviderCBU='" + serviceProviderCBU + '\'' +
                '}';
    }
}
