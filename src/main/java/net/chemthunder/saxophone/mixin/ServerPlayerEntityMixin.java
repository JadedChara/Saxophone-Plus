package net.chemthunder.saxophone.mixin;

import com.mojang.authlib.GameProfile;
import net.chemthunder.saxophone.impl.Saxophone;
import net.chemthunder.saxophone.impl.cca.entity.ArchitectComponent;
import net.chemthunder.saxophone.impl.util.ModUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author Chemthunder
 */
@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity {
    public ServerPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile) {
        super(world, pos, yaw, gameProfile);
    }

    @Inject(method = "getPlayerListName", at = @At("TAIL"), cancellable = true)
    private void saxophone$replaceNameOnTablist(CallbackInfoReturnable<Text> cir) {
        //ServerPlayerEntity player = (ServerPlayerEntity)(Object)this;
        if (ModUtils.isAvarice(this)) {
            cir.setReturnValue(Text.literal("Avarice").withColor(0xFF003C).formatted(Formatting.ITALIC).formatted(Formatting.OBFUSCATED));
        } else if(ArchitectComponent.KEY.get(this).hasFlair() && Saxophone.isNightstrike(this)){
            cir.setReturnValue(
                    Text.literal("The Reaper").withColor(0x3ED6BA).formatted(Formatting.ITALIC));
        } else if(ArchitectComponent.KEY.get(this).hasFlair() && Saxophone.isChem(this)){
            cir.setReturnValue(
                    Text.literal("The Godmaker").withColor(0xAF2CD4).formatted(Formatting.ITALIC));
        } else if(ArchitectComponent.KEY.get(this).hasFlair() && Saxophone.isScarlet(this)){
            cir.setReturnValue(
                    Text.literal("Ennui").withColor(0xD690A9).formatted(Formatting.ITALIC));
        } else if(ArchitectComponent.KEY.get(this).hasFlair() && Saxophone.isHstar(this)){
            cir.setReturnValue(
                    Text.literal("Riftmaster").withColor(0xAD8C00).formatted(Formatting.ITALIC));
        } else if(ArchitectComponent.KEY.get(this).hasFlair() && Saxophone.isHeartless(this)){
            cir.setReturnValue(
                    Text.literal("Heartless").withColor(0x730000).formatted(Formatting.ITALIC));
        }
    }
}