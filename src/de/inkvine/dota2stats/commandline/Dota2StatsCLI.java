package de.inkvine.dota2stats.commandline;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Dota2StatsCLI {
	
	public static void main(String[] args) {
		
		CommandLineParser parser = new BasicParser();
		// create Options object
		Options options = new Options();

		// add t option
		options.addOption("t", false, "display current time");
	
		try {
			CommandLine cmd = parser.parse( options, args);
		} catch (ParseException e) {
			// automatically generate the help statement
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp( "dota2stats", options );
		}
		
	}

}
