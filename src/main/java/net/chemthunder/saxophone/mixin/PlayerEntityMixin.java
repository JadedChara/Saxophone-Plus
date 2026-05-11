package net.chemthunder.saxophone.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.chemthunder.saxophone.impl.Saxophone;
import net.chemthunder.saxophone.impl.cca.deity.AvariceComponent;
import net.chemthunder.saxophone.impl.cca.entity.ArchitectComponent;
import net.chemthunder.saxophone.impl.index.data.SaxoDamageSources;
import net.chemthunder.saxophone.impl.index.tag.SaxoDamageTypeTags;
import net.chemthunder.saxophone.impl.util.ModUtils;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author Chemthunder
 */
@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
    @Inject(method = "shouldRenderName", at = @At("HEAD"), cancellable = true)
    private void saxophone$disableNameRendering(CallbackInfoReturnable<Boolean> cir) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        if (ModUtils.isAvarice(player)) {
            cir.setReturnValue(false);
        }
    }

    @ModifyReturnValue(method = "getDisplayName", at = @At("RETURN"))
    private Text saxophone$changeUsername(Text original) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        MutableText temp = original.copy();

        //Avarice
        if (ModUtils.isAvarice(player)) {
            temp =
                    Text.literal("Avarice").withColor(0xd70048).formatted(Formatting.ITALIC).formatted(Formatting.OBFUSCATED);
            if( AvariceComponent.KEY.get(player).isWavering()) {
                temp = Text
                        .literal("Avarice")
                        .setStyle(ModUtils.nameEffect(Text.of("Avarice")))
                        .withColor(0xd70048)
                        .formatted(Formatting.ITALIC)
                        .formatted(Formatting.OBFUSCATED);

            }
        }

        //Eos
        else if (ModUtils.isEos(player)) {
            temp =
                    Text.literal("E").withColor(0xa16252).append(Text.literal("o").withColor(0xc08f75).append(Text.literal("s").withColor(0xffca8e))).formatted(Formatting.ITALIC);
        }

        //YTNightstrike
        else if(ArchitectComponent.KEY.get(player).hasFlair() && Saxophone.isNightstrike(player)) {
            temp = Text.literal("The Reaper").withColor(0x3ED6BA).formatted(Formatting.ITALIC);
            if(ArchitectComponent.KEY.get(player).hasWavering()){
                temp = Text
                        .literal("The Reaper")
                        .setStyle(ModUtils.nameEffect(Text.of("The Reaper")))
                        .withColor(0x3ED6BA)
                        .formatted(Formatting.ITALIC);
            }
        }

        //Chemthunder
        else if(ArchitectComponent.KEY.get(player).hasFlair() && Saxophone.isChem(player)) {
            temp = Text.literal("The Godmaker").withColor(0xAF2CD4).formatted(Formatting.ITALIC);
            if(ArchitectComponent.KEY.get(player).hasWavering()){
                temp = Text
                        .literal("The Godmaker")
                        .setStyle(ModUtils.nameEffect(Text.of("The Godmaker")))
                        .withColor(0xAF2CD4)
                        .formatted(Formatting.ITALIC);
            }
        }

        //Scarlet
        else if(ArchitectComponent.KEY.get(player).hasFlair() && Saxophone.isScarlet(player)) {
            temp = Text.literal("Ennui").withColor(0xD690A9).formatted(Formatting.ITALIC);
            if(ArchitectComponent.KEY.get(player).hasWavering()){
                temp = Text
                        .literal("Ennui")
                        .setStyle(ModUtils.nameEffect(Text.of("Ennui")))
                        .withColor(0xD690A9)
                        .formatted(Formatting.ITALIC);
            }
        }

        //HStar
        else if(ArchitectComponent.KEY.get(player).hasFlair() && Saxophone.isHstar(player)) {
            temp = Text.literal("Riftmaster").withColor(0xAD8C00).formatted(Formatting.ITALIC);
            if(ArchitectComponent.KEY.get(player).hasWavering()){
                temp = Text
                        .literal("Riftmaster")
                        .setStyle(ModUtils.nameEffect(Text.of("Riftmaster")))
                        .withColor(0xAD8C00)
                        .formatted(Formatting.ITALIC);
            }
        }

        //Heartless
        else if(ArchitectComponent.KEY.get(player).hasFlair() && Saxophone.isHeartless(player)) {
            temp = Text.literal("Heartless").withColor(0x730000).formatted(Formatting.ITALIC);
            if(ArchitectComponent.KEY.get(player).hasWavering()){
                temp = Text
                        .literal("Heartless")
                        .setStyle(ModUtils.nameEffect(Text.of("Heartless")))
                        .withColor(0x730000)
                        .formatted(Formatting.ITALIC);
            }
        }
        return temp;
    }

    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    private void saxophone$negateDamageInAsphodel(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        PlayerEntity player = (PlayerEntity) (Object) this;

        if (ModUtils.isInAsphodel(player)) {
            if (!source.isIn(SaxoDamageTypeTags.ASPHODEL_BYPASS)) {
                cir.setReturnValue(false);
            }
        }
    }

    @Inject(method="isInvulnerableTo",at=@At("TAIL"),cancellable = true)
    private void saxophone$negateDamageAvarice(DamageSource damageSource, CallbackInfoReturnable<Boolean> cir){
        if (AvariceComponent.KEY.get(this).isInvincible()) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "damage", at = @At(value = "HEAD"), cancellable = true)
    private void saxophone$negateDamageAvarice(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        if (!source.isOf(SaxoDamageSources.IVORY_EXPLODE)) {
            if (AvariceComponent.KEY.get(player).isInvincible()) {
                cir.setReturnValue(false);
            }
        }
    }

    @Inject(method = "damage", at = @At(value = "HEAD"), cancellable = true)
    private void saxophone$negateDamageEos(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        if (ModUtils.isEos(player)) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "attack", at = @At(value = "HEAD"), cancellable = true)
    private void saxophone$negateAttacksWhilstInvincible(Entity target, CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;

        if (AvariceComponent.KEY.get(player).isInvincible() && target instanceof PlayerEntity) {
            ci.cancel();
        }
    }

    @Inject(method = "playStepSound", at = @At("HEAD"), cancellable = true)
    private void saxophone$removeStepSounds(BlockPos pos, BlockState state, CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;

        if (AvariceComponent.KEY.get(player).isInvincible() ||
                (
                        ArchitectComponent.KEY.get(player).hasFlair()
                )
        ) {
            ci.cancel();
        }
    }
}