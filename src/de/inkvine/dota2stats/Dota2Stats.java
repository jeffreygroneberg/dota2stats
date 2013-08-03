package de.inkvine.dota2stats;

import de.inkvine.dota2stats.domain.filter.MatchHistoryFilter;
import de.inkvine.dota2stats.domain.matchdetail.MatchDetail;
import de.inkvine.dota2stats.domain.matchhistory.MatchHistory;

public interface Dota2Stats {

	public MatchHistory getMatchHistory();

	public MatchHistory getMatchHistory(MatchHistoryFilter filter);
	
	public MatchDetail getMatchDetails(long matchId);

}
