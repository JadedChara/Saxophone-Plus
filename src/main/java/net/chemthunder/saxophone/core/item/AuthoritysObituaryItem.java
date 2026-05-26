package net.chemthunder.saxophone.core.item;

import com.nitron.nitrogen.util.interfaces.ScreenShaker;
import net.chemthunder.saxophone.api.extendable.SaxophoneItem;
import net.chemthunder.saxophone.core.client.particle.ShockwaveParticleEffect;
import net.chemthunder.saxophone.core.index.SaxoDataComponents;
import net.chemthunder.saxophone.core.index.SaxoParticles;
import net.chemthunder.saxophone.core.util.ModUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.List;

/**
 * @author Chemthunder
 */
public class AuthoritysObituaryItem extends SaxophoneItem {
    public AuthoritysObituaryItem(Settings settings) {
        super(settings.component(SaxoDataComponents.CHARGES, 3));
    }

    public Text getName(ItemStack stack) {
        MutableText text = Text.translatable("item.saxophone.authoritys_obituary");
        return super.getName(stack).copy().setStyle(ModUtils.nameEffect(text)).withColor(0xd70048);
    }

    public void getKillEffect(PlayerEntity player, LivingEntity target, ItemStack stack, World world) {
        List<Entity> shockWavedEntities = ModUtils.getEntitiesAroundEntity(target, 15);
        Box box = new Box(target.getBlockPos());

        for (Entity entity : shockWavedEntities) {
            if (entity instanceof LivingEntity living && !ModUtils.isAvarice(living)) {
                if (living != player) {
                    living.addVelocity(box.getCenter().subtract(entity.getPos()).multiply(-1f));
                    living.addVelocity(0, 3f, 0);
                    living.velocityModified = true;
                }
            }
        }

        if (world instanceof ServerWorld serverWorld) {
            serverWorld.spawnParticles(SaxoParticles.AVARICE_LIGHT,
                    target.getX(),
                    target.getY() + 0.5f,
                    target.getZ(),
                    15,
                    0,
                    0,
                    0,
                    0.4f
            );

            serverWorld.spawnParticles(SaxoParticles.AVARICE_SOUL,
                    target.getX(),
                    target.getY() + 0.5f,
                    target.getZ(),
                    35,
                    0,
                    0.6f,
                    0,
                    0.7f
            );

            serverWorld.spawnParticles(SaxoParticles.AVARICE_GLIMMER,
                    target.getX(),
                    target.getY() + 0.5f,
                    target.getZ(),
                    65,
                    0,
                    0,
                    0,
                    0.4f
            );

            serverWorld.spawnParticles(ParticleTypes.SMOKE,
                    target.getX(),
                    target.getY() + 0.5f,
                    target.getZ(),
                    65,
                    0,
                    0,
                    0,
                    0.1f
            );

            serverWorld.spawnParticles(new ShockwaveParticleEffect(
                    0xd70048,
                    15,
                    Direction.Axis.Y
                    ),
                    target.getX(),
                    target.getY() + 0.5f,
                    target.getZ(),
                    1,
                    0,
                    0,
                    0,
                    0.001f
            );

            serverWorld.getPlayers().forEach(serverPlayer -> {
                if (serverPlayer instanceof ScreenShaker shaker) {
                    shaker.addScreenShake(2.0f, 40);
                }

                serverPlayer.playSoundToPlayer(SoundEvents.ENTITY_WARDEN_DEATH, SoundCategory.PLAYERS, 1, 0.2f);

                if (target instanceof PlayerEntity) {
                    serverPlayer.sendMessage(Text.literal(target.getNameForScoreboard() + " was done away with"));
                }
            });

            if (stack.getOrDefault(SaxoDataComponents.CHARGES, 3) > 1) {
                stack.set(SaxoDataComponents.CHARGES, stack.getOrDefault(SaxoDataComponents.CHARGES, 3) - 1);
            } else {
                stack.decrement(1);
            }

            if (target.getServer() != null && !ModUtils.isAvarice(target)) {
                target.setHealth(target.getMaxHealth());
                ModUtils.teleportToAsphodel(target);
            }
        }
    }

    public int getItemBarColor(ItemStack stack) {
        return 0xd70048;
    }

    public int getItemBarStep(ItemStack stack) {
        return Math.round((float) stack.getOrDefault(SaxoDataComponents.CHARGES, 3) / 3 * 13);
    }

    public boolean isItemBarVisible(ItemStack stack) {
        return stack.getOrDefault(SaxoDataComponents.CHARGES, 3) != 3;
    }
}