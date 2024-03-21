package com.football.kafka.event;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.football.controller.event.controller.EventSource;
import com.football.controller.event.request.CreateTableLeagueEventData;
import com.football.controller.event.request.CreateTableTeamEventData;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "event"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = CreateTableTeamEventData.class, name = "CREATE_TABLE_TEAM"),
        @JsonSubTypes.Type(value = CreateTableLeagueEventData.class, name = "CREATE_TABLE_LEAGUE"),
})
public interface KafkaEvent extends EventSource {
}
