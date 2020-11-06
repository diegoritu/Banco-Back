package com.banco.api.published.request.salaryPayment;

public class Salary {

    private String employeeCBU;
    private Float salary;

    public String getEmployeeCBU() {
        return employeeCBU;
    }

    public Float getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return "Salary{" +
                "employeeCBU='" + employeeCBU + '\'' +
                ", salary='" + salary + '\'' +
                '}';
    }
}
