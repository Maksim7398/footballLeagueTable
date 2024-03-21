package com.football.controller.event.controller;

import com.football.kafka.event.KafkaEvent;
import com.football.kafka.KafkaEventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class KafkaEventController {

    private final KafkaEventService kafkaEventService;

    @PostMapping("/teamTable")
    public void createTeamTable(@RequestBody @Valid KafkaEvent httpEvent) {
       kafkaEventService.processEvent(httpEvent);
    }

    @PostMapping("/matchesResult")
    @SneakyThrows
    public void getMatchesResult(@RequestBody @Valid KafkaEvent httpEvent) {
        kafkaEventService.processEvent(httpEvent);
    }
}
