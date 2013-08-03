package de.inkvine.dota2stats.domain.matchdetail;

import de.inkvine.dota2stats.domain.MatchOverview;
import de.inkvine.dota2stats.domain.filter.MatchHistoryFilter.GameMode;

public interface MatchDetail {

	public Number getSeason();

	public boolean didRadianWin();

	public Number getDurationOfMatch();

	public MatchOverview getMatchOverview();

	public Number getFirstBloodTime();

	public Number getHumanPlayer();

	public Number getLeagueId();

	public Number getPositiveVotes();

	public Number getNegativeVotes();

	public GameMode getGameMode();

}
