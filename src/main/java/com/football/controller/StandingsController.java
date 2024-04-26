package com.football.controller;

import com.football.controller.date_format.DateFormat;
import com.football.controller.response.TeamTable;
import com.football.controller.universal_response.UniversalResponse;
import com.football.controller.universal_response.UniversalSuccessResponse;
import com.football.service.standings.StandingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequiredArgsConstructor
public class StandingsController {

    private final StandingsService standingsService;

    private final DateFormat dateFormat;

    @GetMapping(value = "/standings/{tournamentID}")
    public UniversalResponse<TeamTable> getStandings(@RequestParam("dateMatch") @Nullable String localDateTime,
                                                     @PathVariable Long tournamentID) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat.getFormat());
        if (localDateTime == null) {
            localDateTime = LocalDateTime.now().format(formatter);
        }

        final TeamTable resultTeamTable = standingsService
                .createResultTeamTable(tournamentID,
                        LocalDateTime.parse(localDateTime, formatter));

        return new UniversalSuccessResponse<>(HttpStatus.OK, resultTeamTable);
    }
}
