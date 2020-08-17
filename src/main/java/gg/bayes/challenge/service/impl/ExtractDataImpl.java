package gg.bayes.challenge.service.impl;

import gg.bayes.challenge.entities.BoughtItem;
import gg.bayes.challenge.entities.CastedSpell;
import gg.bayes.challenge.entities.Damage;
import gg.bayes.challenge.entities.Kill;
import gg.bayes.challenge.rest.model.HeroDamage;
import gg.bayes.challenge.rest.model.HeroItems;
import gg.bayes.challenge.rest.model.HeroKills;
import gg.bayes.challenge.rest.model.HeroSpells;
import gg.bayes.challenge.service.ExtractData;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExtractDataImpl implements ExtractData {
    @Override
    public List<HeroKills> extractHeroKills(List<Kill> kills) {
        Map<String, Integer> heroKillsMap = new HashMap<>();
        for (Kill kill : kills) {
            if (heroKillsMap.containsKey(kill.getMurderer())) {
                heroKillsMap.put(kill.getMurderer(), heroKillsMap.get(kill.getMurderer()) + 1);
            } else {
                heroKillsMap.put(kill.getMurderer(), 1);
            }
        }

        List<HeroKills> heroKillsList = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : heroKillsMap.entrySet()) {
            HeroKills heroKills = new HeroKills();
            heroKills.setHero(entry.getKey());
            heroKills.setKills(entry.getValue());
            heroKillsList.add(heroKills);
        }
        return heroKillsList;
    }

    @Override
    public List<HeroDamage> extractHeroDamages(List<Damage> damages) {
        Map<String, HeroDamage> heroDamageMap = new HashMap<>();
        for (Damage damage : damages) {
            if (heroDamageMap.containsKey(damage.getTarget())) {
                HeroDamage heroDamage = heroDamageMap.get(damage.getTarget());
                heroDamage.setDamageInstances(heroDamage.getDamageInstances() + 1);
                heroDamage.setTotalDamage(heroDamage.getTotalDamage() + damage.getDamageAmount());
                heroDamageMap.put(damage.getTarget(), heroDamage);
            } else {
                HeroDamage heroDamage = new HeroDamage();
                heroDamage.setTarget(damage.getTarget());
                heroDamage.setDamageInstances(1);
                heroDamage.setTotalDamage(damage.getDamageAmount());
                heroDamageMap.put(damage.getTarget(), heroDamage);
            }

        }
        return new ArrayList<>(heroDamageMap.values());
    }

    @Override
    public List<HeroSpells> extractHeroSpells(List<CastedSpell> castedSpells) {
        Map<String, Integer> heroKillsMap = new HashMap<>();
        for (CastedSpell castedSpell : castedSpells) {
            if (heroKillsMap.containsKey(castedSpell.getSpell())) {
                heroKillsMap.put(castedSpell.getSpell(), heroKillsMap.get(castedSpell.getSpell()) + 1);
            } else {
                heroKillsMap.put(castedSpell.getSpell(), 1);
            }
        }

        List<HeroSpells> heroSpellsList = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : heroKillsMap.entrySet()) {
            HeroSpells heroSpells = new HeroSpells();
            heroSpells.setSpell(entry.getKey());
            heroSpells.setCasts(entry.getValue());
            heroSpellsList.add(heroSpells);
        }
        return heroSpellsList;
    }

    @Override
    public List<HeroItems> extractHeroItems(List<BoughtItem> boughtItems) {
        List<HeroItems> heroItems = new ArrayList<>();
        for (BoughtItem item : boughtItems) {
            HeroItems heroItem = new HeroItems();
            heroItem.setItem(item.getItem());
            heroItem.setTimestamp(item.getTimestamp());
            heroItems.add(heroItem);
        }
        return heroItems;
    }
}
