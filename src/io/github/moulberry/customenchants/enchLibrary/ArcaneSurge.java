package io.github.moulberry.customenchants.enchLibrary;

import java.util.HashMap;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import io.github.moulberry.customenchants.ApplicableItems;
import io.github.moulberry.customenchants.CustomEnchants;
import io.github.moulberry.customenchants.PowerWord;
import io.github.moulberry.customenchants.enchLibrary.base.Enchant;
import io.github.moulberry.customenchants.utilities.Util;

public class ArcaneSurge extends Enchant{
	
	public void activateDefenceEnchant(Player defender, LivingEntity attacker, ItemStack armor, double damage, int level){
		if(Util.randPercent(level)){
			HashMap<Enchant, Integer> customEnchants = CustomEnchants.getCustomEnchants(armor);
			for(Enchant ench : customEnchants.keySet()){
				if(ench.equals(this))continue;
				int level2 = customEnchants.get(ench);
				ench.activateDefenceEnchant(defender, attacker, armor, damage, level2);
			}
		}
	}

	public int getArcaneRarity() {
		return 370;
	}

	public int getMaxLevel() {
		return 10;
	}

	public ApplicableItems[] getApplicable() {
		ApplicableItems[] arr = {ApplicableItems.ARMOR};
		return arr;
	}

	public PowerWord[] getPowerWords() {
		PowerWord[] arr = {PowerWord.DEFENCE, PowerWord.CONTROL, PowerWord.MIND, PowerWord.LUCK};
		return arr;
	}

}
