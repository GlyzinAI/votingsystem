package ru.project.votingsystem.service.dish;

import org.springframework.stereotype.Service;
import ru.project.votingsystem.model.Dish;
import ru.project.votingsystem.util.exception.NotFoundException;

import java.util.List;

@Service
public interface DishService {

    Dish get(int dishId, int restaurantId) throws NotFoundException;

    void delete(int dishId, int restaurantId) throws NotFoundException;

    Dish createOrUpdate(Dish dish, int restaurantId);

    List<Dish> getAll(int restaurantId);

    List<Dish> getDishesWithRestaurantToday();

    List<Dish> getDishesByRestaurantId(int restaurantId);
}
