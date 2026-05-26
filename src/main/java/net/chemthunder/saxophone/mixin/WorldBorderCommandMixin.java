package net.chemthunder.saxophone.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.chemthunder.saxophone.core.cca.world.AvariceEventComponent;
import net.chemthunder.saxophone.core.util.ModUtils;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.command.WorldBorderCommand;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Vec2f;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WorldBorderCommand.class)
public abstract class WorldBorderCommandMixin {

    @WrapMethod(method = "executeSet")
    private static int saxophone$preventCommandUseWhileEventActive(ServerCommandSource source, double distance, long time, Operation<Integer> original) {
        World world = source.getWorld();

        if (ModUtils.isFollyActive(world)) {
            source.sendFeedback(() -> Text.literal("Unable to change world border parameters!").formatted(Formatting.RED), true);
            return original.call(source, AvariceEventComponent.WORLD_BORDER_SIZE, 0L);
        }
        return original.call(source, distance, time);
    }

    @Inject(method = "executeCenter", at = @At(value = "HEAD"), cancellable = true)
    private static void saxophone$preventCenter(ServerCommandSource source, Vec2f pos, CallbackInfoReturnable<Integer> cir) {
        World world = source.getWorld();

        if (ModUtils.isFollyActive(world)) {
            source.sendFeedback(() -> Text.literal("Unable to change world border parameters!").formatted(Formatting.RED), true);
            cir.cancel();
        }
    }
}
