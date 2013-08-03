package de.inkvine.dota2stats.result.getmatchhistory;

import java.util.List;

public class GetMatchHistoryResult {

	private MatchHistoryResult result;

	public MatchHistoryResult getResult() {
		return result;
	}

	public void setResult(MatchHistoryResult result) {
		this.result = result;
	}

	public static class MatchHistoryResult {

		private int status;
		private int num_results;
		private int total_results;
		private int results_remaining;
		private List<Match> matches;

		public int getStatus() {
			return status;
		}

		public void setStatus(int status) {
			this.status = status;
		}

		public int getNum_results() {
			return num_results;
		}

		public void setNum_results(int num_results) {
			this.num_results = num_results;
		}

		public int getTotal_results() {
			return total_results;
		}

		public void setTotal_results(int total_results) {
			this.total_results = total_results;
		}

		public int getResults_remaining() {
			return results_remaining;
		}

		public void setResults_remaining(int results_remaining) {
			this.results_remaining = results_remaining;
		}

		public List<Match> getMatches() {
			return matches;
		}

		public void setMatches(List<Match> matches) {
			this.matches = matches;
		}

		public static class Match {

			private long match_seq_num;
			private long start_time;
			private int lobby_type;
			private long match_id;
			private List<Player> players;

			public List<Player> getPlayers() {
				return players;
			}

			public void setPlayers(List<Player> players) {
				this.players = players;
			}

			public long getMatch_id() {
				return match_id;
			}

			public void setMatch_id(long match_id) {
				this.match_id = match_id;
			}

			public long getMatch_seq_num() {
				return match_seq_num;
			}

			public void setMatch_seq_num(long match_seq_num) {
				this.match_seq_num = match_seq_num;
			}

			public long getStart_time() {
				return start_time;
			}

			public void setStart_time(long start_time) {
				this.start_time = start_time;
			}

			public int getLobby_type() {
				return lobby_type;
			}

			public void setLobby_type(int lobby_type) {
				this.lobby_type = lobby_type;
			}

			public static class Player {

				private long account_id;

				public long getAccount_id() {
					return account_id;
				}

				public void setAccount_id(long account_id) {
					this.account_id = account_id;
				}

				public int getPlayer_slots() {
					return player_slots;
				}

				public void setPlayer_slots(int player_slots) {
					this.player_slots = player_slots;
				}

				public int getHero_id() {
					return hero_id;
				}

				public void setHero_id(int hero_id) {
					this.hero_id = hero_id;
				}

				private int player_slots;
				private int hero_id;

			}

		}

	}

}
