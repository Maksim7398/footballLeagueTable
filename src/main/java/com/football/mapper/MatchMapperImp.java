package com.football.mapper;

import com.football.controller.response.GetResponseMatch;
import com.football.controller.response.GetResponseTeam;
import com.football.persist.entity.MatchEntity;
import com.football.persist.entity.TeamEntity;
import com.football.model.MatchDTO;
import com.football.model.TeamDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
@Component
@RequiredArgsConstructor
public class MatchMapperImp implements MatchMapper {

    private final TeamMapper teamMapper;

    public GetResponseTeam convertDtoToResponseTeam(TeamDTO teamDTO) {
        if (teamDTO == null) {
            return null;
        } else {
            GetResponseTeam.GetResponseTeamBuilder getResponseTeam = GetResponseTeam.builder();
            getResponseTeam.name(teamDTO.getName());
            getResponseTeam.scipGoals(teamDTO.getScipGoals());
            getResponseTeam.totalGoals(teamDTO.getTotalGoals());
            getResponseTeam.points(teamDTO.getPoints());
            return getResponseTeam.build();
        }
    }

    public List<GetResponseMatch> convertMatchDtoToResponse(List<MatchDTO> matchDTO) {
        if (matchDTO == null) {
            return null;
        } else {
            List<GetResponseMatch> list = new ArrayList(matchDTO.size());
            Iterator var3 = matchDTO.iterator();

            while(var3.hasNext()) {
                MatchDTO matchDTO1 = (MatchDTO)var3.next();
                list.add(this.matchDTOToGetResponseMatch(matchDTO1));
            }

            return list;
        }
    }

    public List<MatchDTO> convertMatchEntityToDto(List<MatchEntity> matchEntity) {
        if (matchEntity == null) {
            return null;
        } else {
            List<MatchDTO> list = new ArrayList(matchEntity.size());
            Iterator var3 = matchEntity.iterator();

            while(var3.hasNext()) {
                MatchEntity matchEntity1 = (MatchEntity)var3.next();
                list.add(this.matchEntityToMatchDTO(matchEntity1));
            }

            return list;
        }
    }

    protected GetResponseMatch matchDTOToGetResponseMatch(MatchDTO matchDTO) {
        if (matchDTO == null) {
            return null;
        } else {
            GetResponseMatch.GetResponseMatchBuilder getResponseMatch = GetResponseMatch.builder();
            getResponseMatch.homeTeam(teamMapper.convertDtoToResponse(matchDTO.getHomeTeamEntity()));
            getResponseMatch.awayTeam(teamMapper.convertDtoToResponse(matchDTO.getAwayTeamEntity()));
            getResponseMatch.dateMatch(matchDTO.getDateMatch());
            getResponseMatch.homeGoals(matchDTO.getHomeGoals());
            getResponseMatch.awayGoals(matchDTO.getAwayGoals());
            return getResponseMatch.build();
        }
    }

    protected TeamDTO teamEntityToTeamDTO(TeamEntity teamEntity) {
        if (teamEntity == null) {
            return null;
        } else {
            TeamDTO.TeamDTOBuilder teamDTO = TeamDTO.builder();
            teamDTO.id(teamEntity.getId());
            teamDTO.name(teamEntity.getName());
            teamDTO.numberOfGames(teamEntity.getNumberOfGames());
            teamDTO.points(teamEntity.getPoints());
            teamDTO.otherPoints(teamEntity.getOtherPoints());
            if (teamEntity.getTotalGoals() != null) {
                teamDTO.totalGoals(teamEntity.getTotalGoals());
            }

            teamDTO.scipGoals(teamEntity.getScipGoals());
            return teamDTO.build();
        }
    }

    protected MatchDTO matchEntityToMatchDTO(MatchEntity matchEntity) {
        if (matchEntity == null) {
            return null;
        } else {
            MatchDTO.MatchDTOBuilder matchDTO = MatchDTO.builder();
            matchDTO.id(matchEntity.getId());
            matchDTO.homeTeamEntity(this.teamEntityToTeamDTO(matchEntity.getHomeTeamEntity()));
            matchDTO.awayTeamEntity(this.teamEntityToTeamDTO(matchEntity.getAwayTeamEntity()));
            matchDTO.dateMatch(matchEntity.getDateMatch());
            matchDTO.homeGoals(matchEntity.getHomeGoals());
            matchDTO.awayGoals(matchEntity.getAwayGoals());
            return matchDTO.build();
        }
    }
}
