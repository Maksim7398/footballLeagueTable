package com.football.model;

import com.football.controller.request.CreateMatchRequest;

public class CreateMatchRequestBuilder {

    private final static String DEFAULT_HOME_TEAM = "VOLGA";

    private final static String DEFAULT_AWAY_TEAM = "ZENIT";

    private final static Integer DEFAULT_HOME_GOALS = 2;

    private final static Integer DEFAULT_AWAY_GOALS = 2;

    private String homeTeam = DEFAULT_HOME_TEAM;

    private Integer homeGoals = DEFAULT_HOME_GOALS;

    private String awayTeam = DEFAULT_AWAY_TEAM;

    private Integer awayGoals = DEFAULT_AWAY_GOALS;

    private CreateMatchRequestBuilder() {
    }

    public static CreateMatchRequestBuilder aCreateMatchRequestBuilder() {
        return new CreateMatchRequestBuilder();
    }


    public CreateMatchRequestBuilder withHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
        return this;
    }

    public CreateMatchRequestBuilder withAwayTeam(String awayTeam) {
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
                .homeTeam(homeTeam)
                .homeGoals(homeGoals)
                .awayTeam(awayTeam)
                .awayGoals(awayGoals).build();
    }
}
