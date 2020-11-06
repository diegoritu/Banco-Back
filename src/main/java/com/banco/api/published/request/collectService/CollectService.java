package com.banco.api.published.request.collectService;

public class CollectService {

    private String serviceId;
    private String name;
    private String dueDate;
    private Float amount;
    private String clientCUIT;
    private String clientCBU;

    public String getServiceId() {
        return serviceId;
    }

    public String getName() {
        return name;
    }

    public String getDueDate() {
        return dueDate;
    }

    public Float getAmount() {
        return amount;
    }

    public String getClientCUIT() {
        return clientCUIT;
    }

    public String getClientCBU() {
        return clientCBU;
    }

    @Override
    public String toString() {
        return "CollectService{" +
                "serviceId='" + serviceId + '\'' +
                ", name='" + name + '\'' +
                ", dueDate='" + dueDate + '\'' +
                ", amount=" + amount +
                ", clientCUIT='" + clientCUIT + '\'' +
                ", clientCBU='" + clientCBU + '\'' +
                '}';
    }
}
