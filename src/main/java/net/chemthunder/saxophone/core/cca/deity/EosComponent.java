package net.chemthunder.saxophone.core.cca.deity;

import net.acoyt.acornlib.api.util.MiscUtils;
import net.chemthunder.saxophone.core.Saxophone;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.registry.RegistryWrapper;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;
import org.ladysnake.cca.api.v3.entity.C2SSelfMessagingComponent;

public class EosComponent implements AutoSyncedComponent, CommonTickingComponent, C2SSelfMessagingComponent {
    public static final ComponentKey<EosComponent> KEY = MiscUtils.getOrCreateKey(Saxophone.id("eos"), EosComponent.class);
    private final PlayerEntity player;

    private boolean eos = false;
    private boolean flight = false;
    private boolean invincible = false;
    private boolean invisible = false;
    private boolean wavering = false;

    public EosComponent(PlayerEntity player) {
        this.player = player;
    }

    public void sync() {
        KEY.sync(player);
    }

    public void tick() {
        if (this.isEos()) {
            if (this.canFly()) {
                this.player.getAbilities().allowFlying = true;
            }
        }
    }

    public void readFromNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        this.eos = nbtCompound.getBoolean("Eos");
        this.flight = nbtCompound.getBoolean("Flight");
        this.invincible = nbtCompound.getBoolean("Invincible");
        this.invisible = nbtCompound.getBoolean("Invisible");
        this.wavering = nbtCompound.getBoolean("Wavering");
    }

    public void writeToNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        nbtCompound.putBoolean("Eos", eos);
        nbtCompound.putBoolean("Flight", flight);
        nbtCompound.putBoolean("Invincible",invincible);
        nbtCompound.putBoolean("Invisible",invisible);
        nbtCompound.putBoolean("Wavering",wavering);
    }

    public boolean isEos() {
        return this.eos;
    }

    public boolean canFly() {
        return this.flight && this.eos;
    }

    public boolean isInvincible(){
        return this.invincible && this.eos;
    }

    public boolean isInvisible() {
        return this.invisible && this.eos;
    }

    public boolean hasWavering() {
        return this.wavering && this.eos;
    }

    public void setEos(boolean bl) {
        this.eos = bl;
        this.sync();
    }

    public void setFlight(boolean bl) {
        this.flight = bl;
        this.sync();
    }

    public void setInvincible(boolean bl) {
        this.invincible = bl;
        this.sync();
    }

    public void setInvisible(boolean bl) {
        this.invisible = bl;
        this.sync();
    }

    public void setWavering(boolean bl){
        this.wavering = bl;
        this.sync();
    }

    public void set(boolean eos, boolean flight) {
        this.eos = eos;
        this.flight = flight;
        this.sync();
    }

    //CLIENT COMMUNICATION

    @Override
    public void handleC2SMessage(RegistryByteBuf registryByteBuf) {
        if(!Saxophone.isChem(this.player)){
            return;
        }
        int[] set = registryByteBuf.readIntArray();
        this.eos = set[0] == 1;
        this.flight = set[1] == 1;
        this.invincible = set[2] == 1;
        this.invisible = set[3] == 1;
        this.wavering = set[4] == 1;
    }

    public void c2sEos(boolean bl){
        this.eos = bl;
        this.serializeAndSend();
    }
    public void c2sFlight(boolean bl){
        this.flight = bl;
        this.serializeAndSend();
    }
    public void c2sInvincible(boolean bl){
        this.invincible = bl;
        this.serializeAndSend();
    }
    public void c2sInvisible(boolean bl){
        this.invisible = bl;
        this.serializeAndSend();
    }
    public void c2sWavering(boolean bl){
        this.wavering = bl;
        this.serializeAndSend();
    }

    public void serializeAndSend(){

        int e = this.eos ? 1 : 0;
        int f = this.flight ? 1 : 0;
        int g = this.invincible ? 1 : 0;
        int h = this.invisible ? 1 : 0;
        int i = this.wavering ? 0 : 1;


        int[] set = {e,f,g,h,i};

        sendC2SMessage(buf->{
            buf.writeIntArray(set);
        });
    }
}
