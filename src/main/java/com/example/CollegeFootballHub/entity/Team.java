package com.example.CollegeFootballHub.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@Table("team") // R2DBC uses @Table instead of @Entity
public class Team {

    @Id // Use Spring Data's @Id, not Jakarta's
    private Long id;

    @Version
    private Long version;

    private String school;
    private String mascot;
    private String abbreviation;

    // Complex collections like @ElementCollection aren't directly supported
    // You'd need to handle these differently, such as:
    // 1. JSON column
    // 2. Separate table with joins
    // 3. Comma-separated string
    private String alternateNames; // Would need conversion to/from List<String>

    private String conference;
    private String division;
    private String classification;
    private String color;

    @Column("alternate_color")
    private String alternateColor;

    // Another collection that needs special handling
    private String logos; // Would need conversion to/from List<String>

    private String twitter;

    // Embedded objects need to be flattened or stored as JSON
    private String locationCity;
    private String locationState;
    private Double locationLatitude;
    private Double locationLongitude;

    // Default constructor
    public Team() {
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", school='" + school + '\'' +
                ", mascot='" + mascot + '\'' +
                ", conference='" + conference + '\'' +
                '}';
    }
}