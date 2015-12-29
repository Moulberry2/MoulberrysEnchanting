package io.github.moulberry.customenchants.enchLibrary;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import de.slikey.effectlib.effect.WarpEffect;
import de.slikey.effectlib.util.DynamicLocation;
import de.slikey.effectlib.util.ParticleEffect;
import io.github.moulberry.customenchants.ApplicableItems;
import io.github.moulberry.customenchants.MoulberrysEnchanting;
import io.github.moulberry.customenchants.PowerWord;
import io.github.moulberry.customenchants.enchLibrary.base.Enchant;
import io.github.moulberry.customenchants.utilities.Util;

public class Entangle extends Enchant{
	
	@SuppressWarnings("deprecation")
	public void activateOffenseEnchant(Player attacker, LivingEntity defender, double damage, int level){
		if(Util.randPercent(3*level)){
			if(defender instanceof Player){
				final Block b1 = defender.getLocation().getBlock();
				final Block b2 = defender.getEyeLocation().getBlock();
				final LivingEntity defenderf = defender;
				
				if(b1.getType().equals(Material.AIR))((Player)defenderf).sendBlockChange(defender.getLocation(), Material.WEB, (byte)0);
				if(b2.getType().equals(Material.AIR))((Player)defenderf).sendBlockChange(defender.getEyeLocation(), Material.WEB, (byte)0);
				
				Bukkit.getScheduler().scheduleSyncDelayedTask(MoulberrysEnchanting.getInstance(), new Runnable() {
					public void run() {
						((Player) defenderf).sendBlockChange(b1.getLocation(), b1.getType(), b1.getData());
						((Player) defenderf).sendBlockChange(b2.getLocation(), b2.getType(), b2.getData());
					}
				}, 60);
			} else {
				defender.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 50, 5));
			}
			
			defender.setVelocity(new Vector());
			
			final WarpEffect eff = new WarpEffect(MoulberrysEnchanting.em);
			eff.particle = ParticleEffect.CLOUD;
			eff.setDynamicOrigin(new DynamicLocation(defender.getLocation()));
			eff.start();
		}
	}

	public int getArcaneRarity() {
		return 175;
	}

	public int getMaxLevel() {
		return 3;
	}

	public ApplicableItems[] getApplicable() {
		ApplicableItems[] arr = {ApplicableItems.BOW};
		return arr;
	}

	public PowerWord[] getPowerWords() {
		PowerWord[] arr = {PowerWord.ASSAULT, PowerWord.RANGED, PowerWord.CONTROL};
		return arr;
	}

}
