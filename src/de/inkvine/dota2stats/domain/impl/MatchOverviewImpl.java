package de.inkvine.dota2stats.domain.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.inkvine.dota2stats.domain.MatchOverview;
import de.inkvine.dota2stats.domain.MatchOverviewPlayer;

public class MatchOverviewImpl implements MatchOverview {

	private static final Object KEY_MATCH_ID = "match_id";
	private static final Object KEY_MATCH_SEQ_NUM = "match_seq_num";
	private static final Object KEY_START_TIME = "start_time";
	private static final Object KEY_LOBBY_TYPE = "lobby_type";

	public MatchOverviewImpl(Map<String, Object> jsonMap) {

		super();
		this.jsonMap = jsonMap;

	}

	private Map<String, Object> jsonMap;

	@Override
	public List<MatchOverviewPlayer> getPlayers() {

		List<Map<String, Object>> players = (List<Map<String, Object>>) jsonMap
				.get("players");

		List<MatchOverviewPlayer> returnablePlayers = new ArrayList<MatchOverviewPlayer>();
		for (Map<String, Object> item : players)
			returnablePlayers.add(new MatchOverviewPlayerImpl(item));

		return returnablePlayers;

	}

	@Override
	public long getMatchId() {
		// TODO Auto-generated method stub
		return  ((Double) jsonMap.get(KEY_MATCH_ID)).longValue();
	}

	@Override
	public long getMatchSeqNumber() {
		// TODO Auto-generated method stub
		return ((Double) jsonMap.get(KEY_MATCH_SEQ_NUM)).longValue();
	}

	@Override
	public long getStartTime() {
		// TODO Auto-generated method stub
		return (Long) jsonMap.get(KEY_START_TIME);
	}

	@Override
	public int getLobbyType() {
		// TODO Auto-generated method stub
		return ((Double) jsonMap.get(KEY_LOBBY_TYPE)).intValue();
	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append("MatchImpl object:\n");
		sb.append("---- id: " + getMatchId() + "\n");
		sb.append("---- SeqNumber: " + getMatchSeqNumber() + "\n");
		sb.append("---- LobbyType: " + getLobbyType() + "\n");

		return sb.toString();

	}

}
