package com.football.controller;

import com.football.controller.response.TeamTable;
import com.football.controller.response.UniversalResponse;
import com.football.service.standings.StandingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequiredArgsConstructor
public class StandingsController {

    private final StandingsService standingsService;

    @Value("${format.date}")
    private String dateFormat;

    @GetMapping(value = "/standings/{tournamentName}")
    public UniversalResponse getStandings(@RequestHeader @Nullable String localDateTime,
                                          @PathVariable String tournamentName) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
        if (localDateTime == null) {
            localDateTime = LocalDateTime.now().format(formatter);
        }

        return UniversalResponse.builder()
                .status(HttpStatus.OK)
                .errorDetails("")
                .payload(TeamTable.builder()
                        .calculateDate(localDateTime)
                        .tournamentName(tournamentName)
                        .teamTable(standingsService
                                .createResultTeamTable(tournamentName,LocalDateTime.parse(localDateTime, formatter)))
                        .build()
                ).build();
    }
}
