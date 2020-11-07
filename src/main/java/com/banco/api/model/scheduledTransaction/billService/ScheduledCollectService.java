package com.banco.api.model.scheduledTransaction.billService;

import com.banco.api.model.ServicePayment;
import com.banco.api.model.scheduledTransaction.ScheduledTransaction;
import com.banco.api.model.scheduledTransaction.ScheduledTransactionStatus;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "collect_service")
public class ScheduledCollectService extends ScheduledTransaction {

    @OneToOne
    @JoinColumn(name = "idService")
    private ServicePayment servicePayment;
    private String serviceProviderCBU;
    private String clientCbu;

    public ScheduledCollectService() {
    }

    public ScheduledCollectService(ServicePayment servicePayment, String serviceProviderCBU, String clientCbu) {
        this.servicePayment = servicePayment;
        this.serviceProviderCBU = serviceProviderCBU;
        this.clientCbu = clientCbu;
        this.scheduledDate = servicePayment.getDue();
        this.setStatus(ScheduledTransactionStatus.PENDING);
    }

    public ServicePayment getServicePayment() {
        return servicePayment;
    }

    public String getServiceProviderCBU() {
        return serviceProviderCBU;
    }

    public String getClientCbu() {
        return clientCbu;
    }
}
