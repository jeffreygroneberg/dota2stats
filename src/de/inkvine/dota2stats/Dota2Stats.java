package de.inkvine.dota2stats;

import de.inkvine.dota2stats.domain.MatchDetail;
import de.inkvine.dota2stats.domain.MatchHistory;
import de.inkvine.dota2stats.domain.filter.MatchHistoryFilter;

public interface Dota2Stats {

	public MatchHistory getMatchHistory();

	public MatchHistory getMatchHistory(MatchHistoryFilter filter);
	
	public MatchDetail getMatchDetails(long matchId);

}
