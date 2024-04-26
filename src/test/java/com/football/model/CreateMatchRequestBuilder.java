package com.football.model;

import com.football.controller.request.CreateMatchRequest;

import java.util.UUID;

public class CreateMatchRequestBuilder {

    private final static Long TOURNAMENT_ID = 1L;

    private final static UUID DEFAULT_HOME_TEAM = UUID.randomUUID();

    private final static UUID DEFAULT_AWAY_TEAM = UUID.randomUUID();

    private final static Integer DEFAULT_HOME_GOALS = 2;

    private final static Integer DEFAULT_AWAY_GOALS = 2;

    private Long tournamentId = TOURNAMENT_ID;

    private UUID homeTeam = DEFAULT_HOME_TEAM;

    private Integer homeGoals = DEFAULT_HOME_GOALS;

    private UUID awayTeam = DEFAULT_AWAY_TEAM;

    private Integer awayGoals = DEFAULT_AWAY_GOALS;

    private CreateMatchRequestBuilder() {
    }

    public static CreateMatchRequestBuilder aCreateMatchRequestBuilder() {
        return new CreateMatchRequestBuilder();
    }


    public CreateMatchRequestBuilder withHomeTeam(UUID homeTeam) {
        this.homeTeam = homeTeam;
        return this;
    }

    public CreateMatchRequestBuilder withAwayTeam(UUID awayTeam) {
        this.awayTeam = awayTeam;
        return this;
    }

    public CreateMatchRequestBuilder withHomeGoals(Integer homeGoals) {
        this.homeGoals = homeGoals;
        return this;
    }

    public CreateMatchRequestBuilder withAwayGoals(Integer awayGoals) {
        this.awayGoals = awayGoals;
        return this;
    }

    public CreateMatchRequest build() {
        return CreateMatchRequest.builder()
                .tournamentId(tournamentId)
                .homeTeam(homeTeam)
                .homeGoals(homeGoals)
                .awayTeam(awayTeam)
                .awayGoals(awayGoals).build();
    }
}
