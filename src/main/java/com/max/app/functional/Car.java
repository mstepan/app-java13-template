package com.max.app.functional;

import java.util.List;

public final class Car {

    private final int gasLevel;
    private final String color;
    private final List<String> passengers;
    private final List<String> trunkContents;

    private Car(int gasLevel, String color, List<String> passengers, List<String> trunkContents) {
        this.gasLevel = gasLevel;
        this.color = color;
        this.passengers = passengers;
        this.trunkContents = trunkContents;
    }

    public static Car withGasColorAndPassengers(int gasLevel, String color, String... passengers) {
        return new Car(gasLevel, color, List.of(passengers), null);
    }

    public static Car withGasColorPassengersAndTrunk(int gasLevel, String color, String... passengers) {
        return new Car(gasLevel, color, List.of(passengers), List.of("jack", "wrench", "spare wheel"));
    }

    public int getGasLevel() {
        return gasLevel;
    }

    public String getColor() {
        return color;
    }

    public List<String> getPassengers() {
        return passengers;
    }

    public List<String> getTrunkContents() {
        return trunkContents;
    }

    @Override
    public String toString() {
        return "gasLevel: " + gasLevel +
                ", color: '" + color + '\'' +
                ", passengers: " + passengers +
                (trunkContents == null ? ", no trunk" : ", trunkContent: " + trunkContents);
    }
}
