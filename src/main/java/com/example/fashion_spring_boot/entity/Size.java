package com.example.fashion_spring_boot.entity;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public enum Size {
    SIZE_34(34),
    SIZE_35(35),
    SIZE_36(36),
    SIZE_37(37),
    SIZE_38(38),
    SIZE_39(39),
    SIZE_40(40),
    SIZE_41(41),
    SIZE_42(42),
    SIZE_43(43),
    SIZE_44(44);

    private final int value;

    Size(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Size fromValue(int value) {
        for (Size s : Size.values()) {
            if (s.value == value) {
                return s;
            }
        }
        throw new IllegalArgumentException("Invalid size value: " + value);
    }
}