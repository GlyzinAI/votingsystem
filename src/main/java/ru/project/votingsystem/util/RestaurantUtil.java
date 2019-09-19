package ru.project.votingsystem.util;

import ru.project.votingsystem.model.Restaurant;
import ru.project.votingsystem.to.RestaurantTo;

import java.util.List;
import java.util.stream.Collectors;

public class RestaurantUtil {

    public static RestaurantTo createNewFromRestaurant(Restaurant restaurant) {
        return new RestaurantTo(restaurant.getId(), restaurant.getName(), restaurant.getDishes());
    }

    public static List<RestaurantTo> createNewFromRestaurant(List<Restaurant> restaurantList) {
        return restaurantList.stream().map(RestaurantUtil::createNewFromRestaurant).collect(Collectors.toList());
    }

    public static List<Restaurant> createNewFromTo(List<RestaurantTo> restaurantListTo) {
        return restaurantListTo.stream().map(RestaurantUtil::createNewFromTo).collect(Collectors.toList());
    }

    public static Restaurant createNewFromTo(RestaurantTo restaurantTo) {
        Restaurant restaurant;
        if (restaurantTo.getId() == null) {
            restaurant = new Restaurant(restaurantTo.getName());
        } else {
            restaurant = new Restaurant(restaurantTo.getId(), restaurantTo.getName());
        }
        return restaurant;
    }

    public static Restaurant updateNewFromTo(Restaurant restaurant, RestaurantTo restaurantTo) {
        restaurant.setName(restaurantTo.getName());
        return restaurant;
    }
}