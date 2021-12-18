package gr.kariera.codingschool.mindthecode.repositories;

import gr.kariera.codingschool.mindthecode.entities.HydrogenCar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HydrogenCarRepository extends JpaRepository<HydrogenCar, Long> {
}