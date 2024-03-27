package com.football.persist.repository;

import com.football.persist.entity.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TeamRepository extends JpaRepository<TeamEntity, UUID> {

    Optional<TeamEntity> findTeamEntityByName(String name);
}
