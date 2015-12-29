package io.github.moulberry.customenchants.enchLibrary;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import io.github.moulberry.customenchants.ApplicableItems;
import io.github.moulberry.customenchants.PowerWord;
import io.github.moulberry.customenchants.enchLibrary.base.Enchant;
import io.github.moulberry.customenchants.utilities.Util;

public class DemonShuffle extends Enchant {
	
	public void activateOffenseEnchant(Player attacker, LivingEntity defender, double damage, int level){
		if(Util.randPercent(0.3f*level)){
			for(PotionEffect pe : defender.getActivePotionEffects()){
				PotionEffectType t = pe.getType();
				if(t.equals(PotionEffectType.INCREASE_DAMAGE)){
					defender.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, pe.getDuration(), pe.getAmplifier()));
				} else if(t.equals(PotionEffectType.SPEED)){
					defender.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, pe.getDuration(), pe.getAmplifier()));
				} else if(t.equals(PotionEffectType.REGENERATION)){
					defender.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, pe.getDuration(), pe.getAmplifier()));
				} else if(t.equals(PotionEffectType.FAST_DIGGING)){
					defender.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, pe.getDuration(), pe.getAmplifier()));
				} else if(t.equals(PotionEffectType.NIGHT_VISION)){
					defender.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, pe.getDuration(), pe.getAmplifier()));
					continue;
				} else if(t.equals(PotionEffectType.ABSORPTION) || t.equals(PotionEffectType.DAMAGE_RESISTANCE) || t.equals(PotionEffectType.FIRE_RESISTANCE)
						|| t.equals(PotionEffectType.INVISIBILITY) || t.equals(PotionEffectType.JUMP) || t.equals(PotionEffectType.WATER_BREATHING)
						|| t.equals(PotionEffectType.SATURATION)){
				} else {
					continue;
				}
				defender.removePotionEffect(t);
			}
		}
	}

	public int getArcaneRarity() {
		return 435;
	}

	public int getMaxLevel() {
		return 3;
	}

	public ApplicableItems[] getApplicable() {
		ApplicableItems[] arr = {ApplicableItems.MELEE};
		return arr;
	}
	
	public PowerWord[] getPowerWords() {
		PowerWord[] arr = {PowerWord.CONTROL, PowerWord.ASSAULT, PowerWord.MIND, PowerWord.DESTROY};
		return arr;
	}

}
