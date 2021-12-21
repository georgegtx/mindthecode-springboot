package gr.kariera.codingschool.mindthecode.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "person")
@TypeAlias("driver")
public class Driver extends Person {

    private String licenceNumber;

    @DBRef
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