package net.chemthunder.saxophone.core.util.keybinds;

import net.chemthunder.saxophone.core.index.data.SaxoDamageSources;
import net.chemthunder.saxophone.core.util.ModUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

/**
 * @author Chemthunder
 */
public class SaxophoneKeybindManager {
    public static void explodeIvory(PlayerEntity player) {
        World world = player.getWorld();

        world.getPlayers().forEach(capturedPlayer -> {
            if (ModUtils.isAvarice(capturedPlayer)) {
                world.addParticle(ParticleTypes.EXPLOSION_EMITTER,
                        capturedPlayer.getX(),
                        capturedPlayer.getY(),
                        capturedPlayer.getZ(),
                        0,
                        0,
                        0
                );

                world.playSound(capturedPlayer, capturedPlayer.getBlockPos(), SoundEvents.ENTITY_GENERIC_EXPLODE.value(), SoundCategory.PLAYERS, 1, 1);
                capturedPlayer.damage(capturedPlayer.getDamageSources().create(SaxoDamageSources.IVORY_EXPLODE), 999f);
            }
        });
    }
}