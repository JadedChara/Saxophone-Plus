package net.chemthunder.saxophone.core.cca.entity;

import net.acoyt.acornlib.api.util.MiscUtils;
import net.chemthunder.saxophone.core.Saxophone;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.registry.RegistryWrapper;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.entity.C2SSelfMessagingComponent;

/**
 * Component purely for cosmetics for contributors. More can be added on request.
 * @author Nightstrike
 */
public class ArchitectComponent implements AutoSyncedComponent, C2SSelfMessagingComponent {
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
    //CLIENT COMMUNICATION

    @Override
    public void handleC2SMessage(RegistryByteBuf registryByteBuf) {
        if (!Saxophone.isContributor(this.player)){
            return;
        }
        int[] set = registryByteBuf.readIntArray();
        this.wavering = set[0] == 1;
        this.fx = set[1] == 1;
        this.flair = set[2] == 1;
    }

    public void c2sWavering(boolean bl){
        this.wavering = bl;
        this.serializeAndSend();
    }
    public void c2sFX(boolean bl){
        this.fx = bl;
        this.serializeAndSend();
    }
    public void c2sFlair(boolean bl){
        this.flair = bl;
        this.serializeAndSend();
    }


    public void serializeAndSend(){

        int b = this.wavering ? 1 : 0;
        int c = this.fx ? 1 : 0;
        int d = this.flair ? 1 : 0;
        int[] set = {b,c,d};

        sendC2SMessage(buf->{
            buf.writeIntArray(set);
        });
    }

}