package net.chemthunder.saxophone.core.entity;

import com.nitron.nitrogen.util.interfaces.ScreenShaker;
import net.chemthunder.saxophone.core.Saxophone;
import net.chemthunder.saxophone.core.client.particle.ShockwaveParticleEffect;
import net.chemthunder.saxophone.core.index.data.SaxoDamageSources;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class HopefulSkyEntity extends Entity {
    private int activityTicks = 0;
    private int orbRadius = 0;
    private int orbQuality = 90;

    private boolean finalized = false;

    public HopefulSkyEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    public void tick() {
        this.activityTicks++;

        if (this.activityTicks < 45) {
            this.orbRadius++;
        }

        if (this.activityTicks == 44) {
            this.finalized = true;
        }

        if (this.activityTicks >= 150) {
            if (this.orbQuality > 0) {
                this.orbQuality--;
                if (this.orbQuality == 0) {
                    if (this.getWorld() instanceof ServerWorld serverWorld) {
                        serverWorld.spawnParticles(
                                new ShockwaveParticleEffect(
                                        0xffe86d,
                                        50,
                                        Direction.Axis.Y),
                                this.getX(),
                                this.getY(),
                                this.getZ(),
                                1,
                                0,
                                0,
                                0,
                                0
                        );

                        serverWorld.spawnParticles(
                                new ShockwaveParticleEffect(
                                        0xffe86d,
                                        50,
                                        Direction.Axis.X),
                                this.getX(),
                                this.getY(),
                                this.getZ(),
                                1,
                                0,
                                0,
                                0,
                                0
                        );

                        serverWorld.spawnParticles(
                                new ShockwaveParticleEffect(
                                        0xffe86d,
                                        50,
                                        Direction.Axis.Z),
                                this.getX(),
                                this.getY(),
                                this.getZ(),
                                1,
                                0,
                                0,
                                0,
                                0
                        );
                    }

                    this.getWorld().getPlayers().forEach(playerEntity -> {
                        if (playerEntity instanceof ScreenShaker shaker) {
                            shaker.addScreenShake(5.0f, 35);
                        }
                    });

                    this.getWorld().getPlayers().forEach(playerEntity -> {
                        if (Saxophone.ALL_CONTRACTED_PLAYERS.contains(playerEntity.getUuid())) {
                            playerEntity.damage(playerEntity.getDamageSources().create(SaxoDamageSources.CLEANSE), playerEntity.getMaxHealth() * playerEntity.getMaxHealth());
                        }
                    });
                    this.discard();
                }
            }
        }
    }

    public Box getVisibilityBoundingBox() {
        return new Box(9000, 9000, 9000, 9000, 9000, 9000);
    }

    protected void initDataTracker(DataTracker.Builder builder) {}

    protected void readCustomDataFromNbt(NbtCompound nbt) {
        this.activityTicks = nbt.getInt("ActivityTicks");
        this.orbRadius = nbt.getInt("OrbRadius");
        this.orbQuality = nbt.getInt("OrbQuality");

        this.finalized = nbt.getBoolean("Finalized");
    }

    protected void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putInt("ActivityTicks", activityTicks);
        nbt.putInt("OrbRadius", orbRadius);
        nbt.putInt("OrbQuality", orbQuality);

        nbt.putBoolean("Finalized", finalized);
    }

    public int getActivityTicks() {
        return this.activityTicks;
    }

    public int getOrbRadius() {
        return this.orbRadius;
    }

    public boolean getFinalized() {
        return this.finalized;
    }

    public int getOrbQuality() {
        return this.orbQuality;
    }

    public void setActivityTicks(int i) {
        this.activityTicks = i;
    }

    public void setOrbRadius(int i) {
        this.orbRadius = i;
    }

    public void setFinalized(boolean bl) {
        this.finalized = bl;
    }

    public void setOrbQuality(int i) {
        this.orbQuality = i;
    }
}
