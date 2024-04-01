package com.football.controller;

import com.football.controller.request.CreateTeamRequest;
import com.football.controller.response.GetResponseTeam;
import com.football.mapper.TeamMapper;
import com.football.service.teams.TeamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/team")
public class TeamController {

    private final TeamService teamService;

    private final TeamMapper teamMapper;

    @PostMapping
    public UUID createTeam(@RequestBody @Valid CreateTeamRequest createTeamRequest) {
        return teamService.createTeam(createTeamRequest);
    }

    @GetMapping
    public List<GetResponseTeam> getTeamsTable(@RequestHeader @Nullable String localDateTime) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        if (localDateTime == null) {
            localDateTime = LocalDateTime.now().format(formatter);
        }

        return teamMapper
                .convertDtoToResponseList(
                        teamService.createResultTeamTable(LocalDateTime.parse(localDateTime, formatter))
                );
    }
}
