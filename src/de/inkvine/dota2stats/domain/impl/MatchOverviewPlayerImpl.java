package de.inkvine.dota2stats.domain.impl;

import java.util.Map;

import de.inkvine.dota2stats.domain.MatchOverviewPlayer;

public class MatchOverviewPlayerImpl implements MatchOverviewPlayer {

	private static final String KEY_ACCOUNT_ID = "account_id";
	private static final String KEY_PLAYER_SLOT = "player_slot";
	private static final String KEY_HERO_ID = "hero_id";
	protected Map<String, Object> jsonMap;

	public MatchOverviewPlayerImpl(Map<String, Object> jsonMap) {

		this.jsonMap = jsonMap;

	}

	@Override
	public long getAccountId() {

		return ((Double) jsonMap.get(KEY_ACCOUNT_ID)).longValue();

	}

	@Override
	public int getPlayerSlots() {

		return ((Double) jsonMap.get(KEY_PLAYER_SLOT)).intValue();
	}

	@Override
	public int getHeroId() {

		return ((Double) jsonMap.get(KEY_HERO_ID)).intValue();
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
