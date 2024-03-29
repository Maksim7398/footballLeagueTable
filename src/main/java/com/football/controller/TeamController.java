package com.football.controller;

import com.football.controller.request.CreateTeamRequest;
import com.football.service.TeamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @PostMapping("/team")
    public UUID createTeam(@RequestBody @Valid CreateTeamRequest createTeamRequest){
        return teamService.createTeam(createTeamRequest);
    }
}
