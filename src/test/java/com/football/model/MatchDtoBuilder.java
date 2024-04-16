package com.football.model;

import com.football.persist.entity.TeamEntity;

import java.time.LocalDateTime;
import java.util.UUID;

public class MatchDtoBuilder {

    public static final UUID DEFAULT_ID = UUID.randomUUID();

    public static final LocalDateTime DATE_MATCH = LocalDateTime.now();

    public static final TeamEntity HOME_TEAM = TeamEntityBuilder.aTeamEntityBuilder().build();

    public static final TeamEntity AWAY_TEAM = TeamEntityBuilder.aTeamEntityBuilder().build();

    public static final Integer HOME_GOALS = (int) (Math.random() * 10);

    public static final Integer AWAY_GOALS = (int) (Math.random() * 10);

    private UUID id = DEFAULT_ID;

    private TeamEntity homeTeamEntity = HOME_TEAM;

    private TeamEntity awayTeamEntity = AWAY_TEAM;

    private LocalDateTime dateMatch = DATE_MATCH;

    private Integer homeGoals = HOME_GOALS;

    private Integer awayGoals = AWAY_GOALS;

    private MatchDtoBuilder() {
    }

    public static MatchDtoBuilder aMatchDtoBuilder() {
        return new MatchDtoBuilder();
    }

    public MatchDtoBuilder withId(UUID id) {
        this.id = id;
        return this;
    }

    public MatchDtoBuilder withHomeTeam(TeamEntity homeTeam) {
        this.homeTeamEntity = homeTeam;
        return this;
    }

    public MatchDtoBuilder withAwayTeam(TeamEntity awayTeam) {
        this.awayTeamEntity = awayTeam;
        return this;
    }

    public MatchDtoBuilder withDateMatch(LocalDateTime dateMatch) {
        this.dateMatch = dateMatch;
        return this;
    }

    public MatchDtoBuilder withHomeGoals(Integer homeGoals) {
        this.homeGoals = homeGoals;
        return this;
    }

    public MatchDtoBuilder withAwayGoals(Integer awayGoals) {
        this.awayGoals = awayGoals;
        return this;
    }

    public MatchDTO build() {
        return MatchDTO.builder()
                .id(id)
                .homeTeamEntity(homeTeamEntity)
                .awayTeamEntity(awayTeamEntity)
                .dateMatch(dateMatch)
                .homeGoals(homeGoals)
                .awayGoals(awayGoals)
                .build();
    }
}
