package com.banco.api.service.billService;

import java.util.List;

public class CreateBillServiceResult {

    private String vendorId;
    private List<String> repeatedIds;

    public CreateBillServiceResult(String vendorId, List<String> repeatedIds) {
        this.vendorId = vendorId;
        this.repeatedIds = repeatedIds;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public List<String> getRepeatedIds() {
        return repeatedIds;
    }

    public void setRepeatedIds(List<String> repeatedIds) {
        this.repeatedIds = repeatedIds;
    }

    @Override
    public String toString() {
        return "CreateBillServiceResult{" +
                "vendorId='" + vendorId + '\'' +
                ", repeatedIds=" + repeatedIds +
                '}';
    }
}
