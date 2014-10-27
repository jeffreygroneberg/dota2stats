package de.inkvine.dota2stats.domain.heros;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.inkvine.dota2stats.domain.MatchOverview;
import de.inkvine.dota2stats.domain.impl.MatchOverviewImpl;
import de.inkvine.dota2stats.domain.matchdetail.MatchDetailPlayer;
import de.inkvine.dota2stats.domain.matchdetail.impl.MatchDetailPlayerImpl;

public class Heroes {


    private static final Object KEY_TOTAL_RESULTS = "total_results";
    private static final Object KEY_HEROES_COUNT = "count";
    private static final Object KEY_RESULTS_REMAINING = "results_remaining";

    private Map<String, Object> jsonMap;


    public Heroes(Map<String, Object> jsonMap) {
        this.jsonMap = jsonMap;
    }

    public int getHeroesCount() {
        return ((Double) jsonMap.get(KEY_HEROES_COUNT)).intValue();
    }


    public List<HeroInfo> getHeroes() {

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> heroes = (List<Map<String, Object>>) jsonMap
                .get("heroes");
        
        List<HeroInfo> returnableHeroes = new ArrayList<HeroInfo>();
        for (Map<String, Object> item : heroes) {
            returnableHeroes.add(new HeroInfo(item));
        }
        
        return returnableHeroes;
    }
}
