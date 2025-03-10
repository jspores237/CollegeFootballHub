package com.example.CollegeFootballHub.repository;
import com.example.CollegeFootballHub.entity.Team;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends ReactiveCrudRepository<Team, Long> {
}