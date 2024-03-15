package com.football.persist.repository;

import com.football.persist.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TeamRepository extends JpaRepository<Team,UUID> {
}
