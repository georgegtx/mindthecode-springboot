package gr.kariera.codingschool.mindthecode.repositories;

import gr.kariera.codingschool.mindthecode.entities.Cook;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CookRepository extends MongoRepository<Cook, String> {
}