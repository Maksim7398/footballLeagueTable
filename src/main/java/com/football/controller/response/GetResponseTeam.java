package com.football.controller.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetResponseTeam {

    private Integer placeInTheRanking;

    private String name;

    private Integer points;

    private Integer totalGoals;

    private int scipGoals;

    private int numberOfGames;
}
