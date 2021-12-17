package gr.kariera.codingschool.mindthecode.controllers;

import gr.kariera.codingschool.mindthecode.entities.Cook;
import gr.kariera.codingschool.mindthecode.repositories.CookRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CookController {
    
    private final CookRepository repository;    

     CookController(CookRepository repository) {
        this.repository = repository;
    }

    @GetMapping(value = "/api/cook")
    List<Cook> GetCooks() {
        return repository.findAll();
    }

    @GetMapping("/api/cook/{id}")
    Cook GetCook(@PathVariable("id") Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cannot find cook with id " + id));
    }

    @PutMapping("/api/cook/{id}")
    Cook updateCook(@RequestBody Cook newCook, @PathVariable Long id) {

        return repository.findById(id)
                .map(match -> {
                    match.setFirstName(newCook.getFirstName());
                    match.setLastName(newCook.getLastName());
                    match.setAge(newCook.getAge());
                    match.setYearsOfExperience(newCook.getYearsOfExperience());
                    match.setCuisine(newCook.getCuisine());
                    return repository.save(match);
                })
                .orElseGet(() -> {
                    newCook.setId(id);
                    return repository.save(newCook);
                });
    }

    @DeleteMapping("/api/cook")
    void deleteAllCooks() {
        repository.deleteAll();
    }

    @DeleteMapping("/api/cook/{id}")
    void deleteCook(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
