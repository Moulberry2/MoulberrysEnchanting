package io.github.moulberry.customenchants.enchLibrary;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import io.github.moulberry.customenchants.ApplicableItems;
import io.github.moulberry.customenchants.MoulberrysEnchanting;
import io.github.moulberry.customenchants.PowerWord;
import io.github.moulberry.customenchants.enchLibrary.base.Enchant;

public class Endure extends Enchant {
	
	public void activateItemBreakEnchant(Player wearer, ItemStack item, int level){
		wearer.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 900, 8+2*level));
		wearer.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 300, 2));
		final ItemStack itemf = item;
		Bukkit.getScheduler().scheduleSyncDelayedTask(MoulberrysEnchanting.getInstance(), new Runnable() {
			public void run() {
				itemf.setType(Material.AIR);
			}
		}, 1);
	}

	public int getMaxLevel() {
		return 3;
	}

	public ApplicableItems[] getApplicable() {
		ApplicableItems[] arr = {ApplicableItems.HELMET};
		return arr;
	}

	public PowerWord[] getPowerWords() {
		PowerWord[] arr = {PowerWord.HEALTH, PowerWord.MIND, PowerWord.DEFENCE};
		return arr;
	}

	public int getArcaneRarity() {
		return 90;
	}

}
