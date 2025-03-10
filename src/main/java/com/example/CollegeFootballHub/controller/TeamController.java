package com.example.CollegeFootballHub.controller;

import com.example.CollegeFootballHub.entity.Team;
import com.example.CollegeFootballHub.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import java.util.List;

@RestController
@RequestMapping("/teams")
public class TeamController {

    private final TeamService teamService;

    @Value("${college-football-hub.api.key}")
    private String configuredApiKey;

    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    // Simple test endpoint
    @GetMapping("/test")
    public Mono<String> test() {
        return Mono.just("TeamController is working!");
    }

    // GET all teams (either from DB or API)
    @GetMapping
    public Mono<List<?>> getAllTeams(@RequestHeader(value = "Authorization", required = false) String apiKey) {
        // Use the configured API key from application.properties if no header provided
        if (apiKey == null) {
            apiKey = configuredApiKey;
        }
        return teamService.getAllTeams(apiKey);
    }

    // GET team by ID
    @GetMapping("/{id}")
    public Mono<Team> getTeamById(@PathVariable Long id) {
        return teamService.getTeamById(id, configuredApiKey);
    }

    // GET teams by conference
    @GetMapping("/conference/{conference}")
    public Mono<List<Team>> getTeamsByConference(@PathVariable String conference) {
        return teamService.getTeamsByConference(conference, configuredApiKey);
    }
}