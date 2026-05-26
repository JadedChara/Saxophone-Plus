package net.chemthunder.saxophone.mixin.client;

import net.chemthunder.saxophone.core.util.ModUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.DimensionEffects;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DimensionEffects.Overworld.class)
public abstract class DimensionEffectsMixin {
    @Inject(method = "adjustFogColor", at = @At("HEAD"), cancellable = true)
    private void saxophone$fogcolor(Vec3d par1, float par2, CallbackInfoReturnable<Vec3d> cir) {
        if (ModUtils.isFollyActive(MinecraftClient.getInstance().world)) {
            cir.setReturnValue(Vec3d.ZERO);
        }
    }
}
