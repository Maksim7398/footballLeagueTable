package com.football.service.teams;

import com.football.controller.request.CreateTeamRequest;
import com.football.mapper.TeamMapper;
import com.football.model.TeamDTO;
import com.football.persist.entity.TeamEntity;
import com.football.persist.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;

    private final TeamMapper teamMapper;

    public TeamDTO createTeam(final CreateTeamRequest createTeamRequest) {
        final TeamEntity teamEntity = teamRepository.save(teamMapper.convertCreateTeamToEntity(createTeamRequest));
        return teamMapper.convertEntityToDto(teamEntity);
    }

    public List<TeamDTO> listTeams(){
        return teamMapper.convertEntityToDtoList(teamRepository.findAll());
    }
}
