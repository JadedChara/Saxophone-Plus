package net.chemthunder.saxophone.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.chemthunder.saxophone.core.util.ModUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.FogShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author Chemthunder
 */
@Mixin(BackgroundRenderer.class)
public abstract class BackgroundRendererMixin {
    @Inject(method = "applyFog", at = @At("HEAD"), cancellable = true)
    private static void saxophone$customFog(Camera camera, BackgroundRenderer.FogType fogType, float viewDistance, boolean thickFog, float tickDelta, CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();

        if (ModUtils.isInAsphodel(client.player)) {
            RenderSystem.setShaderFogStart(1);
            RenderSystem.setShaderFogEnd(6);
            RenderSystem.setShaderFogShape(FogShape.SPHERE);
            RenderSystem.setShaderFogColor(0, 0, 0);
            ci.cancel();
        }
    }
}