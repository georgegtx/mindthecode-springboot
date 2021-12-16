package gr.kariera.codingschool.mindthecode.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "driver")
public class Driver extends Person {

    private String licenceNumber;

    @ManyToMany
    @JoinTable(
            name = "DRIVER_CARS",
            joinColumns = @JoinColumn(name = "driver_id"),
            inverseJoinColumns = @JoinColumn(name = "car_id"))
    @JsonManagedReference
    private List<Car> cars;

    public Driver () {}

    public Driver(String firstName, String lastName, int age, String licenceNumber) {
        super(firstName, lastName, age);
        this.licenceNumber = licenceNumber;
    }

    public String getLicenceNumber() {
        return licenceNumber;
    }

    public void setLicenceNumber(String licenceNumber) {
        this.licenceNumber = licenceNumber;
    }


    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }
}