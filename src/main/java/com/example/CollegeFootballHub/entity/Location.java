package com.example.CollegeFootballHub.entity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Column;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Location {

    private String name;
    private String city;
    private String state;
    private String zip;

    @Column("country_code")
    private String countryCode;

    private String timezone;
    private Double latitude;
    private Double longitude;
    private String elevation;
    private Integer capacity;

    @Column("construction_year")
    private Integer constructionYear;

    private Boolean grass;
    private Boolean dome;

    // Default constructor
    public Location() {
    }
}