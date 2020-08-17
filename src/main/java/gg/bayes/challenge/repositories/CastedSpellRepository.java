package gg.bayes.challenge.repositories;

import gg.bayes.challenge.entities.CastedSpell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CastedSpellRepository extends JpaRepository<CastedSpell, Long> {
    List<CastedSpell> findByMatchIdAndHero(Long matchId, String hero);
}
