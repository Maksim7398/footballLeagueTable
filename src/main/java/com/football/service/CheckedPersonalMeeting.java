package com.football.service;

import com.football.persist.entity.MatchEntity;
import com.football.persist.entity.TeamEntity;

import java.util.List;

public class CheckedPersonalMeeting {

    public static void comparePoints(List<MatchEntity> matchEntityList, TeamEntity o1, TeamEntity o2) {
        if (o1.getPoints().equals(o2.getPoints())) {
            List<Integer> list = matchEntityList.stream().filter(m -> m.getAwayTeamEntity().equals(o1))
                    .map(MatchEntity::getAwayGoals).toList();
            List<Integer> list1 = matchEntityList.stream().filter(m -> m.getAwayTeamEntity().equals(o2))
                    .map(MatchEntity::getAwayGoals).toList();
            if (list.get(0) > list1.get(0)) {
                o1.setOtherPoints(1);
            } else {
                o2.setOtherPoints(1);
            }
        }
    }
}
