package net.chemthunder.saxophone.core.index;

import net.chemthunder.saxophone.core.networking.c2s.ExplodeIvoryPayload;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

/**
 * @author Chemthunder
 */
public interface SaxoNetworking {
    static void registerTypes() {
        PayloadTypeRegistry.playC2S().register(ExplodeIvoryPayload.ID, ExplodeIvoryPayload.CODEC);
    }

    static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(ExplodeIvoryPayload.ID, new ExplodeIvoryPayload.Receiver());
    }

    @Environment(EnvType.CLIENT)
    static void registerS2CPackets() {}
}
