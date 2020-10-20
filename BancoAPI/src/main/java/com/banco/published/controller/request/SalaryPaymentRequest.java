package com.banco.published.controller.request;

import java.util.List;

public class SalaryPaymentRequest {

    private String employerCBU;
    private List<Salary> salaries;

    public String getEmployerCBU() {
        return employerCBU;
    }

    public List<Salary> getSalaries() {
        return salaries;
    }

    @Override
    public String toString() {
        return "SalaryPaymentRequest{" +
                "employerCBU='" + employerCBU + '\'' +
                ", salaries=" + salaries +
                '}';
    }
}
