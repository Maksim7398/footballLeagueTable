package com.football.mapper;

import com.football.controller.request.CreateTeamRequest;
import com.football.persist.entity.Team;
import com.football.persist.model.TeamDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TeamMapper {
    Team converDtoFromTeam(TeamDTO teamDTO);

    TeamDTO convertCreateTeamFromDto(CreateTeamRequest createTeamRequest);

}
