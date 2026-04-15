package de.inkvine.dota2stats.domain.playersearch.impl;

import de.inkvine.dota2stats.domain.playersearch.PlayerSearchResult;

public class PlayerSearchResultImpl implements PlayerSearchResult {

	private String keyUrl;
	private String name;
	private String iconUrl;

	public PlayerSearchResultImpl(String keyUrl, String name, String iconUrl) {
		super();
		this.keyUrl = keyUrl;
		this.name = name;
		this.iconUrl = iconUrl;
	}

	public String getKeyUrl() {
		return keyUrl;
	}

	public void setKeyUrl(String keyUrl) {
		this.keyUrl = keyUrl;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	@Override
	public long getAccountId() {

		int indexOfLastSlash = keyUrl.lastIndexOf("/");
		return Long.parseLong(keyUrl.substring(indexOfLastSlash + 1));
	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append("Player:\n");
		sb.append("---- id: " + getAccountId() + "\n");
		sb.append("---- name: " + getName() + "\n");
		sb.append("---- icon: " + getIconUrl() + "\n");

		return sb.toString();

	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getIconUrl() {

		return this.iconUrl;
	}

}
