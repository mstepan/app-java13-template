package com.max.app.concurrency.cell;

public final class LinkedCell {

    private int value;
    private final LinkedCell next;

    public LinkedCell(int initialValue, LinkedCell next) {
        this.value = initialValue;
        this.next = next;
    }

    public synchronized int getValue() {
        return value;
    }

    public synchronized void setValue(int newValue) {
        this.value = newValue;
    }

    public int size() {
        int size = 0;

        for (LinkedCell cur = this; cur != null; cur = cur.next) {
            ++size;
        }

        return size;
    }

    public int sum() {
        LinkedCell cur = this;

        int sumValue = 0;

        while (cur != null) {
            sumValue += cur.getValue();
            cur = cur.next;
        }

        return sumValue;
    }

}
