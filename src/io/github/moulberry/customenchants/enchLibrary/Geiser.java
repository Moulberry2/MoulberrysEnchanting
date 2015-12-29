package io.github.moulberry.customenchants.enchLibrary;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import io.github.moulberry.customenchants.ApplicableItems;
import io.github.moulberry.customenchants.PowerWord;
import io.github.moulberry.customenchants.enchLibrary.base.Enchant;
import io.github.moulberry.customenchants.utilities.Util;

public class Geiser extends Enchant{
	
	public void activateDefenceEnchant(Player defender, LivingEntity attacker, ItemStack armor, double damage, int level){
		if(Util.randPercent(4))attacker.setVelocity(new Vector(0, Math.min(level,5)/6+1.2, 0));
	}

	public int getMaxLevel() {
		return 5;
	}

	public ApplicableItems[] getApplicable() {
		ApplicableItems[] arr = {ApplicableItems.HELMET, ApplicableItems.CHESTPLATE, ApplicableItems.LEGGINGS, ApplicableItems.BOOTS};
		return arr;
	}
	
	public PowerWord[] getPowerWords() {
		PowerWord[] arr = {PowerWord.WIND, PowerWord.WATER, PowerWord.RANGED};
		return arr;
	}
	
	public int getArcaneRarity() {
		return 25;
	}

}
