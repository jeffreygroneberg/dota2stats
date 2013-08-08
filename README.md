# Dota2Stats Java API Wrapper & Dota2Stats Command Line Tool

## About

This repository is offering a simple Java Wrapper to access most of the interesing stats provided by Valve's Web API. In addition to the wrapper itself you will also find an easy to use [**small command line tool**](#working-with-the-command-line-tool) that has been built on top of the Dota2Stats Java Wrapper.

Right now the API is capable to do the following things:

* **Search a player by name to get his account (steam)id** 
	
* **Get the most recent public match history**

* **Get the match history based on a filter that allowes to limit your search by an accound id, a date, ...**
	
* **Get all the data for specific match by its id**

* **Get aggregated values like AVERAGE KD, KDA RATIO, GPM, XPM, ... for a given amount of matches or by date.**
	* An example: 
    <pre>
    ---- KDA: 13.087734627831715
	---- KD: 1.0310679611650486
	---- Kills: 2124.0
	---- Average Kills: 7.08
	---- Deaths: 2060.0
	---- Average Deaths: 6.866666666666666
	---- Assists: 3617.0
	---- Average Assists: 12.056666666666667
	---- Last Hits: 25323.0
	---- Average Last Hits: 84.41
	---- Denies: 1538.0
	---- Average Denies: 5.126666666666667
	---- Average GPM: 325.0133333333333
	---- Average XPM: 414.47333333333336
	</pre>

*Bare in mind that this API has been hacked together within few hours so a few attributes (like items or units) of the objects you receive won't have a value! I will fill those attributes with values with every commit!!*

## Prerequisites

### Understand Valve's Web API

Before you start using this Java Wrapper you should first take a look a the great description from the [THINGS YOU SHOULD KNOW BEFORE STARTINGDEV](http://dev.dota2.com/showthread.php?t=58317) thread form the official developer forum powered by Valve. There you get a first feeling of which data you can accept and which parameters are available to limit the results of your calls.

### Obtain an API Key

As with every public API you need a key to consume it. Visit http://steamcommunity.com/dev/apikey and fill out the form. It does not matter what URL you are providing, but a good practice would be to take something unique related to yourself. 

## Use The API

### Initialize 

It is quite simple to get started with the Dota2Stats Library. You have two possibilites:

```java
// Initialize to work with a proxy
Dota2Stats stats = new Dota2StatsImpl("YOUR_API_KEY", "YOUR_PROXY_URL", [YOURPROXYPORT]);		
// Initialize without a proxy
Dota2Stats stats = new Dota2StatsImpl("YOUR_API_KEY");
```

If you are wondering why you have access to the implementation of the interface then be warned that a factory will come. :)
**But that's it all. Now you have access to all the functions! Nothing more to do!**

### Get the account id for a player name

```java

try {
	List<PlayerSearchResult> results = stats.searchByPlayerName("john doe");
    
    // Look at the results!
	for(PlayerSearchResult item : results)
		System.out.println(item);	
        
} catch (Dota2StatsAccessException e) {
		// Do something if an error occured			
}

```

### Get the 25 most recent matches

```java
try {

	MatchHistory history = stats.getMostRecentMatchHistory();
	List<MatchOverview> overviews = history.getMatchOverviews();
	
    // print all match overviews found
	for (MatchOverview match : overviews)
		System.out.println(match);

} catch (Dota2StatsAccessException e1) {
	// Do something if an error occurs
}

```

### Working with the filter (eg. get all matches for a specific account id)

With a *MatchHistoryFilter* it is possible to create some search parameters that are then used to access the WebAPI.
The match history filter offers the following functions to parameterize your request:

* forSkill(Skill)
* forGameMode(GameMode)
* forPlayerName(String)
* forAccountId(long)
* forDateMinimum(long)
* forDateMaximum(long)
* forMinimumPlayersNumber(int)
* forStartingMatchId(long)
* forMaximumNumberOfResults(int)

It is simple if you see the API call. Let's assume we want to have all matches for a player that has the accountid 1234 and only All Pick Games:

```java
try {
	MatchHistory history = stats.getMatchHistory(new MatchHistoryFilter().forAccountId(1234).forGameMode(GameMode.All_Pick));
	List<MatchOverview> overviews = history.getMatchOverviews();

	for (MatchOverview match : overviews)
		System.out.println(match);

} catch (Dota2StatsAccessException e1) {
	// Do something if an error occurs
}

```

### Get detailed information for a match and extract all player stats there

From the *MatchOverview* object you can obtain its specific match id. With this it is possible to get all the stats of this match.


```java
try {
		
	// Get the details for a given match id
	MatchDetail detail = stats.getMatchDetails(12345);
			
	// extract all player stats from this game
	List<MatchDetailPlayer> playersStatsOfTheMatch = detail.getPlayers();
			
	// Show 'em
	for(MatchDetailPlayer player : playersStatsOfTheMatch) 
		System.out.println(player);			
			
} catch (Dota2StatsAccessException e1) {
	// Do something if an error occurs
}
```

### Magic: Getting aggregated values for an account id to see average KD/KDA ratio, GPM/XPM per match and overall kills/deaths stats. 

```java
// using a date filter
PlayerStats playerStatsWithFilter = stats.getStats(123456,
	new MatchHistoryFilter().forDateMaximum(1349827200)
							.forDateMinimum(1349395200));

// just a number of recent matches
PlayerStats playerStatsByRecentNumberOfMatches = stats.getStats(123456, 300);

// print it!!
System.out.println(playerStatsWithFilter);
System.out.println(playerStatsByRecentNumberOfMatches);
```

**Keep in mind that this is a quite extensive network abuse and I really advice you not to use this function too often as Valve could just ban your API Key!!! Use with caution!!**

## Working with the command line tool

### Where is it?

The command line tool can be found in the export-cli-jar folder of this repository. 

### How to start it?

First of all you need an up to date Java version. Start your terminal (Unix) or cmd (Windows) and check if Java is available via 
```bash
java -version
```
If everything works fine you will see the current version of your installed JRE. If you can't see it, but you have installed Java on your system, you just have to add the JRE/bin folder to your %PATH% environment.
When everything is done, change your current working directory to that one, where you saved the dota2stats.jar file and simple start the tool with

```bash
java -jar dota2stats.jar
```

An interactive shell will greet you telling all the further steps you need to know to get your stats from the tool:

<pre>
usage: dota2stats <options>
______  _______________________    _______
(  __  \(  ___  \__   __(  ___  )  / ___   )
| (  \  | (   ) |  ) (  | (   ) |  \/   )  |
| |   ) | |   | |  | |  | (___) |      /   )
| |   | | |   | |  | |  |  ___  |    _/   /
| |   ) | |   | |  | |  | (   ) |   /   _/
| (__/  | (___) |  | |  | )   ( |  (   (__/\
(__________________________________________/
(  ____ \__   __(  ___  \__   __(  ____ \
| (    \/  ) (  | (   ) |  ) (  | (    \/
| (_____   | |  | (___) |  | |  | (_____
(_____  )  | |  |  ___  |  | |  (_____  )
.     ) |  | |  | (   ) |  | |        ) |
/\____) |  | |  | )   ( |  | |  /\____) |
\_______)  )_(  |/     \|  )_(  \_______)

 -dsince,--datesince <timestamp>   The date to aggregate all matches when
                                   combined with -s. FROM THE GIVEN DATE to your
                                   very first match. Combine with -dto to create
                                   a specific timeframe (-dto < -dsince)
                                   
 -dto,--dateto <timestamp>         The date to aggregate all matches when
                                   combined with -s. From NOW till the given
                                   date. Combine with -dsince to create a
                                   specific timeframe (-dto < -dsince)
                                   
 -f,--findplayer <name>            Returns a list of players with their
                                   corresponding steam id (32Bit). You can
                                   combine the search with the stats function
                                   (-stats, --getstats). Be aware that the first
                                   result will be taken to get the stats for if
                                   no additional account id has been given.
                                   
 -k,--key <apikey>                 Mandatory!! The API key to access valves
                                   stats service. Visit
                                   http://steamcommunity.com/dev/apikey and fill
                                   out the form to get one.
                                   
 -n,--number <numberofmatches>     The number of recent matches to be
                                   aggregated. (Ignored when -dto and/or -dsince
                                   are given)
                                   
 -pPort,--proxyPort <port>         The port to your proxy server
 -pUrl,--proxyUrl <url>            The url to your proxy server
 -s,--stats <accountid>            Aggregates stats and calculates averages (eg
                                   KDA/KD/GPM/...) for the player with the given

                                   account id. Please use -n to specify the
                                   number of matches to aggregate over. You can
                                   also limit the number of matches by date with

                                   -dsince and -dto.
Usage and examples
====================================
1. Find out the steamid of the player with the name 'Hans' accountid
# dota2stats -f Hans
===
2. Get all the stats of the recent 100 matches for a given accountid (123456)
with API key
# dota2stats -s 123456 -n 100 -k 1212121212121212
===
3. Get all the stats from the very first match to 2012/12/13 (as unix timestamp)

with API key
# dota2stats -s 123456 -dsince 1355356800 -k 1212121212121212
===
4. Get all the stats from NOW to 2012/12/13 (as unix timestamp) with API key
# dota2stats -s 123456 -dto 1355356800 -k 1212121212121212

</pre>
