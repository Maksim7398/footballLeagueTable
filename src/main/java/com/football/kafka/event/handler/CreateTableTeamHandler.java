package com.football.kafka.event.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.football.controller.event.controller.Event;
import com.football.controller.event.controller.EventSource;
import com.football.controller.event.request.CreateTableTeamEventData;
import com.football.kafka.Producer;
import com.football.kafka.event.EventHandler;
import com.football.mapper.TeamMapper;
import com.football.service.match.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "app", name = "kafka.enabled", matchIfMissing = false)
public class CreateTableTeamHandler implements EventHandler<CreateTableTeamEventData> {

    private final MatchService matchService;

    private final Producer producer;

    private final TeamMapper teamMapper;
    @Override
    public boolean canHandle(EventSource eventSource) {
        return Event.CREATE_TABLE_TEAM.equals(eventSource.getEvent());
    }

    @Override
    public void handleEvent(CreateTableTeamEventData eventSource) {
        try {
            producer.sendCreateTableTeam("1",teamMapper.convertDtoToEntityList(matchService.getTeamsTable()));
        } catch (JsonProcessingException | ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        eventSource.getEvent();
    }
}
