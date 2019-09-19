package ru.project.votingsystem.web.rest.vote;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.project.votingsystem.AuthorizedUser;
import ru.project.votingsystem.model.Vote;
import ru.project.votingsystem.service.vote.VoteService;
import ru.project.votingsystem.to.VoteTo;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.security.core.context.SecurityContextHolder.getContext;
import static ru.project.votingsystem.util.ValidationUtil.checkNotFoundWithId;
import static ru.project.votingsystem.util.VoteUtil.checkTime;
import static ru.project.votingsystem.util.VoteUtil.createNewFromVote;

@RestController
@RequestMapping(value = UserVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserVoteController {
    private static final Logger log = getLogger(UserVoteController.class);

    static final String REST_URL = "/votes";

    private final VoteService voteService;

    @Autowired
    public UserVoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @GetMapping
    public VoteTo getToday() {
        int userId = ((AuthorizedUser) getContext().getAuthentication().getPrincipal()).getId();
        log.info("get vote today");
        return createNewFromVote(checkNotFoundWithId(voteService.getTodayByUserId(userId), userId));
    }

    @PostMapping(value = "/{restaurantId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> createOrUpdate(@PathVariable("restaurantId") int restaurantId) {
        log.info("vote for the restaurant {}", restaurantId);
        int userId = ((AuthorizedUser) getContext().getAuthentication().getPrincipal()).getId();
        Vote vote = voteService.getTodayByUserId(userId);
        if (vote == null) {
            vote = new Vote(LocalDate.now());
            Vote created = voteService.createOrUpdate(vote, userId, restaurantId);
            URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{restaurantId}")
                    .buildAndExpand(created.getId()).toUri();

            return ResponseEntity.created(uriOfNewResource).body(created);
        } else {
            checkTime(LocalTime.now());
            voteService.createOrUpdate(vote, userId, restaurantId);
            return ResponseEntity.ok().build();
        }
    }
}