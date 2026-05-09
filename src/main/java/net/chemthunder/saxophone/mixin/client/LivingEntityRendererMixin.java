package net.chemthunder.saxophone.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.mojang.blaze3d.systems.RenderSystem;
import net.chemthunder.saxophone.impl.Saxophone;
import net.chemthunder.saxophone.impl.SaxophoneClient;
import net.chemthunder.saxophone.impl.cca.deity.AvariceComponent;
import net.chemthunder.saxophone.impl.cca.entity.ArchitectComponent;
import net.chemthunder.saxophone.impl.util.ModUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

/**
 * @author Chemthunder
 */
@Mixin(value = LivingEntityRenderer.class, priority = 3500)
public abstract class LivingEntityRendererMixin<T extends LivingEntity, M extends EntityModel<T>> extends EntityRenderer<T> implements FeatureRendererContext<T, M> {
    protected LivingEntityRendererMixin(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Inject(
            method = "render(Lnet/minecraft/entity/LivingEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    private void saxophone$toggleInvis(T livingEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        if (livingEntity instanceof PlayerEntity player) {
            AvariceComponent avarice = AvariceComponent.KEY.get(player);

            if (avarice.isInvisible()) {
                ci.cancel();
            }else if(avarice.isTransparent()){
                //pass in core shader via Satin...
            }
        }
    }

    @ModifyReturnValue(method = "getShadowRadius(Lnet/minecraft/entity/LivingEntity;)F", at = @At("RETURN"))
    private float saxophone$deleteShadow(float original) {
        Entity en = MinecraftClient.getInstance().getCameraEntity();

        if (en instanceof PlayerEntity player) {
            return (
                    AvariceComponent.KEY.get(player).isInvisible()
                            ||AvariceComponent.KEY.get(player).isTransparent()
                            ||(ArchitectComponent.KEY.get(player).hasFlair() && Saxophone.isChem(player))
            ) ? 0f : original;
        }
        return original;
    }

    @Nullable
    @Inject(method="getRenderLayer", at=@At("RETURN"),cancellable = true)
    protected void getRenderLayerPatch(T entity, boolean showBody, boolean translucent, boolean showOutline, CallbackInfoReturnable<RenderLayer> cir
    ){
        RenderLayer b = cir.getReturnValue();
        if(entity instanceof PlayerEntity p){
            if(ModUtils.isAvarice(p)){
                if(AvariceComponent.KEY.get(p).isTransparent()) {
                    cir.setReturnValue(b == null ? null : SaxophoneClient.transientEffect.getRenderLayer(b));
                }else{
                    cir.setReturnValue(b == null ? null : b);
                }
            }else if(ArchitectComponent.KEY.get(p).hasFX() && Saxophone.isNightstrike(p)){
                //YTNightstrike glitching FX
                cir.setReturnValue(b == null ? null : SaxophoneClient.glitchingEffect.getRenderLayer(b));
            }else if(ArchitectComponent.KEY.get(p).hasFX() && Saxophone.isChem(p)){
                //copy of the FX unless Chem has a different request
                cir.setReturnValue(b == null ? null : SaxophoneClient.glitchingEffect.getRenderLayer(b));
            }else{
                cir.setReturnValue(b == null ? null : b);
            }
        }else{
            cir.setReturnValue(b == null ? null : b);
        }

    }


    /**
     * (Method pulled from AceLib, as that library is not yet production-ready.)
     * Facilitates the same shader effect being applied to all the model's layers, including held items.
     * @param args -
     *(MatrixStack, VertexConsumerProvider, int, Entity,
     *float,float,float,float,float, float)
     * @Author YTNightstrike
     */
    @ModifyArgs(
            method= "render(Lnet/minecraft/entity/LivingEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V",
            at= @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/feature/FeatureRenderer;render" +
                    "(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;" +
                    "ILnet/minecraft/entity/Entity;FFFFFF)V")
    )
    public void renderHook(
            Args args
    ){
		/*
		Args(
			net.minecraft.client.util.math.MatrixStack,
			net.minecraft.client.render.VertexConsumerProvider,
			int,
			net.minecraft.entity.Entity,
			float, float, float, float, float, float
		)
		*/

        VertexConsumerProvider fVcp = args.get(1);
        T entityStore = args.get(3);
        VertexConsumerProvider avariceProvider = new VertexConsumerProvider() {
            @Override
            public VertexConsumer getBuffer(RenderLayer renderLayer) {
                return (fVcp.getBuffer(SaxophoneClient.transientEffect.getRenderLayer(renderLayer)));
            }
        };

        VertexConsumerProvider nightstrikeProvider = new VertexConsumerProvider() {
            @Override
            public VertexConsumer getBuffer(RenderLayer renderLayer) {
                return (fVcp.getBuffer(SaxophoneClient.glitchingEffect.getRenderLayer(renderLayer)));
            }
        };
        VertexConsumerProvider chemthunderProvider = new VertexConsumerProvider() {
            @Override
            public VertexConsumer getBuffer(RenderLayer renderLayer) {
                return (fVcp.getBuffer(SaxophoneClient.glitchingEffect.getRenderLayer(renderLayer)));
            }
        };

        if(entityStore instanceof PlayerEntity) {
            if (ModUtils.isAvarice(entityStore) && AvariceComponent.KEY.get(entityStore).isTransparent()) {
                args.set(1, avariceProvider);
            } else if (ArchitectComponent.KEY.get(entityStore).hasFX() && Saxophone.isNightstrike(entityStore)) {
                args.set(1, nightstrikeProvider);
            } else if (ArchitectComponent.KEY.get(entityStore).hasFX() && Saxophone.isChem(entityStore)) {
                args.set(1, chemthunderProvider);
            } else {
                args.set(1, fVcp);
            }
        }
    }
}