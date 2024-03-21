package com.football.mapper;

import com.football.controller.request.CreateTeamRequest;
import com.football.controller.response.GetResponseTeam;
import com.football.persist.entity.TeamEntity;
import com.football.model.TeamDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TeamMapper {
    GetResponseTeam convertDtoToResponse(TeamDTO teamDTO);

    GetResponseTeam convertEntityToResponseTeam(TeamEntity teamEntity);

    List<GetResponseTeam> convertDtoToResponseList(List<TeamDTO> teamDTOList);

    TeamEntity converDtoFromTeam(TeamDTO teamDTO);

    TeamDTO convertEntityToDto(TeamEntity teamEntity);

    List<TeamDTO>  convertEntityToDtoList(List<TeamEntity> teamEntityList);

    List<TeamEntity>  convertDtoToEntityList(List<TeamDTO> teamEntityList);

    TeamDTO convertCreateTeamFromDto(CreateTeamRequest createTeamRequest);
}
