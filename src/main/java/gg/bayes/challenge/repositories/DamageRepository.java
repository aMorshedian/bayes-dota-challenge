package gg.bayes.challenge.repositories;

import gg.bayes.challenge.entities.Damage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DamageRepository extends JpaRepository<Damage, Long> {

    List<Damage> findByMatchIdAndHero(Long matchId, String hero);
}
