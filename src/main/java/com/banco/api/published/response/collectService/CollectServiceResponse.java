package com.banco.api.published.response.collectService;

public class CollectServiceResponse {

    private String serviceProviderId;

    public CollectServiceResponse() {
    }

    public CollectServiceResponse(String serviceProviderId) {
        this.serviceProviderId = serviceProviderId;
    }

    public String getServiceProviderId() {
        return serviceProviderId;
    }

    @Override
    public String toString() {
        return "CollectServiceResponse{" +
                "serviceProviderId='" + serviceProviderId + '\'' +
                '}';
    }
}
