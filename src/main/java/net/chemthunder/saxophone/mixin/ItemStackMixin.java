package net.chemthunder.saxophone.mixin;

import net.chemthunder.saxophone.core.util.ModUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author Chemthunder
 */
@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Inject(method = "use", at = @At(value = "HEAD"), cancellable = true)
    private void saxo$denyUse(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
        if (ModUtils.isInAsphodel(user) && !ModUtils.isAvarice(user)) {
            cir.setReturnValue(TypedActionResult.fail(user.getStackInHand(hand)));
        }
    }

    @Inject(method = "useOnBlock", at = @At(value = "HEAD"), cancellable = true)
    private void saxo$denyUseOnBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        if (ModUtils.isInAsphodel(context.getPlayer()) && !ModUtils.isAvarice(context.getPlayer())) {
            cir.setReturnValue(ActionResult.FAIL);
        }
    }

    @Inject(method = "useOnEntity", at = @At(value = "HEAD"), cancellable = true)
    private void saxo$denyUseOnEntity(PlayerEntity user, LivingEntity entity, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (ModUtils.isInAsphodel(user) && !ModUtils.isAvarice(user)) {
            cir.setReturnValue(ActionResult.FAIL);
        }
    }
}
