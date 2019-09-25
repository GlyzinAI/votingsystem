package ru.project.votingsystem.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import ru.project.votingsystem.model.Vote;
import ru.project.votingsystem.util.exception.NotFoundException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.project.votingsystem.data.RestaurantTestData.FIRST_RESTAURANT;
import static ru.project.votingsystem.data.RestaurantTestData.FIRST_RESTAURANT_ID;
import static ru.project.votingsystem.data.UserTestData.USER;
import static ru.project.votingsystem.data.UserTestData.USER_ID;
import static ru.project.votingsystem.data.VoteTestData.*;

class VoteServiceTest extends AbstractServiceTest {

    @Autowired
    private VoteService service;

    @Test
    void delete() {
        service.delete(FIRST_VOTE_ID);
        assertMatch(service.getAll(), SECOND_VOTE, THIRD_VOTE);
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () ->
                service.delete(1));
    }

    @Test
    void create() {
        Vote newVote = new Vote(null, LocalDate.of(2019, 9, 23), USER, FIRST_RESTAURANT);
        Vote created = service.createOrUpdate(new Vote(newVote), USER_ID, FIRST_RESTAURANT_ID);
        assertMatch(service.getAll(), created, FIRST_VOTE, SECOND_VOTE, THIRD_VOTE);
    }

    @Test
    void createVoteInSameDay() {
        assertThrows(DataIntegrityViolationException.class, () -> {
            Vote newVote = new Vote(null, LocalDate.of(2019, 9, 25), USER, FIRST_RESTAURANT);
            Vote created = service.createOrUpdate(new Vote(newVote), USER_ID, FIRST_RESTAURANT_ID);
            assertMatch(service.getAll(), FIRST_VOTE, SECOND_VOTE, THIRD_VOTE, created);
        });
    }

    @Test
    void update() {
        Vote updated = new Vote(FIRST_VOTE);
        updated.setDate(LocalDate.of(2019, 9, 20));
        service.createOrUpdate(new Vote(updated), USER_ID, FIRST_RESTAURANT_ID);
        assertMatch(service.get(FIRST_VOTE_ID), updated);
    }

    @Test
    void get() {
        assertMatch(service.get(FIRST_VOTE_ID + 1), SECOND_VOTE);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () ->
                service.get(1));
    }

    @Test
    void getAll() {
        assertMatch(service.getAll(), FIRST_VOTE, SECOND_VOTE, THIRD_VOTE);
    }
}