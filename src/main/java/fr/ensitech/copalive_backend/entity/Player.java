package fr.ensitech.copalive_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "players")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(name = "player_position")
    private String position;

    @Column(name = "shirt_number")
    private Integer shirtNumber;

    @ManyToOne
    @JoinColumn(name = "current_team_id")
    private Team currentTeam;
}
