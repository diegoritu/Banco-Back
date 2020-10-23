package com.banco.api.model.scheduledTransaction;

import java.util.HashMap;
import java.util.Map;

public enum ScheduledTransactionStatus {
    PENDING(0),
    DONE(1),
    ERROR(2);

    private final int value;
    private static final Map<Integer, ScheduledTransactionStatus> map = new HashMap<>();

    ScheduledTransactionStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    static {
        for (ScheduledTransactionStatus status : ScheduledTransactionStatus.values()) {
            map.put(status.value, status);
        }
    }

    public static ScheduledTransactionStatus from(int value) {
        return map.get(value);
    }
}
