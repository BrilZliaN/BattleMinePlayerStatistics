package ru.battlemine.playerstatistics;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class PlayerListener implements Listener {
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		Player dead = event.getEntity();
		
		if (dead.getKiller() != null) {
			PlayerStat deadStat = PlayerStatisticsAPI.getPlayerStat(dead.getName());
			deadStat.incrementDeath(); //
			PlayerStatisticsAPI.setPlayerStat(dead.getName(), deadStat);
			
			PlayerStat killerStat = PlayerStatisticsAPI.getPlayerStat(dead.getKiller().getName());
			killerStat.incrementKill(); //
			if (isDiamond(dead.getInventory())) {
				killerStat.incrementKillDiamond(); //
			}
			PlayerStatisticsAPI.setPlayerStat(dead.getKiller().getName(), killerStat);
		}
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		PlayerStat stat = PlayerStatisticsAPI.getPlayerStat(event.getPlayer().getName());
		if (stat.getTimeRegistered() == 0) stat.init();
		stat.updateLastPlayed();
		PlayerStatisticsAPI.setPlayerStat(event.getPlayer().getName(), stat);
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		PlayerStat stat = PlayerStatisticsAPI.getPlayerStat(event.getPlayer().getName());
		stat.calculateTimePlaying();
		PlayerStatisticsAPI.setPlayerStat(event.getPlayer().getName(), stat);
	}
	
	private boolean isDiamond(PlayerInventory inv) {
		for (ItemStack is : inv.getArmorContents()) {
			if (is.getType() != Material.DIAMOND_HELMET && is.getType() != Material.DIAMOND_CHESTPLATE 
					&& is.getType() != Material.DIAMOND_LEGGINGS && is.getType() != Material.DIAMOND_BOOTS) return false;
		}
		return true;
	}

}
