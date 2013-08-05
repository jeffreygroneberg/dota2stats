package de.inkvine.dota2stats.domain;

public enum GameMode {

	All_Pick(1), Captains_Mode(2), Random_Draft(3), Single_Draft(4), All_Random(
			5), INTRO_DEATH(6), The_Diretide(7), Reverse_Captains_Mode(8), Greeviling(
			9), Tutorial(10), Mid_Only(11), Least_Played(12), New_PlayerPool(
			13);

	private final int value;

	private GameMode(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public GameMode getType(int value) {

		return GameMode.values()[value];

	}

}