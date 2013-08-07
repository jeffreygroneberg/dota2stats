package de.inkvine.dota2stats.domain.playerstats.impl;

import de.inkvine.dota2stats.domain.playerstats.PlayerStats;

public class PlayerStatsImpl implements PlayerStats {

	double assists = 0;
	double kills = 0;
	double deaths = 0;
	double xpm = 0;
	double gpm = 0;
	double lastHits = 0;
	double denies = 0;
	int numberOfMatches = 0;

	public PlayerStatsImpl(int numberOfMatches) {
		this.numberOfMatches = numberOfMatches;
	}

	@Override
	public double getKillDeathAssistRatio() {

		return (kills / deaths) + (assists / numberOfMatches);

	}

	@Override
	public double getKillDeathRatio() {

		return (kills / deaths);
	}

	@Override
	public double getOverallKills() {

		return kills;
	}

	@Override
	public double getOverallDeaths() {

		return deaths;
	}

	@Override
	public double getOverallAssists() {

		return assists;
	}

	@Override
	public double getOverallLastHits() {

		return lastHits;

	}

	@Override
	public double getOverallDenies() {

		return denies;
	}

	@Override
	public double getGoldPerMinute() {

		return gpm / numberOfMatches;
	}

	@Override
	public double getXPPerMinute() {

		return xpm / numberOfMatches;
	}

	@Override
	public int getNumberOfMatches() {

		return numberOfMatches;
	}
	
	public void setAssists(double assists) {
		this.assists = assists;
	}

	public void setXPPerMinute(double xpm) {
		this.xpm = xpm;
	}

	public void setGoldPerMinute(double gpm) {
		this.gpm = gpm;
	}

	public void setKills(double kills) {
		this.kills = kills;
	}

	public void setDeaths(double deaths) {
		this.deaths = deaths;
	}

	public void setLastHits(double lastHits) {
		this.lastHits = lastHits;
	}

	public void setDenies(double denies) {
		this.denies = denies;
	}

	@Override
	public double getKillsPerMatch() {

		return kills / numberOfMatches;
	}

	@Override
	public double getDeathsPerMatch() {

		return deaths / numberOfMatches;

	}

	@Override
	public double getAssistsPerMatch() {

		return assists / numberOfMatches;

	}

	@Override
	public double getLastHitsPerMatch() {

		return lastHits / numberOfMatches;

	}

	@Override
	public double getDeniesPerMatch() {

		return denies / numberOfMatches;

	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();
		
		sb.append("PlayerStats object:\n");
		sb.append("---- KDA: " + getKillDeathAssistRatio() + "\n");
		sb.append("---- KD: " + getKillDeathRatio() + "\n");
		sb.append("---- Kills: " + getOverallKills() + "\n");
		sb.append("---- Average Kills: " + getKillsPerMatch() + "\n");
		sb.append("---- Deaths: " + getOverallDeaths() + "\n");
		sb.append("---- Average Deaths: " + getDeathsPerMatch() + "\n");
		sb.append("---- Assists: " + getOverallAssists() + "\n");
		sb.append("---- Average Assists: " + getAssistsPerMatch() + "\n");
		sb.append("---- Last Hits: " + getOverallLastHits() + "\n");
		sb.append("---- Average Last Hits: " + getLastHitsPerMatch() + "\n");
		sb.append("---- Denies: " + getOverallDenies() + "\n");
		sb.append("---- Average Denies: " + getDeniesPerMatch() + "\n");
		sb.append("---- Average GPM: " + getGoldPerMinute() + "\n");
		sb.append("---- Average XPM: " + getXPPerMinute() + "\n");

		return sb.toString();

	}

}
