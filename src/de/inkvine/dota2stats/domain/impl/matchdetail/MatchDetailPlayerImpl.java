package de.inkvine.dota2stats.domain.impl.matchdetail;

import java.util.List;
import java.util.Map;

import de.inkvine.dota2stats.domain.LeaverStatus;
import de.inkvine.dota2stats.domain.impl.MatchOverviewPlayerImpl;
import de.inkvine.dota2stats.domain.matchdetail.Item;
import de.inkvine.dota2stats.domain.matchdetail.MatchDetailPlayer;

public class MatchDetailPlayerImpl extends MatchOverviewPlayerImpl implements
		MatchDetailPlayer {
	
	private static final String KEY_ITEM_0 = "item_0";
	private static final String KEY_ITEM_1 = "item_1";
	private static final String KEY_ITEM_2 = "item_2";
	private static final String KEY_ITEM_3 = "item_3";
	private static final String KEY_ITEM_4 = "item_4";
	private static final String KEY_ITEM_5 = "item_5";
	
	private static final String KEY_KILLS = "kills";
	private static final String KEY_DEATHS = "deaths";
	private static final String KEY_ASSISTS = "assists";
	private static final String KEY_HUMAN_PLAYERS = "human_players";
	private static final String KEY_LEAVER_STATUS = "leaver_status";
	
	private static final String KEY_GOLD = "gold";
	private static final String KEY_LAST_HITS = "last_hits";
	private static final String KEY_DENIES = "denies";
	private static final String KEY_GOLD_PER_MIN = "gold_per_min";
	private static final String KEY_XP_PER_MIN = "xp_per_min";
	private static final String KEY_GOLD_SPENT = "gold_spent";
	private static final String KEY_HERO_DAMAGE = "hero_damage";
	private static final String KEY_TOWER_DAMAGE = "tower_damage";
	private static final String KEY_HERO_HEALING = "tower_damage";
	private static final String KEY_LEVEL = "level";
	
	private static final String KEY_ABILITY_UPGRADES = "ability_upgrades";
	private static final String KEY_ABILITY_UPGRADES_ABILITY = "ability";
	private static final String KEY_ABILITY_UPGRADES_TIME = "time";
	private static final String KEY_ABILITY_UPGRADES_LEVEL = "level";
	
	private static final String KEY_ADDITIONAL_UNITS = "additional_units";
	private static final String KEY_ADDITIONAL_UNITS_ITEM_0 = "item_0";
	private static final String KEY_ADDITIONAL_UNITS_ITEM_1 = "item_1";
	private static final String KEY_ADDITIONAL_UNITS_ITEM_2 = "item_2";
	private static final String KEY_ADDITIONAL_UNITS_ITEM_3 = "item_3";
	private static final String KEY_ADDITIONAL_UNITS_ITEM_4 = "item_4";
	private static final String KEY_ADDITIONAL_UNITS_ITEM_5 = "item_5";	

	public MatchDetailPlayerImpl(Map<String, Object> jsonMap) {
		super(jsonMap);		
	}

	@Override
	public List<Item> getItems() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getKills() {
		int kills = (Integer) jsonMap.get(KEY_KILLS);
		return kills;
	}

	@Override
	public int getDeaths() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getAssists() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public LeaverStatus getLeaverStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getGold() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getLastHits() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getDenies() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getGoldPerMinute() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getXPPerMinute() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getGoldSpent() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getHeroDamageDealt() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTowerDamageDealt() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getHeroDamageHealt() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getHeroLevel() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int[] getSkilledAbilityOrder() {
		// TODO Auto-generated method stub
		return null;
	}

}
