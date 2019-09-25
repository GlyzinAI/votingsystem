package ru.project.votingsystem.web.rest.dish;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.project.votingsystem.model.Dish;
import ru.project.votingsystem.service.DishService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping(value = AdminDishController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminDishController {
    private static final Logger log = getLogger(AdminDishController.class);

    static final String REST_URL = "/admin/restaurants/{restaurantId}/dishes";
    private final DishService dishService;

    @Autowired
    public AdminDishController(DishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping
    public List<Dish> getAll(@PathVariable int restaurantId) {
        log.info("getAll for restaurant {}", restaurantId);
        return dishService.getAll(restaurantId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> create(@PathVariable("restaurantId") int restaurantId,
                                       @Valid @RequestBody Dish dish) {
        log.info("create dish {} for restaurant {}", dish.getName(), restaurantId);
        Dish created = dishService.createOrUpdate(dish, restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{dishId}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping("/{dishId}")
    public Dish get(@PathVariable("restaurantId") int restaurantId,
                    @PathVariable("dishId") int dishId) {
        log.info("get dish {} for restaurant {}", dishId, restaurantId);
        return dishService.get(dishId, restaurantId);
    }

    @DeleteMapping("/{dishId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("restaurantId") int restaurantId,
                       @PathVariable("dishId") int dishId) {
        log.info("delete dish {} for restaurant {}", dishId, restaurantId);
        dishService.delete(dishId, restaurantId);
    }


    @PutMapping(value = "/{dishId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@PathVariable("restaurantId") int restaurantId,
                       @PathVariable("dishId") int dishId, @Valid @RequestBody Dish dish) throws IllegalAccessException {
        if (dish.isNew()) {
            dish.setId(dishId);
        } else if (dish.getId() != dishId) {
            throw new IllegalAccessException("Id ambiguous");
        }
        log.info("update dish {} for restaurant {}", dish.getName(), restaurantId);
        dishService.createOrUpdate(dish, restaurantId);
    }

    @GetMapping("/today")
    public List<Dish> getDailyWithRestaurant() {
        log.info("getDishesWithRestaurant");
        return dishService.getDishesWithRestaurantToday();
    }
}