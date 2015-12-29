package io.github.moulberry.customenchants.customentities;

import java.lang.reflect.Field;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;

import net.minecraft.server.v1_8_R3.Entity;

public class CustomEntities{

	static{
		initializeEntity(UndeadWreatheZombie.class, "Zombie", 54);
		initializeEntity(GuardianAngelBlaze.class, "Blaze", 61);
	}

    public static void spawnEntity(Entity entity, Location loc){
    	entity.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
    	((CraftWorld)loc.getWorld()).getHandle().addEntity(entity);
    }
  
    @SuppressWarnings("rawtypes")
	public static Object getPrivateField(String fieldName, Class clazz, Object object){
    	Field field;
    	Object o = null;
    	try
    	{
    		field = clazz.getDeclaredField(fieldName);
    		field.setAccessible(true);
    		o = field.get(object);
    	}
    	catch(NoSuchFieldException | IllegalAccessException e)
    	{
    		e.printStackTrace();
    	}
    	return o;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	private static void initializeEntity(Class clazz, String name, int id)
    {
        ((Map)getPrivateField("c", net.minecraft.server.v1_8_R3.EntityTypes.class, null)).put(name, clazz);
        ((Map)getPrivateField("d", net.minecraft.server.v1_8_R3.EntityTypes.class, null)).put(clazz, name);
        ((Map)getPrivateField("f", net.minecraft.server.v1_8_R3.EntityTypes.class, null)).put(clazz, Integer.valueOf(id));
    }
}