package ru.project.votingsystem.web.rest.vote;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.project.votingsystem.service.VoteService;
import ru.project.votingsystem.to.VoteTo;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.project.votingsystem.util.VoteUtil.createNewFromVote;

@RestController
@RequestMapping(value = AdminVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminVoteController {

    private static final Logger log = getLogger(AdminVoteController.class);

    private final VoteService voteService;

    static final String REST_URL = "/admin/votes";

    @Autowired
    public AdminVoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @GetMapping("/{voteId}")
    public VoteTo get(@PathVariable int voteId) {
        log.info("get vote by id {}", voteId);
        return createNewFromVote(voteService.get(voteId));
    }

    @GetMapping(value = "/today")
    public List<VoteTo> getTodayVotes() {
        log.info("get all vote today");
        return createNewFromVote(voteService.getTodayVotes());
    }

    @GetMapping
    public List<VoteTo> getAll() {
        log.info("get all vote");
        return createNewFromVote(voteService.getAll());
    }

    @DeleteMapping("/{voteId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int voteId) {
        log.info("delete vote by id {}", voteId);
        voteService.delete(voteId);
    }
}
