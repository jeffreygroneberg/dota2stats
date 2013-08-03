package de.inkvine.dotakdratio;

import de.inkvine.dotakdratio.domain.MatchDetail;
import de.inkvine.dotakdratio.domain.MatchHistory;
import de.inkvine.dotakdratio.domain.filter.MatchHistoryFilter;

public interface Dota2Stats {

	public MatchHistory getMatchHistory();

	public MatchHistory getMatchHistory(MatchHistoryFilter filter);
	
	public MatchDetail getMatchDetails(long matchId);

}
