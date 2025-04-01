package com.example.CollegeFootballHub.controller;
import com.example.CollegeFootballHub.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.spring6.context.webflux.ReactiveDataDriverContextVariable;
import reactor.core.publisher.Mono;
import java.util.List;

@Controller  // Standard MVC controller that returns view names (not @RestController)
@RequestMapping("/teams")  // Base path for all web UI team-related pages
public class TeamViewController {

    private final TeamService teamService;  // Service for team operations

    @Value("${college-football-hub.api.key}")  // Injects API key from properties
    private String apiKey;

    @Autowired
    public TeamViewController(TeamService teamService) {
        this.teamService = teamService;  // Constructor injection
    }

    @GetMapping  // Maps to /teams
    public Mono<String> getAllTeams(Model model) {
        return teamService.getAllTeams(apiKey)
                .doOnNext(teams -> model.addAttribute("teams", teams))  // Adds teams to the Thymeleaf model
                .thenReturn("teams");  // Returns the view name to render (teams.html)
    }

    @GetMapping("/{id}")  // Maps to /teams/{id}
    public Mono<String> getTeamById(@PathVariable Long id, Model model) {
        return teamService.getTeamById(id, apiKey)
                .doOnNext(team -> model.addAttribute("team", team))  // Adds single team to model
                .thenReturn("team-details");  // Returns the team-details.html view
    }

    @GetMapping("/conference/{conference}")  // Maps to /teams/conference/{conference}
    public Mono<String> getTeamsByConference(@PathVariable String conference, Model model) {
        return teamService.getTeamsByConference(conference, apiKey)
                .doOnNext(teams -> model.addAttribute("teams", teams))  // Adds filtered teams to model
                .thenReturn("teams");  // Uses the same teams.html template
    }

    @GetMapping("/refresh")  // Maps to /teams/refresh
    public Mono<String> refreshTeams() {
        // Implement refresh logic if needed
        return Mono.just("redirect:/teams");  // Redirects back to the teams list page
    }
}