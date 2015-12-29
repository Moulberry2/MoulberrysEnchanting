package io.github.moulberry.customenchants.customentities;

import java.util.List;

import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;

import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EntityZombie;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.PathfinderGoalFloat;
import net.minecraft.server.v1_8_R3.PathfinderGoalHurtByTarget;
import net.minecraft.server.v1_8_R3.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_8_R3.PathfinderGoalMeleeAttack;
import net.minecraft.server.v1_8_R3.PathfinderGoalMoveTowardsRestriction;
import net.minecraft.server.v1_8_R3.PathfinderGoalSelector;

public class UndeadWreatheZombie extends EntityZombie{
	
	EntityLiving target;
	
    @SuppressWarnings({ "rawtypes" })
	public UndeadWreatheZombie(org.bukkit.World world, EntityLiving target){
        super(((CraftWorld)world).getHandle());
        
        this.target = target;
        
        List goalB = (List)CustomEntities.getPrivateField("b", PathfinderGoalSelector.class, goalSelector); goalB.clear();
        List goalC = (List)CustomEntities.getPrivateField("c", PathfinderGoalSelector.class, goalSelector); goalC.clear();
        List targetB = (List)CustomEntities.getPrivateField("b", PathfinderGoalSelector.class, targetSelector); targetB.clear();
        List targetC = (List)CustomEntities.getPrivateField("c", PathfinderGoalSelector.class, targetSelector); targetC.clear();

        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(2, new PathfinderGoalMeleeAttack(this, EntityLiving.class, 1.0D, false));
        this.goalSelector.a(5, new PathfinderGoalMoveTowardsRestriction(this, 1.0D));
        this.goalSelector.a(8, new PathfinderGoalLookAtPlayer(this, EntityLiving.class, 8.0F));
        this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, true));
        
        getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.35D);
        
    }
    
    public void t_(){
    	super.t_();
    	
    	if(!target.isAlive())die();
    	setGoalTarget(target, TargetReason.CUSTOM, false);
    }
    
    public Item getLoot(){return null;}
    protected void getRareDrop(){}
    protected int getExpValue(EntityHuman entityhuman){return 0;}
}