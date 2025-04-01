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
@JsonIgnoreProperties(ignoreUnknown = true)  // Ignore unknown JSON properties during deserialization
@Table("team")  // Maps this class to the "team" database table (R2DBC annotation)
public class Team {

    @Id  // Marks this field as the primary key
    private Long id;  // Unique identifier for the team

    @Version  // Used for optimistic locking in database operations
    private Long version;  // Version field for concurrency control

    private String school;  // Name of the school/university
    private String mascot;  // Team mascot name
    private String abbreviation;  // School abbreviation

    // String representation of a list - stored as comma-separated values
    // Example: "Alternate Name 1,Alternate Name 2"
    private String alternateNames;

    private String conference;  // Athletic conference (SEC, Big Ten, etc.)
    private String division;  // Division within conference
    private String classification;  // NCAA classification (FBS, FCS)
    private String color;  // Primary team color (hex code without #)

    @Column("alternate_color")  // Maps to database column with snake_case naming
    private String alternateColor;  // Secondary team color

    // String representation of a list of logo URLs - stored as comma-separated values
    private String logos;

    private String twitter;  // Twitter handle

    // Flattened location fields from the nested Location object in API
    private String locationCity;  // City where team is located
    private String locationState;  // State where team is located
    private Double locationLatitude;  // Stadium latitude
    private Double locationLongitude;  // Stadium longitude

    // Default constructor required by Spring Data
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