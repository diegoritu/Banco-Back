package com.banco.api.published.response.collectService.list;

public class CollectServiceResource {

    private String serviceId;
    private String dueDate;

    public CollectServiceResource() {
    }

    public CollectServiceResource(String serviceId, String dueDate) {
        this.serviceId = serviceId;
        this.dueDate = dueDate;
    }

    public String getServiceId() {
        return serviceId;
    }

    public String getDueDate() {
        return dueDate;
    }

    @Override
    public String toString() {
        return "CollectServiceResource{" +
                "serviceId='" + serviceId + '\'' +
                ", dueDate='" + dueDate + '\'' +
                '}';
    }
}
