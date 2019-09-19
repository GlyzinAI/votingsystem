package ru.project.votingsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.project.votingsystem.model.Dish;

import java.util.List;

@Transactional(readOnly = true)
public interface DishRepository extends JpaRepository<Dish, Integer> {

    @Transactional
    @Override
    Dish save(Dish dish);

    List<Dish> getAllByRestaurantId(int restaurantId);

    @Query("SELECT d FROM Dish d JOIN FETCH d.restaurant WHERE d.date = current_date")
    List<Dish> getDishesWithRestaurantToday();

    @Query("SELECT d FROM Dish d WHERE d.restaurant.id =?1 AND d.date = current_date")
    List<Dish> getDishesByRestaurantId(int restaurantId);

    Dish getByIdAndRestaurantId(int dishId, int restaurantId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Dish d WHERE d.id=:id AND d.restaurant.id=:restaurantId")
    int delete(@Param("id") int id, @Param("restaurantId") int restaurantId);
}
