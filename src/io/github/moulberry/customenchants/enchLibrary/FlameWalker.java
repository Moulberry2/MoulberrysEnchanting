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

public class FlameWalker extends Enchant {

	public void activateMiscEnchant(Player wearer, ItemStack item, int level){
		if(!wearer.getLocation().add(0, -1, 0).getBlock().getType().isSolid())return;
		final Block b = wearer.getLocation().getBlock();
		if(b.getType().equals(Material.AIR)){
			b.setType(Material.FIRE);
			Bukkit.getScheduler().scheduleSyncDelayedTask(MoulberrysEnchanting.getInstance(), new Runnable() {
				public void run() {
					if(b.getType().equals(Material.FIRE))b.setType(Material.AIR);
				}
			}, 30*level);
		}
	}

	public int getArcaneRarity() {
		return 70;
	}

	public int getMaxLevel() {
		return 5;
	}

	public ApplicableItems[] getApplicable() {
		ApplicableItems[] arr = {ApplicableItems.BOOTS};
		return arr;
	}
	
	public PowerWord[] getPowerWords() {
		PowerWord[] arr = {PowerWord.FIRE, PowerWord.GROUND, PowerWord.CONTROL};
		return arr;
	}

}
