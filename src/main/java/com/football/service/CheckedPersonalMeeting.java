package com.football.service;
import com.football.persist.entity.Match;
import com.football.persist.entity.Team;

import java.util.List;

public class CheckedPersonalMeeting {

    public static void comparePoints(List<Match> matchList, Team o1, Team o2) {
        if (o1.getPoints().equals(o2.getPoints())) {
            List<Integer> list = matchList.stream().filter(m -> m.getAwayTeam().equals(o1))
                    .map(Match::getAwayGoals).toList();
            List<Integer> list1 = matchList.stream().filter(m -> m.getAwayTeam().equals(o2))
                    .map(Match::getAwayGoals).toList();
            if (list.get(0) > list1.get(0)) {
                o1.setOtherPoints(1);
            } else {
                o2.setOtherPoints(1);
            }
        }
    }
}
