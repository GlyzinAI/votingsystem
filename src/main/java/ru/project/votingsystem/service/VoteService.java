package ru.project.votingsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.project.votingsystem.model.Vote;
import ru.project.votingsystem.repository.VoteRepository;
import ru.project.votingsystem.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

import static ru.project.votingsystem.util.ValidationUtil.checkNotFoundWithId;

@Service
public class VoteService {

    private final VoteRepository voteRepository;
    private final RestaurantService restaurantService;
    private final UserService userService;

    @Autowired
    public VoteService(VoteRepository voteRepository,
                       RestaurantService restaurantService, UserService userService) {
        this.voteRepository = voteRepository;
        this.restaurantService = restaurantService;
        this.userService = userService;
    }

    public void delete(int voteId) throws NotFoundException {
        checkNotFoundWithId(voteRepository.delete(voteId) != 0, voteId);
    }

    public Vote createOrUpdate(Vote vote, int userId, int restaurantId) {
        vote.setRestaurant(restaurantService.get(restaurantId));
        vote.setUser(userService.get(userId));
        return voteRepository.save(vote);
    }

    public Vote get(int voteId) {
        return checkNotFoundWithId(voteRepository.findById(voteId).orElseThrow(() ->
                new NotFoundException("Not found vote with id = " + voteId)), voteId);
    }

    public Vote getTodayByUserId(int userId) {
        return voteRepository.findByUserIdAndDate(userId, LocalDate.now());
    }

    /*public List<Vote> getAll() {
        return voteRepository.findAll(new Sort(Sort.Direction.ASC, "date"));
    }*/

    public List<Vote> getAll() {
        return voteRepository.findAll();
    }

    public List<Vote> getTodayVotes() {
        return voteRepository.getAllByDate(LocalDate.now());
    }
}