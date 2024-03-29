package com.football.kafka.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "app", name = "kafka.enabled", matchIfMissing = false)
public class TestProducer {

    private final KafkaTemplate<String, String> kafkaTemplateStringMatchResult;

    @Value("${spring.kafka.topic3}")
    private String MATCH_RESULT_TOPIC;

    public void sendCreateMatchResult(final String key, Object createMatchRequest) throws JsonProcessingException, ExecutionException, InterruptedException {
        final ObjectMapper objectMapper = JsonMapper.builder()
                .findAndAddModules()
                .build();
        final String data = objectMapper.writeValueAsString(createMatchRequest);
        final CompletableFuture<SendResult<String, String>> send =
                kafkaTemplateStringMatchResult.send(MATCH_RESULT_TOPIC, key, data);

        log.info("KEY: {}", key);
        log.info("TOPIC: {}", send.get().getRecordMetadata().topic());
    }
}
