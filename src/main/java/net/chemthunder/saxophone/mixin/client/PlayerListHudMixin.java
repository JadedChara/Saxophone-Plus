package net.chemthunder.saxophone.mixin.client;

import net.chemthunder.saxophone.core.cca.deity.EosComponent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Comparator;
import java.util.List;

@Mixin(PlayerListHud.class)
public abstract class PlayerListHudMixin {
    @Shadow @Final private MinecraftClient client;
    @Shadow @Final private static Comparator<PlayerListEntry> ENTRY_ORDERING;

    @Inject(method = "collectPlayerEntries", at = @At("HEAD"), cancellable = true)
    private void saxophone$removeEosFromList(CallbackInfoReturnable<List<PlayerListEntry>> cir) {
        if (this.client.player == null) return;
        cir.setReturnValue(
                this.client.player.networkHandler
                        .getListedPlayerListEntries()
                        .stream()
                        .filter(this::saxophone$isEos)
                        .sorted(ENTRY_ORDERING)
                        .toList()
        );
    }

    @Unique private boolean saxophone$isEos(PlayerListEntry entry) {
        ClientWorld world = this.client.world;
        if (world != null) {
            PlayerEntity player = world.getPlayerByUuid(entry.getProfile().getId());
            return player == null || !EosComponent.KEY.get(player).isEos();
        }

        return true;
    }
}
