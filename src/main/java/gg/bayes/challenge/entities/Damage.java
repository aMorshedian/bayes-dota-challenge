package gg.bayes.challenge.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Damage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String hero;
    private String target;
    private int damageAmount;
    @ManyToOne
    private Match match;

}
