package fr.ensitech.copalive_backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@DiscriminatorValue("SUBSTITUTION")
@EqualsAndHashCode(callSuper = true)
public class Substitution extends Event{

    @ManyToOne
    @JoinColumn(name = "player_in_id")
    private Player playerIn;

    @ManyToOne
    @JoinColumn(name = "player_out_id")
    private Player playerOut;
}
