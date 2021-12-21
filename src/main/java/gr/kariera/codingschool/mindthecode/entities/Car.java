package gr.kariera.codingschool.mindthecode.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "cars")
public abstract class Car {

    @Id
    private String id;

    private int mileage;

    private String maker;

    private String serialNumber;

    @DBRef
    @JsonBackReference
    private List<Driver> drivers;

    @DBRef
    @JsonManagedReference
    private Engine engine;

    public Car(){ }

    public Car(int mileage, String maker, String serialNumber) {
        this.mileage = mileage;
        this.maker = maker;
        this.serialNumber = serialNumber;
    }

    public Car(int mileage, String maker, String serialNumber, Engine engine) {
        this.mileage = mileage;
        this.maker = maker;
        this.serialNumber = serialNumber;
        this.engine = engine;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getId() {
        return id;
    }

    public int getMileage() {
        return mileage;
    }


    public String getMaker() {
        return maker;
    }


    public String getSerialNumber() {
        return serialNumber;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public List<Driver> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<Driver> drivers) {
        this.drivers = drivers;
    }
}
