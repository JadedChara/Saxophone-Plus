package net.chemthunder.saxophone.core.cca.world;

import com.nitron.nitrogen.util.interfaces.ScreenShaker;
import net.acoyt.acornlib.api.util.MiscUtils;
import net.chemthunder.saxophone.core.Saxophone;
import net.chemthunder.saxophone.core.cca.entity.ScreenflashComponent;
import net.chemthunder.saxophone.core.index.SaxoSoundEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.sound.SoundCategory;
import net.minecraft.world.World;
import net.minecraft.world.border.WorldBorder;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

/**
 * @author Chemthunder
 */
public class AvariceEventComponent implements AutoSyncedComponent, CommonTickingComponent {
    public static final ComponentKey<AvariceEventComponent> KEY = MiscUtils.getOrCreateKey(Saxophone.id("avarice_event"), AvariceEventComponent.class);
    private final World world;

    private boolean state = false;
    private boolean shade = false;

    public static final double WORLD_BORDER_SIZE = 300;

    private double savedBorderSize = 0;
    private double savedCenterX = 0;
    private double savedCenterZ = 0;

    private boolean canUseDomainExpansion = false;
    
    public AvariceEventComponent(World world) {
        this.world = world;
    }

    public void sync() {
        KEY.sync(this.world);
    }

    public void readFromNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        this.state = nbtCompound.getBoolean("State");
        this.shade = nbtCompound.getBoolean("Shade");

        this.savedBorderSize = nbtCompound.getDouble("SavedBorderSize");
        this.savedCenterX = nbtCompound.getDouble("SavedCenterX");
        this.savedCenterZ = nbtCompound.getDouble("SavedCenterZ");

        this.canUseDomainExpansion = nbtCompound.getBoolean("CanUseDomainExpansion");
    }

    public void writeToNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        nbtCompound.putBoolean("State", state);
        nbtCompound.putBoolean("Shade", shade);

        nbtCompound.putDouble("SavedBorderSize", savedBorderSize);
        nbtCompound.putDouble("SavedCenterX", savedCenterX);
        nbtCompound.putDouble("SavedCenterZ", savedCenterZ);

        nbtCompound.putBoolean("CanUseDomainExpansion", canUseDomainExpansion);
    }

    public void setState(boolean bl) {
        this.state = bl;
        this.sync();
    }

    public boolean getState() {
        return this.state;
    }

    public boolean getShade() {
        return this.shade;
    }

    public void setShade(boolean bl) {
        this.shade = bl;
        this.sync();
    }

    public double getSavedCenterX() {
        return this.savedCenterX;
    }

    public double getSavedCenterZ() {
        return this.savedCenterZ;
    }

    public void beginEvent(PlayerEntity source) {
        this.state = true;
        this.shade = false;

        WorldBorder border = this.world.getWorldBorder();

        this.savedBorderSize = border.getSize();
        this.savedCenterX = border.getCenterX();
        this.savedCenterZ = border.getCenterZ();

        border.setCenter(source.getX(), source.getZ());
        border.interpolateSize(border.getSize(), WORLD_BORDER_SIZE, 60);

        this.world.getPlayers().forEach(player -> {
            ScreenflashComponent.sendFlash(player, 60);
            player.playSoundToPlayer(SaxoSoundEvents.FOLLY_BEGIN, SoundCategory.MASTER, 1, 1);

            if (player instanceof ScreenShaker shaker) {
                shaker.addScreenShake(0.5f, 65);
            }
        });

        this.sync();
    }

    public void ceaseEvent() {
        this.state = false;
        this.shade = false;

        WorldBorder border = this.world.getWorldBorder();

        border.setCenter(this.savedCenterX, this.savedCenterZ);
        border.interpolateSize(border.getSize(), this.savedBorderSize, 60);

        this.sync();
    }

    public boolean isCanUseDomainExpansion() {
        return this.canUseDomainExpansion;
    }

    public void setCanUseDomainExpansion(boolean bl) {
        this.canUseDomainExpansion = bl;
        this.sync();
    }

    public void tick() {
        if (this.state) {
            if (this.world.getWorldBorder().getSize() > WORLD_BORDER_SIZE) {
                this.world.getWorldBorder().setSize(WORLD_BORDER_SIZE);
            }
        }
    }
}
