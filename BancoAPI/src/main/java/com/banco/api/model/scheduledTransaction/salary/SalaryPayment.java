package com.banco.api.model.scheduledTransaction.salary;

import com.banco.api.model.scheduledTransaction.ScheduledTransaction;
import com.banco.api.model.scheduledTransaction.ScheduledTransactionStatus;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "salary_payment")
public class SalaryPayment extends ScheduledTransaction {

    private String employerCBU;
    private String employeeCBU;
    private Float salary;

    public SalaryPayment(String employerCBU, String employeeCBU, Float salary, Date scheduledDate) {
        this.employeeCBU = employeeCBU;
        this.employerCBU = employerCBU;
        this.salary = salary;
        this.scheduledDate = scheduledDate;
        this.setStatus(ScheduledTransactionStatus.PENDING);
    }

    public String getEmployeeCBU() {
        return employeeCBU;
    }

    public String getEmployerCBU() {
        return employerCBU;
    }

    public Float getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return "SalaryPayment{" +
                "employerCBU='" + employerCBU + '\'' +
                ", employeeCBU='" + employeeCBU + '\'' +
                ", salary=" + salary +
                ", scheduledDate=" + scheduledDate +
                ", status=" + status +
                ", failureCode='" + failureCode + '\'' +
                ", failureMessage='" + failureMessage + '\'' +
                '}';
    }
}