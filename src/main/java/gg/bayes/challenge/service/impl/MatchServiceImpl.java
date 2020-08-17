package gg.bayes.challenge.service.impl;

import gg.bayes.challenge.entities.Match;
import gg.bayes.challenge.repositories.*;
import gg.bayes.challenge.rest.model.HeroDamage;
import gg.bayes.challenge.rest.model.HeroItems;
import gg.bayes.challenge.rest.model.HeroKills;
import gg.bayes.challenge.rest.model.HeroSpells;
import gg.bayes.challenge.service.ExtractData;
import gg.bayes.challenge.service.MatchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MatchServiceImpl implements MatchService {

    private final AnalyseLogImpl analyseLogService;
    private final MatchRepository matchRepository;
    private final ExtractData extractData;
    private final DamageRepository damageRepository;
    private final CastedSpellRepository castedSpellRepository;
    private final BoughtItemRepository boughtItemRepository;
    private final KillRepository killRepository;

    @Autowired
    public MatchServiceImpl(AnalyseLogImpl analyseLogService, MatchRepository matchRepository, ExtractData extractData, DamageRepository damageRepository, CastedSpellRepository castedSpellRepository, BoughtItemRepository boughtItemRepository, KillRepository killRepository) {
        this.analyseLogService = analyseLogService;
        this.matchRepository = matchRepository;
        this.extractData = extractData;
        this.damageRepository = damageRepository;
        this.castedSpellRepository = castedSpellRepository;
        this.boughtItemRepository = boughtItemRepository;
        this.killRepository = killRepository;
    }

    @Override
    public Long ingestMatch(String payload) {
        Match match = analyseLogService.analyseLog(payload);
        return matchRepository.save(match).getId();
    }

    @Override
    public List<HeroKills> getHeros(Long matchId) {
        return extractData.extractHeroKills(killRepository.findByMatchId(matchId));
    }

    @Override
    public List<HeroDamage> getHeroDamages(Long matchId, String heroName) {
        return extractData.extractHeroDamages(damageRepository.findByMatchIdAndHero(matchId, heroName));

    }

    @Override
    public List<HeroSpells> getHeroSpells(Long matchId, String heroName) {
        return extractData.extractHeroSpells(castedSpellRepository.findByMatchIdAndHero(matchId, heroName));
    }

    @Override
    public List<HeroItems> getHeroItem(Long matchId, String heroName) {
        return extractData.extractHeroItems(boughtItemRepository.findByMatchIdAndBuyer(matchId, heroName));
    }
}
