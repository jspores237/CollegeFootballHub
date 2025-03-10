package com.example.CollegeFootballHub.entity;

import com.example.CollegeFootballHub.entity.Location;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TeamDto {
    private Long id;
    private String school;
    private String mascot;
    private String abbreviation;
    private List<String> alternateNames;
    private String conference;
    private String division;
    private String classification;
    private String color;
    private String alternateColor;
    private List<String> logos;
    private String twitter;

    // Use your existing Location class
    private Location location;

    // Conversion method to Entity
    public com.example.CollegeFootballHub.entity.Team toEntity() {
        com.example.CollegeFootballHub.entity.Team team = new com.example.CollegeFootballHub.entity.Team();
        team.setId(this.id);
        team.setSchool(this.school);
        team.setMascot(this.mascot);
        team.setAbbreviation(this.abbreviation);
        team.setAlternateNames(this.alternateNames != null ? String.join(",", this.alternateNames) : null);
        team.setConference(this.conference);
        team.setDivision(this.division);
        team.setClassification(this.classification);
        team.setColor(this.color);
        team.setAlternateColor(this.alternateColor);
        team.setLogos(this.logos != null ? String.join(",", this.logos) : null);
        team.setTwitter(this.twitter);

        // Set location fields if available
        if (this.location != null) {
            team.setLocationCity(this.location.getCity());
            team.setLocationState(this.location.getState());
            team.setLocationLatitude(this.location.getLatitude());
            team.setLocationLongitude(this.location.getLongitude());
        }

        return team;
    }
}