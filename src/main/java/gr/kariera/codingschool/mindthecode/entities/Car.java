package gr.kariera.codingschool.mindthecode.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;


@Entity
@Table(name = "car")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private int mileage;

    @NotBlank(message = "Car Maker is required")
    private String maker;

    @Pattern(regexp = "sn-\\d{0,17}\\Z", message = "Serial number must be in the format sn-[7 digits]")
    private String serialNumber;

    @ManyToMany(mappedBy = "cars")
    @JsonBackReference
    private List<Driver> drivers;

    @OneToOne(mappedBy = "car")
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

    public List<Driver> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<Driver> drivers) {
        this.drivers = drivers;
    }
}
