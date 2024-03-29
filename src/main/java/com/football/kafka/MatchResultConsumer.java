package com.football.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.football.controller.request.CreateMatchRequest;
import com.football.service.match.FootballServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "app", name = "kafka.enabled", matchIfMissing = false)
public class MatchResultConsumer {

    private final FootballServiceImpl footballService;

    @KafkaListener(topics = "match_result_topic", containerFactory = "kafkaListenerContainerFactoryStringForMatchResult")
    public void createMatchResult(String message) {
        final ObjectMapper objectMapper = JsonMapper.builder()
                .findAndAddModules()
                .build();
        try {
            final CreateMatchRequest teamEntities =
                    objectMapper.readValue(message, CreateMatchRequest.class);
            footballService.createGame(teamEntities.getHomeTeam(), teamEntities.getAwayTeam(),
                    teamEntities.getHomeGoals(), teamEntities.getAwayGoals());
            log.info("MESSAGE READING: " + teamEntities);
        } catch (IOException e) {
            log.error("CONSUMER TEAM NOT READING MESSAGE: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
