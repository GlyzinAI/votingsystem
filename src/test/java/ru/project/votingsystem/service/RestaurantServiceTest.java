package ru.project.votingsystem.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import ru.project.votingsystem.model.Restaurant;
import ru.project.votingsystem.util.exception.NotFoundException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.project.votingsystem.data.RestaurantTestData.*;

class RestaurantServiceTest extends AbstractServiceTest {

    @Autowired
    private RestaurantService service;

    @BeforeEach
    void setUp() {
        cacheManager.getCache("dishes").clear();
        jpaUtil.clear2ndLevelCache();
    }

    @Test
    void getAll() {
        assertMatch(service.getAll(), SECOND_RESTAURANT, FOURTH_RESTAURANT, THIRD_RESTAURANT, FIRST_RESTAURANT, FIFTH_RESTAURANT);
    }

    @Test
    void delete() {
        service.delete(SECOND_RESTAURANT_ID);
        service.delete(FOURTH_RESTAURANT_ID);
        assertMatch(service.getAll(), THIRD_RESTAURANT, FIRST_RESTAURANT, FIFTH_RESTAURANT);
    }

    @Test
    void deleteNodFound() {
        assertThrows(NotFoundException.class, () ->
                service.delete(1));
    }

    @Test
    void create() {
        Restaurant newRest = new Restaurant("NewRest");
        Restaurant created = service.create(new Restaurant(newRest));
        newRest.setId(created.getId());
        assertMatch(created, newRest);
        assertMatch(service.getAll(), SECOND_RESTAURANT, newRest, FOURTH_RESTAURANT, THIRD_RESTAURANT, FIRST_RESTAURANT, FIFTH_RESTAURANT);
    }

    @Test
    void duplicateNameCreate() {
        assertThrows(DataAccessException.class, () ->
                service.create(new Restaurant("Сказка Востока")));
    }

    @Test
    void update() {
        Restaurant updated = new Restaurant(FIRST_RESTAURANT);
        updated.setName("UpdatedName");
        service.update(new Restaurant(updated));
        assertMatch(service.get(FIRST_RESTAURANT_ID), updated);
    }

    @Test
    void get() {
        Restaurant restaurant = service.get(FIFTH_RESTAURANT_ID);
        assertMatch(restaurant, FIFTH_RESTAURANT);
    }

    @Test
    void getNodFound() {
        assertThrows(NotFoundException.class, () ->
                service.get(1));
    }
}