package ru.project.votingsystem.data;

import ru.project.votingsystem.model.Vote;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.project.votingsystem.data.RestaurantTestData.*;
import static ru.project.votingsystem.data.UserTestData.ADMIN;
import static ru.project.votingsystem.data.UserTestData.USER;

public class VoteTestData {

    public static final int FIRST_VOTE_ID = 119;
    public static final Vote FIRST_VOTE = new Vote(FIRST_VOTE_ID, LocalDate.of(2019, 9, 24), USER, FIRST_RESTAURANT);
    public static final Vote SECOND_VOTE = new Vote(FIRST_VOTE_ID + 1, LocalDate.of(2019, 9, 25), USER, FOURTH_RESTAURANT);
    public static final Vote THIRD_VOTE = new Vote(FIRST_VOTE_ID + 2, LocalDate.of(2019, 9, 25), ADMIN, THIRD_RESTAURANT);

    public static void assertMatch(Vote actual, Vote expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "restaurant", "user");
    }

    public static void assertMatch(Iterable<Vote> actual, Vote... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<Vote> actual, Iterable<Vote> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("restaurant", "user").isEqualTo(expected);
    }
}
