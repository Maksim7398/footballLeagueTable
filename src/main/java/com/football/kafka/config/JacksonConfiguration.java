package com.football.kafka.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Configuration
public class JacksonConfiguration {
    @Bean
    public ObjectMapper objectMapper() {
        LocalDateTimeDeserializer localDateTimeDeserializer = new
                LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("dd MMMM yyyy",new Locale("ru")));
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule().addDeserializer(LocalDateTime.class, localDateTimeDeserializer));
        return mapper;
    }
}
