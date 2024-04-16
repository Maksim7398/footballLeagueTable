package com.football.model;

import java.util.UUID;

public class TeamDtoBuilder {

    public static final UUID DEFAULT_ID = UUID.randomUUID();

    private static final String DEFAULT_NAME = "VOLGA";

    private static final int DEFAULT_NUMBER_OF_GAMES = 0;

    private static final int DEFAULT_SCIP_GOALS = 0;

    private static final Integer DEFAULT_TOTAL_GOALS = 0;

    private static final Integer DEFAULT_POINTS = 0;


    private UUID id = DEFAULT_ID;

    private String name = DEFAULT_NAME;

    private int numberOfGames = DEFAULT_NUMBER_OF_GAMES;

    private int scipGoals = DEFAULT_SCIP_GOALS;

    private Integer totalGoals = DEFAULT_TOTAL_GOALS;

    private Integer points = DEFAULT_POINTS;

    private TeamDtoBuilder() {

    }

    public static TeamDtoBuilder aTeamDtoBuilder() {
        return new TeamDtoBuilder();
    }

    public TeamDtoBuilder withId(UUID id) {
        this.id = id;
        return this;
    }

    public TeamDtoBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public TeamDtoBuilder withNumberOfGames(int numberOfGames) {
        this.numberOfGames = numberOfGames;
        return this;
    }

    public TeamDtoBuilder withTotalGoals(int toatalGoals) {
        this.totalGoals = toatalGoals;
        return this;
    }

    public TeamDtoBuilder withScipGoals(int scipGoals) {
        this.scipGoals = scipGoals;
        return this;
    }

    public TeamDtoBuilder withPoints(int points) {
        this.points = points;
        return this;
    }

    public TeamDTO build() {
        return TeamDTO.builder()
                .id(id)
                .name(name)
                .points(points)
                .totalGoals(totalGoals)
                .scipGoals(scipGoals)
                .numberOfGames(numberOfGames)
                .build();
    }
}
