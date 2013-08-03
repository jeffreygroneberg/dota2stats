package de.inkvine.dota2stats.domain.impl.matchhistory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.inkvine.dota2stats.domain.MatchOverview;
import de.inkvine.dota2stats.domain.impl.MatchOverviewImpl;
import de.inkvine.dota2stats.domain.matchhistory.MatchHistory;

public class MatchHistoryImpl implements MatchHistory {

	private static final String KEY_STATUS = "status";
	private static final Object KEY_TOTAL_RESULTS = "total_results";
	private static final Object KEY_NUMBER_OF_RESULTS = "num_results";
	private static final Object KEY_RESULTS_REMAINING = "results_remaining";

	private Map<String, Object> jsonMap;

	public MatchHistoryImpl(Map<String, Object> jsonMap) {

		this.jsonMap = jsonMap;

	}

	@Override
	public Number getStatus() {

		return (Number) jsonMap.get(KEY_STATUS);

	}

	@Override
	public Number getNumberOfResults() {
		// TODO Auto-generated method stub
		return (Number) jsonMap.get(KEY_NUMBER_OF_RESULTS);
	}

	@Override
	public Number getTotalNumberOfResults() {
		// TODO Auto-generated method stub
		return (Number) jsonMap.get(KEY_TOTAL_RESULTS);

	}

	@Override
	public Number getResultsRemaining() {
		// TODO Auto-generated method stub
		return (Number) jsonMap.get(KEY_RESULTS_REMAINING);
	}

	@Override
	public List<MatchOverview> getMatchOverviews() {

		List<Map<String, Object>> matches = (List<Map<String, Object>>) jsonMap
				.get("matches");

		List<MatchOverview> returnableMatches = new ArrayList<MatchOverview>();
		for (Map<String, Object> item : matches)
			returnableMatches.add(new MatchOverviewImpl(item));

		return returnableMatches;

	}
}
