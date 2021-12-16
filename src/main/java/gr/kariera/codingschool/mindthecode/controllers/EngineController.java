package gr.kariera.codingschool.mindthecode.controllers;

import gr.kariera.codingschool.mindthecode.entities.Engine;
import gr.kariera.codingschool.mindthecode.repositories.EngineRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EngineController {

    private final EngineRepository repository;

    EngineController(EngineRepository repository) {
        this.repository = repository;
    }

    @GetMapping(value = "/api/engines")
    List<Engine> GetEngines() {
        return repository.findAll();
    }

    @GetMapping("/api/engines/{id}")
    Engine GetEngine(@PathVariable("id") Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cannot find engine with id " + id));
    }

    @PutMapping("/api/engines/{id}")
    Engine updateEngine(@RequestBody Engine newEngine, @PathVariable Long id) {

        return repository.findById(id)
                .map(match -> {
                    match.setCylinders(newEngine.getCylinders());
                    match.setPowerHp(newEngine.getPowerHp());
                    match.setSize(newEngine.getSize());
                    match.setSerialNumber(newEngine.getSerialNumber());
                    return repository.save(match);
                })
                .orElseGet(() -> {
                    newEngine.setId(id);
                    return repository.save(newEngine);
                });
    }

    @DeleteMapping("/api/engines")
    void deleteAllEngines() {
        repository.deleteAll();
    }

    @DeleteMapping("/api/engines/{id}")
    void deleteEngine(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
