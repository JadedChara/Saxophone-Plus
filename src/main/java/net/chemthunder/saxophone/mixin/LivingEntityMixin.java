package net.chemthunder.saxophone.mixin;

import net.chemthunder.saxophone.core.Saxophone;
import net.chemthunder.saxophone.core.cca.deity.AvariceComponent;
import net.chemthunder.saxophone.core.cca.entity.ArchitectComponent;
import net.chemthunder.saxophone.core.cca.entity.InsistenceComponent;
import net.chemthunder.saxophone.core.cca.entity.RevenantDeathAnimationComponent;
import net.chemthunder.saxophone.core.item.AuthoritysObituaryItem;
import net.chemthunder.saxophone.core.item.MartyrdomItem;
import net.chemthunder.saxophone.core.item.RevenantEffigyItem;
import net.chemthunder.saxophone.core.util.ModUtils;
import net.minecraft.entity.Attackable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

/**
 * @author Chemthunder
 */
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements Attackable {
    @Shadow protected abstract void clearPotionSwirls();

    @Shadow @Final private static TrackedData<List<ParticleEffect>> POTION_SWIRLS;

    @Shadow @Final private static TrackedData<Boolean> POTION_SWIRLS_AMBIENT;

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "tryUseTotem", at = @At("HEAD"), cancellable = true)
    private void saxophone$revenantEffigy(DamageSource source, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity living = (LivingEntity) (Object) this;

        if (living instanceof PlayerEntity player) {
            AvariceComponent avarice = AvariceComponent.KEY.get(player);

            if (!avarice.isAvarice()) {
                if (player.getOffHandStack().getItem() instanceof RevenantEffigyItem) {
                    if (Saxophone.isScarlet(player)) {
                        if (!RevenantDeathAnimationComponent.KEY.get(player).animationIsActive()) {
                            RevenantDeathAnimationComponent.KEY.get(player).beginAnimation();
                            player.setHealth(player.getMaxHealth());
                            player.getOffHandStack().decrement(1);
                            cir.setReturnValue(true);
                        } else {
                            cir.setReturnValue(true);
                        }
                    }
                }
            }
        }
    }

    @Inject(method = "tryUseTotem", at = @At("HEAD"), cancellable = true)
    private void saxophone$authorityObituary(DamageSource source, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity living = (LivingEntity) (Object) this;

        if (source.getAttacker() instanceof PlayerEntity player) {
            if (player.getOffHandStack().getItem() instanceof AuthoritysObituaryItem i) {
                cir.setReturnValue(true);
                i.getKillEffect(player, living, player.getOffHandStack(), player.getWorld());
            }
        }
    }

    @Inject(method = "tryUseTotem", at = @At("HEAD"), cancellable = true)
    private void saxophone$martyrdom(DamageSource source, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity living = (LivingEntity) (Object) this;

        if (source.getAttacker() instanceof PlayerEntity player) {
            if (player.getMainHandStack().getItem() instanceof MartyrdomItem i) {
                cir.setReturnValue(true);
                i.getPermakillEffect(player, living, player.getWorld());
            }
        }
    }

    @Inject(method = "applyMovementInput", at = @At("HEAD"), cancellable = true)
    private void saxophone$cancelMovement(Vec3d movementInput, float slipperiness, CallbackInfoReturnable<Vec3d> cir) {
        LivingEntity living = (LivingEntity) (Object) this;
        if (InsistenceComponent.KEY.get(living).getActiveTicks() > 0) {
            cir.setReturnValue(Vec3d.ZERO);
        }
    }

    @Inject(method = "applyFluidMovingSpeed", at = @At("HEAD"), cancellable = true)
    private void saxophone$cancelFluidMovement(double gravity, boolean falling, Vec3d motion, CallbackInfoReturnable<Vec3d> cir) {
        LivingEntity living = (LivingEntity) (Object) this;
        if (InsistenceComponent.KEY.get(living).getActiveTicks() > 0) {
            cir.setReturnValue(Vec3d.ZERO);
        }
    }

    //successful...
    @Redirect(method = "fall",at= @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;" +
            "spawnParticles(Lnet/minecraft/particle/ParticleEffect;DDDIDDDD)I"))
    private int saxophone$cancelFallParticles(ServerWorld instance, ParticleEffect particle, double x, double y,
                                              double z, int count, double deltaX, double deltaY, double deltaZ, double speed){
        if((LivingEntity)(Object)this instanceof PlayerEntity player){
            if (ModUtils.isAvarice(player)) {
                return 0;
            }else if(ArchitectComponent.KEY.get(player).hasFlair() && (Saxophone.isNightstrike(player)||Saxophone.isChem(player))){
                return 0;
            }else{
                instance.spawnParticles(particle, x, y, z, count, deltaX, deltaY, deltaZ, speed);
            }
        }
        return count;
    }

    //doesn't bloody work yet. Seems to be multiple checks, not just here...
    @Redirect(method = "baseTick",at= @At(value = "INVOKE", target = "Lnet/minecraft/world/World;addParticle" +
            "(Lnet/minecraft/particle/ParticleEffect;DDDDDD)V"))
    private void saxophone$cancelWaterParticles(World instance, ParticleEffect parameters, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        if ((LivingEntity) (Object) this instanceof PlayerEntity player) {
            if (ModUtils.isAvarice(player)) {
                return;
            }else if(ArchitectComponent.KEY.get(player).hasFlair() && (Saxophone.isNightstrike(player)||Saxophone.isChem(player))){
                return;
            }else{
                instance.addParticle(parameters, x, y, z, velocityX, velocityY, velocityZ);
            }
        }
    }

    //works like a dream...
    @Inject(method = "tickStatusEffects",at= @At(value = "INVOKE", target = "Lnet/minecraft/world/World;addParticle" +
            "(Lnet/minecraft/particle/ParticleEffect;DDDDDD)V"),cancellable = true)
    private void saxophone$cancelPotionParticles(CallbackInfo ci) {
        if ((LivingEntity) (Object) this instanceof PlayerEntity player) {
            if (ModUtils.isAvarice(player)) {
                this.clearPotionSwirls();
                ci.cancel();
            }else if(ArchitectComponent.KEY.get(player).hasFlair() && (Saxophone.isNightstrike(player)||Saxophone.isChem(player))){
                this.clearPotionSwirls();
                ci.cancel();
            }
        }
    }
    //needed for `cancelPotionParticles`
    @Inject(method="updatePotionSwirls",at=@At("TAIL"),cancellable = true)
    private void saxophone$suspendPotionSwirlTracking(CallbackInfo ci){
        if ((LivingEntity) (Object) this instanceof PlayerEntity player) {
            if (ModUtils.isAvarice(player)) {
                this.dataTracker.set(POTION_SWIRLS, List.of());
                this.dataTracker.set(POTION_SWIRLS_AMBIENT,false);
                ci.cancel();
            }else if(ArchitectComponent.KEY.get(player).hasFlair() && (Saxophone.isNightstrike(player)||Saxophone.isChem(player))){
                this.dataTracker.set(POTION_SWIRLS, List.of());
                this.dataTracker.set(POTION_SWIRLS_AMBIENT,false);
                ci.cancel();
            }
        }
    }
    //FabricLoader.getInstance().isModLoaded("xaerominimap");

}