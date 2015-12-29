package io.github.moulberry.customenchants.utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import io.github.moulberry.customenchants.PowerWord;
import io.github.moulberry.customenchants.enchLibrary.base.Enchant;
import net.md_5.bungee.api.ChatColor;

public class ItemFactory {
	
	private static Random rand = new Random();
	
	public static enum ItemEnum{};
	public static enum Rarity{COMMON, UNCOMMON, RARE, EPIC, LEGENDARY};
	
	public static ItemStack blankPapyrus = ItemFactory
			.gen(Material.BOOK, 1, "Blank Papyrus", "Enchant at an enchanting table to", "inscript this with magic!");
	public static ItemStack protectionScroll = ItemFactory
			.gen(Material.PAPER, 1, "Protection Scroll", true, "Apply to a tool or armor piece to prevent", "it from breaking due to a failed enchant");
	public static ItemStack arcaneOverloadScroll = ItemFactory
			.gen(Material.PAPER, 1, "Arcane Overload Scroll", true, "Apply to a tool or armor piece to increase", "the arcane capacity of the item");
	public static ItemStack arcaneScrambleScroll = ItemFactory
			.gen(Material.PAPER, 1, "Arcane Scramble Scroll", true, "Rerolls the level, success rate and", "destroy rate of an enchantment scroll");
	
	public static ItemStack genEnchantedPapyrus(int energy){
		return ItemFactory.gen(Material.BOOK, 1, "Enchanted Papyrus",
				true,
				"Apply power words and arcane scrolls",
				"to increase the power of this book!",
				"Arcane Energy: "+energy);
	}
	
	public static ItemStack genArcaneScroll(int energy, String name){
		return ItemFactory.gen(Material.PAPER, 1, "Arcane Scroll", 
				true,
				"A magical scroll enchanted with the arcane",
				"energy of sorcerer " + name + "!",
				"There is " + ChatColor.DARK_RED + energy + ChatColor.RESET +
				" arcane energy in this scroll");
	}
	
	public static ItemStack genPowerWordScroll(PowerWord pw){
		return ItemFactory.gen(Material.PAPER, 1, "Power Word Scroll", 
				true,
				"A scroll capable of channeling the very essence",
				"of magic through an enchanted phrase!",
				"Drop onto an enchant book to apply the power word: ",
				ChatColor.DARK_RED+Util.readable(pw.toString())+" ("+Util.readable(pw.getLatinName()) + ")");
	}
	
	public static ItemStack genEnchantmentScroll(Enchant ench, int level){
		return ItemFactory.gen(Material.ENCHANTED_BOOK, 1, ench.getColour()+ChatColor.UNDERLINE+""+ChatColor.BOLD +ench.getName()+" "+Util.toRomanNumeral(level),
				ChatColor.GRAY + "" + "Enchantment Scroll",
				ChatColor.AQUA + "Arcane Energy: " + ench.getArcaneRarity(),
				ChatColor.GREEN + "Success Chance: " + rand.nextInt(100) +"%",
				ChatColor.RED + "Destroy Chance: " + rand.nextInt(100) +"%");
			}
	
	/**
	 * Converts Material -> ItemStack
	 */
	public static ItemStack i(Material mat){
		return new ItemStack(mat);
	}
	
	/**
	 * @param maxAmount The maximum amount (exclusive)
	 */
	public static ItemStack randAmount(Material mat, int maxAmount){
		return randAmount(mat, 1 ,maxAmount);
	}
	
	/**
	 * @param amount1 The minimum amount (inclusive)
	 * @param amount2 The maximum amount (exclusive)
	 */
	public static ItemStack randAmount(Material mat, int amount1, int amount2){
		return new ItemStack(mat, rand.nextInt(amount2-amount1)+amount1);
	}
	
	/**
	 * Generates an item from an amount, shorter to type than "new ItemStack(mat, amount)"
	 */
	public static ItemStack gen(Material mat, int amount){
		return new ItemStack(mat, amount);
	}
	
	/**
	 * Generates an item and automatically applies amount, name and lore!
	 * @param name Set this to null to keep default name!
	 */
	public static ItemStack gen(Material mat, int amount, String name, String... lore){
		return modify(i(mat), amount, name, lore);
	}
	
	public static ItemStack gen(Material mat, int amount, String name, boolean enchanted, String... lore){
		return modify(i(mat), amount, name, enchanted, lore);
	}
	
	/**
	 * Modifies an existing ItemStack to the given parameters!
	 * @param name Set this to null to keep default name!
	 * @param amount Set this to a -1 to keep existing amount!
	 */
	public static ItemStack modify(ItemStack itemStack, int amount, String name, String... lore){
		return modify(itemStack, amount, name, false, lore);
	}
	
	/**
	 * Modifies an existing ItemStack to the given parameters!
	 * @param name Set this to null to keep default name!
	 * @param amount Set this to a -1 to keep existing amount!
	 * @param enchanted Whether this item is enchanted
	 */
	public static ItemStack modify(ItemStack itemStack, int amount, String name, boolean enchanted, String... lore){
		ItemMeta itemMeta = itemStack.getItemMeta();
		
		List<String> lore2 = itemMeta.hasLore() ? itemMeta.getLore() : new ArrayList<String>();
		for(String str:lore)lore2.add(ChatColor.RESET + str);
		
		if(enchanted && !itemMeta.hasEnchants()){
			itemMeta.addEnchant(Enchantment.LURE, 1, true);
			itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		}
		
		if(name!=null && !name.equals(""))itemMeta.setDisplayName(ChatColor.RESET + name);
		itemMeta.setLore(lore2);
		if(amount>0)itemStack.setAmount(amount);
		itemStack.setItemMeta(itemMeta);
		
		return itemStack;
	}
}
