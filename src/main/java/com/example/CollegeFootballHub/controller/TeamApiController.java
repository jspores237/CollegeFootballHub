package com.example.CollegeFootballHub.controller;
import com.example.CollegeFootballHub.entity.Team;
import com.example.CollegeFootballHub.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import java.util.List;


@RestController  // Marks this as a controller that returns data directly (not views)
@RequestMapping("/data/teams")  // Maps all methods to the /teams base path
public class TeamApiController {

    private final TeamService teamService;  // Service for team-related operations

    @Value("${college-football-hub.api.key}")  // Injects the API key from application properties
    private String configuredApiKey;  // Fallback API key when not provided in request

    @Autowired
    public TeamApiController(TeamService teamService) {
        this.teamService = teamService;  // Constructor injection of the team service
    }

    // GET all teams (either from DB or API)
    @GetMapping("/allTeams")  // Maps to /teams
    public Mono<List<?>> getAllTeams(@RequestHeader(value = "Authorization", required = false) String apiKey) {
        // Makes the Authorization header optional with the required=false parameter

        // Use the configured API key from application.properties if no header provided
        if (apiKey == null) {
            apiKey = configuredApiKey;  // Fallback to the application's configured key
        }
        return teamService.getAllTeams(apiKey);  // Delegates to the service layer
    }

    // GET team by ID
    @GetMapping("/{id}")  // Maps to /teams/{id} with a path variable
    public Mono<Team> getTeamById(@PathVariable Long id) {
        // @PathVariable extracts the {id} segment from the URL path
        return teamService.getTeamById(id, configuredApiKey);  // Uses the configured API key
    }

    // GET teams by conference
    @GetMapping("/conference/{conference}")  // Maps to /teams/conference/{conference}
    public Mono<List<Team>> getTeamsByConference(@PathVariable String conference) {
        // @PathVariable extracts the {conference} segment from the URL path
        return teamService.getTeamsByConference(conference, configuredApiKey);  // Groups teams by conference
    }
}