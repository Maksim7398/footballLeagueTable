package com.football.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateMatchRequest {

    @NotBlank(message = "tournamentName must not be blank")
    private String tournamentName;

    @NotBlank(message = "home team must not be blank")
    private String homeTeam;

    @NotNull(message = "home goals must not be null")
    private Integer homeGoals;

    @NotBlank(message = "away team must not be blank")
    private String awayTeam;

    @NotNull(message = "away goals must not be null")
    private Integer awayGoals;
}
