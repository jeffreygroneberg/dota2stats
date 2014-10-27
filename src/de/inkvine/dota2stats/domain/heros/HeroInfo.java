package de.inkvine.dota2stats.domain.heros;

import java.util.Map;

public class HeroInfo {
    
    
    private Map<String, Object> jsonMap;
    
    private static final String KEY_NAME = "name";
    private static final String KEY_ID = "id";

    public HeroInfo(Map<String, Object> jsonMap) {
        // TODO Auto-generated constructor stub
        this.jsonMap = jsonMap;
    }
    
    public String getHeroName() {

        return (String) jsonMap.get(KEY_NAME);
    }
    
    public int getHeroID() {

        return ((Double) jsonMap.get(KEY_ID)).intValue();
    }

}
