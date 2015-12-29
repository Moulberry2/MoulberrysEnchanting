package io.github.moulberry.customenchants;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;

public enum ApplicableItems {
	HELMET(Material.LEATHER_HELMET, Material.GOLD_HELMET, Material.IRON_HELMET, Material.CHAINMAIL_HELMET, Material.DIAMOND_HELMET),
	CHESTPLATE(Material.LEATHER_CHESTPLATE, Material.GOLD_CHESTPLATE, Material.IRON_CHESTPLATE, Material.CHAINMAIL_CHESTPLATE, Material.DIAMOND_CHESTPLATE),
	LEGGINGS(Material.LEATHER_LEGGINGS, Material.GOLD_LEGGINGS, Material.IRON_LEGGINGS, Material.CHAINMAIL_LEGGINGS, Material.DIAMOND_LEGGINGS),
	BOOTS(Material.LEATHER_BOOTS, Material.GOLD_BOOTS, Material.IRON_BOOTS, Material.CHAINMAIL_BOOTS, Material.DIAMOND_BOOTS),
	SWORD(Material.WOOD_SWORD, Material.GOLD_SWORD, Material.IRON_SWORD, Material.DIAMOND_SWORD),
	AXE(Material.WOOD_AXE, Material.GOLD_AXE, Material.IRON_AXE, Material.DIAMOND_AXE),
	PICKAXE(Material.WOOD_PICKAXE, Material.GOLD_PICKAXE, Material.IRON_PICKAXE, Material.DIAMOND_PICKAXE),
	SHOVEL(Material.WOOD_SPADE, Material.GOLD_SPADE, Material.IRON_SPADE, Material.DIAMOND_SPADE),
	HOE(Material.WOOD_HOE, Material.GOLD_HOE, Material.IRON_HOE, Material.DIAMOND_HOE),
	BOW(Material.BOW),
	ALL(HELMET, CHESTPLATE, LEGGINGS, BOOTS, SWORD, AXE, PICKAXE, SHOVEL, HOE, BOW),
	MELEE(SWORD, AXE),
	TOOLS(PICKAXE, SHOVEL, HOE),
	ARMOR(HELMET, CHESTPLATE, LEGGINGS, BOOTS);
	
	private List<Material> materials = new ArrayList<Material>();
	
	private ApplicableItems(Material... items){
		for(Material mat : items)materials.add(mat);
	}
	
	private ApplicableItems(ApplicableItems... items){
		for(ApplicableItems applicable : items){
			for(Material mat : applicable.getMaterials()){
				materials.add(mat);
			}
		}
	}
	
	public List<Material> getMaterials(){
		return materials;
	}
		
}