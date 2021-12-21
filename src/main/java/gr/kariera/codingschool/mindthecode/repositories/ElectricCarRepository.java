package gr.kariera.codingschool.mindthecode.repositories;

import gr.kariera.codingschool.mindthecode.entities.ElectricCar;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ElectricCarRepository extends MongoRepository<ElectricCar, String> {
    List<ElectricCar> findAllByMaker(String maker);

    List<ElectricCar> findByMakerStartingWith(String maker);
}