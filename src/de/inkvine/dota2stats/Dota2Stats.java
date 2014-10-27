package de.inkvine.dota2stats;

import java.util.List;

import de.inkvine.dota2stats.domain.filter.MatchHistoryFilter;
import de.inkvine.dota2stats.domain.heros.Heroes;
import de.inkvine.dota2stats.domain.matchdetail.MatchDetail;
import de.inkvine.dota2stats.domain.matchhistory.MatchHistory;
import de.inkvine.dota2stats.domain.playersearch.PlayerSearchResult;
import de.inkvine.dota2stats.domain.playerstats.PlayerStats;
import de.inkvine.dota2stats.exceptions.Dota2StatsAccessException;

public interface Dota2Stats {

	public List<PlayerSearchResult> searchByPlayerName(String name) throws Dota2StatsAccessException;
	
	public MatchHistory getMostRecentMatchHistory() throws Dota2StatsAccessException;

	public MatchHistory getMatchHistory(MatchHistoryFilter filter) throws Dota2StatsAccessException;
	
	// new function: get match history with level (0:all 1:low 2:medium 3:high)
	public MatchHistory getMatchHistoryWithLevel(int level) throws Dota2StatsAccessException;
	
	public MatchDetail getMatchDetails(long matchId) throws Dota2StatsAccessException;	

	public PlayerStats getStats(long accountId, MatchHistoryFilter filter) throws Dota2StatsAccessException;

	public PlayerStats getStats(long accountId, int numberOfMatches) throws Dota2StatsAccessException;
	
	// new function: get hero information
	public Heroes GetHeroes() throws Dota2StatsAccessException;

}
