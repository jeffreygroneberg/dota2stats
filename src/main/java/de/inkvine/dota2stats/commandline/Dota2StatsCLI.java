package de.inkvine.dota2stats.commandline;

import java.util.List;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import de.inkvine.dota2stats.Dota2Stats;
import de.inkvine.dota2stats.domain.filter.MatchHistoryFilter;
import de.inkvine.dota2stats.domain.playersearch.PlayerSearchResult;
import de.inkvine.dota2stats.domain.playerstats.PlayerStats;
import de.inkvine.dota2stats.exceptions.Dota2StatsAccessException;
import de.inkvine.dota2stats.impl.Dota2StatsImpl;

public class Dota2StatsCLI {

	private static final String OPTION_NAME_DATESINCE = "dsince";
	private static final String OPTION_NAME_DATETO = "dto";
	private static final String OPTION_NAME_NUMBER = "n";
	private static final String OPTION_NAME_APIKEY = "k";
	private static final String OPTION_NAME_STATS = "s";
	private static final String OPTION_NAME_FINDPLAYER = "f";
	private static final String OPTION_NAME_PROXYURL = "pUrl";
	private static final String OPTION_NAME_PROXYPORT = "pPort";

	public static void main(String[] args) {

		int width = 80;
		String syntax = "dota2stats" + " <options>";
//		String header = "*********************************\n"
//				+ "* _____    ___    _____    __   *\n"
//				+ "*||   \\\\ ||   || |=====|  /_\\\\  *\n"
//				+ "*||   || ||   ||   | |   /___\\\\ *\n"
//				+ "*||__//  ||___||   | |  //    \\\\*\n"
//				+ "*                               *\n"
//				+ "*             STATS             *\n"
//				+ "*********************************\n" + "\n" + "\n"
//				+ "=====================================";
		
		String header= 
				 "______  _______________________    _______    \n"+   
				 "(  __  \\(  ___  \\__   __(  ___  )  / ___   )  \n"+  
				 "| (  \\  | (   ) |  ) (  | (   ) |  \\/   )  |  \n"+ 
				 "| |   ) | |   | |  | |  | (___) |      /   )  \n"+
				 "| |   | | |   | |  | |  |  ___  |    _/   /   \n"+
				 "| |   ) | |   | |  | |  | (   ) |   /   _/    \n"+
				 "| (__/  | (___) |  | |  | )   ( |  (   (__/\\  \n"+
				 "(__________________________________________/  \n"+
				 "(  ____ \\__   __(  ___  \\__   __(  ____ \\     \n"+
				 "| (    \\/  ) (  | (   ) |  ) (  | (    \\/     \n"+
				 "| (_____   | |  | (___) |  | |  | (_____      \n"+
				 "(_____  )  | |  |  ___  |  | |  (_____  )     \n"+
				 ".     ) |  | |  | (   ) |  | |        ) |     \n"+
				 "/\\____) |  | |  | )   ( |  | |  /\\____) |     \n"+
				 "\\_______)  )_(  |/     \\|  )_(  \\_______)     \n\n\n";
		                                    

		String footer = "Usage and examples\n"
				+ "====================================\n"
				+ "1. Find out the steamid of the player with the name 'Hans' accountid\n"
				+ " # dota2stats -f Hans\n\n"
				+ "===\n"
				+ "2. Get all the stats of the recent 100 matches for a given accountid (123456) with API key\n"
				+ " # dota2stats -s 123456 -n 100 -k 1212121212121212\n\n"
				+ "===\n"
				+ "3. Get all the stats from the very first match to 2012/12/13 (as unix timestamp) with API key\n"
				+ " # dota2stats -s 123456 -dsince 1355356800 -k 1212121212121212\n\n"
				+ "===\n"
				+ "4. Get all the stats from NOW to 2012/12/13 (as unix timestamp) with API key\n"
				+ " # dota2stats -s 123456 -dto 1355356800 -k 1212121212121212\n\n";

		Dota2Stats dota2stats;

		CommandLineParser parser = new BasicParser();
		// create Options object
		Options options = new Options();
		
		createOptions(options);

		if (args == null || args.length == 0)
			new HelpFormatter().printHelp(width, syntax, header, options,
					footer, false);

		try {
			CommandLine cmd = parser.parse(options, args);	

			String proxyUrl = null;
			int proxyPort = 0;
			boolean hasProxy = false;

			if (cmd.hasOption(OPTION_NAME_PROXYPORT) && cmd.hasOption(OPTION_NAME_PROXYURL)) {

				proxyUrl = cmd.getOptionValue(OPTION_NAME_PROXYURL);
				proxyPort = Integer.parseInt(cmd.getOptionValue(OPTION_NAME_PROXYPORT));
				hasProxy = true;

			}

			if(hasProxy)
				dota2stats = new Dota2StatsImpl("", proxyUrl, proxyPort);
			else
				dota2stats = new Dota2StatsImpl("");
			
			if (cmd.hasOption(OPTION_NAME_FINDPLAYER)) {

				List<PlayerSearchResult> playerResults = null;
				try {
					playerResults = dota2stats
							.searchByPlayerName(cmd.getOptionValue(OPTION_NAME_FINDPLAYER));
				} catch (Dota2StatsAccessException e) {
					
					System.out.print("Error while searching for player: ");
					System.out.println(e.getMessage());
					return;
					
				}
				if (playerResults == null || playerResults.size() == 0)
					System.out
							.println("No players could be found for the given name.");
				else {
					System.out
							.println("Search results:\n--------------------------");
					for (PlayerSearchResult player : playerResults) {

						System.out.println(player);
						

					}
					return;
				}

			}

			if (cmd.hasOption(OPTION_NAME_STATS)) {

				long accountId = Long.parseLong(cmd.getOptionValue(OPTION_NAME_STATS));
				String apiKey;

				if (cmd.hasOption(OPTION_NAME_APIKEY)) {

					apiKey = cmd.getOptionValue(OPTION_NAME_APIKEY);

				} else {

					System.out
							.println("No API Key given. Use -k to provide your key!");
					return;
				}

				if(hasProxy)
					dota2stats = new Dota2StatsImpl(apiKey, proxyUrl, proxyPort);
				else
					dota2stats = new Dota2StatsImpl(apiKey);
				
				int numberOfMatches = -1;
				long dateTo = -1;
				long dateSince = -1;

				if (cmd.hasOption(OPTION_NAME_NUMBER))
					numberOfMatches = Integer.parseInt(cmd.getOptionValue(OPTION_NAME_NUMBER));

				if (cmd.hasOption(OPTION_NAME_DATETO))
					dateTo = Long.parseLong(cmd.getOptionValue(OPTION_NAME_DATETO));

				if (cmd.hasOption(OPTION_NAME_DATESINCE))
					dateSince = Long.parseLong(cmd.getOptionValue(OPTION_NAME_DATESINCE));

				PlayerStats stats = null;
				try {

					if (numberOfMatches != -1)
						stats = dota2stats.getStats(accountId, numberOfMatches);

					else {

						if (dateSince != -1 && dateTo == -1)
							stats = dota2stats.getStats(accountId,
									new MatchHistoryFilter()
											.forDateMaximum(dateSince));
						if (dateTo != -1 && dateSince == -1)
							stats = dota2stats.getStats(accountId,
									new MatchHistoryFilter()
											.forDateMinimum(dateTo));
						if (dateTo != -1 && dateSince != -1)
							stats = dota2stats.getStats(
									accountId,
									new MatchHistoryFilter().forDateMinimum(
											dateTo).forDateMaximum(dateSince));
					}

				} catch (Dota2StatsAccessException e) {

					System.out.print("Error while retrieving stats: ");
					System.out.println(e.getMessage());
					return;

				}

				System.out.println("Stats for the player:\n\n" + stats);

			}

		} catch (ParseException e) {

			System.out.println(e.getLocalizedMessage());
			new HelpFormatter().printHelp(width, syntax, header, options,
					footer, false);

		}

	}

	private static void createOptions(Options options) {
		// date_min
		options.addOption(OptionBuilder
				.withLongOpt("dateto")
				.withDescription(
						"The date to aggregate all matches when combined with -s. From NOW till the given date. Combine with -dsince to create a specific timeframe (-dto < -dsince)")
				.hasArg().withArgName("timestamp").create(OPTION_NAME_DATETO));

		// date_max
		options.addOption(OptionBuilder
				.withLongOpt("datesince")
				.withDescription(
						"The date to aggregate all matches when combined with -s. FROM THE GIVEN DATE to your very first match. Combine with -dto to create a specific timeframe (-dto < -dsince)")
				.hasArg().withArgName("timestamp").create(OPTION_NAME_DATESINCE));

		options.addOption(OptionBuilder
				.withLongOpt("findplayer")
				.withDescription(
						"Returns a list of players with their corresponding steam id (32Bit). You can combine the search with the stats function (-stats, --getstats). Be aware that the first result will be taken to get the stats for if no additional account id has been given.")
				.hasArg().withArgName("name").create(OPTION_NAME_FINDPLAYER));

		options.addOption(OptionBuilder
				.withLongOpt("stats")
				.withDescription(
						"Aggregates stats and calculates averages (eg KDA/KD/GPM/...) for the player with the given account id. Please use -n to specify the number of matches to aggregate over. You can also limit the number of matches by date with -dsince and -dto.")
				.hasArg().withArgName("accountid").create(OPTION_NAME_STATS));

		options.addOption(OptionBuilder
				.withLongOpt("number")
				.withDescription(
						"The number of recent matches to be aggregated. (Ignored when -dto and/or -dsince are given)")
				.hasArg().withArgName("numberofmatches").create(OPTION_NAME_NUMBER));

		options.addOption(OptionBuilder
				.withLongOpt("key")
				.withDescription(
						"Mandatory!! The API key to access valves stats service. Visit http://steamcommunity.com/dev/apikey and fill out the form to get one.")
				.hasArg().withArgName("apikey").create(OPTION_NAME_APIKEY));

		options.addOption(OptionBuilder.withLongOpt("proxyUrl")
				.withDescription("The url to your proxy server").hasArg()
				.withArgName("url").create(OPTION_NAME_PROXYURL));

		options.addOption(OptionBuilder.withLongOpt("proxyPort")
				.withDescription("The port to your proxy server").hasArg()
				.withArgName("port").create(OPTION_NAME_PROXYPORT));

	}
}
