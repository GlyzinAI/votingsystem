package ru.project.votingsystem.service.restaurant;

import org.springframework.stereotype.Service;
import ru.project.votingsystem.model.Restaurant;
import ru.project.votingsystem.to.RestaurantTo;
import ru.project.votingsystem.util.exception.NotFoundException;

import java.util.List;

@Service
public interface RestaurantService {
    List<Restaurant> getAll();

    void delete(int restaurantId) throws NotFoundException;

    Restaurant create(Restaurant restaurant);

    Restaurant update(RestaurantTo restaurantTo, Restaurant restaurant);

    Restaurant get(int restaurant1Id);

    List<RestaurantTo> getWithDailyDishes() throws NotFoundException;
}
