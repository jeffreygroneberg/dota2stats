package de.inkvine.dotakdratio.domain;

import java.util.List;

public interface MatchHistory {

	public Number getStatus();

	public Number getNumberOfResults();

	public Number getTotalNumberOfResults();

	public Number getResultsRemaining();

	public List<Match> getMatches();

}
