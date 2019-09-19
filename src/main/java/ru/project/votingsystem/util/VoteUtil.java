package ru.project.votingsystem.util;

import ru.project.votingsystem.model.Vote;
import ru.project.votingsystem.to.VoteTo;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

public class VoteUtil {
    private static final LocalTime TIME = LocalTime.of(11, 0);

    public static void checkTime(LocalTime localTime) {
        if (localTime.isAfter(TIME)) {
            throw new IllegalArgumentException(localTime + " it's to late");
        }
    }

    public static VoteTo createNewFromVote(Vote vote) {
        return new VoteTo(vote.getId(), vote.getDate(),
                vote.getUser().getId(), vote.getUser().getName(), vote.getRestaurant().getId(), vote.getRestaurant().getName());
    }

    public static List<VoteTo> createNewFromVote(List<Vote> votes) {
        return votes.stream().map(VoteUtil::createNewFromVote).collect(Collectors.toList());
    }
}