package de.inkvine.dota2stats.domain;

import java.util.List;

public interface Match {

	public List<Player> getPlayers();

	public double getMatchId();

	public double getMatchSeqNumber();

	public long getStartTime();

	public double getLobbyType();

}
