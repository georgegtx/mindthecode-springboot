package gr.kariera.codingschool.mindthecode.repositories;

import gr.kariera.codingschool.mindthecode.entities.Engine;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EngineRepository extends MongoRepository<Engine, String> { }