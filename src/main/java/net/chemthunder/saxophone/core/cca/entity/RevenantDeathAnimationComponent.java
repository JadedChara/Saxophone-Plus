package net.chemthunder.saxophone.core.cca.entity;

import com.nitron.nitrogen.util.interfaces.ScreenShaker;
import net.acoyt.acornlib.api.util.MiscUtils;
import net.chemthunder.saxophone.core.Saxophone;
import net.chemthunder.saxophone.core.cca.deity.AvariceComponent;
import net.chemthunder.saxophone.core.client.particle.ShockwaveParticleEffect;
import net.chemthunder.saxophone.core.index.SaxoParticles;
import net.chemthunder.saxophone.core.index.data.SaxoDamageSources;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Direction;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

/**
 * @author Chemthunder
 */
public class RevenantDeathAnimationComponent implements AutoSyncedComponent, CommonTickingComponent {
    public static final ComponentKey<RevenantDeathAnimationComponent> KEY = MiscUtils.getOrCreateKey(Saxophone.id("revenant_death"), RevenantDeathAnimationComponent.class);

    private final PlayerEntity player;
    private int animTicks = 0;

    public RevenantDeathAnimationComponent(PlayerEntity player) {
        this.player = player;
    }

    public void sync() {
        KEY.sync(player);
    }

    public void tick() {
        if (this.animTicks > 0) {
            tickAnimations();
            this.animTicks--;
            if (this.animTicks == 0) {
                endAnimation();
                sync();
            }
        }
    }

    private void tickAnimations() {
        if (player.getWorld() instanceof ServerWorld serverWorld) {
            serverWorld.spawnParticles(SaxoParticles.AVARICE_SOUL,
                    player.getX(),
                    player.getY() + 1.0f,
                    player.getZ(),
                    15,
                    0,
                    0,
                    0,
                    0.8f
            );

            serverWorld.spawnParticles(ParticleTypes.SMOKE,
                    player.getX(),
                    player.getY() + 1.0f,
                    player.getZ(),
                    15,
                    0,
                    0,
                    0,
                    0.8f
            );

            serverWorld.spawnParticles(SaxoParticles.AVARICE_LIGHT,
                    player.getX(),
                    player.getY() + 1.0f,
                    player.getZ(),
                    7,
                    0,
                    0,
                    0,
                    0.8f
            );

            player.addVelocity(0, 0.10f, 0);
            player.velocityModified = true;
        }
    }

    private void endAnimation() {
        if (player.getWorld() instanceof ServerWorld serverWorld) {
            serverWorld.spawnParticles(
                    new ShockwaveParticleEffect(
                            0xd70048,
                            7,
                            Direction.Axis.Y
                    ),
                    player.getPos().x, player.getPos().y, player.getPos().z,
                    1,
                    0, 0, 0,
                    0.4f
            );

            serverWorld.spawnParticles(SaxoParticles.AVARICE_GLIMMER,
                    player.getX() + 0.5f,
                    player.getY() + 0.5f,
                    player.getZ() + 0.5f,
                    25,
                    0,
                    0,
                    0,
                    0.4f
            );

            serverWorld.getPlayers().forEach(serverPlayer -> {
                if (serverPlayer instanceof ScreenShaker shaker) {
                    shaker.addScreenShake(3.0f, 20);
                }

                serverPlayer.playSoundToPlayer(SoundEvents.ENTITY_WITHER_DEATH, SoundCategory.PLAYERS, 0.6f, 0.2f);
            });

            player.damage(player.getDamageSources().create(SaxoDamageSources.AVARICES_WILL), player.getMaxHealth() * player.getMaxHealth());

            AvariceComponent avariceComponent = AvariceComponent.KEY.get(player);
            avariceComponent.setAvarice(true);
        }
    }

    public void readFromNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        this.animTicks = nbtCompound.getInt("AnimTicks");
    }

    public void writeToNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        nbtCompound.putInt("AnimTicks", animTicks);
    }

    public void beginAnimation() {
        this.animTicks = 100;
        this.sync();
    }

    public boolean animationIsActive() {
        return this.animTicks > 0;
    }
}