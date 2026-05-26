package net.chemthunder.saxophone.core.entity;

import net.chemthunder.saxophone.core.util.ModUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;

import java.util.List;

/**
 * @author Chemthunder
 */
public class ForsakenCharterEntity extends Entity {
    public ForsakenCharterEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    protected void initDataTracker(DataTracker.Builder builder) {}

    public void tick() {
        tickBorders();
    }

    private void tickBorders() {
        List<Entity> ENTITIES = ModUtils.getEntitiesAroundEntity(this, 150);

        for (Entity entity : ENTITIES) {
            if (entity instanceof LivingEntity living && !(entity instanceof PlayerEntity)) {
                living.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 90, 0, false, false, false));
            }

            if (entity instanceof PlayerEntity player) {
                if (!ModUtils.isAvarice(player)) {
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 90, 0, false, false, false));
                }
            }
        }
    }

    protected void readCustomDataFromNbt(NbtCompound nbt) {}
    protected void writeCustomDataToNbt(NbtCompound nbt) {}
}