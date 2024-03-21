package com.football.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class MatchDTO {

    private UUID id;

    private TeamDTO homeTeamEntity;

    private TeamDTO awayTeamEntity;

    private LocalDateTime dateMatch;

    private Integer homeGoals;

    private Integer awayGoals;
}
