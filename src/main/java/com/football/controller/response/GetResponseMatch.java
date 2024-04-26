package com.football.controller.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.football.persist.entity.Tournament;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class GetResponseMatch {

    @JsonFormat(locale = "ru", pattern = "dd MMMM yyyy")
    private LocalDate dateMatch;

    private GetResponseTeamForMatch homeTeam;

    private GetResponseTeamForMatch awayTeam;

    private Integer homeGoals;

    private Integer awayGoals;

    private Tournament tournament;
}
