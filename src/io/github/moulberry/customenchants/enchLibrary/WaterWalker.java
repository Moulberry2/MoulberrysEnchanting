package io.github.moulberry.customenchants.enchLibrary;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import io.github.moulberry.customenchants.ApplicableItems;
import io.github.moulberry.customenchants.MoulberrysEnchanting;
import io.github.moulberry.customenchants.PowerWord;
import io.github.moulberry.customenchants.enchLibrary.base.Enchant;

public class WaterWalker extends Enchant{
	public void activateMiscEnchant(Player wearer, ItemStack item, int level){
		if(wearer.getLocation().getY()%1>0.05)return;
		Block b = wearer.getWorld().getBlockAt(wearer.getLocation());
		if((!b.getType().isSolid()) && (!b.isLiquid())){
			for(int i = -1; i < 2; i++) {
				for(int j = -1; j < 2; j++) {
					if(wearer.getWorld().getBlockAt(wearer.getLocation().add(i, -1, j)).getType().equals(Material.STATIONARY_WATER)){
						if(!wearer.isFlying()){
							boolean canFly = wearer.getAllowFlight();
							wearer.teleport(wearer.getLocation().add(0,0.1564-wearer.getLocation().getY()%1,0));
							wearer.setAllowFlight(true);
							wearer.setFlying(true);
							wearer.setFlySpeed(wearer.getWalkSpeed()/12*level);
							
							final Player wearer2 = wearer;
							final boolean canFly2 = canFly;
							Bukkit.getScheduler().scheduleSyncDelayedTask(MoulberrysEnchanting.getInstance(), new Runnable(){
								public void run(){
									if(Math.floor(wearer2.getLocation().getY()%1*10000) == 1564){ 
										if(wearer2.getWorld().getBlockAt(wearer2.getLocation()).getType().equals(Material.AIR)){
											for (int i = -1; i < 2; i++) {
												for (int j = -1; j < 2; j++) {
													if (wearer2.getWorld().getBlockAt(wearer2.getLocation().add(i,-1,j)).getType().equals(Material.STATIONARY_WATER)){
														Bukkit.getScheduler().scheduleSyncDelayedTask(MoulberrysEnchanting.getInstance(), this, 1L);
														return;
													}
												}
											}
										}
									} 
									
									try {
										wearer2.setFlying(canFly2);
										wearer2.setAllowFlight(canFly2);
										wearer2.setFlySpeed(0.1F);
									} catch (IllegalArgumentException localIllegalArgumentException) {}
								}
							}, 1L);
						}
						return;
					}
				}
			}
		}
	}
  
	public int getMaxLevel(){
		return 3;
	}
	  
	public ApplicableItems[] getApplicable(){
		ApplicableItems[] arr = { ApplicableItems.BOOTS };
		return arr;
	}
	  
	public PowerWord[] getPowerWords(){
		PowerWord[] arr = { PowerWord.WATER };
		return arr;
	}
	  
	public int getArcaneRarity(){
		return 160;
	}
}
