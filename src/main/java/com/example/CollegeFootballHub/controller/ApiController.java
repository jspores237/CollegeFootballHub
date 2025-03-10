package com.example.CollegeFootballHub.controller;

import com.example.CollegeFootballHub.entity.TeamDto;
import com.example.CollegeFootballHub.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
public class ApiController {

    private final ApiService apiService;

    @Autowired
    public ApiController(ApiService apiService) {
        this.apiService = apiService;
    }

    // GET teams from API
    @GetMapping
    public Mono<List<TeamDto>> getTeamsFromApi(@RequestHeader("Authorization") String apiKey) {
        return apiService.fetchTeamsFromApi(apiKey);  // Directly return Mono with teams
    }
}
