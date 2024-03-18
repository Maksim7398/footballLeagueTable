package com.football.service;

import com.football.controller.request.CreateTeamRequest;
import com.football.mapper.TeamMapper;
import com.football.persist.entity.Team;
import com.football.persist.model.TeamDTO;
import com.football.persist.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;

    private final TeamMapper teamMapper;
    @Transactional
    public UUID createTeam(final CreateTeamRequest createTeamRequest){
        TeamDTO teamDTO = teamMapper.convertCreateTeamFromDto(createTeamRequest);
        Team save = teamRepository.save(teamMapper.converDtoFromTeam(teamDTO));
        return save.getId();
    }


}
