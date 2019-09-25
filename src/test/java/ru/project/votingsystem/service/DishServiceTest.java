package ru.project.votingsystem.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.project.votingsystem.model.Dish;
import ru.project.votingsystem.util.exception.NotFoundException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.project.votingsystem.data.DishTestData.*;
import static ru.project.votingsystem.data.DishTestData.assertMatch;
import static ru.project.votingsystem.data.RestaurantTestData.*;

class DishServiceTest extends AbstractServiceTest {

    @Autowired
    private DishService service;

    @BeforeEach
    void setUp() {
        cacheManager.getCache("dishes").clear();
        jpaUtil.clear2ndLevelCache();
    }

    @Test
    void get() {
        assertMatch(service.get(FIRST_DISH_ID, FIRST_RESTAURANT_ID), FIRST_DISH);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () ->
                service.get(1, 1));
    }

    @Test
    void delete() {
        service.delete(FIRST_DISH_ID, FIRST_RESTAURANT_ID);
        assertMatch(service.getAll(FIRST_RESTAURANT_ID), THIRD_DISH, SECOND_DISH);
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () ->
                service.delete(1, 1));
    }

    @Test
    void getAll() {
        assertMatch(service.getAll(SECOND_RESTAURANT_ID), FIFTH_DISH, FOURTH_DISH);
    }

    @Test
    void create() {
        Dish dish = new Dish(null, "NewDish", 100, FIFTH_RESTAURANT, LocalDate.of(2019, 9, 25));
        Dish created = service.createOrUpdate(dish, FIFTH_RESTAURANT_ID);
        assertMatch(service.getAll(FIFTH_RESTAURANT_ID), created, TWELVE_DISH, ELEVENTH_DISH);
    }

    @Test
    void update() {
        Dish updated = new Dish(FIRST_DISH);
        updated.setPrice(333);
        updated.setDate(LocalDate.of(2019, 9, 24));
        service.createOrUpdate(new Dish(updated), FIRST_RESTAURANT_ID);
        assertMatch(service.get(FIRST_DISH_ID, FIRST_RESTAURANT_ID), updated);
    }
}