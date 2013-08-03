package de.inkvine.dotakdratio.domain;

import de.inkvine.dotakdratio.domain.filter.MatchHistoryFilter.GameMode;

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
