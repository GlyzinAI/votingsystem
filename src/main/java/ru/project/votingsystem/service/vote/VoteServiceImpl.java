package ru.project.votingsystem.service.vote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.project.votingsystem.model.Vote;
import ru.project.votingsystem.repository.VoteRepository;
import ru.project.votingsystem.service.restaurant.RestaurantService;
import ru.project.votingsystem.service.user.UserService;
import ru.project.votingsystem.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

import static ru.project.votingsystem.util.ValidationUtil.checkNotFoundWithId;

@Service
public class VoteServiceImpl implements VoteService {

    private final VoteRepository voteRepository;
    private final RestaurantService restaurantService;
    private final UserService userService;

    @Autowired
    public VoteServiceImpl(VoteRepository voteRepository,
                           RestaurantService restaurantService, UserService userService) {
        this.voteRepository = voteRepository;
        this.restaurantService = restaurantService;
        this.userService = userService;
    }

    @Override
    public void delete(int voteId) throws NotFoundException {
        checkNotFoundWithId(voteRepository.delete(voteId) != 0, voteId);
    }

    @Override
    public Vote createOrUpdate(Vote vote, int userId, int restaurantId) {
        vote.setRestaurant(restaurantService.get(restaurantId));
        vote.setUser(userService.get(userId));
        return voteRepository.save(vote);
    }

    @Override
    public Vote get(int voteId) {
        return checkNotFoundWithId(voteRepository.findById(voteId).orElseThrow(() ->
                new NotFoundException("Not found vote with id = " + voteId)), voteId);
    }

    @Override
    public Vote getTodayByUserId(int userId) {
        return voteRepository.findByUserIdAndDate(userId, LocalDate.now());
    }

    @Override
    public List<Vote> getAll() {
        return voteRepository.findAll();
    }

    @Override
    public List<Vote> getTodayVotes() {
        return voteRepository.getAllByDate(LocalDate.now());
    }
}