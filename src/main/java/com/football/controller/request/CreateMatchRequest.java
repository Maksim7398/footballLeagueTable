package com.football.controller.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class CreateMatchRequest {

    @NotNull(message = "tournament id must not be null")
    private Long tournamentId;

    @NotNull(message = "home team must not be null")
    private UUID homeTeam;

    @NotNull(message = "home goals must not be null")
    private Integer homeGoals;

    @NotNull(message = "away team must not be null")
    private UUID awayTeam;

    @NotNull(message = "away goals must not be null")
    private Integer awayGoals;
}
