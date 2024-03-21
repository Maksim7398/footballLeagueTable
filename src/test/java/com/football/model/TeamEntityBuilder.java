package com.football.model;

import com.football.persist.entity.TeamEntity;

import java.util.UUID;

public class TeamEntityBuilder {

    public static final UUID DEFAULT_ID = UUID.randomUUID();

    public static final String DEFAULT_NAME = "Volga";

    public static final Integer DEFAULT_POINTS = 0;

    public static final Integer DEFAULT_OTHER_POINTS = 0;

    public static final int DEFAULT_NUMBER_OF_GAMES = 0;

    public static final int DEFAULT_TOTAL_GOALS = 0;

    public static final int DEFAULT_SCIP_GOALS = 0;

    private UUID id = DEFAULT_ID;

    private String name = DEFAULT_NAME;

    private Integer points = DEFAULT_POINTS;

    private Integer otherPoints = DEFAULT_OTHER_POINTS;

    private int numberOfGames = DEFAULT_NUMBER_OF_GAMES;

    private int totalGoals = DEFAULT_TOTAL_GOALS;

    private int scipGoals = DEFAULT_SCIP_GOALS;

    private TeamEntityBuilder() {

    }

    public static TeamEntityBuilder aTeamEntityBuilder() {
        return new TeamEntityBuilder();
    }

    public TeamEntityBuilder withId(UUID id){
        this.id = id;
        return this;
    }

    public TeamEntityBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public TeamEntityBuilder withPoints(Integer points) {
        this.points = points;
        return this;
    }

    public TeamEntityBuilder withOtherPoints(Integer otherPoints) {
        this.otherPoints = otherPoints;
        return this;
    }

    public TeamEntityBuilder withNumberOfGames(int numberOfGames) {
        this.numberOfGames = numberOfGames;
        return this;
    }

    public TeamEntityBuilder withTotalGoals(Integer totalGoals) {
        this.totalGoals = totalGoals;
        return this;
    }

    public TeamEntityBuilder withScipGoals(int scipGoals) {
        this.scipGoals = scipGoals;
        return this;
    }

    public TeamEntity build() {
        return TeamEntity.builder()
                .id(id)
                .name(name)
                .points(points)
                .otherPoints(otherPoints)
                .numberOfGames(numberOfGames)
                .totalGoals(totalGoals)
                .scipGoals(scipGoals)
                .build();
    }
}
