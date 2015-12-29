package io.github.moulberry.customenchants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import io.github.moulberry.customenchants.enchLibrary.ArcaneRepair;
import io.github.moulberry.customenchants.enchLibrary.ArcaneSurge;
import io.github.moulberry.customenchants.enchLibrary.BloodOath;
import io.github.moulberry.customenchants.enchLibrary.DemonShuffle;
import io.github.moulberry.customenchants.enchLibrary.DemonSiphon;
import io.github.moulberry.customenchants.enchLibrary.DualWield;
import io.github.moulberry.customenchants.enchLibrary.EnderStealth;
import io.github.moulberry.customenchants.enchLibrary.Endure;
import io.github.moulberry.customenchants.enchLibrary.Entangle;
import io.github.moulberry.customenchants.enchLibrary.FlameWalker;
import io.github.moulberry.customenchants.enchLibrary.Geiser;
import io.github.moulberry.customenchants.enchLibrary.GuardianAngel;
import io.github.moulberry.customenchants.enchLibrary.Narcotics;
import io.github.moulberry.customenchants.enchLibrary.Nimble;
import io.github.moulberry.customenchants.enchLibrary.ObsidianSkin;
import io.github.moulberry.customenchants.enchLibrary.Pyromaniac;
import io.github.moulberry.customenchants.enchLibrary.Spectral;
import io.github.moulberry.customenchants.enchLibrary.UndeadWreathe;
import io.github.moulberry.customenchants.enchLibrary.WaterWalker;
import io.github.moulberry.customenchants.enchLibrary.base.AbilityEnchant;
import io.github.moulberry.customenchants.enchLibrary.base.Enchant;
import io.github.moulberry.customenchants.utilities.DistributedRandomNumberGenerator;
import io.github.moulberry.customenchants.utilities.ItemFactory;
import io.github.moulberry.customenchants.utilities.Util;
import net.md_5.bungee.api.ChatColor;

public class CustomEnchants implements Listener{
	
	private static Random rand = new Random();//
	
	private static LinkedHashMap<String, Enchant> registeredEnchants = new LinkedHashMap<>();
	private static DistributedRandomNumberGenerator pwDRNG = new DistributedRandomNumberGenerator();
	static {
		/** REGISTERING ENCHANTS **/
		registerEnchant(new Geiser());
		registerEnchant(new Endure());
		registerEnchant(new WaterWalker());
		registerEnchant(new Narcotics());
		registerEnchant(new UndeadWreathe());
		registerEnchant(new GuardianAngel());
		registerEnchant(new Nimble());
		registerEnchant(new Spectral());
		registerEnchant(new Entangle());
		registerEnchant(new ArcaneSurge());
		registerEnchant(new ArcaneRepair());
		registerEnchant(new DemonSiphon());
		registerEnchant(new DemonShuffle());
		registerEnchant(new BloodOath());
		registerEnchant(new FlameWalker());
		registerEnchant(new ObsidianSkin());
		registerEnchant(new Pyromaniac());
		registerEnchant(new EnderStealth());
		registerEnchant(new DualWield());

		orderRegisteredEnchants();
		
		/** REGISTERING POWERWORD RANDOM GENERATOR **/
		for(PowerWord pw : PowerWord.values()){
			pwDRNG.addWeight(pw.ordinal(), pw.getArcanePower());
		}
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(MoulberrysEnchanting.getInstance(), new Runnable(){
			public void run() {
				try{
					for(World world : Bukkit.getWorlds()){
						for(Player p : world.getPlayers()){
							for(ItemStack armor : p.getInventory().getArmorContents()){
								HashMap<Enchant, Integer> customEnchants = getCustomEnchants(armor);
								for(Enchant ench : customEnchants.keySet()){
									Integer level = customEnchants.get(ench);
									ench.activateMiscEnchant(p, armor, level);
								}
							}
							if(p.getInventory().getItemInHand().getType().equals(Material.ENCHANTED_BOOK))return;
							HashMap<Enchant, Integer> customEnchants = getCustomEnchants(p.getInventory().getItemInHand());
							for(Enchant ench : customEnchants.keySet()){
								Integer level = customEnchants.get(ench);
								ench.activateMiscEnchant(p, p.getInventory().getItemInHand(), level);
							}
						}
					}
				}catch(Exception e){}
			}
		}, 1l, 1l);
	}
	
	public static void registerEnchant(Enchant ench){
		registeredEnchants.put(ench.getName().toLowerCase().trim(), ench);
	}
	
	public static void orderRegisteredEnchants(){
		Collection<Enchant> c = registeredEnchants.values();
		List<Enchant> list = new ArrayList<Enchant>(c);
		Collections.sort(list, new Comparator<Enchant>( ){
			public int compare(Enchant e1, Enchant e2) {
				return new Integer(e1.getArcaneRarity()).compareTo(e2.getArcaneRarity());
			}			
		} );
		
		registeredEnchants.clear();
		for(Enchant e : list){
			registerEnchant(e);
		}
	}
	
	public static HashMap<String, Enchant> getRegisteredEnchants(){
		return registeredEnchants;
	}
	
	public static ItemStack addEnchantToItem(Enchant enchant, ItemStack item, int level){
		ItemMeta meta = item.getItemMeta();
		List<String> lore = meta.getLore()==null ? new ArrayList<String>() : meta.getLore();
		lore.add(enchant.getReadable(level));
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	
	public static HashMap<Enchant, Integer> getCustomEnchants(ItemStack item){
		HashMap<Enchant, Integer> enchants = new HashMap<Enchant, Integer>();
		if(item==null)return enchants;
		if(!item.hasItemMeta())return enchants;
		if(!item.getItemMeta().hasLore())return enchants;
		for(String lore : item.getItemMeta().getLore()){
			if(lore.split(" ").length != 2)continue;
			
			try{
				Enchant e=registeredEnchants.get(ChatColor.stripColor(lore.trim().split(" ")[0]).toLowerCase().trim());
				enchants.put(e, Util.fromRomanNumeral(lore.split(" ")[1]));
			} catch(Exception e){
				continue;
			}
		}
		return enchants;
	}
	
	@EventHandler
	public static void onPlayerAttack(EntityDamageByEntityEvent e){
		Entity damager = e.getDamager();
		if(damager instanceof Projectile)damager=(Entity)((Projectile)damager).getShooter();
			
		if(damager instanceof Player){
			Player p = (Player)damager;
			
			if(p.getInventory().getItemInHand() == null) return;
			HashMap<Enchant, Integer> customEnchants = getCustomEnchants(p.getInventory().getItemInHand());
			for(Enchant ench : customEnchants.keySet()){
				Integer level = customEnchants.get(ench);
				ench.activateOffenseEnchant(p, (LivingEntity)e.getEntity(), e.getDamage(), level);
			}
			
		}
		
		if(e.getEntity() instanceof Player){
			Player p = (Player)e.getEntity();
			
			for(ItemStack armor : p.getInventory().getArmorContents()){
				HashMap<Enchant, Integer> customEnchants = getCustomEnchants(armor);
				for(Enchant ench : customEnchants.keySet()){
					int level = customEnchants.get(ench);
					ench.activateDefenceEnchant(p, (LivingEntity)damager, armor, e.getDamage(), level);
				}
			}
		}
	}
	
	@EventHandler
	public static void onPlayerEnchant(EnchantItemEvent e){
		if(e.getItem().getItemMeta()!=null &&
				e.getItem().getItemMeta().getDisplayName()
				.equals(ItemFactory.blankPapyrus.getItemMeta().getDisplayName())){
			int xp = e.getExpLevelCost();
			
			if(Util.randPercent(65)){
				double energy=Math.pow(xp, 2)/18;
				energy+=rand.nextInt((int)Math.ceil(Math.pow(xp, 2.3)/60)+5);
				energy=Math.min(energy, 100);
				energy=Math.max(energy, 2);
				
				final ItemStack arcaneScroll = ItemFactory.genArcaneScroll((int)energy, e.getEnchanter().getDisplayName());
				final EnchantItemEvent ev = e;
				Bukkit.getScheduler().scheduleSyncDelayedTask(MoulberrysEnchanting.getInstance(), new Runnable() {
					public void run() {
						ev.getItem().setType(arcaneScroll.getType());
						ev.getItem().setItemMeta(arcaneScroll.getItemMeta());
					}
				});
			} else {
				PowerWord pw = PowerWord.values()[pwDRNG.nextInteger()];
				final ItemStack powerWordScroll = ItemFactory.genPowerWordScroll(pw);
				final EnchantItemEvent ev = e;
				Bukkit.getScheduler().scheduleSyncDelayedTask(MoulberrysEnchanting.getInstance(), new Runnable() {
					public void run() {
						ev.getItem().setType(powerWordScroll.getType());
						ev.getItem().setItemMeta(powerWordScroll.getItemMeta());
					}
				});
			}
		}
	}
	
	@EventHandler
	public static void onInventoryClick(InventoryClickEvent e){
		try{
			ItemStack cursor = e.getCursor().clone();
			ItemStack item = e.getCurrentItem();

			if(cursor.getType().equals(Material.AIR))return;
			if(item.getType().equals(Material.AIR))return;
			
			/**
			 * Applying power word to an enchanted papyrus
			 */
			if(item.hasItemMeta() &&
				item.getItemMeta().hasDisplayName() &&
				item.getItemMeta().getDisplayName()
				.equals(ChatColor.RESET + "Enchanted Papyrus")){
				if(cursor.getItemMeta().getDisplayName()
					.equals(ChatColor.RESET + "Power Word Scroll")){
					if(e.getAction().equals(InventoryAction.PLACE_ALL) ||
							e.getAction().equals(InventoryAction.SWAP_WITH_CURSOR)){
						if(item.getAmount()>1){
							e.getWhoClicked().sendMessage("You can't apply that to more than one book!");
						} else {
						
							String loreToAdd = cursor.getItemMeta().getLore().get(3);
							
							ItemMeta meta = item.getItemMeta();
							List<String> lore = meta.getLore();
							
							if(lore.contains(loreToAdd)){
								e.getWhoClicked().sendMessage("That book already has that power word!");
							} else {
								lore.add(loreToAdd);
								meta.setLore(lore);
								item.setItemMeta(meta);
								
								e.setCancelled(true);
								e.getView().setCursor(null);
								e.setCurrentItem(null);
								e.getWhoClicked().setItemOnCursor(null);

								if(cursor.getAmount()>1){
									cursor.setAmount(cursor.getAmount()-1);
									e.getWhoClicked().getInventory().addItem(cursor);
									e.getWhoClicked().closeInventory();
								}
								
								e.getView().setItem(e.getRawSlot(), item);
							}
						}
					}
				}	
			}
			
			/**
			 * Applying arcane energy to a blank papyrus, making it an enchanted papyrus
			 */
			if(item.hasItemMeta() &&
				item.getItemMeta().hasDisplayName() &&
				item.getItemMeta().getDisplayName()
				.equals(ItemFactory.blankPapyrus.getItemMeta().getDisplayName())){
				if(cursor.getItemMeta().getDisplayName()
					.equals(ChatColor.RESET + "Arcane Scroll")){
						if(e.getAction().equals(InventoryAction.PLACE_ALL) ||
							e.getAction().equals(InventoryAction.SWAP_WITH_CURSOR)){
							if(item.getAmount()>1){
								e.getWhoClicked().sendMessage("You can't apply that to more than one book!");
							} else {

								e.setCancelled(true);
								e.getView().setCursor(null);
								e.setCurrentItem(null);
								e.getWhoClicked().setItemOnCursor(null);
								
								ItemStack item2;
								if((e.isLeftClick() && e.getWhoClicked().getGameMode().equals(GameMode.SURVIVAL)) || cursor.getAmount()==1){
									item2 = ItemFactory.genEnchantedPapyrus(Integer.parseInt(ChatColor.stripColor(cursor.getItemMeta().getLore().get(2)).split(" ")[2])*cursor.getAmount());
								} else {
									item2 = ItemFactory.genEnchantedPapyrus(Integer.parseInt(ChatColor.stripColor(cursor.getItemMeta().getLore().get(2)).split(" ")[2]));
									cursor.setAmount(cursor.getAmount()-1);
									e.getWhoClicked().getInventory().addItem(cursor);
									e.getWhoClicked().closeInventory();
								}
								
								e.getView().setItem(e.getRawSlot(), item2);
							}
						}
					}	
			}
			
			/**
			 * Applying arcane energy to a enchanted papyrus, giving it more arcane energy
			 */
			if(item.hasItemMeta() &&
				item.getItemMeta().hasDisplayName() &&
				item.getItemMeta().getDisplayName()
				.equals(ChatColor.RESET + "Enchanted Papyrus")){
				if(cursor.getItemMeta().getDisplayName()
					.equals(ChatColor.RESET + "Arcane Scroll")){
						if(e.getAction().equals(InventoryAction.PLACE_ALL) ||
							e.getAction().equals(InventoryAction.SWAP_WITH_CURSOR)){
							if(item.getAmount()>1){
								e.getWhoClicked().sendMessage("You can't apply that to more than one book!");
							} else {
								int arcaneEnergyPapyrus = Integer.parseInt(item.getItemMeta().getLore().get(2).split(" ")[2]);
								int arcaneEnergyScroll;
								e.setCancelled(true);
								e.getView().setCursor(null);
								e.setCurrentItem(null);
								e.getWhoClicked().setItemOnCursor(null);
								
								if((e.isLeftClick() && e.getWhoClicked().getGameMode().equals(GameMode.SURVIVAL)) || cursor.getAmount()==1){
									arcaneEnergyScroll = Integer.parseInt(ChatColor.stripColor(cursor.getItemMeta().getLore().get(2)).split(" ")[2])*cursor.getAmount();
								} else {
									arcaneEnergyScroll = Integer.parseInt(ChatColor.stripColor(cursor.getItemMeta().getLore().get(2)).split(" ")[2]);	
									cursor.setAmount(cursor.getAmount()-1);
									e.getWhoClicked().getInventory().addItem(cursor);
									e.getWhoClicked().closeInventory();
								}
								
								e.getView().setItem(e.getRawSlot(), ItemFactory.genEnchantedPapyrus(arcaneEnergyPapyrus + arcaneEnergyScroll));
							}
						}
					}	
			}
			
			/**
			 * Applying arcane scramble scroll to an enchanted book
			 */
			if(item.hasItemMeta() &&
				item.getItemMeta().hasLore() &&
				item.getItemMeta().getLore().get(0)
				.equals(ChatColor.RESET.toString()+ChatColor.GRAY + "Enchantment Scroll")){
				if(cursor.getItemMeta().getDisplayName().equals(ItemFactory.arcaneScrambleScroll.getItemMeta().getDisplayName())){
						if(e.getAction().equals(InventoryAction.PLACE_ALL) ||
							e.getAction().equals(InventoryAction.SWAP_WITH_CURSOR)){
							
							e.setCancelled(true);
							e.getView().setCursor(null);
							e.setCurrentItem(null);
							e.getWhoClicked().setItemOnCursor(null);
								
							Enchant ench = registeredEnchants.get(ChatColor.stripColor(item.getItemMeta().getDisplayName().split(" ")[0].toLowerCase()));
							
							e.getView().setItem(e.getRawSlot(), ItemFactory.genEnchantmentScroll(ench, rand.nextInt(ench.getMaxLevel())+1));
						}
					}	
			}
			
			/**
			 * Applying protection scroll to an item
			 */
			if(cursor.hasItemMeta() &&
				cursor.getItemMeta().hasDisplayName() &&
				cursor.getItemMeta().getDisplayName()
				.equals(ItemFactory.protectionScroll.getItemMeta().getDisplayName())){
				if(e.getAction().equals(InventoryAction.PLACE_ALL) ||
					e.getAction().equals(InventoryAction.SWAP_WITH_CURSOR)){
					if(!ApplicableItems.ALL.getMaterials().contains(item.getType()))return;
					ItemMeta meta = item.getItemMeta();
					List<String> lore = meta.getLore()==null?new ArrayList<String>():meta.getLore();
					
					if(lore.size()==0?false:lore.get(lore.size()-1).equals(ChatColor.WHITE+""+ChatColor.BOLD+"PROTECTED")){
						e.getWhoClicked().sendMessage("That item already is already protected!");
					} else {
						lore.add(ChatColor.WHITE+""+ChatColor.BOLD+"PROTECTED");
						meta.setLore(lore);
						item.setItemMeta(meta);
							
						e.setCancelled(true);
						e.getView().setCursor(null);
						e.setCurrentItem(null);
						e.getWhoClicked().setItemOnCursor(null);
							
						if(cursor.getAmount()>1){
							cursor.setAmount(cursor.getAmount()-1);
							e.getWhoClicked().getInventory().addItem(cursor);
							e.getWhoClicked().closeInventory();
						}
						e.getView().setItem(e.getRawSlot(), item);
					}
				}
			}
			
			/**
			 * Applying arcane overload scroll to an item
			 */
			if(cursor.hasItemMeta() &&
				cursor.getItemMeta().hasDisplayName() &&
				cursor.getItemMeta().getDisplayName()
				.equals(ItemFactory.arcaneOverloadScroll.getItemMeta().getDisplayName())){
				if(e.getAction().equals(InventoryAction.PLACE_ALL) ||
					e.getAction().equals(InventoryAction.SWAP_WITH_CURSOR)){
					if(!ApplicableItems.ALL.getMaterials().contains(item.getType()))return;
					ItemMeta meta = item.getItemMeta();
					List<String> lore = meta.getLore()==null?new ArrayList<String>():meta.getLore();
					
					boolean isProtected = lore.size()==0?false:lore.get(lore.size()-1).equals(ChatColor.WHITE+""+ChatColor.BOLD+"PROTECTED");
					
					String[] arr = ChatColor.stripColor(lore.get(lore.size()-1-(isProtected?1:0))).split(" ");
					int a = Integer.parseInt(arr[2].split("/")[0]);
					int b = Integer.parseInt(arr[2].split("/")[1]);
					if(b+100>1500){
						e.getWhoClicked().sendMessage("The maximum arcane capacity for that item is 1500!");
						return;
					}
					
					if(lore.size()>0 && ChatColor.stripColor(lore.get(Math.max(lore.size()-1-(isProtected?1:0), 0))).toLowerCase().startsWith("arcane capacity: ")){
						lore.set(lore.size()-1-(isProtected?1:0), ChatColor.RESET + "Arcane Capacity: " + ChatColor.RED + a + ChatColor.GRAY + "/" + ChatColor.RED + (b+100));	
					} else {	
						lore.add(Math.max(lore.size()-1, 0), ChatColor.RESET + "Arcane Capacity: " + ChatColor.RED + 0 + ChatColor.GRAY + "/" + ChatColor.RED + "1300");
					}
					
					meta.setLore(lore);
					item.setItemMeta(meta);
					
					e.setCancelled(true);
					e.getView().setCursor(null);
					e.setCurrentItem(null);
					e.getWhoClicked().setItemOnCursor(null);
							
					if(cursor.getAmount()>1){
						cursor.setAmount(cursor.getAmount()-1);
						e.getWhoClicked().getInventory().addItem(cursor);
						e.getWhoClicked().closeInventory();
					}
					
					e.getView().setItem(e.getRawSlot(), item);
				}
			}	
			
			/**
			 * Applying an enchant to an item
			 */
			if(cursor.hasItemMeta() &&
				cursor.getItemMeta().hasLore() &&
				cursor.getItemMeta().getLore().get(0)
				.equals(ChatColor.RESET.toString()+ChatColor.GRAY + "Enchantment Scroll")){
				if(e.getAction().equals(InventoryAction.PLACE_ALL) ||
					e.getAction().equals(InventoryAction.SWAP_WITH_CURSOR)){
					Enchant e3 = registeredEnchants.get(ChatColor.stripColor(cursor.getItemMeta().getDisplayName().split(" ")[0].toLowerCase()));
					String loreToAdd = e3.getColour()+ChatColor.stripColor(cursor.getItemMeta().getDisplayName());
					if(!e3.canApplyTo(item)){
						if(!e3.isApplicable(item)){
							e.getWhoClicked().sendMessage("That enchant cannot be applied to that item!");
						} else {
							e.getWhoClicked().sendMessage("That item already has that enchant!");
						}
					} else {
						ItemMeta meta = item.getItemMeta();
						List<String> lore = meta.getLore()==null?new ArrayList<String>():meta.getLore();
						
						boolean isProtected = lore.size()==0?false:lore.get(lore.size()-1).equals(ChatColor.WHITE+""+ChatColor.BOLD+"PROTECTED");

						int a=0, b=0;
						if(lore.size()>0){
							String[] arr = ChatColor.stripColor(lore.get(lore.size()-1-(isProtected?1:0))).split(" ");
							a = Integer.parseInt(arr[2].split("/")[0]);
							b = Integer.parseInt(arr[2].split("/")[1]);
							if(a+e3.getArcaneRarity()>b){
								e.getWhoClicked().sendMessage("That item doesn't have enough arcane capacity!");
								return;
							}
						}

						float success = Float.parseFloat(cursor.getItemMeta().getLore().get(2).split(" ")[2].replace("%", ""));
						if(Util.randPercent(success) || success>=95){
							if(lore.size()>0 && ChatColor.stripColor(lore.get(Math.max(lore.size()-1-(isProtected?1:0), 0))).toLowerCase().startsWith("arcane capacity: ")){
								lore.set(lore.size()-1-(isProtected?1:0), ChatColor.RESET + "Arcane Capacity: " + ChatColor.RED + (e3.getArcaneRarity()+a) + ChatColor.GRAY + "/" + ChatColor.RED + b);	
							} else {
								lore.add(Math.max(lore.size()-1, 0), ChatColor.RESET + "Arcane Capacity: " + ChatColor.RED + e3.getArcaneRarity() + ChatColor.GRAY + "/" + ChatColor.RED + "1200");
							}
							
							lore.add(Math.max(lore.size()-1-(isProtected?1:0), 0), loreToAdd);
							
						} else {
							if(Util.randPercent(Float.parseFloat(cursor.getItemMeta().getLore().get(3).split(" ")[2].replace("%", "")))){
								if(!isProtected){
									item=null;
								} else {
									lore.remove(lore.size()-1);
								}
							}
						}
						
						if(item!=null){
							meta.setLore(lore);
							item.setItemMeta(meta);
							if(!item.getItemMeta().hasEnchants()){
								item = Util.addEnchantmentGlow(item);
							}
						}
						
						e.setCancelled(true);
						e.getView().setCursor(null);
						e.setCurrentItem(null);
						e.getWhoClicked().setItemOnCursor(null);

						if(cursor.getAmount()>1){
							cursor.setAmount(cursor.getAmount()-1);
							e.getWhoClicked().getInventory().addItem(cursor);
							e.getWhoClicked().closeInventory();
						}
						
						e.getView().setItem(e.getRawSlot(), item);
					}
				}
			}		
		} catch(Exception e2){}
	}
	
	@EventHandler
	public static void onItemInteract(PlayerInteractEvent e){
		try{
			ItemStack item = e.getItem();
			if(item.getItemMeta().getDisplayName()
				.equals(ChatColor.RESET + "Enchanted Papyrus")){
				
				ArrayList<PowerWord> powerWords = new ArrayList<PowerWord>();
				
				for(String lore : item.getItemMeta().getLore()){
					try{
						PowerWord word = PowerWord.valueOf(ChatColor.stripColor(lore).split(" ")[0].toUpperCase());
						if(word==null)continue;
						
						powerWords.add(word);
						
					} catch(Exception e3){}
				}
				e.getPlayer().setItemInHand(generateRandomEnchant(Integer.parseInt(item.getItemMeta().getLore().get(2).split(" ")[2]), powerWords));
				
			}
		}catch(Exception e2){}
		
		if(e.getPlayer().isSneaking() && (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK))){
			HashMap<Enchant, Integer> enchants = getCustomEnchants(e.getPlayer().getInventory().getChestplate());
			for(Enchant ench : enchants.keySet()){
				if(ench instanceof AbilityEnchant){
					if(AbilityEnchant.isAbilityReady(e.getPlayer())){
						AbilityEnchant.setCooldown(e.getPlayer(), 240);
						((AbilityEnchant)ench).activateAbilityEnchant(e.getPlayer(), enchants.get(ench));
					} else {
						e.getPlayer().sendMessage(ChatColor.RESET + "Whoa, hold up, you can't use that again for another " 
								+ AbilityEnchant.getCooldownFor(e.getPlayer()) + " seconds!");
					}
				}
			}
		}
	}
	
	@EventHandler
	public static void onItemBreak(PlayerItemBreakEvent e){
		try{
			HashMap<Enchant, Integer> enchants = getCustomEnchants(e.getBrokenItem());
			for(Enchant ench : enchants.keySet()){
				ench.activateItemBreakEnchant(e.getPlayer(), e.getBrokenItem(), enchants.get(ench));
			}
		} catch(Exception e2){}
	}

	public static ItemStack generateRandomEnchant(int arcaneEnergy, List<PowerWord> words){
		Enchant[] arr = registeredEnchants.values().toArray(new Enchant[registeredEnchants.size()]);
		
		int i = enchantSearchInteger(arcaneEnergy);
		Enchant ench = arr[i];
		
		if(!new ArrayList<PowerWord>(words).retainAll(Arrays.asList(ench.getPowerWords()))){
			return ItemFactory.genEnchantmentScroll(ench, rand.nextInt(ench.getMaxLevel())+1);
		} else {
			int j=0;
			while(i-j>=0 || i+j<=registeredEnchants.size()){
				j++;
				if(i-j>=0){
					if(!new ArrayList<PowerWord>(words).retainAll(Arrays.asList(arr[i-j].getPowerWords()))){
						return ItemFactory.genEnchantmentScroll(arr[i-j], rand.nextInt(arr[i-j].getMaxLevel())+1); 
					}
				}
				
				if(i+j<=registeredEnchants.size()){
					if(!new ArrayList<PowerWord>(words).retainAll(Arrays.asList(arr[i+j].getPowerWords()))){
						return ItemFactory.genEnchantmentScroll(arr[i+j], rand.nextInt(arr[i+j].getMaxLevel())+1); 
					}
				}
			}
			words.remove(Util.chooseRandom(words));
			return generateRandomEnchant(arcaneEnergy, words);
		}
	}
	
	public static Enchant enchantSearch(int arcaneEnergy) {
		Enchant[] a = registeredEnchants.values().toArray(new Enchant[registeredEnchants.size()]);
		return a[enchantSearchInteger(arcaneEnergy)];
    }
	
	public static int enchantSearchInteger(int arcaneEnergy) {
		ArrayList<Integer> intArr = new ArrayList<Integer>();
		for(Enchant e : registeredEnchants.values())intArr.add(e.getArcaneRarity());

		int i = Collections.binarySearch(intArr, arcaneEnergy);
		if(i>=0){
        	return i;
        } else {
        	if(i==-1)return 0;
        	if(i==-1-intArr.size())return intArr.size()-1;
        	return Math.abs(intArr.get(-i-2)-arcaneEnergy)<=Math.abs(intArr.get(-i-1)-arcaneEnergy)?-i-2:-i-1;
        }
    }
	
}
