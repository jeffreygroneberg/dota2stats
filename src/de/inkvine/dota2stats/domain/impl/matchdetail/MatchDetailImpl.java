package de.inkvine.dota2stats.domain.impl.matchdetail;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.inkvine.dota2stats.domain.GameMode;
import de.inkvine.dota2stats.domain.MatchOverview;
import de.inkvine.dota2stats.domain.MatchOverviewPlayer;
import de.inkvine.dota2stats.domain.impl.MatchOverviewImpl;
import de.inkvine.dota2stats.domain.impl.MatchOverviewPlayerImpl;
import de.inkvine.dota2stats.domain.matchdetail.MatchDetail;
import de.inkvine.dota2stats.domain.matchdetail.MatchDetailPlayer;

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
	public boolean didRadianWin() {

		return (Boolean) jsonMap.get(KEY_RADIANT_WIN);

	}

	@Override
	public int getDurationOfMatch() {
		// TODO Auto-generated method stub
		return ((Double) jsonMap.get(KEY_DURATION)).intValue();
	}

	@Override
	public MatchOverview getMatchOverview() {

		return new MatchOverviewImpl(jsonMap);

	}

	@Override
	public int getFirstBloodTime() {

		return ((Double) jsonMap.get(KEY_FIRST_BLOOD_TIME)).intValue();
	}

	@Override
	public int getHumanPlayer() {

		return ((Double) jsonMap.get(KEY_HUMAN_PLAYERS)).intValue();
	}

	@Override
	public int getLeagueId() {
		// TODO Auto-generated method stub
		return ((Double) jsonMap.get(KEY_LEAGUE_ID)).intValue();
	}

	@Override
	public int getPositiveVotes() {
		// TODO Auto-generated method stub
		return ((Double) jsonMap.get(KEY_POSITIVE_VOTES)).intValue();
	}

	@Override
	public int getNegativeVotes() {
		// TODO Auto-generated method stub
		return ((Double) jsonMap.get(KEY_NEGATIVE_VOTES)).intValue();
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

	@Override
	public List<MatchDetailPlayer> getPlayers() {
		
		List<Map<String, Object>> players = (List<Map<String, Object>>) jsonMap
				.get("players");

		List<MatchDetailPlayer> returnablePlayers = new ArrayList<MatchDetailPlayer>();
		for (Map<String, Object> item : players)
			returnablePlayers.add(new MatchDetailPlayerImpl(item));

		return returnablePlayers;
		
	}

}
