package de.inkvine.dota2stats.domain.matchdetail;

import java.util.List;

import de.inkvine.dota2stats.domain.GameMode;
import de.inkvine.dota2stats.domain.MatchOverview;

public interface MatchDetail {

	public boolean didRadianWin();

	public int getDurationOfMatch();

	public MatchOverview getMatchOverview();

	public int getFirstBloodTime();

	public int getHumanPlayer();

	public int getLeagueId();

	public int getPositiveVotes();

	public int getNegativeVotes();

	public GameMode getGameMode();

	public List<MatchDetailPlayer> getPlayers();

}
