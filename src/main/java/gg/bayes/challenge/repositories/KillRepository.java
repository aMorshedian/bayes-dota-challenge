package gg.bayes.challenge.repositories;

import gg.bayes.challenge.entities.Kill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KillRepository extends JpaRepository<Kill, Long> {
    List<Kill> findByMatchId(Long matchId);
}
