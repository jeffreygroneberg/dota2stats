package de.inkvine.dota2stats.domain.impl;

import java.util.Map;

import de.inkvine.dota2stats.domain.PlayerSearchResult;

public class PlayerSearchResultImpl implements PlayerSearchResult {

	private static String KEY_ICON = "icon";
	private static String KEY_NAME = "name";
	private static String KEY_URL = "url";

	protected Map<String, Object> jsonMap;

	public PlayerSearchResultImpl(Map<String, Object> jsonMap) {

		this.jsonMap = jsonMap;

	}

	@Override
	public String getName() {

		return (String) jsonMap.get(KEY_NAME);

	}

	@Override
	public String getIconUrl() {
		return (String) jsonMap.get(KEY_ICON);
	}

	@Override
	public long getAccountId() {
		String accountUrl = (String) jsonMap.get(KEY_URL);
		int indexOfLastSlash = accountUrl.lastIndexOf("/");
		return Long.parseLong(accountUrl.substring(indexOfLastSlash + 1));
	}
	
	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append("PlayerSearchResultImpl object:\n");
		sb.append("---- id: " + getAccountId() + "\n");
		sb.append("---- name: " + getName() + "\n");
		sb.append("---- icon: " + getIconUrl() + "\n");

		return sb.toString();

	}

}
