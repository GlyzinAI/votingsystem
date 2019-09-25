package ru.project.votingsystem.data;

import ru.project.votingsystem.model.Dish;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.project.votingsystem.data.RestaurantTestData.*;

public class DishTestData {

    public static final int FIRST_DISH_ID = 107;
    public static final Dish FIRST_DISH = new Dish(FIRST_DISH_ID, "Стейк из баранины", 590, FIRST_RESTAURANT, LocalDate.of(2019, 9, 25));
    public static final Dish SECOND_DISH = new Dish(FIRST_DISH_ID + 1, "Чахохбили", 360, FIRST_RESTAURANT, LocalDate.of(2019, 9, 25));
    public static final Dish THIRD_DISH = new Dish(FIRST_DISH_ID + 2, "Плов", 470, FIRST_RESTAURANT, LocalDate.of(2019, 9, 25));
    public static final Dish FOURTH_DISH = new Dish(FIRST_DISH_ID + 3, "Хинкали", 200, SECOND_RESTAURANT, LocalDate.of(2019, 9, 25));
    public static final Dish FIFTH_DISH = new Dish(FIRST_DISH_ID + 4, "Жареный картофель с грибами", 240, SECOND_RESTAURANT, LocalDate.of(2019, 9, 25));
    public static final Dish SIXTH_DISH = new Dish(FIRST_DISH_ID + 5, "Форель", 550, THIRD_RESTAURANT, LocalDate.of(2019, 9, 25));
    public static final Dish SEVENTH_DISH = new Dish(FIRST_DISH_ID + 6, "Манты", 390, THIRD_RESTAURANT, LocalDate.of(2019, 9, 25));
    public static final Dish EIGHTH_DISH = new Dish(FIRST_DISH_ID + 7, "Рагу из индейки", 290, FOURTH_RESTAURANT, LocalDate.of(2019, 9, 25));
    public static final Dish NINTH_DISH = new Dish(FIRST_DISH_ID + 8, "Котлеты пожарские", 330, FOURTH_RESTAURANT, LocalDate.of(2019, 9, 25));
    public static final Dish TENTH_DISH = new Dish(FIRST_DISH_ID + 9, "Медальоны из говядины'", 490, FOURTH_RESTAURANT, LocalDate.of(2019, 9, 25));
    public static final Dish ELEVENTH_DISH = new Dish(FIRST_DISH_ID + 10, "Харчо", 350, FIFTH_RESTAURANT, LocalDate.of(2019, 9, 25));
    public static final Dish TWELVE_DISH = new Dish(FIRST_DISH_ID + 11, "Паста(Карбонара)", 420, FIFTH_RESTAURANT, LocalDate.of(2019, 9, 25));


    public static void assertMatch(Dish actual, Dish expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "restaurant");
    }

    public static void assertMatch(Iterable<Dish> actual, Dish... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<Dish> actual, Iterable<Dish> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("restaurant").isEqualTo(expected);
    }

}
