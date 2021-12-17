package gr.kariera.codingschool.mindthecode.controllers;

import gr.kariera.codingschool.mindthecode.entities.Driver;
import gr.kariera.codingschool.mindthecode.repositories.DriverRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DriverController {

    private final DriverRepository repository;

    DriverController(DriverRepository repository) {
        this.repository = repository;
    }

    @GetMapping(value = "/api/Drivers")
    List<Driver> GetDrivers() {
        return repository.findAll();
    }

    @GetMapping("/api/Drivers/{id}")
    Driver GetDriver(@PathVariable("id") Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cannot find Driver with id " + id));
    }

    @PutMapping("/api/Drivers/{id}")
    Driver updateDriver(@RequestBody Driver newDriver, @PathVariable Long id) {

        return repository.findById(id)
                .map(match -> {
                    match.setCars(newDriver.getCars());
                    match.setLastName(newDriver.getLastName());
                    match.setFirstName(newDriver.getFirstName());
                    match.setLicenceNumber(newDriver.getLicenceNumber());
                    match.setAge(newDriver.getAge());
                    return repository.save(match);
                })
                .orElseGet(() -> {
                    newDriver.setId(id);
                    return repository.save(newDriver);
                });
    }

    @DeleteMapping("/api/Drivers")
    void deleteAllDrivers() {
        repository.deleteAll();
    }

    @DeleteMapping("/api/Drivers/{id}")
    void deleteDriver(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
