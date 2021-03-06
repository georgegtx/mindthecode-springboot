package gr.kariera.codingschool.mindthecode.controllers.mvc;

import gr.kariera.codingschool.mindthecode.controllers.mvc.models.CarSearchModel;
import gr.kariera.codingschool.mindthecode.entities.Car;
import gr.kariera.codingschool.mindthecode.repositories.CarRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class CarWebController {

    private final CarRepository repository;

    CarWebController(CarRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/cars")
    public Object searchCarsSubmit(
            @ModelAttribute CarSearchModel searchModel) {
        return "redirect:/cars?search=" + searchModel.getQuery();
    }

    @GetMapping("/cars")
    public Object showCars(
            Model model,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String search
    ) {
        if (page < 1) {
            return new RedirectView("/cars?page=1&size="+ size);
        };

        Page<Car> cars = findPaginated(
                !search.equals("") ?
                        repository.freeTextSearch(search) :
                        repository.findAll(),
                PageRequest.of(page - 1, size)
        );

        int totalPages = cars.getTotalPages();

        if (page > totalPages) {
            return new RedirectView("/cars?size="+ size + "&page=" + totalPages);
        };

        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(Math.max(1, page-2), Math.min(page + 2, totalPages))
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        model.addAttribute("page", page);
        model.addAttribute("cars", cars);
        model.addAttribute("searchModel", new CarSearchModel(search));
        return "cars";
    }

    @GetMapping("/cars/addcar")
    public String addCar(Model model) {
        model.addAttribute("car", new Car());
        return "add-car";
    }

    @GetMapping("/cars/deleteOld")
    public String deleteOld(Model model) {
        repository.deleteOldCars();
        return "redirect:/cars";
    }



    @PostMapping("/cars/addcar")
    public String addCar(@Valid Car car, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-car";
        }

        repository.save(car);
        model.addAttribute("car", car);
        return "redirect:/cars";
    }

    @GetMapping("/cars/update/{id}")
    public String updateCar(@PathVariable("id") long id, Model model) {
        Car car = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid car Id:" + id));

        model.addAttribute("car", car);
        return "update-car";
    }

    @PostMapping("/cars/update/{id}")
    public String updateCar(@PathVariable("id") long id, @Valid Car car,
                            BindingResult result, Model model) {
        if (result.hasErrors()) {
            car.setId(id);
            return "update-car";
        }

        repository.save(car);
        return "redirect:/cars";
    }

    @GetMapping("/cars/delete/{id}")
    public String deleteCar(@PathVariable("id") long id, Model model) {
        Car car = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid car Id:" + id));
        repository.delete(car);
        return "redirect:/cars";
    }

    private Page<Car> findPaginated(List<Car> cars, Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<Car> result;

        if (cars.size() < startItem) {
            result = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, cars.size());
            result = cars.subList(startItem, toIndex);
        }

        Page<Car> carPage = new PageImpl<Car>(result, PageRequest.of(currentPage, pageSize), cars.size());

        return carPage;
    }

}
