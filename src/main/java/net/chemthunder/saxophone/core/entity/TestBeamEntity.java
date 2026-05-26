package net.chemthunder.saxophone.core.entity;

import net.chemthunder.saxophone.core.index.SaxoEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;

/**
 * @author Chemthunder
 */
public class TestBeamEntity extends Entity {
    public float beamRadius = 0;

    public TestBeamEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    public TestBeamEntity(World world) {
        super(SaxoEntities.TEST_BEAM, world);
    }

    public void tick() {
        if (this.beamRadius < 5) {
            this.beamRadius = this.beamRadius + 0.5f;
        }
    }

    protected void initDataTracker(DataTracker.Builder builder) {}

    protected void readCustomDataFromNbt(NbtCompound nbt) {
        this.beamRadius = nbt.getFloat("BeamRadius");
    }

    protected void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putFloat("BeamRadius", beamRadius);
    }
}
