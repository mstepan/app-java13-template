package com.max.app.concurrency.tank;

public class WaterTankImpl implements WaterTank {

    private static final double MIN_CAPACITY = 10.0;

    private final Object mutex = new Object();

    private double capacity;
    private double volume;

    public WaterTankImpl(double capacity) {
        if (Double.compare(capacity, MIN_CAPACITY) < 0) {
            throw new IllegalArgumentException("Very small capacity for WaterTank, should be at least " + MIN_CAPACITY +
                                                       ", but passed " + capacity);
        }
        this.capacity = capacity;
    }

    @Override
    public double getCapacity() {
        return capacity;
    }

    @Override
    public double getVolume() {
        return volume;
    }

    @Override
    public synchronized void addWater(double amount) {
        synchronized (mutex) {

            double curVolume = volume + amount;

            if (Double.compare(curVolume, capacity) > 0) {
                throw new IllegalStateException("Overflow detected");
            }

            this.volume += amount;
        }
    }

    @Override
    public void removeWater(double amount) {
        synchronized (mutex) {
            if (Double.compare(amount, volume) > 0) {
                throw new IllegalStateException("Underflow detected");
            }

            volume -= amount;
        }
    }
}
