package gg.bayes.challenge.repositories;

import gg.bayes.challenge.entities.BoughtItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoughtItemRepository extends JpaRepository<BoughtItem, Long> {
    List<BoughtItem> findByMatchIdAndBuyer(Long matchId, String buyer);
}
