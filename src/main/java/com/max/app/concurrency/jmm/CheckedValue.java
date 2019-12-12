package com.max.app.concurrency.jmm;

public class CheckedValue {

    private int a;
    private int b;

    public void set() {
        a = 1;
        b = -1;
    }

    public boolean check() {
        return (b == 0) || (b == -1 && a == 1);
    }
}
