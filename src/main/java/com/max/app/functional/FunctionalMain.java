package com.max.app.functional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

public final class FunctionalMain {

    private static final Logger LOG = LoggerFactory.getLogger(FunctionalMain.class);

    public static void main(String[] args) {

        List<Car> cars = Arrays.asList(
                Car.withGasColorAndPassengers(6, "Red", "Jim", "Sheila"),
                Car.withGasColorAndPassengers(3, "Octarine", "Rincewind", "Ridcully"),
                Car.withGasColorAndPassengers(9, "Black", "Weatherwax", "Magrat"),
                Car.withGasColorAndPassengers(7, "Green", "Valentine", "Gillian", "Ann", "Dr. Mahmoud"),
                Car.withGasColorAndPassengers(6, "Red", "Ender", "Hyrum", "Locky", "Bonzo"),
                Car.withGasColorAndPassengers(133, null, "Zorro")
        );

        List<Car> filteredCars = cars.stream().filter(car -> "Black".equals(car.getColor())).collect(toList());

        showAll(filteredCars);

        LOG.info("FunctionalMain done. java version {}", System.getProperty("java.version"));
    }

    private static void showAll(List<Car> cars) {
        LOG.info("-------------------------------------------------------------------------------------------------");
        for (Car singleCar : cars) {
            LOG.info(String.valueOf(singleCar));
        }

        LOG.info("-------------------------------------------------------------------------------------------------");
    }

}
