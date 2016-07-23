package ru.battlemine.playerstatistics;

public class PlayerStat {
	
	private int deaths; //смертей
	private int kills; //убийств
	private int killsDiamond; //убийств алмазников
	private long timePlaying; //время игры в мс
	private long timeRegistered; //датавремя регистрации в мс
	private long lastPlayed; //последний раз в игре (время в мс)
	
	public PlayerStat() {
		deaths = 0;
		kills = 0;
		killsDiamond = 0;
		timePlaying = 0;
		timeRegistered = 0;
		lastPlayed = 0;
	}
	
	public void init() {
		timeRegistered = System.currentTimeMillis();
		lastPlayed = System.currentTimeMillis();
	}
	
	public int getDeaths() {
		return deaths;
	}
	
	public int getKills() {
		return kills;
	}
	
	public int getKillsDiamond() {
		return killsDiamond;
	}
	
	public long getTimePlaying() {
		return timePlaying;
	}
	
	public long getTimeRegistered() {
		return timeRegistered;
	}
	
	public long getLastPlayed() {
		return lastPlayed;
	}
	
	public void incrementDeath() {
		deaths++;
	}
	
	public void incrementKill() {
		kills++;
	}
	
	public void incrementKillDiamond() {
		killsDiamond++;
	}
	
	public void updateLastPlayed() {
		lastPlayed = System.currentTimeMillis();
	}
	
	public void calculateTimePlaying() {
		long current = System.currentTimeMillis();
		timePlaying += current - lastPlayed;
		lastPlayed = current;
	}
	
	public String serialize() {
		return PlayerStat.serialize(this);
	}
	
	public static PlayerStat deserialize(String string) throws Exception {
		try {
			String[] data = string.split(";");
			PlayerStat stat = new PlayerStat();
			stat.deaths = Integer.parseInt(data[0]);
			stat.kills = Integer.parseInt(data[1]);
			stat.killsDiamond = Integer.parseInt(data[2]);
			stat.timePlaying = Long.parseLong(data[3]);
			stat.timeRegistered = Long.parseLong(data[4]);
			stat.lastPlayed = Long.parseLong(data[5]);
			return stat;
		} catch (Exception e) {
			throw new Exception("Can't deserialize PlayerStat: " + string);
		}
	}
	
	public static String serialize(PlayerStat stat) {
		return stat.deaths + ";" + stat.kills + ";" + stat.killsDiamond + ";" + stat.timePlaying + ";" + stat.timeRegistered + ";" + stat.lastPlayed;
	}

}
