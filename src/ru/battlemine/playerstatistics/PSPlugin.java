package ru.battlemine.playerstatistics;

import org.bukkit.plugin.java.JavaPlugin;

public class PSPlugin extends JavaPlugin {
	
	@Override
	public void onEnable() {
		PlayerStatisticsAPI.load();
		
		getServer().getPluginManager().registerEvents(new PlayerListener(), this);
	}
	
	@Override
	public void onDisable() {
		PlayerStatisticsAPI.save();
	}

}
