package net.chemthunder.saxophone.impl.networking.c2s;

import net.chemthunder.saxophone.impl.Saxophone;
import net.chemthunder.saxophone.impl.util.keybinds.SaxophoneKeybindManager;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import org.joml.Vector3f;


public record UpdateAvaricePayload(Vector3f toggles) implements CustomPayload {
    public static final CustomPayload.Id<UpdateAvaricePayload> ID = new CustomPayload.Id<>(Saxophone.id(
            "update_avarice"));
    public static final PacketCodec<RegistryByteBuf, UpdateAvaricePayload> CODEC =
            PacketCodec.tuple(PacketCodecs.VECTOR3F,UpdateAvaricePayload::toggles,UpdateAvaricePayload::new);

    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static void send(Vector3f toggles) {
        ClientPlayNetworking.send(new UpdateAvaricePayload(toggles));
    }

    public static class Receiver implements ServerPlayNetworking.PlayPayloadHandler<UpdateAvaricePayload> {
        public void receive(UpdateAvaricePayload payload, ServerPlayNetworking.Context context) {
            PlayerEntity player = context.player();
            if (player != null) {
            }
        }
    }
}
