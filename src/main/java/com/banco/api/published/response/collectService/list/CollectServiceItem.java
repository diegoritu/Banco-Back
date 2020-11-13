package com.banco.api.published.response.collectService.list;

public class CollectServiceItem {

    private CollectServiceResource resource;
    private String status;
    private CollectServiceError error;

    public CollectServiceItem() {
    }

    public CollectServiceItem(CollectServiceResource resource, String status, CollectServiceError error) {
        this.resource = resource;
        this.status = status;
        this.error = error;
    }

    public CollectServiceResource getResource() {
        return resource;
    }

    public String getStatus() {
        return status;
    }

    public CollectServiceError getError() {
        return error;
    }

    @Override
    public String toString() {
        return "CollectServiceItem{" +
                "resource=" + resource +
                ", status='" + status + '\'' +
                ", error=" + error +
                '}';
    }
}
