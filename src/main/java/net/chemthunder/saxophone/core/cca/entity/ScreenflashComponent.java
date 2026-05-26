package net.chemthunder.saxophone.core.cca.entity;

import net.acoyt.acornlib.api.util.MiscUtils;
import net.chemthunder.saxophone.core.Saxophone;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.ClientTickingComponent;

public class ScreenflashComponent implements AutoSyncedComponent, ClientTickingComponent {
    public static final ComponentKey<ScreenflashComponent> KEY = MiscUtils.getOrCreateKey(Saxophone.id("screenflash"), ScreenflashComponent.class);
    private final PlayerEntity player;

    private int flashTicks = 0;

    public ScreenflashComponent(PlayerEntity player) {
        this.player = player;
    }

    public void sync() {
        KEY.sync(this.player);
    }

    public void clientTick() {
        if (this.flashTicks > 0) {
            this.flashTicks--;
            if (this.flashTicks == 0) {
                this.sync();
            }
        }
    }

    public void readFromNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        this.flashTicks = nbtCompound.getInt("FlashTicks");
    }

    public void writeToNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        nbtCompound.putInt("FlashTicks", flashTicks);
    }

    public void sendFlash(int ticks) {
        this.flashTicks = ticks;
        this.sync();
    }

    public int getFlashTicks() {
        return this.flashTicks;
    }

    public boolean isFlashActive() {
        return this.flashTicks > 0;
    }

    public static void sendFlash(PlayerEntity player, int ticks) {
        ScreenflashComponent component = ScreenflashComponent.KEY.get(player);

        component.sendFlash(ticks);
    }
}
