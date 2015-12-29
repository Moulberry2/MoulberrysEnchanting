package io.github.moulberry.customenchants.enchLibrary;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import io.github.moulberry.customenchants.ApplicableItems;
import io.github.moulberry.customenchants.PowerWord;
import io.github.moulberry.customenchants.enchLibrary.base.Enchant;
import io.github.moulberry.customenchants.utilities.Util;

public class Pyromaniac extends Enchant {
	
	public void activateMiscEnchant(Player wearer, ItemStack item, int level){
		if(wearer.getFireTicks()>0){
			Util.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 65, 0), wearer);
			Util.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 65, 0), wearer);
		}
	}

	public int getArcaneRarity() {
		return 130;
	}

	public int getMaxLevel() {
		return 2;
	}

	public ApplicableItems[] getApplicable() {
		ApplicableItems[] arr = {ApplicableItems.HELMET, ApplicableItems.CHESTPLATE, ApplicableItems.LEGGINGS, ApplicableItems.BOOTS};
		return arr;
	}
	
	public PowerWord[] getPowerWords() {
		PowerWord[] arr = {PowerWord.FIRE, PowerWord.DEFENCE};
		return arr;
	}

}
