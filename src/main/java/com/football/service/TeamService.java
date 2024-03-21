package com.football.service;

import com.football.controller.request.CreateTeamRequest;
import com.football.mapper.TeamMapper;
import com.football.persist.entity.TeamEntity;
import com.football.model.TeamDTO;
import com.football.persist.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;

    private final TeamMapper teamMapper;

    public UUID createTeam(final CreateTeamRequest createTeamRequest){
        final TeamDTO teamDTO = teamMapper.convertCreateTeamFromDto(createTeamRequest);
        teamDTO.setOtherPoints(0);
        teamDTO.setPoints(0);
        final TeamEntity save = teamRepository.save(teamMapper.converDtoFromTeam(teamDTO));

        return save.getId();
    }
}
