package de.slikey.effectlib.effect;

import org.bukkit.Location;
import org.bukkit.Sound;

import de.slikey.effectlib.Effect;
import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.EffectType;
import de.slikey.effectlib.util.ParticleEffect;
import de.slikey.effectlib.util.RandomUtils;

public class ExplodeEffect extends Effect {

    /**
     * Amount of spawned smoke-sparks
     */
    public int amount = 25;

    /**
     * Movement speed of smoke-sparks. Should be increases with force.
     */
    public float speed = .5f;

    public Sound sound = Sound.EXPLODE;

    public ExplodeEffect(EffectManager effectManager) {
        super(effectManager);
        type = EffectType.INSTANT;
    }

    @SuppressWarnings("deprecation")
	@Override
    public void onRun() {
        Location location = getLocation();
        location.getWorld().playSound(location, sound, 4.0F, (1.0F + (RandomUtils.random.nextFloat() - RandomUtils.random.nextFloat()) * 0.2F) * 0.7F);
        ParticleEffect.EXPLOSION_NORMAL.display(location, visibleRange, 0, 0, 0, speed, amount);
        ParticleEffect.EXPLOSION_HUGE.display(location, visibleRange, 0, 0, 0, 0, amount);
    }

}
