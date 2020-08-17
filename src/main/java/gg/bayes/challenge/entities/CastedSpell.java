package gg.bayes.challenge.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class CastedSpell {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String hero;
    private String spell;
    @ManyToOne
    private Match match;

}
