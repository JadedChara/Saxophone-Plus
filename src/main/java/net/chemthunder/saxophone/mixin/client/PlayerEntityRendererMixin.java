package net.chemthunder.saxophone.mixin.client;

import net.chemthunder.saxophone.core.Saxophone;
import net.chemthunder.saxophone.core.SaxophoneClient;
import net.chemthunder.saxophone.core.cca.deity.AvariceComponent;
import net.chemthunder.saxophone.core.util.ModUtils;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.util.SkinTextures;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author Chemthunder
 */
@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin {
    @Inject(
            method = "getTexture(Lnet/minecraft/client/network/AbstractClientPlayerEntity;)Lnet/minecraft/util/Identifier;",
            at = @At("HEAD"),
            cancellable = true
    )
    private void saxophone$changeSkinTexture(AbstractClientPlayerEntity player, CallbackInfoReturnable<Identifier> cir) {
        if (ModUtils.isAvarice(player)) {
            cir.setReturnValue(Saxophone.id("textures/entity/avarice.png"));
        }

        if (ModUtils.isEos(player)) {
            cir.setReturnValue(Saxophone.id("textures/entity/eos.png"));
        }
    }

    @Redirect(
            method = "renderArm",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/network/AbstractClientPlayerEntity;getSkinTextures()Lnet/minecraft/client/util/SkinTextures;"
            )
    )
    private SkinTextures saxophone$keepArmSkinConcurrentToChangedSkin(AbstractClientPlayerEntity instance) {
        SkinTextures defaultTextures = instance.getSkinTextures();
        if (ModUtils.isAvarice(instance)) {
            return new SkinTextures(
                    Saxophone.id("textures/entity/avarice.png"),
                    defaultTextures.textureUrl(),
                    defaultTextures.capeTexture(),
                    defaultTextures.elytraTexture(),
                    SkinTextures.Model.WIDE,
                    defaultTextures.secure()
            );
        }

        if (ModUtils.isEos(instance)) {
            return new SkinTextures(
                    Saxophone.id("textures/entity/eos.png"),
                    defaultTextures.textureUrl(),
                    defaultTextures.capeTexture(),
                    defaultTextures.elytraTexture(),
                    SkinTextures.Model.SLIM,
                    defaultTextures.secure()
            );
        }
        return defaultTextures;
    }
    @Inject(
            method="renderArm",
            at= @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/util/SkinTextures;texture()Lnet/minecraft/util/Identifier;",
                    shift = At.Shift.AFTER
            ),
            cancellable
            = true)
    private void saxophone$transparentArm(
            MatrixStack matrices,
            VertexConsumerProvider vertexConsumers,
            int light,
            AbstractClientPlayerEntity player,
            ModelPart arm,
            ModelPart sleeve,
            CallbackInfo ci
    ){
        Identifier huh = new SkinTextures(
                Saxophone.id("textures/entity/avarice.png"),
                player.getSkinTextures().textureUrl(),
                player.getSkinTextures().capeTexture(),
                player.getSkinTextures().elytraTexture(),
                SkinTextures.Model.WIDE,
                player.getSkinTextures().secure()
        ).texture();
        if(ModUtils.isAvarice(player)){
            if(AvariceComponent.KEY.get(player).isTransparent()){
                arm.render(matrices,
                        vertexConsumers.getBuffer(
                                SaxophoneClient
                                        .transientEffect
                                        .getRenderLayer(RenderLayer.getEntitySolid(huh))
                        ),
                        light,
                        OverlayTexture.DEFAULT_UV);
                sleeve.pitch = 0.0f;
                sleeve.render(matrices,
                        vertexConsumers.getBuffer(
                                SaxophoneClient
                                        .transientEffect
                                        .getRenderLayer(RenderLayer.getEntityTranslucent(huh))
                        ),
                        light,
                        OverlayTexture.DEFAULT_UV);
                ci.cancel();
            }
        }
    }
}