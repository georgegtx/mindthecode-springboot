package gr.kariera.codingschool.mindthecode.repositories;

import gr.kariera.codingschool.mindthecode.entities.ElectricCar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ElectricCarRepository extends JpaRepository<ElectricCar, Long> {
}