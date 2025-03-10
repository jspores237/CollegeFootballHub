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

@Controller
@RequestMapping("/web/teams")
public class WebController {

    private final TeamService teamService;

    @Value("${college-football-hub.api.key}")
    private String apiKey;

    @Autowired
    public WebController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping("/web")
    public String home() {
        return "redirect:/web/teams";
    }

    @GetMapping
    public Mono<String> getAllTeams(Model model) {
        return teamService.getAllTeams(apiKey)
                .doOnNext(teams -> model.addAttribute("teams", teams))
                .thenReturn("teams");
    }


    @GetMapping("/{id}")
    public Mono<String> getTeamById(@PathVariable Long id, Model model) {
        return teamService.getTeamById(id, apiKey)
                .doOnNext(team -> model.addAttribute("team", team))
                .thenReturn("team-details");
    }

    @GetMapping("/conference/{conference}")
    public Mono<String> getTeamsByConference(@PathVariable String conference, Model model) {
        return teamService.getTeamsByConference(conference, apiKey)
                .doOnNext(teams -> model.addAttribute("teams", teams))
                .thenReturn("teams");
    }

    @GetMapping("/refresh")
    public Mono<String> refreshTeams() {
        // Implement refresh logic if needed
        return Mono.just("redirect:/web/teams");
    }
}