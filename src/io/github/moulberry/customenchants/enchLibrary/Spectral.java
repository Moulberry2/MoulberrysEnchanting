package io.github.moulberry.customenchants.enchLibrary;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import io.github.moulberry.customenchants.ApplicableItems;
import io.github.moulberry.customenchants.PowerWord;
import io.github.moulberry.customenchants.enchLibrary.base.Enchant;
import io.github.moulberry.customenchants.utilities.Util;

public class Spectral extends Enchant{
	
	public void activateMiscEnchant(Player wearer, ItemStack item, int level){
		if(Util.randPercent(0.0625f)){
			wearer.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 20+10*level, 1));
			wearer.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20+10*level, 10));
			wearer.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 10+10*level, 1));
			Util.sendActionMessage(wearer, "You feel a strong force in the spectral realm! [Spectral " + Util.toRomanNumeral(level) + "]");
		}
	}

	public int getArcaneRarity() {
		return 360;
	}

	public int getMaxLevel() {
		return 5;
	}

	public ApplicableItems[] getApplicable() {
		ApplicableItems[] arr = {ApplicableItems.HELMET, ApplicableItems.CHESTPLATE, ApplicableItems.LEGGINGS, ApplicableItems.BOOTS};
		return arr;
	}

	public PowerWord[] getPowerWords() {
		PowerWord[] arr = {PowerWord.DEFENCE, PowerWord.DIVERT};
		return arr;
	}

}
