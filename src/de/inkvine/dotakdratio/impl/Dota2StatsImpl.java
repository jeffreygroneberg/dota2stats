package de.inkvine.dotakdratio.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import de.inkvine.dotakdratio.Dota2Stats;
import de.inkvine.dotakdratio.domain.MatchDetail;
import de.inkvine.dotakdratio.domain.MatchHistory;
import de.inkvine.dotakdratio.domain.filter.MatchHistoryFilter;
import de.inkvine.dotakdratio.domain.filter.QueryStringBuilder;
import de.inkvine.dotakdratio.domain.impl.MatchDetailImpl;
import de.inkvine.dotakdratio.domain.impl.MatchHistoryImpl;

public class Dota2StatsImpl implements Dota2Stats {

	private static String API_GET_MATCH_HISTORY_URL = "https://api.steampowered.com/IDOTA2Match_570/GetMatchHistory/V001/?";
	private static String API_GET_MATCH_DETAILS_URL = "https://api.steampowered.com/IDOTA2Match_570/GetMatchDetails/V001/?";
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

		Map<String, Object> map = doHttpGetAndConvertJson(queryString);

		MatchHistory hist = new MatchHistoryImpl(
				(Map<String, Object>) map.get("result"));

		return hist;

	}

	@Override
	public MatchDetail getMatchDetails(long matchId) {

		String queryString = API_GET_MATCH_DETAILS_URL + "key=" + API_KEY
				+ "&match_id=" + matchId;

		System.out.println(queryString);

		Map<String, Object> map = doHttpGetAndConvertJson(queryString);

		MatchDetailImpl details = new MatchDetailImpl(
				(Map<String, Object>) map.get("result"));

		return details;

	}

	protected Map<String, Object> doHttpGetAndConvertJson(String queryString) {
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

}
