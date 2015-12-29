package io.github.moulberry.customenchants.enchLibrary;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import io.github.moulberry.customenchants.ApplicableItems;
import io.github.moulberry.customenchants.PowerWord;
import io.github.moulberry.customenchants.customentities.CustomEntities;
import io.github.moulberry.customenchants.customentities.UndeadWreatheZombie;
import io.github.moulberry.customenchants.enchLibrary.base.Enchant;
import io.github.moulberry.customenchants.utilities.Util;
import net.minecraft.server.v1_8_R3.EntityLiving;

public class UndeadWreathe extends Enchant {
	
	Random rand = new Random();
	
	public void activateDefenceEnchant(Player defender, LivingEntity attacker, ItemStack armor, double damage, int level){
		if(Util.randPercent(2)){
			int amountOfZombies = rand.nextInt((int)Math.ceil(level/2)) + (int)Math.floor(level/2);
			
			int difference = 360/amountOfZombies;
			Location loc = attacker.getLocation();
			for(int i=0; i<amountOfZombies; i++){
				Location loc2 = loc.clone().add(Math.sin(Math.toRadians(difference*i))*3, 0, Math.cos(Math.toRadians(difference*i))*3);
				UndeadWreatheZombie zombie = new UndeadWreatheZombie(loc.getWorld(), (EntityLiving)((CraftEntity)attacker).getHandle());
				CustomEntities.spawnEntity(zombie, loc2);
			}
		}
	}

	public int getMaxLevel() {
		return 10;
	}

	public ApplicableItems[] getApplicable() {
		ApplicableItems[] arr = {ApplicableItems.HELMET, ApplicableItems.CHESTPLATE, ApplicableItems.LEGGINGS, ApplicableItems.BOOTS};
		return arr;
	}
	
	public PowerWord[] getPowerWords() {
		PowerWord[] arr = {PowerWord.ASSAULT, PowerWord.CONTROL, PowerWord.VOLATILITY};
		return arr;
	}
	
	public int getArcaneRarity() {
		return 340;
	}

}
