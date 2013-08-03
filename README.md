dota2stats
==========

A simple Java library to get access to all the public dota 2 stats provided by Valve.

Please first read: <http://dev.dota2.com/showthread.php?t=58317> to understand what data you receive and how to obtain your secret API key.



```

Dota2Stats dota2stats;
dota2stats = new Dota2StatsImpl("YOUR_SECRET_API_KEY");

MatchHistory hist = dota2stats.getMatchHistory(new MatchHistoryFilter()
    		.forAccountId(2138233));      
        
MatchDetail details = dota2stats.getMatchDetails(MATCH_ID);
        
```


