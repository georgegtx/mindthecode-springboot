package gr.kariera.codingschool.mindthecode.controllers.mvc;

import gr.kariera.codingschool.mindthecode.controllers.mvc.models.CarSearchModel;
import gr.kariera.codingschool.mindthecode.entities.ElectricCar;
import gr.kariera.codingschool.mindthecode.repositories.ElectricCarRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class ElectricCarWebController {

    private final ElectricCarRepository repository;

    ElectricCarWebController(ElectricCarRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/cars/electric")
    public String searchCarsSubmit(
            @ModelAttribute CarSearchModel searchModel) {
        return "redirect:/cars/electric?searchByMaker=" + searchModel.getMaker();
    }

    @GetMapping("/cars/electric")
    public String showCars(
            Model model,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String searchByMaker
    ) {
        if (page < 1) {
            return "redirect:/cars/electric?page=1&size="+ size;
        };

        Page<ElectricCar> cars = findPaginated(
                !searchByMaker.equals("") ?
                        repository.findByMakerStartingWith(searchByMaker) :
                        repository.findAll(),
                PageRequest.of(page - 1, size)
        );

        int totalPages = cars.getTotalPages();

        if (totalPages > 0 && page > totalPages) {
            return "redirect:/cars/electric?size="+ size + "&page=" + totalPages;
        };

        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(Math.max(1, page-2), Math.min(page + 2, totalPages))
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        model.addAttribute("page", page);
        model.addAttribute("cars", cars);
        model.addAttribute("searchModel", new CarSearchModel(searchByMaker));
        return "cars";
    }

    @GetMapping("/cars/electric/addcar")
    public String addCar(Model model) {
        model.addAttribute("car", new ElectricCar());
        return "add-car";
    }

    @PostMapping("/cars/electric/addcar")
    public String addCar(ElectricCar car, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-car";
        }

        repository.save(car);
        model.addAttribute("car", car);
        return "redirect:/cars/electric";
    }

    @GetMapping("/cars/electric/update/{id}")
    public String updateCar(@PathVariable("id") String id, Model model) {
        ElectricCar car = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid car Id:" + id));

        model.addAttribute("car", car);
        return "update-car";
    }

    @PostMapping("/cars/electric/update/{id}")
    public String updateCar(@PathVariable("id") String id, ElectricCar car,
                            BindingResult result, Model model) {
        if (result.hasErrors()) {
            car.setId(id);
            return "update-car";
        }

        repository.save(car);
        return "redirect:/cars/electric";
    }

    @GetMapping("/cars/electric/delete/{id}")
    public String deleteCar(@PathVariable("id") String id, Model model) {
        ElectricCar car = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid car Id:" + id));
        repository.delete(car);
        return "redirect:/cars/electric";
    }

    private Page<ElectricCar> findPaginated(List<ElectricCar> cars, Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<ElectricCar> result;

        if (cars.size() < startItem) {
            result = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, cars.size());
            result = cars.subList(startItem, toIndex);
        }

        Page<ElectricCar> carPage = new PageImpl<ElectricCar>(result, PageRequest.of(currentPage, pageSize), cars.size());

        return carPage;
    }

}
