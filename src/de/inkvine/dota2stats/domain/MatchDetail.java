package de.inkvine.dota2stats.domain;

import de.inkvine.dota2stats.domain.filter.MatchHistoryFilter.GameMode;

public interface MatchDetail {

	public Number getSeason();

	public boolean didRadianWin();

	public Number getDurationOfMatch();

	public Match getMatchOverview();

	public Number getFirstBloodTime();

	public Number getHumanPlayer();

	public Number getLeagueId();

	public Number getPositiveVotes();

	public Number getNegativeVotes();

	public GameMode getGameMode();

}
