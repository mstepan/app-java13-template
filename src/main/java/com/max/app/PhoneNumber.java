package com.max.app;

import lombok.Data;

import java.util.Comparator;

@Data
public class PhoneNumber implements Comparable<PhoneNumber> {

    private final short areaCode;
    private final int phone;

    private static final Comparator<PhoneNumber> CMP =
            Comparator.comparingInt((PhoneNumber value) -> value.areaCode).
                    thenComparingInt(value -> value.phone);

    @Override
    public int compareTo(PhoneNumber other) {
        return CMP.compare(this, other);
    }
}
