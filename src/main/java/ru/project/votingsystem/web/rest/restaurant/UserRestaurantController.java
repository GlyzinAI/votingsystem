package ru.project.votingsystem.web.rest.restaurant;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.project.votingsystem.service.restaurant.RestaurantService;
import ru.project.votingsystem.to.RestaurantTo;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.project.votingsystem.util.RestaurantUtil.createNewFromRestaurant;

@RestController
@RequestMapping(value = UserRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserRestaurantController {
    private static final Logger log = getLogger(UserRestaurantController.class);

    static final String REST_URL = "/restaurants";
    private final RestaurantService restaurantService;

    @Autowired
    public UserRestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping(value = "/{id}")
    public RestaurantTo get(@PathVariable("id") int id) {
        log.info("get restaurant by id {}", id);
        return createNewFromRestaurant(restaurantService.get(id));
    }

    @GetMapping("/today")
    public List<RestaurantTo> getWithDailyDishes() {
        log.info("get restaurant with dishes");
        return restaurantService.getWithDailyDishes();
    }
}