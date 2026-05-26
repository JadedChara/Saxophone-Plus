package net.chemthunder.saxophone.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.chemthunder.saxophone.core.cca.deity.AvariceComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author Chemthunder
 */
@Mixin(Entity.class)
public abstract class EntityMixin {
    @ModifyReturnValue(method = "getTeamColorValue", at = @At("RETURN"))
    private int saxophone$customTeamColors(int original) {
        Entity entity = (Entity) (Object) this;

        if (entity instanceof PlayerEntity living) {
            AvariceComponent component = AvariceComponent.KEY.get(living);

            if (component.isAvarice()) {
                return 0xFFd70048;
            }
        }
        return original;
    }

    @Inject(method = "spawnSprintingParticles", at = @At(value = "HEAD"), cancellable = true)
    private void saxophone$disableSprintParticles(CallbackInfo ci) {
        Entity entity = (Entity) (Object) this;

        if (entity instanceof PlayerEntity player) {
            if (AvariceComponent.KEY.get(player).isInvisible()) {
                ci.cancel();
            }
        }
    }
}