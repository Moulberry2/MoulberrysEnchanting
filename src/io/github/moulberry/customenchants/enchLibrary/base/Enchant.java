package io.github.moulberry.customenchants.enchLibrary.base;

import java.util.Random;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import io.github.moulberry.customenchants.ApplicableItems;
import io.github.moulberry.customenchants.PowerWord;
import io.github.moulberry.customenchants.utilities.Util;
import net.md_5.bungee.api.ChatColor;

public abstract class Enchant {
	
	public static Random random = new Random();
	
	public void activateOffenseEnchant(Player attacker, LivingEntity defender, double damage, int level){}
	public void activateDefenceEnchant(Player defender, LivingEntity attacker, ItemStack armor, double damage, int level){}
	public void activateMiscEnchant(Player wearer, ItemStack item, int level){}
	public void activateItemBreakEnchant(Player wearer, ItemStack item, int level){}
	
	public String getName(){
		return this.getClass().getSimpleName();
	}
	
	public String getColour(){
		int i = getArcaneRarity();
		if(i>=400){
			return ChatColor.GOLD+"";
		} else if(i>=300){
			return ChatColor.DARK_PURPLE+"";
		} else if(i>=200){
			return ChatColor.BLUE+"";
		} else if(i>=100){
			return ChatColor.GREEN+"";
		} else {
			return ChatColor.GRAY+"";
		}
	}
	
	public String getReadable(int level){
		return getColour() + getName() + " " + Util.toRomanNumeral(level);
	}
	
	public boolean isApplicable(ItemStack item){
		for(ApplicableItems items : getApplicable()){
			if(items.getMaterials().contains(item.getType()))return true;
		}
		return false;
	}
	
	public boolean canApplyTo(ItemStack item){
		if(!isApplicable(item))return false;
		if(item.hasItemMeta() && item.getItemMeta().hasLore()){
			for(String lore : item.getItemMeta().getLore()){
				if(ChatColor.stripColor(lore.split(" ")[0]).equalsIgnoreCase(ChatColor.stripColor(getReadable(1).split(" ")[0]))){
					return false;
				}
			}
		}
		return true;
	}
	
	public abstract int getArcaneRarity();
	public abstract int getMaxLevel();
	public abstract ApplicableItems[] getApplicable();
	public abstract PowerWord[] getPowerWords();

}
