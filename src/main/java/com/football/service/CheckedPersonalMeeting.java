package com.football.service;

import com.football.model.TeamDTO;
import com.football.persist.entity.MatchEntity;

import java.util.List;

public class CheckedPersonalMeeting {

    public static void comparePoints(List<MatchEntity> matchEntityList, TeamDTO o1, TeamDTO o2) {
        if (o1.getPoints() == o2.getPoints()) {
            List<Integer> list = matchEntityList.stream()
                    .filter(m ->
                            m.getAwayTeam().getId().equals(o2.getId())
                                    && m.getHomeTeam().getId().equals(o1.getId()))
                    .map(MatchEntity::getAwayGoals).toList();

            List<Integer> list1 = matchEntityList.stream()
                    .filter(m ->
                            m.getAwayTeam().getId().equals(o1.getId())
                                    && m.getHomeTeam().getId().equals(o2.getId()))
                    .map(MatchEntity::getAwayGoals).toList();
            System.out.println(matchEntityList);
            if (list.get(0) > list1.get(0)) {
                o1.setOtherPoints(1);
            }
            if (list.get(0).equals(list1.get(0))) {
                return;
            } else {
                o2.setOtherPoints(1);
            }
        }
    }
}
