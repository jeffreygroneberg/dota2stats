package de.inkvine.dota2stats;

import java.util.List;

import de.inkvine.dota2stats.domain.PlayerSearchResult;
import de.inkvine.dota2stats.domain.filter.MatchHistoryFilter;
import de.inkvine.dota2stats.domain.matchdetail.MatchDetail;
import de.inkvine.dota2stats.domain.matchhistory.MatchHistory;

public interface Dota2Stats {

	public List<PlayerSearchResult> searchByPlayerName(String name);
	
	public MatchHistory getMatchHistory();

	public MatchHistory getMatchHistory(MatchHistoryFilter filter);
	
	public MatchDetail getMatchDetails(long matchId);
	
	public double getKDRatio(long accountId);

}
