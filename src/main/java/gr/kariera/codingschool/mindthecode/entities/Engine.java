package gr.kariera.codingschool.mindthecode.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "engine")
public class Engine {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "car_id")
    @JsonBackReference
    private Car car;

    private int size;
    private int powerHp;
    private int cylinders;

    private String serialNumber;

    public Engine() { }

    public Engine(String serialNumber, int size, Car car) {
        this.serialNumber = serialNumber;
        this.size = size;
        this.powerHp = powerHp;
        this.cylinders = cylinders;
        this.car = car;
    }
    public Engine(String serialNumber, int size, int powerHp, int cylinders, Car car) {
        this.serialNumber = serialNumber;
        this.size = size;
        this.powerHp = powerHp;
        this.cylinders = cylinders;
        this.car = car;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPowerHp() {
        return powerHp;
    }

    public void setPowerHp(int powerHp) {
        this.powerHp = powerHp;
    }

    public int getCylinders() {
        return cylinders;
    }

    public void setCylinders(int cylinders) {
        this.cylinders = cylinders;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Car getCar() {
        return car;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}