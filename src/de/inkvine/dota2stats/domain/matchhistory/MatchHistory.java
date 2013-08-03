package de.inkvine.dota2stats.domain.matchhistory;

import java.util.List;

import de.inkvine.dota2stats.domain.MatchOverview;

public interface MatchHistory {

	public Number getStatus();

	public Number getNumberOfResults();

	public Number getTotalNumberOfResults();

	public Number getResultsRemaining();

	public List<MatchOverview> getMatchOverviews();

}
