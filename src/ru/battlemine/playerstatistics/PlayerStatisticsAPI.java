package ru.battlemine.playerstatistics;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.Map.Entry;

public class PlayerStatisticsAPI {
	
	private static Map<String, PlayerStat> playerStats;
	
	private static boolean init = false;
	
	public static PlayerStat getPlayerStat(String name) {
		if (!init) return null;
		PlayerStat stat = playerStats.get(name);
		if (stat == null) {
			stat = new PlayerStat();
			playerStats.put(name, stat);
		}
		return stat;
	}
	
	public static void setPlayerStat(String name, PlayerStat stat) {
		if (!init) return;
		
		playerStats.put(name, stat);
	}
	
	public static Map<String, PlayerStat> getAllStatistics() {
		return playerStats;
	}
	
	public static void load() {
		if (init) return;
		
		playerStats = new TreeMap<String, PlayerStat>(String.CASE_INSENSITIVE_ORDER);
		
		File db = new File("plugins/BattleMine/playerstats.db");
		if (!db.exists()) {
			init = true;
			return;
		}
		try (Scanner scanner = new Scanner(new FileReader(db))) {
			while (scanner.hasNextLine()) {
				String info = scanner.nextLine();
				String[] inf = info.split("=");
				try {
					playerStats.put(inf[0], PlayerStat.deserialize(inf[1]));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("[BattleMinePlayerStatistics] Loaded playerstats.db: " + playerStats.size() + " entries");
		
		init = true;
	}
	
	public static void save() {
		if (!init) return;
		
		try {
			File db = new File("plugins/BattleMine/playerstats.db");
			if (!db.exists()) db.createNewFile();
			
			PrintWriter writer = new PrintWriter("plugins/BattleMine/playerstats.db", "UTF-8");
			for (Entry<String, PlayerStat> stat : playerStats.entrySet()) {
				writer.println(stat.getKey() + "=" + stat.getValue().serialize());
			}
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("[BattleMinePlayerStatistics] Saved playerstats.db");
	}
	
}
