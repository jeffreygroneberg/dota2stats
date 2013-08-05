dota2stats
==========

A simple Java library to get access to all the public dota 2 stats provided by Valve.

Please first read: <http://dev.dota2.com/showthread.php?t=58317> to understand what data you receive and how to obtain your secret API key.



```

Dota2Stats dota2stats = new Dota2StatsImpl("YOUR_SECRET_API_KEY");

// Search account id by player name
List<PlayerSearchResult> results = stats.searchByPlayerName("player_name");
	
for(PlayerSearchResult item : results)

    // print found players
    System.out.println(item);	
		
}		

// Get KD Ratio
double kdratio = stats.getKDRatio(accountId);	
System.out.println(kdratio);

        
```


