package io.github.moulberry.customenchants.enchLibrary;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import io.github.moulberry.customenchants.ApplicableItems;
import io.github.moulberry.customenchants.PowerWord;
import io.github.moulberry.customenchants.enchLibrary.base.Enchant;
import io.github.moulberry.customenchants.utilities.Util;

public class Nimble extends Enchant{

	public void activateMiscEnchant(Player wearer, ItemStack item, int level){
		Util.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 65, level-1), wearer);
	}
	
	public int getMaxLevel() {
		return 3;
	}

	public ApplicableItems[] getApplicable() {
		ApplicableItems[] arr = {ApplicableItems.SWORD, ApplicableItems.PICKAXE, ApplicableItems.AXE, ApplicableItems.HOE};
		return arr;
	}

	public PowerWord[] getPowerWords() {
		PowerWord[] arr = {PowerWord.SPEED, PowerWord.WIND};
		return arr;
	}
	
	public int getArcaneRarity() {
		return 265;
	}

}
