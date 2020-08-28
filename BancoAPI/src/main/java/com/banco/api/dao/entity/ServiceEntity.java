package com.banco.api.dao.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "services")
public class ServiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idService;
    private String name;
    private float amount;
    @Temporal(TemporalType.TIMESTAMP)
    private Date due;

    public ServiceEntity(int idService, String name, float amount, Date due) {
        super();
        this.idService = idService;
        this.name = name;
        this.amount = amount;
        this.due = due;
    }

    public int getIdService() {
        return idService;
    }

    public void setIdService(int idService) {
        this.idService = idService;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Date getDue() {
        return due;
    }

    public void setDue(Date due) {
        this.due = due;
    }
}
