package net.chemthunder.saxophone.core.networking.c2s;

import net.chemthunder.saxophone.core.Saxophone;
import net.chemthunder.saxophone.core.util.keybinds.SaxophoneKeybindManager;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

/**
 * @author Chemthunder
 */
public record ExplodeIvoryPayload() implements CustomPayload {
    public static final CustomPayload.Id<ExplodeIvoryPayload> ID = new CustomPayload.Id<>(Saxophone.id("explode_ivory"));
    public static final PacketCodec<RegistryByteBuf, ExplodeIvoryPayload> CODEC = PacketCodec.unit(new ExplodeIvoryPayload());

    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static void send() {
        ClientPlayNetworking.send(new ExplodeIvoryPayload());
    }

    public static class Receiver implements ServerPlayNetworking.PlayPayloadHandler<ExplodeIvoryPayload> {
        public void receive(ExplodeIvoryPayload payload, ServerPlayNetworking.Context context) {
            PlayerEntity player = context.player();
            if (player != null) {
                if (Saxophone.isChem(player)) {
                    SaxophoneKeybindManager.explodeIvory(player);
                }
            }
        }
    }
}
