package gr.kariera.codingschool.mindthecode;

import gr.kariera.codingschool.mindthecode.entities.Car;
import gr.kariera.codingschool.mindthecode.entities.Engine;
import gr.kariera.codingschool.mindthecode.repositories.CarRepository;
import gr.kariera.codingschool.mindthecode.repositories.EngineRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Configuration
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(CarRepository carRepo, EngineRepository engineRepo) {
        return args -> {
            log.info("Preloading " + carRepo.saveAll(generateRandomData()));
            log.info("Preloading " + engineRepo.saveAll(generateRandomEngines(carRepo.findAll())));
        };
    }

    private static String getRandomMaker() {
        double r = Math.random();
        if (r > .5) {
            return "Toyota";
        } else {
            return "Suzuki";
        }
    }

    private static List<Engine> generateRandomEngines(List<Car> allCars) {

        return allCars.stream().map(car -> {

            Engine engine = new Engine();
            engine.setSize(new Random().nextInt(3000));
            engine.setPowerHp(new Random().nextInt(250));
            engine.setCylinders(new Random().nextInt(4));
            engine.setSerialNumber("esn-" + new Random().nextInt(15000000));

            engine.setCar(car);

            return engine;
        }).collect(Collectors.toList());

    }

    private static List<Car> generateRandomData() {
        int count = new Random().nextInt(500);

        List<Car> cars = new ArrayList<>();

        for (int i = 0; i < count; i++) {

            cars.add(
                    new Car(
                            new Random().nextInt(150000),
                            getRandomMaker(),
                            "sn-" + new Random().nextInt(15000000)
                    )
            );
        }
        return cars;
    }

}
