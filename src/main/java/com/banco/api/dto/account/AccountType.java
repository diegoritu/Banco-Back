package com.banco.api.dto.account;

import java.util.HashMap;
import java.util.Map;

public enum AccountType {

    CHECKING(0),
    SAVINGS(1);

    private final int value;
    private static final Map<Integer, AccountType> map = new HashMap<>();

    AccountType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    static {
        for (AccountType accountType : AccountType.values()) {
            map.put(accountType.value, accountType);
        }
    }

    public static AccountType valueOf(int value) {
        return map.get(value);
    }
}
