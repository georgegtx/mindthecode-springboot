package gr.kariera.codingschool.mindthecode.entities;

import javax.persistence.*;


@Entity
@Table(name = "car")
public class Car {

    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private int mileage;
    private String maker;
    private String serialNumber;



    @OneToOne(mappedBy = "car")
    private Engine engine;

    public Car (){}

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

    public void setId(Long id) {
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

    public Long getId() {
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
}
