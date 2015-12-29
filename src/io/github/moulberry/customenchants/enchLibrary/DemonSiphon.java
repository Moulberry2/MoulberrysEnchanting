package io.github.moulberry.customenchants.enchLibrary;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Wither;
import org.bukkit.entity.Zombie;

import io.github.moulberry.customenchants.ApplicableItems;
import io.github.moulberry.customenchants.PowerWord;
import io.github.moulberry.customenchants.enchLibrary.base.Enchant;
import io.github.moulberry.customenchants.utilities.Util;

public class DemonSiphon extends Enchant {
	
	public void activateOffenseEnchant(Player attacker, LivingEntity defender, double damage, int level){
		if(defender instanceof Zombie || defender instanceof Skeleton || defender instanceof Wither){
			if(damage>=defender.getHealth() && Util.randPercent(25*level)){
				attacker.setHealth(Math.min(attacker.getHealth()+level/2+random.nextInt(level/2), attacker.getMaxHealth()));
				attacker.setFoodLevel(Math.min(attacker.getFoodLevel()+level/2+random.nextInt(level/2), 20));
			}
		}
	}
	
	public int getArcaneRarity() {
		return 280;
	}

	public int getMaxLevel() {
		return 4;
	}

	public ApplicableItems[] getApplicable() {
		ApplicableItems[] arr = {ApplicableItems.SWORD, ApplicableItems.AXE};
		return arr;
	}

	public PowerWord[] getPowerWords() {
		PowerWord[] arr = {PowerWord.HEALTH, PowerWord.ASSAULT, PowerWord.DAMAGE, PowerWord.DIVERT};
		return arr;
	}

}
