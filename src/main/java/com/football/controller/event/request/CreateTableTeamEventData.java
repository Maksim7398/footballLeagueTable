package com.football.controller.event.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.football.controller.event.controller.Event;
import com.football.kafka.event.KafkaEvent;
import lombok.Data;

@Data
public class CreateTableTeamEventData implements KafkaEvent {

    @Override
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public Event getEvent() {
        return Event.CREATE_TABLE_TEAM;
    }
}