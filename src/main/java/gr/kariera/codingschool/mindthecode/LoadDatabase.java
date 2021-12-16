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
        int count = new Random().nextInt(500);

        List<Cook> cooks = new ArrayList<>();

        for (int i = 0; i < count; i++) {

            cooks.add(
                    new Cook(
                            driverNames[new Random().nextInt(driverNames.length)],
                            driverLastNames[new Random().nextInt(driverLastNames.length)],
                            new Random().nextInt(11) + 18,
                            new Random().nextInt(15),
                            "Greek"
                    )
            );
        }
        return cooks;
    }

    private static List<Driver> getRandomDrivers(List<Car> allCars) {



        List<GroupedDrivers> groupedDrivers = allCars.stream().map(car -> {
            int driverCount = new Random().nextInt(3);

            List<Driver> drivers = new ArrayList<>();
            for(int i = 0; i < driverCount; i++) {

                Driver d = new Driver();
                d.setLicenceNumber("cln-" + + new Random().nextInt(15000000));
                d.setAge(new Random().nextInt(11) + 18);
                d.setFirstName(driverNames[new Random().nextInt(driverNames.length)]);
                d.setLastName(driverLastNames[new Random().nextInt(driverLastNames.length)]);
                d.setCars(List.of(new Car[] { car }));

                drivers.add(d);
            }


            return new GroupedDrivers(car.getId(), drivers);
        })
        .collect(Collectors.toList());

        List<Driver> finalDrivers = groupedDrivers.stream().map(
                group -> group
                    .getDrivers().stream().map(driver -> {
                        List<Car> randomCars = allCars.stream().filter(car -> {
                            double r = Math.random() * allCars.size();
                            return !driver.getCars().contains(car) && r < 2; // 2 / cars.size()
                        }).collect(Collectors.toList());

                        driver.setCars(randomCars);

                        return driver;
                    })
                    .collect(Collectors.toList())
        )
        .flatMap(g -> g.stream())
        .collect(Collectors.toList());

        return finalDrivers;
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

    private static List<Car> generateRandomCars() {
        int count = new Random().nextInt(500);

        List<Car> cars = new ArrayList<>();

        for (int i = 0; i < count; i++) {

            cars.add(
                    new Car(
                            new Random().nextInt(150000),
                            getRandomCarMaker(),
                            "sn-" + new Random().nextInt(15000000)
                    )
            );
        }
        return cars;
    }

}
