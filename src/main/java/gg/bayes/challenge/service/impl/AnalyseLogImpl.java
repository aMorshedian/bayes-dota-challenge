package gg.bayes.challenge.service.impl;

import gg.bayes.challenge.entities.*;
import gg.bayes.challenge.service.AnalyseLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AnalyseLogImpl implements AnalyseLog {
    @Override
    public Match analyseLog(String s) {
        Match match = new Match();
        List<BoughtItem> boughtItems = new ArrayList<>();
        List<CastedSpell> castedSpells = new ArrayList<>();
        List<Kill> kills = new ArrayList<>();
        List<Damage> damages = new ArrayList<>();
        String[] logs = s.split("\n");
        for (String log : logs) {
            if (log.contains("is killed by")) {
                String[] logElements = log.split(" ");
                if (logElements[5].startsWith("npc_dota_hero")) {
                    Kill kill = new Kill();
                    kill.setSlain(logElements[1].substring(9));
                    kill.setMurderer(logElements[5].substring(14));
                    kill.setMatch(match);
                    kills.add(kill);
                }

            } else if (log.contains("hits")) {
                String[] logElements = log.split(" ");
                if (logElements[1].startsWith("npc_dota_hero") && logElements[3].startsWith("npc_dota_hero")) {
                    Damage damage = new Damage();
                    damage.setHero(logElements[1].substring(14));
                    damage.setTarget(logElements[3].substring(14));
                    damage.setDamageAmount(Integer.valueOf(logElements[7]));
                    damage.setMatch(match);
                    damages.add(damage);
                }
            } else if (log.contains("casts")) {
                String[] logElements = log.split(" ");
                if (logElements[1].startsWith("npc_dota_hero")) {
                    CastedSpell spell = new CastedSpell();
                    spell.setHero(logElements[1].substring(14));
                    spell.setSpell(logElements[4]);
                    spell.setMatch(match);
                    castedSpells.add(spell);
                }

            } else if (log.contains("buys")) {
                String[] logElements = log.split(" ");
                if (logElements[1].startsWith("npc_dota_hero")) {
                    BoughtItem boughtItem = new BoughtItem();
                    boughtItem.setBuyer(logElements[1].substring(14));
                    boughtItem.setItem(logElements[4].substring(5));
                    boughtItem.setTimestamp(calculateTimestamp(logElements[0].substring(1, 13)));
                    boughtItem.setMatch(match);
                    boughtItems.add(boughtItem);
                }

            }


        }

        match.setBoughtItems(boughtItems);
        match.setCastedSpells(castedSpells);
        match.setKills(kills);
        match.setDamages(damages);
        return match;
    }

    private long calculateTimestamp(String timeString) {
        long timestamp;
        String[] elemnts = timeString.split(":");
        timestamp = Long.valueOf(elemnts[0]) * 3600000 + Long.valueOf(elemnts[1]) * 60000
                + Long.valueOf(elemnts[2].substring(0, 2)) * 1000 + Long.valueOf(elemnts[2].substring(3));
        return timestamp;
    }
}
