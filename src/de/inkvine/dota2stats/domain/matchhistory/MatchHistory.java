package de.inkvine.dota2stats.domain.matchhistory;

import java.util.List;

import de.inkvine.dota2stats.domain.MatchOverview;

public interface MatchHistory {

	public int getStatus();

	public int getNumberOfResults();

	public int getTotalNumberOfResults();

	public int getResultsRemaining();

	public List<MatchOverview> getMatchOverviews();

}
