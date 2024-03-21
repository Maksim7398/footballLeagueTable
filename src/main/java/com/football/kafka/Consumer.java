package com.football.kafka;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.football.persist.entity.MatchEntity;
import com.football.persist.entity.TeamEntity;
import com.football.service.CreateTableLeague;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "app", name = "kafka.enabled", matchIfMissing = false)
public class Consumer {

    private final CreateTableLeague createTableLeague;

    @KafkaListener(topics = "match_topic", containerFactory = "kafkaListenerContainerFactoryString")
    public void createTableMatch(String message) {
        final ObjectMapper objectMapper = JsonMapper.builder()
                .findAndAddModules()
                .build();
        try {
            final List<MatchEntity> matchEntities =
                    objectMapper.readValue(message, new TypeReference<List<MatchEntity>>(){});

            createTableLeague.createTableMatch(matchEntities);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @KafkaListener(topics = "team_topic", containerFactory = "kafkaListenerContainerFactoryString")
    public void setCreateTableLeague(String message) {
        final ObjectMapper objectMapper = JsonMapper.builder()
                .findAndAddModules()
                .build();
        try {
            final List<TeamEntity> teamEntities =
                    objectMapper.readValue(message, new TypeReference<List<TeamEntity>>(){});

            createTableLeague.createTableLeague(teamEntities);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
