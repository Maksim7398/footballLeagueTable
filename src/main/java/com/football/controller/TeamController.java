package com.football.controller;

import com.football.controller.request.CreateTeamRequest;
import com.football.controller.response.GetTeamResponse;
import com.football.controller.universal_response.UniversalResponse;
import com.football.controller.universal_response.UniversalSuccessResponse;
import com.football.mapper.TeamMapper;
import com.football.service.teams.TeamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/teams")
public class TeamController {

    private final TeamService teamService;

    private final TeamMapper teamMapper;

    @PostMapping
    public UniversalResponse<GetTeamResponse> createTeam(@RequestBody @Valid CreateTeamRequest createTeamRequest) {
        return new UniversalSuccessResponse<>(HttpStatus.OK,
                teamMapper.convertDtoToNewTeamResponse(teamService.createTeam(createTeamRequest)));
    }

    @GetMapping
    public UniversalResponse<GetTeamResponse> getTeams() {
        return new UniversalSuccessResponse<>(HttpStatus.OK, GetTeamResponse.builder()
                .teamDTOList(teamService.listTeams())
                .build());
    }
}
