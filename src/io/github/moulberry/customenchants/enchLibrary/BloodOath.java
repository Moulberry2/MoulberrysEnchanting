package io.github.moulberry.customenchants.enchLibrary;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import io.github.moulberry.customenchants.ApplicableItems;
import io.github.moulberry.customenchants.PowerWord;
import io.github.moulberry.customenchants.enchLibrary.base.AbilityEnchant;

public class BloodOath extends AbilityEnchant {

	public int getArcaneRarity() {
		return 385;
	}

	public int getMaxLevel() {
		return 4;
	}

	public ApplicableItems[] getApplicable() {
		ApplicableItems[] arr = {ApplicableItems.CHESTPLATE};
		return arr;
	}
	
	public PowerWord[] getPowerWords() {
		PowerWord[] arr = {PowerWord.DAMAGE, PowerWord.HEALTH, PowerWord.VOLATILITY};
		return arr;
	}

	public void activateAbilityEnchant(Player p, int level) {
		p.setHealth(Math.max(p.getHealth()-p.getMaxHealth()/2, 1));
		p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100, level));
	}

}
