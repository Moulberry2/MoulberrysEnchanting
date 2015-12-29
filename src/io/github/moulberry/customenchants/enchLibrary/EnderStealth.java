package io.github.moulberry.customenchants.enchLibrary;

import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import io.github.moulberry.customenchants.ApplicableItems;
import io.github.moulberry.customenchants.PowerWord;
import io.github.moulberry.customenchants.enchLibrary.base.Enchant;
import io.github.moulberry.customenchants.utilities.Util;

public class EnderStealth extends Enchant {
	
	public void activateDefenceEnchant(Player defender, LivingEntity attacker, ItemStack armor, double damage, int level){
		if(defender.getHealth()<=damage && Util.randPercent(level)){
			defender.setHealth(defender.getHealth()+damage);
			int t=0;
			while(true){
				if(t++>=75)return;
				Block b1 = defender.getLocation().add(random.nextInt(level*2), random.nextInt(level*2), random.nextInt(level*2)).getBlock();
				Block h = b1.getLocation().getWorld().getHighestBlockAt(b1.getLocation()).getLocation().add(0,1,0).getBlock();
				if(b1.getLocation().getBlockY() > h.getLocation().getBlockY())b1 = h;
				Block b2 = b1.getLocation().add(0,1,0).getBlock();
				if(b1.isEmpty() || b1.isLiquid()){
					if(b2.isEmpty() || b2.isLiquid()){
						defender.teleport(b1.getLocation());
						defender.setVelocity(new Vector());
						return;
					}
				}
			}
		}
	}

	public int getArcaneRarity() {
		return 190;
	}

	public int getMaxLevel() {
		return 10;
	}

	public ApplicableItems[] getApplicable() {
		ApplicableItems[] arr = {ApplicableItems.HELMET, ApplicableItems.CHESTPLATE, ApplicableItems.LEGGINGS, ApplicableItems.BOOTS};
		return arr;
	}
	
	public PowerWord[] getPowerWords() {
		PowerWord[] arr = {PowerWord.DEFENCE, PowerWord.MIND, PowerWord.CONTROL, PowerWord.DIVERT};
		return arr;
	}

}
