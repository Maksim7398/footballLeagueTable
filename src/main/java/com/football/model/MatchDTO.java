package com.football.model;

import com.football.persist.entity.TeamEntity;
import com.football.persist.entity.Tournament;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class MatchDTO {

    private UUID id;

    private TeamEntity homeTeamEntity;

    private TeamEntity awayTeamEntity;

    private LocalDateTime dateMatch;

    private Integer homeGoals;

    private Integer awayGoals;

    private Tournament tournament;

}
