package com.football.kafka;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.football.controller.response.GetResponseMatch;
import com.football.controller.response.GetResponseTeam;
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

    @KafkaListener(topics = "match_topic", containerFactory = "kafkaListenerContainerFactoryByteArrayForMatch")
    public void createTableMatch(byte[] message) {
        final ObjectMapper objectMapper = JsonMapper.builder()
                .findAndAddModules()
                .build();
        try {
            final List<GetResponseMatch> matchEntities =
                    objectMapper.readValue(message, new TypeReference<List<GetResponseMatch>>() {
                    });

            createTableLeague.createTableMatch(matchEntities);

            log.info("MESSAGE READING: " + matchEntities);
        } catch (IOException e) {
            log.error("CONSUMER MATCH NOT READING MESSAGE: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @KafkaListener(topics = "team_topic", containerFactory = "kafkaListenerContainerFactoryStringForTeam")
    public void setCreateTableLeague(String message) {
        final ObjectMapper objectMapper = JsonMapper.builder()
                .findAndAddModules()
                .build();
        try {
            final List<GetResponseTeam> teamEntities =
                    objectMapper.readValue(message, new TypeReference<List<GetResponseTeam>>() {
                    });

            createTableLeague.createTableLeague(teamEntities);

            log.info("MESSAGE READING: " + teamEntities);
        } catch (IOException e) {
            log.error("CONSUMER TEAM NOT READING MESSAGE: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
