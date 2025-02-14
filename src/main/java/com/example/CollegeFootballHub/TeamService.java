package com.example.CollegeFootballHub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@Service
public class TeamService {

    @Value("${college-football-hub.api.key}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    public TeamRepository teamRepository;

    @Autowired
    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }



    private static final String API_URL = "https://api.sportradar.com/ncaafb/trial/v7/en/league/teams.json";

    // Method to get all teams
    public List<Team> getAllTeams() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + apiKey);  // Add the Authorization header with the API key

        HttpEntity<String> entity = new HttpEntity<>(headers);  // Wrap headers in HttpEntity

        ResponseEntity<List<Team>> response = restTemplate.exchange(  // Make the GET request
                API_URL,
                HttpMethod.GET,
                entity,  // Add HttpEntity to include headers
                new ParameterizedTypeReference<List<Team>>() {
                } // Expected response type
        );
        return response.getBody();  // Return the body containing the list of teams
    }

    // Method to get a team by ID
// Method to get a team by ID
    public Team getTeamById(Long id) {
        List<Team> teams = getAllTeams();  // Get all teams
        return teams.stream()  // Use Java streams to find the team by ID
                .filter(team -> team.getId() != null && team.getId().equals(id))  // Match the ID
                .findFirst()  // Return the first match
                .orElseThrow(() -> new RuntimeException("Team not found with ID: " + id));  // Handle case where no match is found
    }














/*
public Team createTeam(Team team) {
        return teamRepository.save(team);
    }

    public Team updateTeam(Team team, Long id) {
        Team existingTeam = teamRepository.findById(id).orElseThrow(() -> new RuntimeException("Team not found"));
        existingTeam.setName(team.getName());
        existingTeam.setMarket(team.getMarket());
        existingTeam.setAlias(team.getAlias());
        return teamRepository.save(existingTeam);
    }

    public void deleteTeamById(Long id) {
        teamRepository.deleteById(id);
    }

    public void deleteAllTeams() {
        teamRepository.deleteAll();
    } */
}
