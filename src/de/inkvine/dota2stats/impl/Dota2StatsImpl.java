package de.inkvine.dota2stats.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import de.inkvine.dota2stats.Dota2Stats;
import de.inkvine.dota2stats.domain.PlayerSearchResult;
import de.inkvine.dota2stats.domain.filter.MatchHistoryFilter;
import de.inkvine.dota2stats.domain.filter.QueryStringBuilder;
import de.inkvine.dota2stats.domain.impl.PlayerSearchResultImpl;
import de.inkvine.dota2stats.domain.impl.matchdetail.MatchDetailImpl;
import de.inkvine.dota2stats.domain.impl.matchhistory.MatchHistoryImpl;
import de.inkvine.dota2stats.domain.matchdetail.MatchDetail;
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

		System.out.println(queryString);

		Map<String, Object> map = doHttpGetAndConvertJsonToObject(queryString);

		MatchHistory hist = new MatchHistoryImpl(
				(Map<String, Object>) map.get("result"));

		return hist;

	}

	@Override
	public MatchDetail getMatchDetails(long matchId) {

		String queryString = API_GET_MATCH_DETAILS_URL + "key=" + API_KEY
				+ "&match_id=" + matchId;

		System.out.println(queryString);

		Map<String, Object> map = doHttpGetAndConvertJsonToObject(queryString);

		MatchDetailImpl details = new MatchDetailImpl(
				(Map<String, Object>) map.get("result"));

		return details;

	}
	
	protected List<Object> doHttpGetAndConvertJsonToList(String queryString) {
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

		Gson gson = new Gson();
		List<Object> list = gson.fromJson(jsonString,
				new TypeToken<List<Object>>() {
				}.getType());
		return list;
	}

	protected Map<String, Object> doHttpGetAndConvertJsonToObject(String queryString) {
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

		Gson gson = new Gson();
		Map<String, Object> map = gson.fromJson(jsonString,
				new TypeToken<HashMap<String, Object>>() {
				}.getType());
		return map;
	}

	@Override
	public List<PlayerSearchResult> searchByPlayerName(String name) {
		String queryString = API_DOTABUFF_SEARCH_PLAYER + name;
			
		List<Object> list = doHttpGetAndConvertJsonToList(queryString);

		// add all results to the list of players
		List<PlayerSearchResult> results = new ArrayList<PlayerSearchResult>();
		for(Object item : list)
			results.add(new PlayerSearchResultImpl((Map<String, Object>) item));
		
		return results;	
		
	}

	

}
