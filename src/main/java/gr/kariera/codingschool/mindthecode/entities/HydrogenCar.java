package gr.kariera.codingschool.mindthecode.entities;

import javax.persistence.Entity;

@Entity
public class HydrogenCar extends Car {
    private double hydrogenLevel;

    public HydrogenCar() {
    }

    public HydrogenCar(int mileage, String maker, String serialNumber,double hydrogenLevel) {
        super(mileage, maker, serialNumber);
        this.hydrogenLevel = hydrogenLevel;
    }

    public HydrogenCar(int mileage, String maker, String serialNumber, Engine engine, double hydrogenLevel) {
        super(mileage, maker, serialNumber, engine);
        this.hydrogenLevel = hydrogenLevel;
    }
}