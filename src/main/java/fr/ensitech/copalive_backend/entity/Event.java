package fr.ensitech.copalive_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "events")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // Stratégie : Une seule table pour tous les types
@DiscriminatorColumn(name = "event_type", discriminatorType = DiscriminatorType.STRING) // La colonne qui dit "C'est un BUT" ou "C'est un CARTON"
@Data
public abstract class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "occurred_at", nullable = false)
    private String occurredAt;

    @ManyToOne
    @JoinColumn(name = "match_id")
    private Match match;

}
