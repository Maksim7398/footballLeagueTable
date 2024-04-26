package com.football.model;

import com.football.persist.entity.MatchEntity;
import com.football.persist.entity.TeamEntity;
import com.football.persist.entity.Tournament;

import java.time.LocalDateTime;
import java.util.UUID;

public class MatchEntityBuilder {

    public static final UUID DEFAULT_ID = UUID.randomUUID();

    public static final LocalDateTime DATE_MATCH = LocalDateTime.now();

    public static final TeamEntity HOME_TEAM = TeamEntityBuilder.aTeamEntityBuilder().build();

    public static final TeamEntity AWAY_TEAM = TeamEntityBuilder.aTeamEntityBuilder().build();

    public static final Integer HOME_GOALS = (int) (Math.random() * 10);

    public static final Integer AWAY_GOALS = (int) (Math.random() * 10);

    public static final Tournament DEFAULT_TOURNAMENT = new Tournament(1L,"Russia");

    private UUID id = DEFAULT_ID;

    private TeamEntity homeTeamEntity = HOME_TEAM;

    private TeamEntity awayTeamEntity = AWAY_TEAM;

    private LocalDateTime dateMatch = DATE_MATCH;

    private Integer homeGoals = HOME_GOALS;

    private Integer awayGoals = AWAY_GOALS;

    private Tournament tournament = DEFAULT_TOURNAMENT;

    private MatchEntityBuilder() {
    }

    public static MatchEntityBuilder aMatchEntityBuilder() {
        return new MatchEntityBuilder();
    }

    public MatchEntityBuilder withId(UUID id) {
        this.id = id;
        return this;
    }

    public MatchEntityBuilder withHomeTeam(TeamEntity homeTeam) {
        this.homeTeamEntity = homeTeam;
        return this;
    }

    public MatchEntityBuilder withAwayTeam(TeamEntity awayTeam) {
        this.awayTeamEntity = awayTeam;
        return this;
    }

    public MatchEntityBuilder withTournament(Tournament tournament) {
        this.tournament = tournament;
        return this;
    }

    public MatchEntityBuilder withDateMatch(LocalDateTime dateMatch) {
        this.dateMatch = dateMatch;
        return this;
    }

    public MatchEntityBuilder withHomeGoals(Integer homeGoals) {
        this.homeGoals = homeGoals;
        return this;
    }

    public MatchEntityBuilder withAwayGoals(Integer awayGoals) {
        this.awayGoals = awayGoals;
        return this;
    }

    public MatchEntity build() {
        return MatchEntity.builder()
                .id(id)
                .homeTeam(homeTeamEntity)
                .awayTeam(awayTeamEntity)
                .dateMatch(dateMatch)
                .homeGoals(homeGoals)
                .awayGoals(awayGoals)
                .tournament(tournament)
                .build();
    }
}
