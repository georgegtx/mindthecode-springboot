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

    @GetMapping(value = "/api/drivers")
    List<Driver> GetDrivers() {
        return repository.findAll();
    }

    @GetMapping("/api/drivers/{id}")
    Driver GetDriver(@PathVariable("id") Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cannot find driver with id " + id));
    }

    @PutMapping("/api/drivers/{id}")
    Driver updateDriver(@RequestBody Driver newDriver, @PathVariable Long id) {

        return repository.findById(id)
                .map(match -> {
                    match.setFirstName(newDriver.getFirstName());
                    match.setLastName(newDriver.getLastName());
                    match.setAge(newDriver.getAge());
                    match.setLicenceNumber(newDriver.getLicenceNumber());
                    match.setCars(newDriver.getCars());
                    return repository.save(match);
                })
                .orElseGet(() -> {
                    newDriver.setId(id);
                    return repository.save(newDriver);
                });
    }

    @DeleteMapping("/api/drivers")
    void deleteAllDrivers() {
        repository.deleteAll();
    }

    @DeleteMapping("/api/drivers/{id}")
    void deleteDriver(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
