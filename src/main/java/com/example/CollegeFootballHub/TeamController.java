package com.example.CollegeFootballHub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/teams") // This can help you define the base path for all team-related endpoints
public class TeamController {

    @Autowired //seems to go above all instantiations of either Service or Controller class in their opposite Service or Controller class
    private TeamService teamService;




    @GetMapping
    public ResponseEntity<List<Team>> getAllTeams() {
        List<Team> teams = teamService.getAllTeams();
        return ResponseEntity.ok(teams);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Team> getTeamById(@PathVariable Long id) {
        Team team = teamService.getTeamById(id);
        return ResponseEntity.ok(team);
    }

    /*
    @PostMapping
    public ResponseEntity<Team> createTeam(@RequestBody Team team) {
        Team newTeam = teamService.createTeam(team);
        return ResponseEntity.ok(newTeam);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Team> updateTeam(@RequestBody Team team, @PathVariable Long id) {
        Team existingTeam = teamService.updateTeam(team, id);
        return ResponseEntity.ok(existingTeam);
    }

    @DeleteMapping("/{id}")
    public void deleteTeamById(@PathVariable Long id) {
        teamService.deleteTeamById(id);
    }

    @DeleteMapping("/all")
    public void deleteAllTeams() {
        teamService.deleteAllTeams();
    } */
}
