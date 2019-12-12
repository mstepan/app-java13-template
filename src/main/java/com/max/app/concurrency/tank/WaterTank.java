package com.max.app.concurrency.tank;

public interface WaterTank {

    double getCapacity();

    double getVolume();

    void addWater(double amount);

    void removeWater(double amount);
}
