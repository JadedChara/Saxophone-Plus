package net.chemthunder.saxophone.core.cca.entity;

import net.acoyt.acornlib.api.util.MiscUtils;
import net.chemthunder.saxophone.core.Saxophone;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

/**
 * @author Chemthunder
 */
public class InsistenceComponent implements AutoSyncedComponent, CommonTickingComponent {
    public static final ComponentKey<InsistenceComponent> KEY = MiscUtils.getOrCreateKey(Saxophone.id("insistence"), InsistenceComponent.class);

    private final LivingEntity living;
    private int activeTicks = 0;

    public InsistenceComponent(LivingEntity living) {
        this.living = living;
    }

    public void sync() {
        KEY.sync(living);
    }

    public void tick() {
        if (this.activeTicks > 0) {
            this.activeTicks--;
            if (this.activeTicks == 0) {
                this.sync();
            }
        }
    }

    public void readFromNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        this.activeTicks = nbtCompound.getInt("ActiveTicks");
    }

    public void writeToNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        nbtCompound.putInt("ActiveTicks", activeTicks);
    }

    public int getActiveTicks() {
        return this.activeTicks;
    }

    public void setActiveTicks(int i) {
        this.activeTicks = i;
        this.sync();
    }
}