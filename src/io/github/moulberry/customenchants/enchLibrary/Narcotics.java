package io.github.moulberry.customenchants.enchLibrary;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import io.github.moulberry.customenchants.ApplicableItems;
import io.github.moulberry.customenchants.PowerWord;
import io.github.moulberry.customenchants.enchLibrary.base.Enchant;
import io.github.moulberry.customenchants.utilities.Util;

public class Narcotics extends Enchant {

	public void activateOffenseEnchant(Player attacker, LivingEntity defender, double damage, int level) {
		if(Util.randPercent(2+level/2)){
			defender.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 300, 10));
			defender.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 300, 3));
			defender.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 300, 3));
			
			if(level>1){
				defender.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 300, 2));
				defender.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 300, 2));
			}
			
			if(level>3)defender.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 300, 10));
		}
	}
	
	public int getMaxLevel() {
		return 4;
	}

	public ApplicableItems[] getApplicable() {
		ApplicableItems[] arr = {ApplicableItems.AXE, ApplicableItems.SWORD};
		return arr;
	}
	
	public PowerWord[] getPowerWords() {
		PowerWord[] arr = {PowerWord.POISON, PowerWord.ASSAULT, PowerWord.HEALTH, PowerWord.MIND};
		return arr;
	}
	
	public int getArcaneRarity() {
		return 280;
	}

}
