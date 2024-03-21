package com.football.kafka.event;

import com.football.controller.event.controller.EventSource;

public interface EventHandler< T extends EventSource> {

    boolean canHandle(EventSource eventSource);

    void handleEvent(T eventSource);
}
