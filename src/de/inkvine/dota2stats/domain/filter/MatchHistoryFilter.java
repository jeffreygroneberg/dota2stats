package de.inkvine.dota2stats.domain.filter;

import java.util.ArrayList;
import java.util.List;

import de.inkvine.dota2stats.domain.GameMode;

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


	public MatchHistoryFilter forSkill(final Skill skill) {

		criterias.add(new QueryFilterCriteria() {

			@Override
			public Object value() {

				return skill.getValue();

			}

			@Override
			public String queryName() {

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
