package com.banco.api.dto.movement;

import java.util.HashMap;
import java.util.Map;

public enum MovementType {

    DEPOSIT(0),
    EXTRACTION(1),
    COMMISSION(2),
    MAINTENANCE(3),
    SERVICES_PAYMENT(4),
    TRANSFER_BETWEEN_OWN_ACCOUNTS(5),
    TRANSFER_TO_OTHER_ACCOUNTS(6),
    INTERESTS(7);

    private final int value;
    private static final Map<Integer, MovementType> map = new HashMap<>();

    MovementType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    static {
        for (MovementType movementType : MovementType.values()) {
            map.put(movementType.value, movementType);
        }
    }

    public static MovementType valueOf(int value) {
        return map.get(value);
    }
}
