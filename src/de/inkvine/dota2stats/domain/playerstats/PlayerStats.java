package de.inkvine.dota2stats.domain.playerstats;

public interface PlayerStats {
	
	public double getKillDeathAssistRatio();
	
	public double getKillDeathRatio();
	
	public double getOverallKills();
	
	public double getKillsPerMatch();

	public double getOverallDeaths();
	
	public double getDeathsPerMatch();

	public double getOverallAssists();
	
	public double getAssistsPerMatch();

	public double getOverallLastHits();
	
	public double getLastHitsPerMatch();

	public double getOverallDenies();
	
	public double getDeniesPerMatch();

	public double getGoldPerMinute();

	public double getXPPerMinute();
	
	public int getNumberOfMatches();

}
