package com.banco.published.controller.request;

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
