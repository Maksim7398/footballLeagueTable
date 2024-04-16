package com.football.controller.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetResponseTeam {

    private String name;

    private String country;

    private String city;

    private int numberOfGames;

    private int scipGoals;

    private Integer totalGoals;

    private Integer points;
}
