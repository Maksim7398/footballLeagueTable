package com.football.model;

import com.football.controller.response.Standings;

public class GetResponseTeamBuilder {

    private static final String DEFAULT_NAME = "VOLGA";

    private static final int DEFAULT_NUMBER_OF_GAMES = 2;

    private static final int DEFAULT_SCIP_GOALS = 2;

    private static final Integer DEFAULT_TOTAL_GOALS = 2;

    private static final Integer DEFAULT_POINTS = 3;

    private String name = DEFAULT_NAME;

    private int numberOfGames = DEFAULT_NUMBER_OF_GAMES;

    private int scipGoals = DEFAULT_SCIP_GOALS;

    private Integer totalGoals = DEFAULT_TOTAL_GOALS;

    private Integer points = DEFAULT_POINTS;

    private GetResponseTeamBuilder() {

    }

    public static GetResponseTeamBuilder aTeamGetresponseTeamBuilder() {
        return new GetResponseTeamBuilder();
    }

    public GetResponseTeamBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public GetResponseTeamBuilder withNumberOfGames(int numberOfGames) {
        this.numberOfGames = numberOfGames;
        return this;
    }

    public GetResponseTeamBuilder withTotalGoals(int toatalGoals) {
        this.totalGoals = toatalGoals;
        return this;
    }

    public GetResponseTeamBuilder withScipGoals(int scipGoals) {
        this.scipGoals = scipGoals;
        return this;
    }

    public GetResponseTeamBuilder withPoints(int points) {
        this.points = points;
        return this;
    }

    public Standings build() {
        return Standings.builder()
                .name(name)
                .points(points)
                .totalGoals(totalGoals)
                .scipGoals(scipGoals)
                .numberOfGames(numberOfGames)
                .build();
    }
}
