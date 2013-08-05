package de.inkvine.dota2stats.impl;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import de.inkvine.dota2stats.Dota2Stats;
import de.inkvine.dota2stats.domain.MatchOverview;
import de.inkvine.dota2stats.domain.PlayerSearchResult;
import de.inkvine.dota2stats.domain.filter.MatchHistoryFilter;
import de.inkvine.dota2stats.domain.filter.QueryStringBuilder;
import de.inkvine.dota2stats.domain.impl.PlayerSearchResultImpl;
import de.inkvine.dota2stats.domain.impl.matchdetail.MatchDetailImpl;
import de.inkvine.dota2stats.domain.impl.matchhistory.MatchHistoryImpl;
import de.inkvine.dota2stats.domain.matchdetail.MatchDetail;
import de.inkvine.dota2stats.domain.matchdetail.MatchDetailPlayer;
import de.inkvine.dota2stats.domain.matchhistory.MatchHistory;

public class Dota2StatsImpl implements Dota2Stats {

	private static String API_GET_MATCH_HISTORY_URL = "https://api.steampowered.com/IDOTA2Match_570/GetMatchHistory/V001/?";
	private static String API_GET_MATCH_DETAILS_URL = "https://api.steampowered.com/IDOTA2Match_570/GetMatchDetails/V001/?";
	private static String API_DOTABUFF_SEARCH_PLAYER = "http://dotabuff.com/search/hints.json?q=";

	private QueryStringBuilder builder = new QueryStringBuilder();

	private String API_KEY;

	@Override
	public MatchHistory getMatchHistory() {

		return this.getMatchHistory(null);

	}

	public Dota2StatsImpl(String APIKey) {

		API_KEY = APIKey;

	}

	@Override
	public MatchHistory getMatchHistory(MatchHistoryFilter filter) {

		String queryString = API_GET_MATCH_HISTORY_URL + "key=" + API_KEY
				+ this.builder.buildQueryStringForFilter(filter);

		Map<String, Object> map = doHttpGetAndConvertJsonToObject(queryString);

		MatchHistory hist = new MatchHistoryImpl(
				(Map<String, Object>) map.get("result"));

		return hist;

	}

	@Override
	public MatchDetail getMatchDetails(long matchId) {

		String queryString = API_GET_MATCH_DETAILS_URL + "key=" + API_KEY
				+ "&match_id=" + matchId;

		Map<String, Object> map = doHttpGetAndConvertJsonToObject(queryString);

		MatchDetailImpl details = new MatchDetailImpl(
				(Map<String, Object>) map.get("result"));

		return details;

	}

	@Override
	public List<PlayerSearchResult> searchByPlayerName(String name) {
		String queryString = API_DOTABUFF_SEARCH_PLAYER + name;

		List<Object> list = doHttpGetAndConvertJsonToList(queryString);

		// add all results to the list of players
		List<PlayerSearchResult> results = new ArrayList<PlayerSearchResult>();
		for (Object item : list)
			results.add(new PlayerSearchResultImpl((Map<String, Object>) item));

		return results;

	}

	protected List<Object> doHttpGetAndConvertJsonToList(String queryString) {

		String jsonString = doHttpGet(queryString);

		Gson gson = new Gson();
		List<Object> list = gson.fromJson(jsonString,
				new TypeToken<List<Object>>() {
				}.getType());
		return list;
	}

	protected Map<String, Object> doHttpGetAndConvertJsonToObject(
			String queryString) {

		String jsonString = doHttpGet(queryString);

		Gson gson = new Gson();
		Map<String, Object> map = gson.fromJson(jsonString,
				new TypeToken<HashMap<String, Object>>() {
				}.getType());
		return map;
	}

	protected String doHttpGet(String queryString) {

		System.out.println("Requesting: " + queryString);

		DefaultHttpClient http = new DefaultHttpClient();
		HttpGet getMatches = new HttpGet(queryString);

		HttpResponse resp = null;
		try {
			resp = http.execute(getMatches);
		} catch (ClientProtocolException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		String jsonString = null;
		try {
			jsonString = EntityUtils.toString(resp.getEntity());
		} catch (ParseException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		return jsonString;
	}

	@Override
	public double getKDRatio(long accountId) {
		
		System.out.println("Acquiring data for accountId :" + accountId);

		final ExecutorService pool = Executors.newFixedThreadPool(50);
		MatchHistory hist = this.getMatchHistory(new MatchHistoryFilter()
				.forAccountId(accountId));

		List<Callable<MatchDetail>> matchRequests = new ArrayList<Callable<MatchDetail>>();

		List<MatchOverview> matchesOverview = hist.getMatchOverviews();

		for (final MatchOverview match : matchesOverview) {

			matchRequests.add(new Callable<MatchDetail>() {

				@Override
				public MatchDetail call() throws Exception {

					return getMatchDetails(match.getMatchId());

				}
			});

		}

		List<Future<MatchDetail>> matchDetailCallResults = new ArrayList<Future<MatchDetail>>();

		for (Callable<MatchDetail> matchDetailCall : matchRequests) {

			matchDetailCallResults.add(pool.submit(matchDetailCall));

		}

		List<MatchDetail> receivedMatches = new ArrayList<MatchDetail>();

		for (Future<MatchDetail> future : matchDetailCallResults) {

			try {

				receivedMatches.add(future.get());
			} catch (InterruptedException e) {

				e.printStackTrace();
			} catch (ExecutionException e) {

				e.printStackTrace();
			}

		}
		
		pool.shutdown();

		double kills = 0;
		double deaths = 0;

		for (MatchDetail match : receivedMatches) {

			List<MatchDetailPlayer> players = match.getPlayers();
			for (MatchDetailPlayer player : players) {

				if (player.getAccountId() == accountId) {
					
					System.out.println("Found player in match: " + match.getMatchOverview().getMatchId());
					kills += player.getKills();
					deaths += player.getDeaths();
				}

			}

		}

		return kills / deaths;

	}

}
