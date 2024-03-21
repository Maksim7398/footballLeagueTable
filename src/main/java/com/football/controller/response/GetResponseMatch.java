package com.football.controller.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Builder
public class GetResponseMatch {
    @JsonFormat(locale = "ru", pattern = "dd MMMM yyyy")
    private LocalDateTime dateMatch;

    private GetResponseTeam homeTeam;

    private GetResponseTeam awayTeam;

    private Integer homeGoals;

    private Integer awayGoals;
}
