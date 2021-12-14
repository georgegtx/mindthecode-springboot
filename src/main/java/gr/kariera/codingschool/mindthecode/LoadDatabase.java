package gr.kariera.codingschool.mindthecode;

import gr.kariera.codingschool.mindthecode.entities.Car;
import gr.kariera.codingschool.mindthecode.repositories.CarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@Configuration
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(CarRepository repository) {
        return args -> {
            log.info("Preloading " + repository.saveAll(generateRandomData()));
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

    private static List<Car> generateRandomData() {
        int count = new Random().nextInt(500);
        List<Car> cars = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            cars.add(
                    new Car(i,
                            new Random().nextInt(150000),
                            getRandomMaker(),
                            "sn-" + new Random().nextInt(15000000)
                    )
            );
        }
        return cars;
    }

}
