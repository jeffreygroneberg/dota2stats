package de.inkvine.dota2stats.domain.matchdetail;

import java.util.List;

import de.inkvine.dota2stats.domain.LeaverStatus;
import de.inkvine.dota2stats.domain.MatchOverviewPlayer;

public interface MatchDetailPlayer extends MatchOverviewPlayer {	
	
	public List<Item> getItems();

	public int getKills();

	public int getDeaths();

	public int getAssists();

	public LeaverStatus getLeaverStatus();

	public int getGold();

	public int getLastHits();

	public int getDenies();

	public int getGoldPerMinute();

	public int getXPPerMinute();

	public int getGoldSpent();

	public int getHeroDamageDealt();

	public int getTowerDamageDealt();

	public int getHeroDamageHealt();

	public int getHeroLevel();

	public int[] getSkilledAbilityOrder();
	// todo implement
	// additional_units - details about additional units controlled by the
	// player (i.e. lone druid's spirit bear)
	// unitname - the name of the unit
	// item_0 - the numeric ID of the item that player finished with in their
	// top-left slot.
	// item_1 - the numeric ID of the item that player finished with in their
	// top-center slot.
	// item_2 - the numeric ID of the item that player finished with in their
	// top-right slot.
	// item_3 - the numeric ID of the item that player finished with in their
	// bottom-left slot.
	// item_4 - the numeric ID of the item that player finished with in their
	// bottom-center slot.
	// item_5 - the numeric ID of the item that player finished with in their
	// bottom-right slot.

}
