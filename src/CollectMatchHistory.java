import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.inkvine.dota2stats.Dota2Stats;
import de.inkvine.dota2stats.domain.MatchOverview;
import de.inkvine.dota2stats.domain.heros.HeroInfo;
import de.inkvine.dota2stats.domain.matchdetail.MatchDetail;
import de.inkvine.dota2stats.domain.matchdetail.MatchDetailPlayer;
import de.inkvine.dota2stats.domain.matchhistory.MatchHistory;
import de.inkvine.dota2stats.exceptions.Dota2StatsAccessException;
import de.inkvine.dota2stats.impl.Dota2StatsImpl;

public class CollectMatchHistory {

    public static void main(String[] args) throws Exception {
        if (args.length != 3) {
            System.out.println("Usage: <dev key> <match level "
                            + "(0:all 1:low 2:medium 3:high)> <with KDA (\"true\" or \"false\")>");
            System.out
                    .println("Note: the software will write every 300 match histories to disk.");
            System.exit(0);
        }
        // player level
        int level = Integer.parseInt(args[1]);
        
        // whether to receive KDA info
        boolean withKDA = false;
        if ("true".equals(args[2]))
            withKDA = true;
        
        // buffer size
        int Buffer_Size = 300;
        
        // open client with dev key
        Dota2Stats client = new Dota2StatsImpl(args[0]);

        // build output buffer
        String matchItem = null;
        Map<Long, String> matchBuf = new HashMap<Long, String>();

        // get heros
        int heroCount = client.GetHeroes().getHeroesCount();
        List<HeroInfo> heroList = client.GetHeroes().getHeroes();

        System.out.println("Heroes count:" + heroCount);
        for (HeroInfo oneHero : heroList) {
            int id = oneHero.getHeroID();
            String name = oneHero.getHeroName();

            System.out.println(name + ":" + id);
        }

        // match history collection
        while (true) {

            try {
                MatchHistory history = client.getMatchHistoryWithLevel(level);
                List<MatchOverview> matches = history.getMatchOverviews();

                int dupItem = 0, newItem = 0;
                
                // retrieve the details about each match history obtained
                MATCHLOOP: for (MatchOverview match : matches) {
                    long matchID = match.getMatchId();

                    // request details for a given match id
                    MatchDetail detail = client.getMatchDetails(matchID);
                    
                    if (detail.getHumanPlayer() != 10)
                        continue;

                    if (matchBuf.containsKey(matchID)) {
                        dupItem++;
                        continue;
                    }

                    matchItem = "ID:" + matchID;

                    boolean radiantWin = detail.didRadianWin();
                    if (radiantWin)
                        matchItem += " WIN:radiant";
                    else
                        matchItem += " WIN:dire";

                    // player details
                    List<MatchDetailPlayer> players = detail.getPlayers();

                    for (MatchDetailPlayer player : players) {
                        int slot = player.getPlayerSlots();
                        int heroID = player.getHeroId();
                        
                        if (heroID == 0) {
                            System.out.println();
                            continue MATCHLOOP;
                        }

                        String team = (slot >> 7 != 0) ? "dire" : "radiant";
                        matchItem += " HERO:" + heroID + ":" + team;
                        
                        // with KDA
                        if (withKDA) {
                            int[] KDA = {0,0,0};
                            KDA[0] = player.getKills();
                            KDA[1] = player.getDeaths();
                            KDA[2] = player.getAssists();
                            
                            matchItem += ":KDA:" + KDA[0] + "_" + KDA[1] + "_" + KDA[2];
                        }

                    }
                    matchItem += "\n";
                    System.out.println(matchItem);

                    matchBuf.put(matchID, matchItem);
                    newItem++;

                    // write to file if buffer is full
                    if (matchBuf.size() >= Buffer_Size) {
                        try {
                            WriteToDisk(matchBuf);
                            matchBuf.clear();
                            System.out.println("\nCong: cache is written to file!");
                            Thread.sleep(1000);
                            continue;
                        } catch (Exception e) {
                            // TODO: handle exception
                            System.out
                                    .println("ERROR: write to file! continue!");
                            continue;
                        }
                    }

                }

                System.out.println("\nLOOP OVER:\n dup item=" + dupItem
                        + ", new item=" + newItem + ", dup rate:"
                        + ((double) dupItem / (dupItem + newItem))
                        + ", BufSize=" + matchBuf.size());
                if ((double) dupItem / (dupItem + newItem) > 0.5)
                    Thread.sleep(1000 * 60 * 5);
                else if ((double) dupItem / (dupItem + newItem) > 0.4)
                    Thread.sleep(1000 * 60 * 4);
                else
                    Thread.sleep(1000 * 60 * 3);

            } catch (Dota2StatsAccessException e1) {
                // Do something if an error occurs
                Thread.sleep(1000 * 60);
                continue;
            }
        }

    }

    public static void WriteToDisk(Map<Long, String> matchBuf)
            throws IOException {
        
        // select output filename
        File file = new File("./");
        String[] filenames = file.list();
        int maxid = 0;
        for (String name : filenames) {
            if (name.startsWith("match_data_")) {
                int id = Integer.parseInt(name.split("_")[2]);
                if (id > maxid)
                    maxid = id;
            }
        }
        String name = "match_data_" + (maxid+1);
        // open output file
        BufferedWriter br = null;
        
        try {
            br = new BufferedWriter(new FileWriter(new File(name)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (long id : matchBuf.keySet()) {
            br.write(matchBuf.get(id));
        }

        br.close();
    }
}
