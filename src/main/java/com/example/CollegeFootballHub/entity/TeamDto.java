package com.example.CollegeFootballHub.entity;
import com.example.CollegeFootballHub.entity.Location;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)  // Ignores any JSON properties not mapped to fields in this class
public class TeamDto {
    private Long id;  // Team identifier
    private String school;  // School/university name
    private String mascot;  // Team mascot
    private String abbreviation;  // School abbreviation
    private List<String> alternateNames;  // List of alternative names for the school
    private String conference;  // Athletic conference (e.g., SEC, Big Ten)
    private String division;  // Division within the conference
    private String classification;  // NCAA classification (e.g., FBS, FCS)
    private String color;  // Primary team color
    private String alternateColor;  // Secondary team color
    private List<String> logos;  // URLs to team logo images
    private String twitter;  // Team's Twitter handle

    // Nested location object containing geographic and venue information
    private Location location;

    /**
     * Converts this DTO (Data Transfer Object) to a database entity
     * This method transforms the DTO's structure (which matches the API) to the
     * database entity structure (which is flattened for storage)
     */
    public com.example.CollegeFootballHub.entity.Team toEntity() {
        com.example.CollegeFootballHub.entity.Team team = new com.example.CollegeFootballHub.entity.Team();
        team.setId(this.id);
        team.setSchool(this.school);
        team.setMascot(this.mascot);
        team.setAbbreviation(this.abbreviation);

        // Convert List<String> to comma-separated String for database storage
        team.setAlternateNames(this.alternateNames != null ? String.join(",", this.alternateNames) : null);

        team.setConference(this.conference);
        team.setDivision(this.division);
        team.setClassification(this.classification);
        team.setColor(this.color);
        team.setAlternateColor(this.alternateColor);

        // Convert List<String> to comma-separated String for database storage
        team.setLogos(this.logos != null ? String.join(",", this.logos) : null);

        team.setTwitter(this.twitter);

        // Flatten the nested location object into separate fields on the entity
        if (this.location != null) {
            team.setLocationCity(this.location.getCity());
            team.setLocationState(this.location.getState());
            team.setLocationLatitude(this.location.getLatitude());
            team.setLocationLongitude(this.location.getLongitude());
        }

        return team;
    }
}