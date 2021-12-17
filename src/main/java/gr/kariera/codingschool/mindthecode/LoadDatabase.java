package gr.kariera.codingschool.mindthecode;

import gr.kariera.codingschool.mindthecode.entities.Car;
import gr.kariera.codingschool.mindthecode.entities.Cook;
import gr.kariera.codingschool.mindthecode.entities.Driver;
import gr.kariera.codingschool.mindthecode.entities.Engine;
import gr.kariera.codingschool.mindthecode.repositories.CarRepository;
import gr.kariera.codingschool.mindthecode.repositories.CookRepository;
import gr.kariera.codingschool.mindthecode.repositories.DriverRepository;
import gr.kariera.codingschool.mindthecode.repositories.EngineRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

class GroupedDrivers {
    private Long CarId;
    private List<Driver> drivers;

    public GroupedDrivers(Long carId, List<Driver> drivers) {
        CarId = carId;
        this.drivers = drivers;
    }

    public Long getCarId() {
        return CarId;
    }

    public void setCarId(Long carId) {
        CarId = carId;
    }

    public List<Driver> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<Driver> drivers) {
        this.drivers = drivers;
    }
}

@Configuration
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    private static String[] driverNames = new String[] {
            "James",
            "George",
            "Maria",
            "Eleni"
    };

    private static String[] driverLastNames = new String[] {
            "Papadopoulos",
            "Karamanlis",
            "Georgalas",
            "Charitou",
            "Papargyris",
            "Tsanos"
    };

    @Bean
    CommandLineRunner initDatabase(
            CarRepository carRepo,
            EngineRepository engineRepo,
            DriverRepository driverRepo,
            CookRepository cookRepo
    ) {
        return args -> {
            log.info("Preloading " + carRepo.saveAll(generateRandomCars()));
            log.info("Preloading " + engineRepo.saveAll(generateRandomEngines(carRepo.findAll())));
            log.info("Preloading " + driverRepo.saveAll(getRandomDrivers(carRepo.findAll())));
            log.info("Preloading " + cookRepo.saveAll(generateRandomCooks()));
        };
    }

    private static String getRandomCarMaker() {
        double r = Math.random();
        if (r > .5) {
            return "Toyota";
        } else {
            return "Suzuki";
        }
    }

    private static List<Cook> generateRandomCooks() {
        int count = getRandomUpperBound(500);

        List<Cook> cooks = new ArrayList<>();

        for (int i = 0; i < count; i++) {

            cooks.add(
                    new Cook(
                            driverNames[getRandomUpperBound(driverNames.length)],
                            driverLastNames[getRandomUpperBound(driverLastNames.length)],
                            getRandomUpperBound(11) + 18,
                            getRandomUpperBound(15),
                            "Greek"
                    )
            );
        }
        return cooks;
    }

    private static List<Driver> getRandomDrivers(List<Car> allCars) {
        List<GroupedDrivers> groupedDrivers = groupDriversByCar(allCars);
        return mixAndMatchDriversWithCars(allCars, groupedDrivers);
    }

    private static List<Driver> mixAndMatchDriversWithCars(List<Car> allCars, List<GroupedDrivers> groupedDrivers) {
        return groupedDrivers.stream().flatMap(
                group -> group
                    .getDrivers().stream().map(mixAndMatchDriverWithCars(allCars))
        )
        .collect(Collectors.toList());
    }

    private static Function<Driver, Driver> mixAndMatchDriverWithCars(List<Car> allCars) {
        return driver -> {

            List<Car> randomCars = allCars.stream()
                    .filter(randomly(allCars))
                    .filter(whenCarAlreadyOwnedBy(driver))
                    .collect(Collectors.toList());

            driver.setCars(randomCars);

            return driver;
        };
    }

    private static List<GroupedDrivers> groupDriversByCar(List<Car> allCars) {
        return allCars.stream()
                .map(generateRandomDriversForEachCar())
                .collect(Collectors.toList());
    }

    private static Function<Car, GroupedDrivers> generateRandomDriversForEachCar() {
        return car -> {
            int driverCount = getRandomUpperBound(3);

            List<Driver> drivers = new ArrayList<>();
            for(int i = 0; i < driverCount; i++) {

                Driver d = new Driver(
                    driverNames[getRandomUpperBound(driverNames.length)],
                    driverLastNames[getRandomUpperBound(driverLastNames.length)],
                    getRandomUpperBound(11) + 18,
                    "cln-" + +getRandomUpperBound(15000000)
                );
                d.setCars(List.of(new Car[] { car }));

                drivers.add(d);
            }
            return new GroupedDrivers(car.getId(), drivers);
        };
    }

    private static int getRandomUpperBound(int i) {
        return new Random().nextInt(i);
    }

    private static Predicate<Car> whenCarAlreadyOwnedBy(Driver driver) {
        return car -> {
            return !driver.getCars().contains(car);
        };
    }

    private static Predicate<Car> randomly(List<Car> allCars) {
        return car-> {
            double r = Math.random() * allCars.size();
            return r < 10;
        };
    }


    private static List<Engine> generateRandomEngines(List<Car> allCars) {

        return allCars.stream().map(car -> {

            Engine engine = new Engine();
            engine.setSize(getRandomUpperBound(3000));
            engine.setPowerHp(getRandomUpperBound(250));
            engine.setCylinders(getRandomUpperBound(4));
            engine.setSerialNumber("esn-" + getRandomUpperBound(15000000));

            engine.setCar(car);

            return engine;
        }).collect(Collectors.toList());

    }

    private static List<Car> generateRandomCars() {
        int count = getRandomUpperBound(500);

        List<Car> cars = new ArrayList<>();

        for (int i = 0; i < count; i++) {

            cars.add(
                    new Car(
                            getRandomUpperBound(150000),
                            getRandomCarMaker(),
                            "sn-" + getRandomUpperBound(15000000)
                    )
            );
        }
        return cars;
    }

}
