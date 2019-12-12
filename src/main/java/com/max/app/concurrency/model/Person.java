package com.max.app.concurrency.model;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public final class Person {

    private final AtomicBoolean married = new AtomicBoolean(false);
    private final AtomicLong income = new AtomicLong(0L);
    private final AtomicInteger age = new AtomicInteger(0);

    public boolean isMarried() {
        return married.get();
    }

    public void setMarried(boolean newValue) {
        married.set(newValue);
    }

    public long getIncome() {
        return income.get();
    }

    public void setIncome(long newIncome) {
        if (newIncome < 0L) {
            throw new IllegalArgumentException("Income can't be negative: " + newIncome);
        }
        income.set(newIncome);
    }

    public int getAge() {
        return age.get();
    }

    public void setAge(int newAge) {
        if (newAge < 0 || newAge > 150) {
            throw new IllegalArgumentException("Incorrect age value : " + newAge + ", expected in range [0; 150]");
        }
        age.set(newAge);
    }
}
