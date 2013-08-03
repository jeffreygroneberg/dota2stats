package de.inkvine.dotakdratio.domain.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.inkvine.dotakdratio.domain.Match;
import de.inkvine.dotakdratio.domain.MatchHistory;

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
	public List<Match> getMatches() {

		List<Map<String, Object>> matches = (List<Map<String, Object>>) jsonMap
				.get("matches");

		List<Match> returnableMatches = new ArrayList<Match>();
		for (Map<String, Object> item : matches)
			returnableMatches.add(new MatchImpl(item));

		return returnableMatches;

	}
}
