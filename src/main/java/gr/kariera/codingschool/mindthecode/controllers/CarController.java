package gr.kariera.codingschool.mindthecode.controllers;

import gr.kariera.codingschool.mindthecode.entities.Car;
import gr.kariera.codingschool.mindthecode.repositories.CarRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CarController {

    private final CarRepository repository;

    CarController(CarRepository repository) {
        this.repository = repository;
    }

    @GetMapping(value = "/api/cars")
    List<Car> GetCars() {
        return repository.findAll();
    }

    @GetMapping("/api/cars/{id}")
    Car GetCar(@PathVariable("id") Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cannot find car with id " + id));
    }

    @PutMapping("/api/cars/{id}")
    Car updateCar(@RequestBody Car newCar, @PathVariable Long id) {

        return repository.findById(id)
                .map(match -> {
                    match.setMaker(newCar.getMaker());
                    match.setMileage(newCar.getMileage());
                    match.setSerialNumber(newCar.getSerialNumber());
                    return repository.save(match);
                })
                .orElseGet(() -> {
                    newCar.setId(id);
                    return repository.save(newCar);
                });
    }

    @DeleteMapping("/api/cars")
    void deleteAllCars() {
        repository.deleteAll();
    }

    @DeleteMapping("/api/cars/{id}")
    void deleteCar(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
