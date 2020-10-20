package com.banco.api.model.scheduledTransaction.salary;

public class Salary {

    private String employeeCBU;
    private String salary;

    public String getEmployeeCBU() {
        return employeeCBU;
    }

    public String getSalary() {
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
