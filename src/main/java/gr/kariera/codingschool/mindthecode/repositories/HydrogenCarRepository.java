package gr.kariera.codingschool.mindthecode.repositories;

import gr.kariera.codingschool.mindthecode.entities.Car;
import gr.kariera.codingschool.mindthecode.entities.HydrogenCar;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface HydrogenCarRepository extends MongoRepository<HydrogenCar, String> {
    List<Car> findAllByMaker(String maker);

    List<Car> findByMakerStartingWith(String maker);
}