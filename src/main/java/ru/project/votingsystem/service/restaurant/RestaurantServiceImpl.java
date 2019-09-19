package ru.project.votingsystem.service.restaurant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.project.votingsystem.model.Dish;
import ru.project.votingsystem.model.Restaurant;
import ru.project.votingsystem.repository.DishRepository;
import ru.project.votingsystem.repository.RestaurantRepository;
import ru.project.votingsystem.to.RestaurantTo;
import ru.project.votingsystem.util.exception.NotFoundException;

import java.util.*;

import static ru.project.votingsystem.util.RestaurantUtil.createNewFromRestaurant;
import static ru.project.votingsystem.util.RestaurantUtil.updateNewFromTo;
import static ru.project.votingsystem.util.ValidationUtil.checkNotFoundWithId;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final DishRepository dishRepository;

    @Autowired
    public RestaurantServiceImpl(RestaurantRepository restaurantRepository, DishRepository dishRepository) {
        this.restaurantRepository = restaurantRepository;
        this.dishRepository = dishRepository;
    }

    @Override
    public List<RestaurantTo> getWithDailyDishes() throws NotFoundException {
        List<Dish> dishes = dishRepository.getDishesWithRestaurantToday();
        List<Restaurant> restaurantWithDailyDishes = new ArrayList<>();

        Map<Integer, List<Dish>> map = new HashMap<>();
        for (Dish dish : dishes) {
            Restaurant restaurant = dish.getRestaurant();
            int restaurantId = restaurant.getId();
            if (map.containsKey(restaurantId)) {
                List<Dish> dishList = map.get(restaurantId);
                dishList.add(dish);
                map.put(restaurantId, dishList);
            } else {
                List<Dish> newList = new ArrayList<>();
                newList.add(dish);
                map.put(restaurantId, newList);
            }
        }
        for (Dish dish : dishes) {
            Restaurant restaurant = dish.getRestaurant();
            if (!restaurantWithDailyDishes.contains(restaurant)) {
                restaurant.setDishes(Collections.emptyList());
                restaurant.setDishes(map.get(restaurant.getId()));
                restaurantWithDailyDishes.add(restaurant);
            }
        }
        return createNewFromRestaurant(restaurantWithDailyDishes);
    }

    @Override
    public List<Restaurant> getAll() {
        return restaurantRepository.getAll();
    }

    @Override
    public void delete(int restaurantId) throws NotFoundException {
        checkNotFoundWithId(restaurantRepository.delete(restaurantId) != 0, restaurantId);
    }

    @Override
    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant update(RestaurantTo restaurantTo, Restaurant restaurant) {
        return restaurantRepository.save(updateNewFromTo(restaurant, restaurantTo));
    }

    @Override
    public Restaurant get(int restaurant1Id) {
        return checkNotFoundWithId(restaurantRepository.findById(restaurant1Id).orElse(null), restaurant1Id);
    }
}