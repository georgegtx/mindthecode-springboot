package gr.kariera.codingschool.mindthecode.entities;

import javax.persistence.Entity;

@Entity
public class ElectricCar extends Car {
    private double batteryLevel;

    public ElectricCar() {
    }

    public ElectricCar(int mileage, String maker, String serialNumber, double batteryLevel) {
        super(mileage, maker, serialNumber);
        this.batteryLevel = batteryLevel;

    }

    public ElectricCar(int mileage, String maker, String serialNumber, Engine engine, double batteryLevel) {
        super(mileage, maker, serialNumber, engine);
        this.batteryLevel = batteryLevel;
    }

    public double getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(double batteryLevel) {
        this.batteryLevel = batteryLevel;
    }
}