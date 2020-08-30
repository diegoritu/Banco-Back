package com.banco.api.dto.user;

import java.util.HashMap;
import java.util.Map;

public enum UserType {

    PHYSICAL(0),
    LEGAL(1),
    ADMINISTRATIVE(2);

    private final int value;
    private static final Map<Integer, UserType> map = new HashMap<>();

    UserType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    static {
        for (UserType userType : UserType.values()) {
            map.put(userType.value, userType);
        }
    }

    public static UserType valueOf(int value) {
        return map.get(value);
    }
}
