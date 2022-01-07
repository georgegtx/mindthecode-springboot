package gr.kariera.codingschool.mindthecode.repositories;

import gr.kariera.codingschool.mindthecode.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findAllByMaker(String maker);

    List<Car> findByMakerStartingWith(String maker);

    @Query("SELECT c FROM Car c WHERE Concat(c.maker, c.mileage, c.serialNumber) LIKE %?1%")
    List<Car> freeTextSearch(String query);

    @Transactional
    @Modifying
    @Query("DELETE from Car c WHERE c.mileage > 100000")
    void deleteOldCars();
}