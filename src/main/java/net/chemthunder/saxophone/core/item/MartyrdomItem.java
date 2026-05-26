package net.chemthunder.saxophone.core.item;

import com.nitron.nitrogen.util.interfaces.ScreenShaker;
import net.acoyt.acornlib.api.item.CritEffectItem;
import net.acoyt.acornlib.api.item.CustomHitSoundItem;
import net.acoyt.acornlib.api.item.ModelVaryingItem;
import net.acoyt.acornlib.api.util.MiscUtils;
import net.chemthunder.saxophone.api.extendable.SaxophoneItem;
import net.chemthunder.saxophone.core.Saxophone;
import net.chemthunder.saxophone.core.index.SaxoParticles;
import net.chemthunder.saxophone.core.index.SaxoSoundEvents;
import net.chemthunder.saxophone.core.util.ModUtils;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

/**
 * @author Chemthunder
 */
public class MartyrdomItem extends SaxophoneItem implements CustomHitSoundItem, CritEffectItem, ModelVaryingItem {
    public MartyrdomItem(Settings settings) {
        super(settings);
    }

    public static AttributeModifiersComponent createAttributeModifiers() {
        return AttributeModifiersComponent.builder()
                .add(
                        EntityAttributes.GENERIC_ATTACK_DAMAGE,
                        new EntityAttributeModifier(BASE_ATTACK_DAMAGE_MODIFIER_ID, 9.0f, EntityAttributeModifier.Operation.ADD_VALUE),
                        AttributeModifierSlot.MAINHAND
                )
                .add(
                        EntityAttributes.GENERIC_ATTACK_SPEED,
                        new EntityAttributeModifier(BASE_ATTACK_SPEED_MODIFIER_ID, -2.8f, EntityAttributeModifier.Operation.ADD_VALUE),
                        AttributeModifierSlot.MAINHAND
                )
                .build();
    }

    public void playHitSound(PlayerEntity playerEntity, Entity entity) {
        playerEntity.playSound(SaxoSoundEvents.MARTYRDOM_SWING, 1.0f, (float) (1.0f + playerEntity.getRandom().nextGaussian() / 10.0f));
    }

    public Identifier getModel(ModelTransformationMode modelTransformationMode, ItemStack itemStack, @Nullable LivingEntity livingEntity) {
        return MiscUtils.isGui(modelTransformationMode) ? Saxophone.id("martyrdom") : Saxophone.id("martyrdom_handheld");
    }

    public List<Identifier> getModelsToLoad() {
        return Arrays.asList(
                Saxophone.id("martyrdom"),
                Saxophone.id("martyrdom_handheld")
        );
    }

    public Text getName(ItemStack stack) {
        MutableText text = Text.translatable("item.saxophone.martyrdom");
        return super.getName(stack).copy().setStyle(ModUtils.nameEffect(text)).withColor(0xd70048);
    }

    public void critEffect(PlayerEntity playerEntity, ItemStack itemStack, Entity entity) {
        playerEntity.setVelocity(playerEntity.getRotationVec(0).multiply(0.3f));
        playerEntity.velocityModified = true;

        if (playerEntity instanceof ScreenShaker shaker) {
            shaker.addScreenShake(0.4f, 4);
        }
    }

    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return !miner.isCreative();
    }

    public void getPermakillEffect(PlayerEntity player, LivingEntity target, World world) {
        if (world instanceof ServerWorld serverWorld && !ModUtils.isAvarice(target)) {
            serverWorld.spawnParticles(ParticleTypes.SMOKE,
                    target.getX(),
                    target.getY() + 0.5f,
                    target.getZ(),
                    35,
                    0,
                    1,
                    0,
                    0.2f
            );

            serverWorld.spawnParticles(SaxoParticles.AVARICE_LIGHT,
                    target.getX(),
                    target.getY() + 0.5f,
                    target.getZ(),
                    35,
                    0,
                    1,
                    0,
                    0.2f
            );

            if (target instanceof PlayerEntity) {
                serverWorld.getPlayers().forEach(serverPlayer -> {
                    serverPlayer.sendMessage(Text.literal(target.getNameForScoreboard() + " was sent away for a greater cause"));
                });
            }

            target.setHealth(target.getMaxHealth());
            ModUtils.teleportToAsphodel(target);
        }
    }
}