package com.football.kafka.event.handler;

import com.football.controller.event.controller.Event;
import com.football.controller.event.controller.EventSource;
import com.football.controller.event.request.CreateTableLeagueEventData;
import com.football.kafka.Producer;
import com.football.kafka.event.EventHandler;
import com.football.mapper.MatchMapper;
import com.football.persist.entity.MatchEntity;
import com.football.service.match.MatchService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "app", name = "kafka.enabled", matchIfMissing = false)
public class CreateTableLeagueHandler implements EventHandler<CreateTableLeagueEventData> {

    private final MatchService matchService;

    private final MatchMapper matchMapper;

    private final Producer producer;

    @Override
    public boolean canHandle(EventSource eventSource) {
        return Event.CREATE_TABLE_LEAGUE.equals(eventSource.getEvent());
    }

    @SneakyThrows
    @Override
    public void handleEvent(CreateTableLeagueEventData eventSource) {
        final List<MatchEntity> matchEntities = matchMapper.convertDtoToEntityList(
                matchService.getMatchesResult(
                        LocalDateTime.now()));
        producer.sendCreateTableMatch("key", matchEntities);
    }
}
