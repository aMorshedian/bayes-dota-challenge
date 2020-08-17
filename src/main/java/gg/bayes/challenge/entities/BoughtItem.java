package gg.bayes.challenge.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class BoughtItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String buyer;
    private String item;
    private Long timestamp;
    @ManyToOne
    private Match match;
}
