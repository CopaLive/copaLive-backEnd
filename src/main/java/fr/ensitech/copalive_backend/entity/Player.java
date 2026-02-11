package fr.ensitech.copalive_backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "players")
@Data
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "player_position")
    private String position;

    @Column(name = "shirt_number")
    private Integer shirtNumber;

    @ManyToOne
    @JoinColumn(name = "current_team_id")
    private Team currentTeam;
}