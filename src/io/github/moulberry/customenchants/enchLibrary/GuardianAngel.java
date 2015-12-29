package io.github.moulberry.customenchants.enchLibrary;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Player;

import io.github.moulberry.customenchants.ApplicableItems;
import io.github.moulberry.customenchants.PowerWord;
import io.github.moulberry.customenchants.customentities.CustomEntities;
import io.github.moulberry.customenchants.customentities.GuardianAngelBlaze;
import io.github.moulberry.customenchants.enchLibrary.base.AbilityEnchant;
import net.minecraft.server.v1_8_R3.EntityLiving;

public class GuardianAngel extends AbilityEnchant{

	public void activateAbilityEnchant(Player p, int level) {
		CustomEntities.spawnEntity(new GuardianAngelBlaze(p.getWorld(), (EntityLiving)((CraftEntity)p).getHandle()), p.getLocation().add(0, 3, 0));
	}

	public int getMaxLevel() {
		return 10;
	}

	public ApplicableItems[] getApplicable() {
		ApplicableItems[] arr = {ApplicableItems.CHESTPLATE};
		return arr;
	}

	public PowerWord[] getPowerWords() {
		PowerWord[] arr = {PowerWord.FIRE, PowerWord.RANGED, PowerWord.CONTROL};
		return arr;
	}
	
	public int getArcaneRarity() {
		return 465;
	}

}
