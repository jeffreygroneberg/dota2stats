package de.inkvine.dota2stats.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import de.inkvine.dota2stats.Dota2Stats;
import de.inkvine.dota2stats.domain.MatchOverview;
import de.inkvine.dota2stats.domain.filter.MatchHistoryFilter;
import de.inkvine.dota2stats.domain.filter.QueryStringBuilder;
import de.inkvine.dota2stats.domain.matchdetail.MatchDetail;
import de.inkvine.dota2stats.domain.matchdetail.MatchDetailPlayer;
import de.inkvine.dota2stats.domain.matchdetail.impl.MatchDetailImpl;
import de.inkvine.dota2stats.domain.matchhistory.MatchHistory;
import de.inkvine.dota2stats.domain.matchhistory.impl.MatchHistoryImpl;
import de.inkvine.dota2stats.domain.playersearch.PlayerSearchResult;
import de.inkvine.dota2stats.domain.playersearch.impl.PlayerSearchResultImpl;
import de.inkvine.dota2stats.domain.playerstats.PlayerStats;
import de.inkvine.dota2stats.domain.playerstats.impl.PlayerStatsImpl;
import de.inkvine.dota2stats.exceptions.Dota2StatsAccessException;

public class Dota2StatsImpl implements Dota2Stats {

	private static final int MAXIMUM_NUMBER_OF_MATCH_OVERVIEWS_PER_REQUEST = 100;
	private static final String API_GET_MATCH_HISTORY_URL = "https://api.steampowered.com/IDOTA2Match_570/GetMatchHistory/V001/?";
	private static final String API_GET_MATCH_DETAILS_URL = "https://api.steampowered.com/IDOTA2Match_570/GetMatchDetails/V001/?";
	private static final String API_DOTABUFF_SEARCH_PLAYER = "http://dotabuff.com/search?utf8=%E2%9C%93&commit=Search&q=";

	private QueryStringBuilder builder = new QueryStringBuilder();
	HttpHost proxy;

	private String API_KEY;

	@Override
	public MatchHistory getMostRecentMatchHistory()
			throws Dota2StatsAccessException {

		return this.getMatchHistory(null);

	}

	public Dota2StatsImpl(String APIKey) {

		API_KEY = APIKey;

	}

	public Dota2StatsImpl(String APIKey, String proxyUrl, int proxyPort) {

		this(APIKey);
		proxy = new HttpHost(proxyUrl, proxyPort, "http");

	}

	@Override
	public MatchHistory getMatchHistory(MatchHistoryFilter filter)
			throws Dota2StatsAccessException {

		String queryString = API_GET_MATCH_HISTORY_URL + "key=" + API_KEY
				+ this.builder.buildQueryStringForFilter(filter);

		Map<String, Object> map = doHttpGetAndConvertJsonToObject(queryString);

		@SuppressWarnings("unchecked")
		MatchHistory hist = new MatchHistoryImpl(
				(Map<String, Object>) map.get("result"));

		return hist;

	}

	@Override
	public MatchDetail getMatchDetails(long matchId)
			throws Dota2StatsAccessException {

		String queryString = API_GET_MATCH_DETAILS_URL + "key=" + API_KEY
				+ "&match_id=" + matchId;

		Map<String, Object> map = doHttpGetAndConvertJsonToObject(queryString);

		@SuppressWarnings("unchecked")
		MatchDetailImpl details = new MatchDetailImpl(
				(Map<String, Object>) map.get("result"));

		return details;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PlayerSearchResult> searchByPlayerName(String name)
			throws Dota2StatsAccessException {
		String queryString = API_DOTABUFF_SEARCH_PLAYER + name;
		Elements playerNames;
		Document doc;

		List<PlayerSearchResult> results = new ArrayList<PlayerSearchResult>();
		// List<Object> list =
		try {

			doc = Jsoup
					.connect(queryString)
					.userAgent(
							"Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2049.0 Safari/537.36")
					.get();

			// try to get a list of users
			playerNames = doc.select("div.record.player[data-link-to]");

			// check if we found users
			if (playerNames.size() > 0) {

				for (Element element : playerNames) {

					PlayerSearchResult result = extractPlayerSearchResult(element);
					results.add(result);

				}

				return results;

			}

			// check if the result was ONE single user
			Elements playerContainer = doc
					.select("div.image-container.image-container-avatar.image-container-player");
			Elements playerInfo = playerContainer.select("a[href]");

			if (playerContainer.size() == 1) {
				
				results.add(extractPlayerSearchResult(playerInfo.first()));
				
				return results;
				
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new Dota2StatsAccessException(
					"Something went wrong whole searching for the given name. Are you connected to the internet??");

		}

		return null;

	}

	private PlayerSearchResult extractPlayerSearchResult(Element element) {
		String playerName = element.select("img").attr("alt");
		String iconUrl = element.select("img").attr("src");
		String keyUrl = element.select("a").attr("href");

		PlayerSearchResult result = new PlayerSearchResultImpl(
				keyUrl, playerName, iconUrl);
		return result;
	}

	protected List<Object> doHttpGetAndConvertJsonToList(String queryString)
			throws Dota2StatsAccessException {

		String jsonString = doHttpGet(queryString);

		Gson gson = new Gson();
		List<Object> list = gson.fromJson(jsonString,
				new TypeToken<List<Object>>() {
				}.getType());
		return list;
	}

	protected Map<String, Object> doHttpGetAndConvertJsonToObject(
			String queryString) throws Dota2StatsAccessException {

		String jsonString = doHttpGet(queryString);

		Gson gson = new Gson();
		try {
			Map<String, Object> map = gson.fromJson(jsonString,
					new TypeToken<HashMap<String, Object>>() {
					}.getType());

			return map;
		} catch (JsonSyntaxException e) {

			throw new Dota2StatsAccessException(
					"Something went wrong after accessing the Dota2 WebAPI. Check accountid and/or API key!");

		}

	}

	protected String doHttpGet(String queryString)
			throws Dota2StatsAccessException {

		System.out.println("Requesting: " + queryString);

		DefaultHttpClient http = new DefaultHttpClient();

		if (proxy != null)
			http.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);

		HttpGet getMatches = new HttpGet(queryString);

		HttpResponse resp = null;
		try {
			resp = http.execute(getMatches);
		} catch (ClientProtocolException e) {

			e.printStackTrace();
		} catch (IOException e) {

			throw new Dota2StatsAccessException(
					"Cannot access host. Please check if "
							+ "your network is working and if you are "
							+ "connecting through a proxy (Configure proxy with parameters -pPort "
							+ "and -pUrl)");
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
	public PlayerStats getStats(long accountId, MatchHistoryFilter filter)
			throws Dota2StatsAccessException {

		return this.getStats(accountId, -1, filter);

	}

	@Override
	public PlayerStats getStats(long accountId, int numberOfMatches)
			throws Dota2StatsAccessException {

		return this.getStats(accountId, numberOfMatches, null);

	}

	protected PlayerStats getStats(long accountId, int numberOfMatches,
			MatchHistoryFilter filter) throws Dota2StatsAccessException {

		System.out.println("Acquiring data for accountId :" + accountId);

		if (filter == null)
			filter = new MatchHistoryFilter().forAccountId(accountId);

		MatchHistory hist = this
				.getMatchHistory(filter.forAccountId(accountId));

		// first request
		List<MatchOverview> matchesOverview = hist.getMatchOverviews();

		if (matchesOverview == null || matchesOverview.size() == 0)
			return null;

		// user has not provided a numberofmatches instead a he has given a
		// filter
		if (numberOfMatches == -1)
			numberOfMatches = hist.getTotalNumberOfResults();

		System.out.println("Found a total of matches (with applied filter): "
				+ numberOfMatches);

		// calculate the number of additional requests we need to gather all the
		// information
		int numberOfLoops = calculateNumberOfLoops(numberOfMatches, hist);

		// get last id for next match overview request
		long lastId = matchesOverview.get(matchesOverview.size() - 1)
				.getMatchId();

		// if we want less than 25 matches returned, we have to cut off the not
		// needed matches
		int matchesFetched = matchesOverview.size();
		if (matchesFetched > numberOfMatches) {

			matchesFetched = numberOfMatches;
			matchesOverview = matchesOverview.subList(0, matchesFetched);

		}

		// unfortunately we have to get the metaData one after another :(
		for (int i = 1; i < numberOfLoops; i++) {

			hist = this.getMatchHistory(new MatchHistoryFilter().forAccountId(
					accountId).forStartingMatchId(lastId));

			List<MatchOverview> retrievedMatches = hist.getMatchOverviews();

			for (MatchOverview match : retrievedMatches) {
				if (matchesFetched >= numberOfMatches)
					break;

				matchesOverview.add(match);
				matchesFetched++;

			}

			lastId = hist.getMatchOverviews()
					.get(hist.getMatchOverviews().size() - 1).getMatchId();

		}

		PlayerStats stats = getStatsForMatches(accountId, matchesOverview);

		return stats;

	}

	private int calculateNumberOfLoops(int numberOfMatches, MatchHistory hist) {
		int totalNumberOfResults = hist.getTotalNumberOfResults();

		// check if we have all those matches available
		if (totalNumberOfResults < numberOfMatches)
			numberOfMatches = totalNumberOfResults;

		// calculate loops
		int numberOfLoops = (int) (numberOfMatches / MAXIMUM_NUMBER_OF_MATCH_OVERVIEWS_PER_REQUEST);
		if ((numberOfMatches % MAXIMUM_NUMBER_OF_MATCH_OVERVIEWS_PER_REQUEST) != 0)
			numberOfLoops += 1;

		return numberOfLoops;
	}

	private PlayerStats getStatsForMatches(long accountId,
			List<MatchOverview> matchesOverview) {
		List<Callable<MatchDetail>> matchRequests = new ArrayList<Callable<MatchDetail>>();

		final ExecutorService pool = Executors.newFixedThreadPool(25);

		int reqCounter = 1;

		for (final MatchOverview match : matchesOverview) {

			System.out.println("Adding " + reqCounter + " / "
					+ matchesOverview.size() + " requests");

			matchRequests.add(new Callable<MatchDetail>() {

				@Override
				public MatchDetail call() throws Exception {

					return getMatchDetails(match.getMatchId());

				}
			});

			reqCounter++;

		}

		List<Future<MatchDetail>> matchDetailCallResults = new ArrayList<Future<MatchDetail>>();

		for (Callable<MatchDetail> matchDetailCall : matchRequests) {

			matchDetailCallResults.add(pool.submit(matchDetailCall));

		}

		List<MatchDetail> receivedMatches = new ArrayList<MatchDetail>();

		for (Future<MatchDetail> future : matchDetailCallResults) {

			try {

				receivedMatches.add(future.get());

				synchronized (this) {
					System.out.println("Received request. " + --reqCounter
							+ " requests to go!");
				}

			} catch (InterruptedException e) {

				e.printStackTrace();
			} catch (ExecutionException e) {

				e.printStackTrace();
			}

		}

		// Wait till everything has calmed down!
		pool.shutdown();

		double kills = 0;
		double deaths = 0;
		double xpm = 0;
		double gpm = 0;
		double lastHits = 0;
		double denies = 0;
		double assists = 0;

		for (MatchDetail match : receivedMatches) {

			List<MatchDetailPlayer> players = match.getPlayers();
			for (MatchDetailPlayer player : players) {

				if (player.getAccountId() == accountId) {

					System.out.println("Found player in match: "
							+ match.getMatchOverview().getMatchId());

					kills += player.getKills();
					xpm += player.getXPPerMinute();
					gpm += player.getGoldPerMinute();
					deaths += player.getDeaths();
					denies += player.getDenies();
					lastHits += player.getLastHits();
					assists += player.getAssists();

				}

			}

		}

		PlayerStatsImpl playerStats = new PlayerStatsImpl(
				receivedMatches.size());
		playerStats.setDeaths(deaths);
		playerStats.setDenies(denies);
		playerStats.setGoldPerMinute(gpm);
		playerStats.setKills(kills);
		playerStats.setLastHits(lastHits);
		playerStats.setXPPerMinute(xpm);
		playerStats.setAssists(assists);

		return playerStats;

	}

}
