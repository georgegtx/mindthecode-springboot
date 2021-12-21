package gr.kariera.codingschool.mindthecode;

import gr.kariera.codingschool.mindthecode.entities.*;
import gr.kariera.codingschool.mindthecode.repositories.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

class GroupedDrivers {
    private String CarId;
    private List<Driver> drivers;

    public GroupedDrivers(String carId, List<Driver> drivers) {
        CarId = carId;
        this.drivers = drivers;
    }

    public String getCarId() {
        return CarId;
    }

    public void setCarId(String carId) {
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

    @Autowired
    ElectricCarRepository electricCarRepo;
    @Autowired
    HydrogenCarRepository hydrogenCarRepo;
    @Autowired
    EngineRepository engineRepo;
    @Autowired
    DriverRepository driverRepo;
    @Autowired
    CookRepository cookRepo;

    @EventListener(ApplicationReadyEvent.class)
    void initDatabase() {
        log.info("Preloading " + electricCarRepo.saveAll(generateRandomElectricCars()));
        log.info("Preloading " + hydrogenCarRepo.saveAll(generateRandomHydrogenCars()));
        log.info("Preloading " + engineRepo.saveAll(generateRandomEngines(electricCarRepo.findAll())));
        log.info("Preloading " + engineRepo.saveAll(generateRandomEngines(hydrogenCarRepo.findAll())));
        log.info("Preloading " + driverRepo.saveAll(getRandomDrivers(electricCarRepo.findAll())));
        log.info("Preloading " + driverRepo.saveAll(getRandomDrivers(hydrogenCarRepo.findAll())));
        log.info("Preloading " + cookRepo.saveAll(generateRandomCooks()));
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

    private static <T extends Car> List<Driver> getRandomDrivers(List<T> allCars) {
        List<GroupedDrivers> groupedDrivers = groupDriversByCar(allCars);
        return mixAndMatchDriversWithCars(allCars, groupedDrivers);
    }

    private static <T extends Car> List<Driver> mixAndMatchDriversWithCars(List<T> allCars, List<GroupedDrivers> groupedDrivers) {
        return groupedDrivers.stream().flatMap(
                group -> group
                    .getDrivers().stream().map(mixAndMatchDriverWithCars(allCars))
        )
        .collect(Collectors.toList());
    }

    private static <T extends Car> Function<Driver, Driver> mixAndMatchDriverWithCars(List<T> allCars) {
        return driver -> {

            List<Car> randomCars = allCars.stream()
                    .filter(randomly(allCars))
                    .filter(whenCarAlreadyOwnedBy(driver))
                    .collect(Collectors.toList());

            driver.setCars(randomCars);

            return driver;
        };
    }

    private static <T extends Car> List<GroupedDrivers> groupDriversByCar(List<T> allCars) {
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

    private static <T extends Car> Predicate<T> randomly(List<T> allCars) {
        return car-> {
            double r = Math.random() * allCars.size();
            return r < 10;
        };
    }


    private static <T extends Car> List<Engine> generateRandomEngines(List<T> allCars) {

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

    private static List<ElectricCar> generateRandomElectricCars() {
        int count = getRandomUpperBound(500);

        List<ElectricCar> cars = new ArrayList<>();

        for (int i = 0; i < count; i++) {

            cars.add(
                    new ElectricCar(
                            getRandomUpperBound(150000),
                            getRandomCarMaker(),
                            "sn-" + getRandomUpperBound(15000000),
                            getRandomUpperBound(100),
                            ElectricCar.ChargingSocket.Type2
                    )
            );

        }
        return cars;
    }

    private static List<HydrogenCar> generateRandomHydrogenCars() {
        int count = getRandomUpperBound(500);

        List<HydrogenCar> cars = new ArrayList<>();

        for (int i = 0; i < count; i++) {

            cars.add(
                    new HydrogenCar(
                            getRandomUpperBound(150000),
                            getRandomCarMaker(),
                            "sn-" + getRandomUpperBound(15000000),
                            getRandomUpperBound(100)
                    )
            );


        }
        return cars;
    }

}
