package com.banco.api.published.response.salaryPaymentFailure;

public class SalaryPaymentFailure {

    private Resource resource;
    private String errorCode;
    private String error;

    public SalaryPaymentFailure() {
    }

    public SalaryPaymentFailure(Resource resource, String errorCode, String error) {
        this.resource = resource;
        this.errorCode = errorCode;
        this.error = error;
    }

    public Resource getResource() {
        return resource;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getError() {
        return error;
    }
}
