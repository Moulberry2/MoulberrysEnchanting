package io.github.moulberry.customenchants.customentities;

import java.util.List;

import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;

import de.slikey.effectlib.effect.LineEffect;
import de.slikey.effectlib.util.DynamicLocation;
import de.slikey.effectlib.util.ParticleEffect;
import io.github.moulberry.customenchants.MoulberrysEnchanting;
import net.minecraft.server.v1_8_R3.DamageSource;
import net.minecraft.server.v1_8_R3.EntityBlaze;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.PathfinderGoalFloat;
import net.minecraft.server.v1_8_R3.PathfinderGoalHurtByTarget;
import net.minecraft.server.v1_8_R3.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_8_R3.PathfinderGoalSelector;

public class GuardianAngelBlaze extends EntityBlaze{
	
	private EntityLiving owner;
	private int attackTimer = 0;
	
    @SuppressWarnings({ "rawtypes" })
	public GuardianAngelBlaze(org.bukkit.World world, EntityLiving owner){
        super(((CraftWorld)world).getHandle());
        
        this.owner = owner;
        
        List goalB = (List)CustomEntities.getPrivateField("b", PathfinderGoalSelector.class, goalSelector); goalB.clear();
        List goalC = (List)CustomEntities.getPrivateField("c", PathfinderGoalSelector.class, goalSelector); goalC.clear();
        List targetB = (List)CustomEntities.getPrivateField("b", PathfinderGoalSelector.class, targetSelector); targetB.clear();
        List targetC = (List)CustomEntities.getPrivateField("c", PathfinderGoalSelector.class, targetSelector); targetC.clear();

        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(8, new PathfinderGoalLookAtPlayer(this, EntityLiving.class, 8.0F));
        this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, true));
        
    }
    
    public void t_(){
    	super.t_();
    	
    	if(!owner.isAlive())die();
    	if(inBlock())die();
    	if(ticksLived >= 2400)die();
    	if(getGoalTarget()!=null && !getGoalTarget().isAlive())setGoalTarget(null);
    		
    	EntityLiving lastDamager = owner.getLastDamager();
    	if(lastDamager!=null)setGoalTarget(lastDamager, TargetReason.TARGET_ATTACKED_OWNER, false);
    	
    	setPosition(owner.locX, owner.locY+3, owner.locZ);
    	
    	attackTimer++;
    	if((attackTimer%5==0 || attackTimer>80) && attackTimer>15 && getGoalTarget()!=null){
    		LineEffect eff = new LineEffect(MoulberrysEnchanting.em);
			
			eff.setDynamicOrigin(new DynamicLocation(this.bukkitEntity));
			eff.setDynamicTarget(new DynamicLocation(getGoalTarget().getBukkitEntity()));
			
			eff.particle = attackTimer>=80 ? ParticleEffect.LAVA : ParticleEffect.CRIT;
			eff.duration = 250;
			eff.period = 1;
			
			eff.start();
    	}
    	
    	if(attackTimer>=80 && getGoalTarget()!=null){
    		getGoalTarget().damageEntity(DamageSource.MAGIC, 5);
    		getGoalTarget().setOnFire(100);
    		attackTimer=0;
    	}
    	
    }
    
    public boolean damageEntity(DamageSource damagesource, float f){
    	if(damagesource.getEntity()!=null && damagesource.getEntity().equals(owner)){
    		return false;
    	}
    	
    	return super.damageEntity(damagesource, f);
    }
    
    public Item getLoot(){return null;}
    protected void getRareDrop(){}
    protected int getExpValue(EntityHuman entityhuman){return 0;}
}