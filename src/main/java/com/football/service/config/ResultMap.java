package com.football.service.config;

import com.football.persist.entity.Match;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Configuration
public class ResultMap {

    @Bean
    List<Match> result( List<Match> result) {
        return new ArrayList<>(result);
    }
}
