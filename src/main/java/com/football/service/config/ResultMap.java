package com.football.service.config;

import com.football.persist.entity.Team;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Configuration
public class ResultMap {

    @Bean
    Map<String, Map<LocalDateTime, List<Team>>> result(Map<String, Map<LocalDateTime, List<Team>>> result) {
        return new HashMap<>(result);
    }
}
