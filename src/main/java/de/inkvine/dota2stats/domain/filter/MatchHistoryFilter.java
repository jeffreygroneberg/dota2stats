package de.inkvine.dota2stats.domain.filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.inkvine.dota2stats.domain.GameMode;

public class MatchHistoryFilter {

	private Map<String, QueryFilterCriteria> criterias = new HashMap<String, QueryFilterCriteria>();

	public enum Skill {

		Any(0), Normal(1), High(2), Very_High(3);

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

		criterias.put("skill", new QueryFilterCriteria() {

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

		criterias.put("game_mode", new QueryFilterCriteria() {

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

		criterias.put("player_name", new QueryFilterCriteria() {

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

		criterias.put("account_id", new QueryFilterCriteria() {

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

		criterias.put("date_min", new QueryFilterCriteria() {

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

		criterias.put("date_max", new QueryFilterCriteria() {

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

		criterias.put("min_players", new QueryFilterCriteria() {

			@Override
			public Object value() {

				return number;

			}

			@Override
			public String queryName() {

				return "min_players";

			}
		});

		return this;

	}

	public MatchHistoryFilter forStartingMatchId(final long id) {

		criterias.put("start_at_match_id", new QueryFilterCriteria() {

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

		criterias.put("matches_requested", new QueryFilterCriteria() {

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
		return new ArrayList<QueryFilterCriteria>(criterias.values());
	}

	public void setCriterias(Map<String, QueryFilterCriteria> criterias) {
		this.criterias = criterias;
	}

}
