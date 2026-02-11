package fr.ensitech.copalive_backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@DiscriminatorValue("GOAL")
@Data
@EqualsAndHashCode(callSuper = true)
public class Goal extends Event{

    @ManyToOne
    @JoinColumn(name = "scorer_id")
    private Player scorer;

    @Column(name = "goal_type")
    private String type;
}
