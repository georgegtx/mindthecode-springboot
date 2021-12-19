package gr.kariera.codingschool.mindthecode.controllers.mvc.models;

import org.springframework.stereotype.Component;

@Component
public class CarSearchModel {
    private String maker;

    public CarSearchModel(){}
    public CarSearchModel(String maker) {
        this.maker = maker;
    }

    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }
}
