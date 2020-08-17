package gg.bayes.challenge.service;

import gg.bayes.challenge.entities.BoughtItem;
import gg.bayes.challenge.entities.CastedSpell;
import gg.bayes.challenge.entities.Damage;
import gg.bayes.challenge.entities.Kill;
import gg.bayes.challenge.rest.model.HeroDamage;
import gg.bayes.challenge.rest.model.HeroItems;
import gg.bayes.challenge.rest.model.HeroKills;
import gg.bayes.challenge.rest.model.HeroSpells;

import java.util.List;

public interface ExtractData {
    List<HeroKills> extractHeroKills(List<Kill> kills);

    List<HeroDamage> extractHeroDamages(List<Damage> damages);

    List<HeroSpells> extractHeroSpells(List<CastedSpell> castedSpells);

    List<HeroItems> extractHeroItems(List<BoughtItem> boughtItems);
}
