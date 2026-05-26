package net.chemthunder.saxophone.mixin;

import com.mojang.brigadier.arguments.ArgumentType;
import net.chemthunder.saxophone.core.util.command.ExternalModArgumentType;
import net.chemthunder.saxophone.core.util.command.ItemArgumentType;
import net.minecraft.command.argument.ArgumentTypes;
import net.minecraft.command.argument.serialize.ArgumentSerializer;
import net.minecraft.command.argument.serialize.ConstantArgumentSerializer;
import net.minecraft.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ArgumentTypes.class)
public abstract class ArgumentTypesMixin {
    @Shadow
    private static <A extends ArgumentType<?>, T extends ArgumentSerializer.ArgumentTypeProperties<A>> ArgumentSerializer<A, T> register(Registry<ArgumentSerializer<?, ?>> registry, String id, Class<? extends A> clazz, ArgumentSerializer<A, T> serializer) {
        throw new AssertionError();
    }

    @Inject(method = "register(Lnet/minecraft/registry/Registry;)Lnet/minecraft/command/argument/serialize/ArgumentSerializer;", at = @At("TAIL"))
    private static void mindsEye$addArguments(Registry<ArgumentSerializer<?, ?>> registry, CallbackInfoReturnable<ArgumentSerializer<?, ?>> cir) {
        register(registry, "saxophone:item", ItemArgumentType.class, ConstantArgumentSerializer.of(ItemArgumentType::itemStack));
        register(registry, "saxophone:external", ExternalModArgumentType.class, ConstantArgumentSerializer.of(ExternalModArgumentType::itemStack));
    }
}
