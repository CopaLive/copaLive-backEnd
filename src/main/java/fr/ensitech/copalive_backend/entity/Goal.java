package fr.ensitech.copalive_backend.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
}
