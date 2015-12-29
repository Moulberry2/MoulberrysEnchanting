package io.github.moulberry.customenchants.enchLibrary;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import io.github.moulberry.customenchants.ApplicableItems;
import io.github.moulberry.customenchants.MoulberrysEnchanting;
import io.github.moulberry.customenchants.PowerWord;
import io.github.moulberry.customenchants.enchLibrary.base.Enchant;

public class ArcaneRepair extends Enchant {
	
	public void activateItemBreakEnchant(Player wearer, ItemStack item, int level){
		final ItemStack itemf = item;
		Bukkit.getScheduler().scheduleSyncDelayedTask(MoulberrysEnchanting.getInstance(), new Runnable() {
			public void run() {
				ItemMeta meta = itemf.getItemMeta();
				meta.setLore(new ArrayList<String>());
				itemf.setItemMeta(meta);
					
				itemf.setDurability((short) 0);
			}
		}, 1);
	}

	public int getArcaneRarity() {
		return 35;
	}

	public int getMaxLevel() {
		return 5;
	}

	public ApplicableItems[] getApplicable() {
		ApplicableItems[] arr = {ApplicableItems.ARMOR};
		return arr;
	}
	
	public PowerWord[] getPowerWords() {
		PowerWord[] arr = {PowerWord.CONTROL, PowerWord.DEFENCE, PowerWord.MIND};
		return arr;
	}

}
