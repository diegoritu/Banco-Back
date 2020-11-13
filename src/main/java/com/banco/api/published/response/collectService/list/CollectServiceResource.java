package com.banco.api.published.response.collectService.list;

public class CollectServiceResource {

    private String serviceId;
    private String dueDate;
    private boolean automatic;

    public CollectServiceResource() {
    }

    public CollectServiceResource(String serviceId, String dueDate, boolean automatic) {
        this.serviceId = serviceId;
        this.dueDate = dueDate;
        this.automatic = automatic;
    }

    public String getServiceId() {
        return serviceId;
    }

    public String getDueDate() {
        return dueDate;
    }

    public boolean isAutomatic() {
        return automatic;
    }

    @Override
    public String toString() {
        return "CollectServiceResource{" +
                "serviceId='" + serviceId + '\'' +
                ", dueDate='" + dueDate + '\'' +
                ", automatic=" + automatic +
                '}';
    }
}
