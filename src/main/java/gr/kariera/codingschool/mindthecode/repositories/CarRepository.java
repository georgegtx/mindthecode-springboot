package gr.kariera.codingschool.mindthecode.repositories;

import gr.kariera.codingschool.mindthecode.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findAllByMaker(String maker);

    List<Car> findByMakerStartingWith(String maker);
}