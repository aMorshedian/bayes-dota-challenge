package gg.bayes.challenge.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Kill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String murderer;
    private String slain;
    @ManyToOne
    private Match match;

}
