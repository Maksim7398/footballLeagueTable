package com.football.kafka;

import com.football.controller.event.controller.EventSource;
import com.football.kafka.event.EventHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class KafkaEventService {

    private final Set<EventHandler<EventSource>> eventHandlers;

    public void processEvent(final EventSource eventSource) {
        eventHandlers.stream()
                .filter(eventSourceEventHandler -> eventSourceEventHandler.canHandle(eventSource))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Handler for eventsource not found"))
                .handleEvent(eventSource);
    }
}
