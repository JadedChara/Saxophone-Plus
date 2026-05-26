package net.chemthunder.saxophone.mixin.client;

import net.chemthunder.saxophone.core.cca.deity.AvariceComponent;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author JadedChara
 * 
 */
@Mixin(EntityRenderDispatcher.class)
public abstract class EntityRenderDispatcherMixin {
    @Inject(
            method="renderHitbox",
            at=@At("HEAD"),
            cancellable = true
    )
    private static void saxophone$suspendHitbox(MatrixStack matrices, VertexConsumer vertices, Entity entity, float tickDelta, float red, float green, float blue, CallbackInfo ci){
        if (entity instanceof PlayerEntity player) {
            AvariceComponent component = AvariceComponent.KEY.get(player);
            if (component.isInvisible()) {
                ci.cancel();
            }
        }
    }
}