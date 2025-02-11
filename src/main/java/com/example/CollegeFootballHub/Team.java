package com.example.CollegeFootballHub;
import jakarta.persistence.*;


@Entity
@Table(name = "teams")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public String id;
    public String name;
    public String market;
    public String alias;

    public Team() {}

    public Team(String id, String name, String market, String alias) {
        this.id = id;
        this.name = name;
        this.market = market;
        this.alias = alias;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
