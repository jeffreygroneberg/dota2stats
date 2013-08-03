package de.inkvine.dota2stats.domain.impl;

import java.util.Map;

import de.inkvine.dota2stats.domain.MatchOverviewPlayer;

public class MatchOverviewPlayerImpl implements MatchOverviewPlayer {

	private static final String KEY_ACCOUNT_ID = "account_id";
	private static final String KEY_PLAYER_SLOT = "player_slot";
	private static final String KEY_HERO_ID = "hero_id";
	private Map<String, Object> jsonMap;

	public MatchOverviewPlayerImpl(Map<String, Object> jsonMap) {

		this.jsonMap = jsonMap;

	}

	@Override
	public Number getAccountId() {

		return (Number) jsonMap.get(KEY_ACCOUNT_ID);

	}

	@Override
	public Number getPlayerSlots() {
		// TODO Auto-generated method stub
		return (Number) jsonMap.get(KEY_PLAYER_SLOT);
	}

	@Override
	public Number getHeroId() {
		// TODO Auto-generated method stub
		return (Number) jsonMap.get(KEY_HERO_ID);
	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append("PlayerImpl object:\n");
		sb.append("---- AccountId: " + getAccountId() + "\n");
		sb.append("---- PlayerSlots: " + getPlayerSlots() + "\n");
		sb.append("---- HeroId: " + getHeroId() + "\n");

		return sb.toString();
	}
}
