package com.football.kafka;

import com.football.controller.request.CreateMatchRequest;
import com.football.kafka.producer.TestProducer;
import com.football.model.CreateMatchRequestBuilder;
import com.football.model.TeamEntityBuilder;
import com.football.persist.entity.TeamEntity;
import com.football.persist.repository.MatchRepository;
import com.football.persist.repository.TeamRepository;
import lombok.SneakyThrows;
import org.assertj.core.api.AssertionsForInterfaceTypes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Testcontainers
@ActiveProfiles("kafka")
public class KafkaMessageListenerTest {

    @Autowired
    private TestProducer testProducer;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private TeamRepository teamRepositoryMock;

    @Container
    final static KafkaContainer kafkaContainer =
            new KafkaContainer(
                    DockerImageName.parse("confluentinc/cp-kafka:6.1.1")
            );

    static {
        kafkaContainer.withEmbeddedZookeeper();
        kafkaContainer.addEnv("KAFKA_CREATE_TOPICS", "team_topic");
    }

    @DynamicPropertySource
    static void registryKafkaProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrapAddress", kafkaContainer::getBootstrapServers);
    }

    @SneakyThrows
    @Test
    public void whenSendKafkaMessage_thenHandleMessageByListener() {
        final TeamEntity team1 = TeamEntityBuilder.aTeamEntityBuilder().build();
        final TeamEntity team2 = TeamEntityBuilder.aTeamEntityBuilder()
                .withName("Spartak")
                .build();

        final CreateMatchRequest expected = CreateMatchRequestBuilder.aCreateMatchRequestBuilder()
                .withHomeTeam(team1.getName())
                .withAwayTeam(team2.getName())
                .build();

        teamRepositoryMock.saveAll(List.of(team1, team2));

        testProducer.sendCreateMatchResult("1", expected);

        await()
                .atMost(10, TimeUnit.SECONDS)
                .untilAsserted(() -> {
                            AssertionsForInterfaceTypes.assertThat(matchRepository.findAll())
                                    .anySatisfy(m -> {
                                        assertEquals(m.getHomeTeam().getName(), expected.getHomeTeam());
                                        assertEquals(m.getAwayTeam().getName(), expected.getAwayTeam());
                                        assertEquals(m.getAwayGoals(), expected.getAwayGoals());
                                        assertEquals(m.getHomeGoals(), expected.getHomeGoals());
                                    });
                        }

                );
    }
}