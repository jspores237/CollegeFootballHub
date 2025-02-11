package com.example.CollegeFootballHub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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

    private static final String API_URL = "https://api.sportradar.com/ncaafb/trial/v7/en/league/teams.json";



//My test dummy get methods for the api wiring
    public List<Team> getAllTeams() {
        String url = API_URL + "?api_key=" + apiKey; // Construct the full API URL by appending the API key as a query parameter
        ResponseEntity<List<Team>> response = restTemplate.exchange(  // Make an HTTP GET request to the constructed URL and expect a response containing a list of Team objects
                url, // The URL to send the request to
                HttpMethod.GET, // The HTTP method to use (GET in this case)
                null, // The request entity (null because no request body or headers are needed for a GET request)
                new ParameterizedTypeReference<List<Team>>() {} // The expected response type, specified using a ParameterizedTypeReference to handle generic types
        );
            return response.getBody(); // Return the body of the response, which contains the list of Team objects
    }


    public Team getTeamById(Long id) {
        String url = API_URL + "/" + id + "?api_key=" + apiKey;
        ResponseEntity<Team> response = restTemplate.exchange(
          url,
          HttpMethod.GET,
          null,
          Team.class
          //new ParameterizedTypeReference<Team>() {}
        );

        return response.getBody();
}












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
    }
}
