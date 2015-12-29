package io.github.moulberry.customenchants;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import de.slikey.effectlib.EffectManager;
import io.github.moulberry.customenchants.enchLibrary.base.AbilityEnchant;
import io.github.moulberry.customenchants.enchLibrary.base.Enchant;
import io.github.moulberry.customenchants.utilities.ItemFactory;
import io.github.moulberry.customenchants.utilities.Util;
import net.md_5.bungee.api.ChatColor;

public class MoulberrysEnchanting extends JavaPlugin implements Listener{

	private static MoulberrysEnchanting instance;
	public static EffectManager em;
	
	public static MoulberrysEnchanting getInstance(){
		return instance;
	}
	
	public void onEnable(){
		instance = this;
		em = new EffectManager(this);
		
		Bukkit.getPluginManager().registerEvents(new CustomEnchants(), this);
		Bukkit.getPluginManager().registerEvents(this, this);
	}
	
	public boolean onCommand(CommandSender sender, Command command, String str, String[] args){
		if(!(sender instanceof Player)){
			sender.sendMessage("§4§lThat command cannot be executed as a " + sender.getName());
			return false;
		}
		
		try {
			if(str.startsWith("mbe")){
				
				if(args.length==1){
					if(args[0].equalsIgnoreCase("list")){
						for(Enchant e : CustomEnchants.getRegisteredEnchants().values()){
							String str2 = e.getReadable(e.getMaxLevel());
							str2+=ChatColor.RESET + " AE: " + e.getArcaneRarity() + "" + ChatColor.GRAY;
							for(PowerWord pw : e.getPowerWords()){
								str2+=" " + Util.readable(pw.getLatinName());
							}
							sender.sendMessage(str2);
						}
						return true;
					} else if(args[0].equalsIgnoreCase("resetability")){
						AbilityEnchant.setCooldown((Player)sender, 0);
						return true;
					}
				}

				Enchant e = CustomEnchants.getRegisteredEnchants().get(args[0].trim());
				
				ItemStack item = CustomEnchants.addEnchantToItem(e, ((Player)sender).getInventory().getItemInHand(),
						Integer.parseInt(args[1].trim()));
				
				((Player)sender).getInventory().setItemInHand(item);
				
				return true;
			}
		} catch(Exception e){
			e.printStackTrace();
			sender.sendMessage("§4§lUh oh... something went wrong!");
		}
		
		return false;
	}
	
	@EventHandler
	public static void onChat(AsyncPlayerChatEvent e){
		if(e.getMessage().equalsIgnoreCase("givepapyrus"))e.getPlayer().getInventory().addItem(ItemFactory.blankPapyrus);
		if(e.getMessage().equalsIgnoreCase("givepscroll"))e.getPlayer().getInventory().addItem(ItemFactory.protectionScroll);
		if(e.getMessage().equalsIgnoreCase("giveoscroll"))e.getPlayer().getInventory().addItem(ItemFactory.arcaneOverloadScroll);
		if(e.getMessage().equalsIgnoreCase("givesscroll"))e.getPlayer().getInventory().addItem(ItemFactory.arcaneScrambleScroll);
	}

}
