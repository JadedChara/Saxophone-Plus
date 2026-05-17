package net.chemthunder.saxophone.impl.cca.deity;

import net.acoyt.acornlib.api.util.MiscUtils;
import net.chemthunder.saxophone.impl.Saxophone;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.registry.RegistryWrapper;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.entity.C2SSelfMessagingComponent;

/**
 * @author Chemthunder
 */
public class AvariceComponent implements AutoSyncedComponent, C2SSelfMessagingComponent {
    public static final ComponentKey<AvariceComponent> KEY = MiscUtils.getOrCreateKey(Saxophone.id("avarice"), AvariceComponent.class);
    private final PlayerEntity player;

    private boolean avarice = false;
    private boolean invisible = false;
    private boolean invincible = false;
    private boolean transparent = false;
    private boolean wavering = false;

    public AvariceComponent(PlayerEntity player) {
        this.player = player;
    }

    public void sync() {
        KEY.sync(player);
    }

    public void readFromNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        this.avarice = nbtCompound.getBoolean("Avarice");
        this.invisible = nbtCompound.getBoolean("Invisible");
        this.invincible = nbtCompound.getBoolean("Invincible");
        this.transparent = nbtCompound.getBoolean("Transparent");
        this.wavering = nbtCompound.getBoolean("Wavering");
    }

    public void writeToNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        nbtCompound.putBoolean("Avarice", avarice);
        nbtCompound.putBoolean("Invisible", invisible);
        nbtCompound.putBoolean("Invincible", invincible);
        nbtCompound.putBoolean("Transparent", transparent);
        nbtCompound.putBoolean("Wavering", wavering);
    }

    public boolean isAvarice() {
        return this.avarice;
    }

    public void setAvarice(boolean bl) {
        this.avarice = bl;
        this.sync();
    }

    public boolean isInvisible() {
        return this.invisible && this.avarice;
    }

    public void setInvisible(boolean bl) {
        this.invisible = bl;
        this.sync();
    }

    public boolean isInvincible() {
        return this.invincible && this.avarice;
    }

    public void setInvincible(boolean bl) {
        this.invincible = bl;
        this.sync();
    }

    public boolean isWavering() {
        return this.wavering && this.avarice;
    }

    public void setWavering(boolean bl) {
        this.wavering = bl;
        this.sync();
    }

    public boolean isTransparent() {
        return this.transparent && this.avarice;
    }

    public void setTransparent(boolean bl) {
        this.transparent = bl;
        this.sync();
    }

    //CLIENT COMMUNICATION

    @Override
    public void handleC2SMessage(RegistryByteBuf registryByteBuf) {
        int[] set = registryByteBuf.readIntArray();

        this.avarice = set[0] == 1;
        this.invincible = set[1] == 1;
        this.invisible = set[2] == 1;
        this.wavering = set[3] == 1;
        this.transparent = set[4] == 1;
    }

    public void c2sAvarice(boolean bl){
        this.avarice = bl;
        this.serializeAndSend();
    }
    public void c2sInvincibility(boolean bl){
        this.invincible = bl;
        this.serializeAndSend();
    }
    public void c2sInvisibility(boolean bl){
        this.invisible = bl;
        this.serializeAndSend();
    }
    public void c2sWavering(boolean bl){
        this.wavering = bl;
        this.serializeAndSend();
    }
    public void c2sTransparency(boolean bl){
        this.transparent = bl;
        this.serializeAndSend();
    }

    public void serializeAndSend(){

        int ava = this.avarice ? 1 : 0;
        int invc = this.invincible ? 1 : 0;
        int invs = this.invisible ? 1 : 0;
        int wav = this.wavering ? 1 : 0;
        int trans = this.transparent ? 1 : 0;

        int[] set = {ava,invc,invs,wav,trans};

        sendC2SMessage(buf->{
            buf.writeIntArray(set);
        });
    }
}