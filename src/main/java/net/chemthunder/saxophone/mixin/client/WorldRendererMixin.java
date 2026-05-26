package net.chemthunder.saxophone.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.systems.RenderSystem;
import net.chemthunder.saxophone.core.Saxophone;
import net.chemthunder.saxophone.core.util.ModUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author Chemthunder
 */
@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin {
    @Unique private static final Identifier EVENT_BORDER = Saxophone.id("textures/misc/event_border.png");

    @WrapOperation(
            method = "renderSky",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderTexture(ILnet/minecraft/util/Identifier;)V",
                    ordinal = 0
            )
    )
    private void saxophone$sunRetextures(int texture, Identifier id, Operation<Void> original) {
        original.call(texture, ModUtils.isInAsphodel(MinecraftClient.getInstance().player) ? Saxophone.id("textures/environment/asphodel_sun.png") : id);
    }

    @WrapOperation(
            method = "renderSky",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderTexture(ILnet/minecraft/util/Identifier;)V",
                    ordinal = 0
            )
    )
    private void saxophone$sunRetextures2(int texture, Identifier id, Operation<Void> original) {
        original.call(texture, ModUtils.isFollyActive(MinecraftClient.getInstance().world) ? Saxophone.id("textures/environment/shattered.png") : id);
    }

    @WrapOperation(
            method = "renderSky",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/world/ClientWorld;getSkyColor(Lnet/minecraft/util/math/Vec3d;F)Lnet/minecraft/util/math/Vec3d;"
            )
    )
    private Vec3d saxophone$skyColor(ClientWorld instance, Vec3d cameraPos, float tickDelta, Operation<Vec3d> original) {
        return ModUtils.isFollyActive(MinecraftClient.getInstance().world) ? Vec3d.ZERO : original.call(instance, cameraPos, tickDelta);
    }

    @WrapOperation(
            method = "renderWorldBorder",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderTexture(ILnet/minecraft/util/Identifier;)V"
            )
    )
    private void saxophone$worldBorderRetexture(int texture, Identifier id, Operation<Void> original) {
        original.call(texture, ModUtils.isFollyActive(MinecraftClient.getInstance().world) ? EVENT_BORDER : id);
    }

    @WrapOperation(
            method = "renderWorldBorder",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderColor(FFFF)V"
            )
    )
    private void saxophone$worldBorderRecolor(float red, float green, float blue, float alpha, Operation<Void> original) {
        if (ModUtils.isFollyActive(MinecraftClient.getInstance().world)) {
            original.call(0.8431372549019608f, 0.0f, 0.2823529411764706f, 9.5f);
        } else {
            original.call(red, green, blue, alpha);
        }
    }

    @Inject(method = "renderEntity", at = @At(value = "TAIL"))
    private void saxophone$fruitPunchEntities(Entity entity, double cameraX, double cameraY, double cameraZ, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, CallbackInfo ci) {
        if (!ModUtils.isAvarice(entity)) {
            if (ModUtils.isFollyActive(entity.getWorld())) {
                RenderSystem.setShaderColor(0.8431372549019608f, 0.0f, 0.2823529411764706f, 1.0f);
            }
        }
    }

    @WrapOperation(
            method = "renderSky",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderTexture(ILnet/minecraft/util/Identifier;)V",
                    ordinal = 1
            )
    )
    private void saxophone$moonRetexture(int texture, Identifier id, Operation<Void> original) {
        original.call(texture, ModUtils.isFollyActive(MinecraftClient.getInstance().world) ? Saxophone.id("textures/environment/shattered_moon_phases.png") : id);
    }

    @Inject(method = "setupTerrain", at = @At(value = "TAIL"))
    private void saxophone$fruitPunchTerrain(Camera camera, Frustum frustum, boolean hasForcedFrustum, boolean spectator, CallbackInfo ci) {
        if (ModUtils.isFollyActive(MinecraftClient.getInstance().world)) {
            RenderSystem.setShaderColor(0.8431372549019608f, 0.0f, 0.2823529411764706f, 1.0f);
        }
    }
}