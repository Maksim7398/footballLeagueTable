package com.football.model;

import com.football.persist.entity.TeamEntity;

import java.util.UUID;

public class TeamEntityBuilder {

    public static final UUID DEFAULT_ID = UUID.randomUUID();

    public static final String DEFAULT_NAME = "Volga";

    private UUID id = DEFAULT_ID;

    private String name = DEFAULT_NAME;



    private TeamEntityBuilder() {

    }

    public static TeamEntityBuilder aTeamEntityBuilder() {
        return new TeamEntityBuilder();
    }

    public TeamEntityBuilder withId(UUID id){
        this.id = id;
        return this;
    }

    public TeamEntityBuilder withName(String name) {
        this.name = name;
        return this;
    }


    public TeamEntity build() {
        return TeamEntity.builder()
                .id(id)
                .name(name)
                .build();
    }
}
