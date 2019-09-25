package ru.project.votingsystem.data;

import ru.project.votingsystem.model.Restaurant;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RestaurantTestData {

    public static final int FIRST_RESTAURANT_ID = 102;
    public static final int SECOND_RESTAURANT_ID = 103;
    public static final int THIRD_RESTAURANT_ID = 104;
    public static final int FOURTH_RESTAURANT_ID = 105;
    public static final int FIFTH_RESTAURANT_ID = 106;

    public static final Restaurant FIRST_RESTAURANT = new Restaurant(FIRST_RESTAURANT_ID, "Сказка Востока");
    public static final Restaurant SECOND_RESTAURANT = new Restaurant(SECOND_RESTAURANT_ID, "La Cucaracha");
    public static final Restaurant THIRD_RESTAURANT = new Restaurant(THIRD_RESTAURANT_ID, "Жемчужина");
    public static final Restaurant FOURTH_RESTAURANT = new Restaurant(FOURTH_RESTAURANT_ID, "Taleon");
    public static final Restaurant FIFTH_RESTAURANT = new Restaurant(FIFTH_RESTAURANT_ID, "Хочу харчо");

    public static void assertMatch(Restaurant actual, Restaurant expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "dishes", "votes");
    }

    public static void assertMatch(Iterable<Restaurant> actual, Restaurant... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<Restaurant> actual, Iterable<Restaurant> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("dishes", "votes").isEqualTo(expected);
    }
}
