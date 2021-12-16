package gr.kariera.codingschool.mindthecode.entities;

import javax.persistence.Entity;

@Entity
public class Cook extends Person {

    private int yearsOfExperience;
    private String cuisine;

    public Cook() { }

    public Cook(String firstName, String lastName, int age, int yearsOfExperience, String cuisine) {
        super(firstName, lastName, age);
        this.yearsOfExperience = yearsOfExperience;
        this.cuisine = cuisine;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }


}