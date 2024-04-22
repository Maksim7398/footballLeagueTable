package com.football.mapper;

import com.football.controller.request.CreateTeamRequest;
import com.football.controller.response.GetTeamResponse;
import com.football.controller.response.GetResponseTeamForMatch;
import com.football.model.TeamDTO;
import com.football.persist.entity.TeamEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TeamMapper {

    GetResponseTeamForMatch convertEntityToResponseTeam(TeamEntity teamEntity);

    TeamEntity convertCreateTeamToEntity(CreateTeamRequest createTeamRequest);

    TeamDTO convertEntityToDto(TeamEntity teamEntity);

    List<TeamDTO> convertEntityToDtoList(List<TeamEntity> teamEntityList);

    GetTeamResponse convertDtoToNewTeamResponse(TeamDTO teamDTO);
}
