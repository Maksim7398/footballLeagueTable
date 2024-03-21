package com.football.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.football.persist.entity.MatchEntity;
import com.football.persist.entity.TeamEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "app", name = "kafka.enabled", matchIfMissing = false)
public class Producer {

    private final KafkaTemplate<String, String> kafkaTemplateStringTeam;

    private final KafkaTemplate<String, String> kafkaTemplateStringMatch;

    public void sendCreateTableMatch(final String key, List<MatchEntity> matchEntityList, final String topic) throws JsonProcessingException, ExecutionException, InterruptedException {
        final ObjectMapper objectMapper = JsonMapper.builder()
                .findAndAddModules()
                .build();
        String data = objectMapper.writeValueAsString(matchEntityList);
        final CompletableFuture<SendResult<String, String>> send = kafkaTemplateStringMatch.send(topic, key, data);

        log.info("KEY: {}", key);
        log.info("TOPIC: {}", send.get().getRecordMetadata().topic());
    }

    public void sendCreateTableTeam(final String key, final String topic, List<TeamEntity> teamEntityList) throws JsonProcessingException, ExecutionException, InterruptedException {
        final ObjectMapper objectMapper = JsonMapper.builder()
                .findAndAddModules()
                .build();
        final String data = objectMapper.writeValueAsString(teamEntityList);
        final CompletableFuture<SendResult<String, String>> send = kafkaTemplateStringTeam.send(topic, key, data);

        log.info("KEY: {}", key);
        log.info("TOPIC: {}", send.get().getRecordMetadata().topic());
    }
}
