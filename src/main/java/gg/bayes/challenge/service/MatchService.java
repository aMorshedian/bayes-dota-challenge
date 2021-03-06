package gg.bayes.challenge.service;

import gg.bayes.challenge.rest.model.HeroDamage;
import gg.bayes.challenge.rest.model.HeroItems;
import gg.bayes.challenge.rest.model.HeroKills;
import gg.bayes.challenge.rest.model.HeroSpells;

import java.util.List;

public interface MatchService {
    Long ingestMatch(String payload);

    List<HeroKills> getHeros(Long matchId);

    List<HeroDamage> getHeroDamages(Long matchId, String heroName);

    List<HeroSpells> getHeroSpells(Long matchId, String heroName);

    List<HeroItems> getHeroItem(Long matchId, String heroName);
}
