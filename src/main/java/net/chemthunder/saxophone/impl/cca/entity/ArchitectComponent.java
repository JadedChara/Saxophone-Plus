package net.chemthunder.saxophone.impl.cca.entity;

import net.acoyt.acornlib.api.util.MiscUtils;
import net.chemthunder.saxophone.impl.Saxophone;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;

/**
 * Component purely for cosmetics for contributors. More can be added on request.
 * @author Nightstrike
 */
public class ArchitectComponent implements AutoSyncedComponent {
    public static final ComponentKey<ArchitectComponent> KEY = MiscUtils.getOrCreateKey(
            Saxophone.id("architect"),
            ArchitectComponent.class);
    private final PlayerEntity player;

    private boolean flair = false;
    private boolean fx = false;
    private boolean wavering = false;


    public ArchitectComponent(PlayerEntity player) {
        this.player = player;
    }

    public void sync() {
        KEY.sync(player);
    }

    public void readFromNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        this.flair = nbtCompound.getBoolean("Flair");
        this.fx = nbtCompound.getBoolean("FX");
        this.wavering = nbtCompound.getBoolean("Wavering");
    }

    public void writeToNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        nbtCompound.putBoolean("Flair", flair);
        nbtCompound.putBoolean("FX", fx);
        nbtCompound.putBoolean("Wavering",wavering);

    }

    public boolean hasFlair() {
        return this.flair;
    }

    public void setFlair(boolean bl) {
        this.flair = bl;
        this.sync();
    }

    public boolean hasFX() {
        return this.fx;
    }

    public void setFX(boolean bl) {
        this.fx = bl;
        this.sync();
    }

    public boolean hasWavering() {
        return this.wavering;
    }

    public void setWavering(boolean bl) {
        this.wavering = bl;
        this.sync();
    }

}