package gg.bayes.challenge.entities;


import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(cascade = CascadeType.ALL)
    private List<BoughtItem> boughtItems;
    @OneToMany(cascade = CascadeType.ALL)
    private List<CastedSpell> castedSpells;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Kill> kills;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Damage> damages;

}
