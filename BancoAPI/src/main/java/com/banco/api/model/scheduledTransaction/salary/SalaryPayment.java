package com.banco.api.model.scheduledTransaction.salary;

import com.banco.api.model.scheduledTransaction.ScheduledTransaction;
import com.banco.api.model.scheduledTransaction.ScheduledTransactionStatus;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "salary_payment")
public class SalaryPayment extends ScheduledTransaction {

    private String employerCbu;
    private String employeeCbu;
    private Float salary;

    public SalaryPayment(String employerCbu, String employeeCBU, Float salary, Date scheduledDate) {
        this.employeeCbu = employeeCBU;
        this.employerCbu = employerCbu;
        this.salary = salary;
        this.scheduledDate = scheduledDate;
        this.setStatus(ScheduledTransactionStatus.PENDING);
    }

    public String getEmployeeCbu() {
        return employeeCbu;
    }

    public String getEmployerCbu() {
        return employerCbu;
    }

    public Float getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return "SalaryPayment{" +
                "employerCbu='" + employerCbu + '\'' +
                ", employeeCbu='" + employeeCbu + '\'' +
                ", salary=" + salary +
                ", scheduledDate=" + scheduledDate +
                ", status=" + status +
                ", failureCode='" + failureCode + '\'' +
                ", failureMessage='" + failureMessage + '\'' +
                '}';
    }
}
