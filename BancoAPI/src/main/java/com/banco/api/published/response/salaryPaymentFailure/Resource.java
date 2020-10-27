package com.banco.api.published.response.salaryPaymentFailure;

public class Resource {

    private String employeeCBU;
    private String date;

    public Resource() {
    }

    public Resource(String employeeCBU, String date) {
        this.employeeCBU = employeeCBU;
        this.date = date;
    }

    public String getEmployeeCBU() {
        return employeeCBU;
    }

    public String getDate() {
        return date;
    }
}
