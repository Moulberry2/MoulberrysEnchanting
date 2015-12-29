package io.github.moulberry.customenchants.enchLibrary;

import java.util.HashMap;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import io.github.moulberry.customenchants.ApplicableItems;
import io.github.moulberry.customenchants.CustomEnchants;
import io.github.moulberry.customenchants.PowerWord;
import io.github.moulberry.customenchants.enchLibrary.base.Enchant;
import io.github.moulberry.customenchants.utilities.Util;

public class DualWield extends Enchant {
	
	public void activateOffenseEnchant(Player attacker, LivingEntity defender, double damage, int level) {
		if(Util.randPercent(level*2)){
			defender.damage(damage);
			HashMap<Enchant, Integer> customEnchants = CustomEnchants.getCustomEnchants(attacker.getInventory().getItemInHand());
			for(Enchant ench : customEnchants.keySet()){
				if(ench.equals(this))continue;
				Integer level2 = customEnchants.get(ench);
				ench.activateOffenseEnchant(attacker, defender, damage, level2);
			}
		}
	}

	public int getArcaneRarity() {
		return 490;
	}

	public int getMaxLevel() {
		return 10;
	}

	public ApplicableItems[] getApplicable() {
		ApplicableItems[] arr = {ApplicableItems.SWORD};
		return arr;
	}
	
	public PowerWord[] getPowerWords() {
		PowerWord[] arr = {PowerWord.ASSAULT, PowerWord.DAMAGE, PowerWord.MIND, PowerWord.VOLATILITY};
		return arr;
	}

}
