package io.github.moulberry.customenchants.utilities;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import io.github.moulberry.customenchants.MoulberrysEnchanting;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.MobEffect;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagList;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutBlockBreakAnimation;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;

public class Util
implements Listener {
    private static final Random random = new Random();
    private static final String[] romans = new String[]{"I", "IV", "V", "IX", "X", "XL", "L", "XC", "C", "CD", "D", "CM", "M"};
    private static final int[] ints = new int[]{1, 4, 5, 9, 10, 40, 50, 90, 100, 400, 500, 900, 1000};

    public static void sendBreakPacket(World world, Location location, int data) {
        BlockPosition bp = new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ());
        PacketPlayOutBlockBreakAnimation packet = new PacketPlayOutBlockBreakAnimation(new Random().nextInt(Integer.MAX_VALUE), bp, data);
        for (Player player : world.getPlayers()) {
            ((CraftPlayer)player).getHandle().playerConnection.sendPacket((Packet<?>)packet);
        }
    }
    
    @SuppressWarnings("deprecation")
	public static boolean addPotionEffect(PotionEffect pe, Player p){
    	EntityPlayer ep = ((CraftPlayer)p).getHandle();
    	MobEffect eff = ep.effects.get(pe.getType().getId());
    	if(eff==null || eff.getAmplifier()<pe.getAmplifier() || eff.getDuration()<pe.getDuration()){
    		return p.addPotionEffect(pe, true);
    	}
    	return false;
    }
    
    public static void sendActionMessage(Player p, String message){
      IChatBaseComponent icbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + ChatColor.GOLD + ChatColor.BOLD + message + "\"}");
      PacketPlayOutChat bar = new PacketPlayOutChat(icbc, (byte)2);
      ((CraftPlayer)p).getHandle().playerConnection.sendPacket(bar);
    }
    
    public static void sendDelayedActionMessage(Player p, String m, int delay) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(MoulberrysEnchanting.getInstance(), new Runnable() {
			public void run() { sendActionMessage(p, m); }
		}, delay);
	}

    public static String toRomanNumeral(int num) {
        StringBuilder sb = new StringBuilder();
        int times = 0;
        int i = ints.length - 1;
        while (i >= 0) {
            times = num / ints[i];
            num %= ints[i];
            while (times > 0) {
                sb.append(romans[i]);
                --times;
            }
            --i;
        }
        return sb.toString();
    }

    public static int fromRomanNumeral(String number) {
        if (number.startsWith("M"))return 1000 + Util.fromRomanNumeral(number.substring(1));
        if (number.startsWith("CM"))return 900 + Util.fromRomanNumeral(number.substring(2));
        if (number.startsWith("D"))return 500 + Util.fromRomanNumeral(number.substring(1));
        if (number.startsWith("CD"))return 400 + Util.fromRomanNumeral(number.substring(2));
        if (number.startsWith("C"))return 100 + Util.fromRomanNumeral(number.substring(1));
        if (number.startsWith("XC"))return 90 + Util.fromRomanNumeral(number.substring(2));
        if (number.startsWith("L"))return 50 + Util.fromRomanNumeral(number.substring(1));
        if (number.startsWith("XL"))return 40 + Util.fromRomanNumeral(number.substring(2));
        if (number.startsWith("X"))return 10 + Util.fromRomanNumeral(number.substring(1));
        if (number.startsWith("IX"))return 9 + Util.fromRomanNumeral(number.substring(2));
        if (number.startsWith("V"))return 5 + Util.fromRomanNumeral(number.substring(1));
        if (number.startsWith("IV"))return 4 + Util.fromRomanNumeral(number.substring(2));
        if (number.startsWith("I"))return 1 + Util.fromRomanNumeral(number.substring(1));
        return 0;
    }   
    
    public static ItemStack addEnchantmentGlow(ItemStack item){
    	try {
    		String name = "net.minecraft.server." + Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + ".ItemStack";
    		Class<?> nmsClass = Class.forName(name);
    		Object nmsStack = CraftItemStack.asNMSCopy(item);
    	
			if(!(boolean)nmsClass.getMethod("hasTag").invoke(nmsStack)){
				nmsClass.getMethod("setTag").invoke(nmsStack, new NBTTagCompound());
			}
	    	NBTTagCompound tag = (NBTTagCompound)nmsClass.getMethod("getTag").invoke(nmsStack);
	    
	    	tag.set("ench", new NBTTagList());
	    	return (ItemStack)CraftItemStack.class.getMethod("asCraftMirror", nmsClass).invoke(null, nmsStack);
	    } catch (Exception e) {
			return item;
		}
    }

    public static boolean randPercent(float f) {
        if (random.nextDouble() * 100.0 <= (double)f) {
            return true;
        }
        return false;
    }

    public static String readable(String str) {
        return String.valueOf(str.substring(0, 1).toUpperCase()) + str.substring(1).toLowerCase();
    }

    public static int getOccurences(Collection<?> arr, Object obj) {
        return Collections.frequency(arr, obj);
    }
    
    public static <T> T chooseRandom(List<T> arr){
    	return arr.get(random.nextInt(arr.size()));
    }
    
    public static <T> T chooseRandom(T[] arr){
    	return arr[random.nextInt(arr.length)];
    }

	public static void sendPlayerDelayedMessage(Player p, String m, int delay) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(MoulberrysEnchanting.getInstance(), new Runnable() {
			public void run() { p.sendMessage(m); }
		}, delay);
	}
}