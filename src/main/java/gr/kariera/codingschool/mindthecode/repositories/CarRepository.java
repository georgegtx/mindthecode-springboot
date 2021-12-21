package gr.kariera.codingschool.mindthecode.repositories;

import gr.kariera.codingschool.mindthecode.entities.Car;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CarRepository extends MongoRepository<Car, String> {
    List<Car> findAllByMaker(String maker);

    List<Car> findByMakerStartingWith(String maker);
}