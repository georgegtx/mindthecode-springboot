package gr.kariera.codingschool.mindthecode.entities;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cars")
@TypeAlias("hydrogen")
public class HydrogenCar extends Car {

    private int tankLevel;

    public HydrogenCar(){ }

    public HydrogenCar(int mileage, String maker, String serialNumber, int tankLevel) {
        super(mileage, maker, serialNumber);
        this.tankLevel = tankLevel;
    }

    public HydrogenCar(int mileage, String maker, String serialNumber, int tankLevel, Engine engine) {
        super(mileage, maker, serialNumber, engine);
        this.tankLevel = tankLevel;
    }

    public int getTankLevel() {
        return tankLevel;
    }

    public void setTankLevel(int tankLevel) {
        this.tankLevel = tankLevel;
    }

}