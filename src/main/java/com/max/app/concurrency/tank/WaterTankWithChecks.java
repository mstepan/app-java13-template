package com.max.app.concurrency.tank;

public class WaterTankWithChecks implements WaterTank {

    private final WaterTank tank;

    public WaterTankWithChecks(WaterTank tank) {
        this.tank = tank;
    }

    private void checkInvariants() {
        if (Double.compare(tank.getVolume(), 0.0) < 0 ||
                Double.compare(tank.getVolume(), tank.getCapacity()) > 0) {
            throw new AssertionError("Invariant violated");
        }
    }

    @Override
    public synchronized void addWater(double amount) {
        checkInvariants();
        try {
            tank.addWater(amount);
        }
        finally {
            checkInvariants();
        }
    }

    @Override
    public synchronized void removeWater(double amount) {
        checkInvariants();
        try {
            tank.removeWater(amount);
        }
        finally {
            checkInvariants();
        }
    }

    @Override
    public double getCapacity() {
        return tank.getCapacity();
    }

    @Override
    public double getVolume() {
        return tank.getVolume();
    }
}
