package ru.project.votingsystem.to;

import ru.project.votingsystem.model.Dish;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

public class RestaurantTo extends BaseTo implements Serializable {

    @NotBlank
    @Size(min = 2, max = 100)
    private String name;

    private List<Dish> dishes;

    public RestaurantTo() {
    }

    public RestaurantTo(Integer id, String name, List<Dish> dishes) {
        super(id);
        this.name = name;
        this.dishes = dishes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }

    @Override
    public String toString() {
        return "RestaurantTo{" + " id=" + id +
                ", name='" + name + '\'' +
                ", dishes=" + dishes +
                "}";
    }
}
