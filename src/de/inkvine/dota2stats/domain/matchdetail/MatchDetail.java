package de.inkvine.dota2stats.domain.matchdetail;

import java.util.List;

import de.inkvine.dota2stats.domain.GameMode;
import de.inkvine.dota2stats.domain.MatchOverview;

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

	public List<MatchDetailPlayer> getPlayers();

}
