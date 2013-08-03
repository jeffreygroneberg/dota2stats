package de.inkvine.dotakdratio.domain.impl;

import java.util.Map;

import de.inkvine.dotakdratio.domain.Match;
import de.inkvine.dotakdratio.domain.MatchDetail;
import de.inkvine.dotakdratio.domain.filter.MatchHistoryFilter.GameMode;

public class MatchDetailImpl implements MatchDetail {

	private static final String KEY_FIRST_BLOOD_TIME = "first_blood_time";
	private static final String KEY_SEASON = "season";
	private static final String KEY_RADIANT_WIN = "radiant_win";
	private static final String KEY_DURATION = "duration";
	private static final String KEY_HUMAN_PLAYERS = "human_players";
	private static final String KEY_LEAGUE_ID = "leagueid";
	private static final String KEY_POSITIVE_VOTES = "positive_votes";
	private static final String KEY_NEGATIVE_VOTES = "negative_votes";
	private static final String KEY_GAME_MODE = "game_mode";
	private Map<String, Object> jsonMap;

	public MatchDetailImpl(Map<String, Object> map) {

		this.jsonMap = map;

	}

	@Override
	public Number getSeason() {
		// TODO Auto-generated method stub
		return (Number) jsonMap.get(KEY_SEASON);
	}

	@Override
	public boolean didRadianWin() {

		return (Boolean) jsonMap.get(KEY_RADIANT_WIN);

	}

	@Override
	public Number getDurationOfMatch() {
		// TODO Auto-generated method stub
		return (Number) jsonMap.get(KEY_DURATION);
	}

	@Override
	public Match getMatchOverview() {

		return new MatchImpl(jsonMap);

	}

	@Override
	public Number getFirstBloodTime() {

		return (Number) jsonMap.get(KEY_FIRST_BLOOD_TIME);
	}

	@Override
	public Number getHumanPlayer() {

		return (Number) jsonMap.get(KEY_HUMAN_PLAYERS);
	}

	@Override
	public Number getLeagueId() {
		// TODO Auto-generated method stub
		return (Number) jsonMap.get(KEY_LEAGUE_ID);
	}

	@Override
	public Number getPositiveVotes() {
		// TODO Auto-generated method stub
		return (Number) jsonMap.get(KEY_POSITIVE_VOTES);
	}

	@Override
	public Number getNegativeVotes() {
		// TODO Auto-generated method stub
		return (Number) jsonMap.get(KEY_NEGATIVE_VOTES);
	}

	@Override
	public GameMode getGameMode() {

		return GameMode.values()[((Double) jsonMap.get(KEY_GAME_MODE))
				.intValue()-1];
	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append("MatchDetailImpl object:\n");
		sb.append("Overview:" + getMatchOverview() + "\n");
		sb.append("---- Season: " + getSeason() + "\n");
		sb.append("---- DidRadiantWin: " + didRadianWin() + "\n");
		sb.append("---- DurationofMatch: " + getDurationOfMatch() + "\n");
		sb.append("---- FirstBloodTime: " + getFirstBloodTime() + "\n");
		sb.append("---- HumanPlayers: " + getHumanPlayer() + "\n");
		sb.append("---- LeagueId: " + getLeagueId() + "\n");
		sb.append("---- PositiveVotes: " + getPositiveVotes() + "\n");
		sb.append("---- NegativeVotes: " + getNegativeVotes() + "\n");
		sb.append("---- GameMode: " + getGameMode() + "\n");

		return sb.toString();

	}

}
