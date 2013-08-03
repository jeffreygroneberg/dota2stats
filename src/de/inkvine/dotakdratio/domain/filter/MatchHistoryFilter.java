package de.inkvine.dotakdratio.domain.filter;

import java.util.ArrayList;
import java.util.List;

public class MatchHistoryFilter {

	private List<QueryFilterCriteria> criterias = new ArrayList();

	public enum Skill {

		Normal(0), High(1), Very_High(2);

		private final int value;

		private Skill(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}

		public Skill getType(int value) {

			return Skill.values()[value];

		}

	}

	public static enum GameMode {

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

	public MatchHistoryFilter forSkill(final Skill skill) {

		criterias.add(new QueryFilterCriteria() {

			@Override
			public Object value() {

				return skill.getValue();

			}

			@Override
			public String queryName() {
				// TODO Auto-generated method stub
				return "skill";
			}
		});

		return this;

	}

	public MatchHistoryFilter forGameMode(final GameMode mode) {

		criterias.add(new QueryFilterCriteria() {

			@Override
			public Object value() {

				return mode.getValue();

			}

			@Override
			public String queryName() {
				// TODO Auto-generated method stub
				return "game_mode";
			}
		});

		return this;

	}

	public MatchHistoryFilter forPlayerName(final String id) {

		criterias.add(new QueryFilterCriteria() {

			@Override
			public Object value() {

				return id;

			}

			@Override
			public String queryName() {
				// TODO Auto-generated method stub
				return "player_name";
			}
		});

		return this;

	}

	public MatchHistoryFilter forAccountId(final long id) {

		criterias.add(new QueryFilterCriteria() {

			@Override
			public Object value() {

				return id;

			}

			@Override
			public String queryName() {
				// TODO Auto-generated method stub
				return "account_id";
			}
		});

		return this;

	}

	public MatchHistoryFilter forDateMinimum(final long timeStamp) {

		criterias.add(new QueryFilterCriteria() {

			@Override
			public Object value() {

				return timeStamp;

			}

			@Override
			public String queryName() {
				// TODO Auto-generated method stub
				return "date_min";
			}
		});

		return this;

	}

	public MatchHistoryFilter forDateMaximum(final long timeStamp) {

		criterias.add(new QueryFilterCriteria() {

			@Override
			public Object value() {

				return timeStamp;

			}

			@Override
			public String queryName() {
				// TODO Auto-generated method stub
				return "date_max";
			}
		});

		return this;

	}

	public MatchHistoryFilter forMinimumPlayersNumber(final int number) {

		criterias.add(new QueryFilterCriteria() {

			@Override
			public Object value() {

				return number;

			}

			@Override
			public String queryName() {
				// TODO Auto-generated method stub
				return "date_max";
			}
		});

		return this;

	}

	public MatchHistoryFilter forStartingMatchId(final int id) {

		criterias.add(new QueryFilterCriteria() {

			@Override
			public Object value() {

				return id;

			}

			@Override
			public String queryName() {
				// TODO Auto-generated method stub
				return "start_at_match_id";
			}
		});

		return this;

	}

	public MatchHistoryFilter forMaximumNumberOfResults(final int number) {

		criterias.add(new QueryFilterCriteria() {

			@Override
			public Object value() {

				return number;

			}

			@Override
			public String queryName() {
				// TODO Auto-generated method stub
				return "matches_requested";
			}
		});

		return this;

	}

	public List<QueryFilterCriteria> getCriterias() {
		return criterias;
	}

	public void setCriterias(List<QueryFilterCriteria> criterias) {
		this.criterias = criterias;
	}

}
