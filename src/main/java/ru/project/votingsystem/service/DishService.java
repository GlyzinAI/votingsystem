package ru.project.votingsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.project.votingsystem.model.Dish;
import ru.project.votingsystem.repository.DishRepository;
import ru.project.votingsystem.util.exception.NotFoundException;

import java.util.List;

import static ru.project.votingsystem.util.ValidationUtil.checkNotFoundWithId;

@Service
public class DishService {

    private final RestaurantService restaurantService;

    private final DishRepository dishRepository;

    @Autowired
    public DishService(DishRepository dishRepository, RestaurantService restaurantService) {
        this.dishRepository = dishRepository;
        this.restaurantService = restaurantService;
    }

    public Dish get(int dishId, int restaurantId) throws NotFoundException {
        return checkNotFoundWithId(dishRepository.getByIdAndRestaurantId(dishId, restaurantId), dishId);
    }

    @CacheEvict(value = "dishes", allEntries = true)
    public void delete(int dishId, int restaurantId) throws NotFoundException {
        checkNotFoundWithId(dishRepository.delete(dishId, restaurantId) != 0, dishId);
    }

    @Cacheable("dishes")
    public List<Dish> getAll(int restaurantId) {
        return dishRepository.getAllByRestaurantId(restaurantId);
    }

    @CacheEvict(value = "dishes", allEntries = true)
    public Dish createOrUpdate(Dish dish, int restaurantId) {
        Assert.notNull(dish, "dish must not be null");
        dish.setRestaurant(restaurantService.get(restaurantId));
        return dishRepository.save(dish);
    }

    public List<Dish> getDishesWithRestaurantToday() {
        return dishRepository.getDishesWithRestaurantToday();
    }
}