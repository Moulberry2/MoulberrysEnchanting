package io.github.moulberry.customenchants.enchLibrary.base;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import io.github.moulberry.customenchants.MoulberrysEnchanting;

public abstract class AbilityEnchant extends Enchant{
	
	private static HashMap<UUID, Integer> cooldowns = new HashMap<UUID, Integer>();
	
	public static int getCooldownFor(Player p){
		return cooldowns.get(p.getUniqueId());
	}
	
	public static boolean isAbilityReady(Player p){
		return !cooldowns.containsKey(p.getUniqueId());
	}
	
	public static void setCooldown(Player p, int cooldownSeconds){
		cooldowns.put(p.getUniqueId(), cooldownSeconds);
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(MoulberrysEnchanting.getInstance(), new Runnable(){
			final UUID uuid = p.getUniqueId();
			public void run() {
				cooldowns.remove(uuid);
			}	
		}, cooldownSeconds*20);
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(MoulberrysEnchanting.getInstance(), new Runnable(){
			final UUID uuid = p.getUniqueId();
			public void run() {
				if(!cooldowns.containsKey(uuid))return;
				cooldowns.put(uuid, cooldowns.get(uuid)-1);
				Bukkit.getScheduler().scheduleSyncDelayedTask(MoulberrysEnchanting.getInstance(), this, 20);
			}	
		}, 20);
	}
	
	public abstract void activateAbilityEnchant(Player p, int level);

}
