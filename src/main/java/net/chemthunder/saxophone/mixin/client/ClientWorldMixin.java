package net.chemthunder.saxophone.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.chemthunder.saxophone.core.cca.world.AvariceEventComponent;
import net.chemthunder.saxophone.core.util.ModUtils;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ClientWorld.class)
public abstract class ClientWorldMixin {

    @ModifyReturnValue(method = "getSkyColor", at = @At("RETURN"))
    private Vec3d saxophone$skycolor(Vec3d original) {
        ClientWorld world = (ClientWorld) (Object) this;

        if (ModUtils.isFollyActive(world)) {
            return Vec3d.ZERO;
        }
        return original;
    }

    @ModifyReturnValue(method = "getSkyBrightness", at = @At("RETURN"))
    private float saxophone$starBrightness(float original) {
        ClientWorld world = (ClientWorld) (Object) this;

        if (ModUtils.isFollyActive(world)) {
            return AvariceEventComponent.KEY.get(world).getShade() ? 0.0f : 0.9f;
        }
        return original;
    }
}
