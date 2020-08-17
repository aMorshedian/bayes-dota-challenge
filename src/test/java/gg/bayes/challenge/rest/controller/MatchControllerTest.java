package gg.bayes.challenge.rest.controller;

import gg.bayes.challenge.entities.*;
import gg.bayes.challenge.repositories.MatchRepository;
import gg.bayes.challenge.service.MatchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class MatchControllerTest {
    public String COMBAT_LOG = "[00:08:52.791] npc_dota_hero_rubick buys item item_enchanted_mango\n" +
            "[00:08:53.058] npc_dota_hero_rubick buys item item_ward_observer\n" +
            "[00:11:17.489] npc_dota_hero_snapfire is killed by npc_dota_hero_mars\n" +
            "[00:11:20.322] npc_dota_hero_rubick is killed by npc_dota_hero_pangolier\n" +
            "[00:11:24.654] npc_dota_hero_puck casts ability puck_illusory_orb (lvl 1) on dota_unknown\n" +
            "[00:11:28.553] npc_dota_hero_bloodseeker casts ability bloodseeker_bloodrage (lvl 1) on npc_dota_hero_bloodseeker\n" +
            "[00:11:39.250] npc_dota_hero_death_prophet hits npc_dota_hero_dragon_knight with dota_unknown for 47 damage (783->736)\n" +
            "[00:11:43.716] npc_dota_hero_bane hits npc_dota_hero_puck with dota_unknown for 64 damage (354->290)";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MatchService matchService;
    @Autowired
    private MatchRepository matchRepository;

    private Long setup() {
        Match match = new Match();
        List<BoughtItem> boughtItems = new ArrayList<>();
        List<CastedSpell> castedSpells = new ArrayList<>();
        List<Kill> kills = new ArrayList<>();
        List<Damage> damages = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            Kill kill = new Kill();
            kill.setMatch(match);
            kill.setMurderer("hero1");
            kill.setSlain("hero" + String.valueOf(i));
            kills.add(kill);
        }
        for (int i = 0; i < 4; i++) {
            Damage damage = new Damage();
            damage.setMatch(match);
            damage.setDamageAmount(23);
            damage.setHero("hero1");
            damage.setTarget("hero" + String.valueOf(i));
            damages.add(damage);
        }
        for (int i = 0; i < 4; i++) {
            BoughtItem boughtItem = new BoughtItem();
            boughtItem.setMatch(match);
            boughtItem.setBuyer("hero1");
            boughtItem.setItem("item" + String.valueOf(i));
            boughtItem.setTimestamp(1223L);
            boughtItems.add(boughtItem);
        }
        for (int i = 0; i < 4; i++) {
            CastedSpell spell = new CastedSpell();
            spell.setSpell("spell");
            spell.setHero("hero1");
            spell.setMatch(match);
            castedSpells.add(spell);
        }
        match.setBoughtItems(boughtItems);
        match.setCastedSpells(castedSpells);
        match.setKills(kills);
        match.setDamages(damages);
        return matchRepository.save(match).getId();

    }

    @BeforeEach
    private void deleteRepository() {
        matchRepository.deleteAll();
    }

    @Test
    public void create() throws Exception {
        mockMvc.perform(
                post("/api/match")
                        .contentType("text/plain")
                        .content(COMBAT_LOG)
        )
                // then
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void kills() throws Exception {
        Long matchId = setup();
        mockMvc.perform(
                get("/api/match/{id}", matchId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        )
                // then
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));

    }

    @Test
    public void damages() throws Exception {
        Long matchId = setup();
        mockMvc.perform(
                get("/api/match/{id}/{heroName}/damage", matchId, "hero1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        )
                // then
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(4));

    }

    @Test
    public void items() throws Exception {
        Long matchId = setup();
        mockMvc.perform(
                get("/api/match/{id}/{heroName}/items", matchId, "hero1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        )
                // then
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(4));

    }

    @Test
    public void spells() throws Exception {
        Long matchId = setup();
        mockMvc.perform(
                get("/api/match/{id}/{heroName}/spells", matchId, "hero1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        )
                // then
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));

    }
}